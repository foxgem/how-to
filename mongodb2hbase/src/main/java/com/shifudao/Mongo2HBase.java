package com.shifudao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.io.encoding.DataBlockEncoding;
import org.apache.hadoop.hbase.util.Bytes;
import org.bson.Document;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Mongo2HBase {
    public static void main(String[] args) throws MasterNotRunningException, IOException {
        Instant start = Instant.now();

        Configuration hbaseConfig = HBaseConfiguration.create();
        final ArrayList<String> ZOOKEEPER_LIST = new ArrayList<>();
        ZOOKEEPER_LIST.add("172.16.250.10");
        ZOOKEEPER_LIST.add("172.16.250.13");
        ZOOKEEPER_LIST.add("172.16.250.14");
        final String HBASE_CONFIGURATION_ZOOKEEPER_QUORUM = String.join(",", ZOOKEEPER_LIST);
        hbaseConfig.set("hbase.zookeeper.quorum", HBASE_CONFIGURATION_ZOOKEEPER_QUORUM);
        Connection hbaseConnection = ConnectionFactory.createConnection(hbaseConfig);
        Admin hbaseAdmin = hbaseConnection.getAdmin();

        if (! hbaseAdmin.tableExists(TableName.valueOf("aircareRealtimeDB"))) {
            HTableDescriptor aircareRealtimeDB = new HTableDescriptor(TableName.valueOf("aircareRealtimeDB"));
            HColumnDescriptor rtdata = new HColumnDescriptor("rtdata");
            rtdata.setCompressionType(Compression.Algorithm.SNAPPY);
            rtdata.setCompactionCompressionType(Compression.Algorithm.SNAPPY);
            rtdata.setDataBlockEncoding(DataBlockEncoding.PREFIX_TREE);
            HColumnDescriptor downsampling = new HColumnDescriptor("hourly");
            downsampling.setCompressionType(Compression.Algorithm.SNAPPY);
            downsampling.setCompactionCompressionType(Compression.Algorithm.SNAPPY);
            downsampling.setDataBlockEncoding(DataBlockEncoding.PREFIX_TREE);
            aircareRealtimeDB.addFamily(rtdata);
            aircareRealtimeDB.addFamily(downsampling);
            hbaseAdmin.createTable(aircareRealtimeDB);
        }

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("aircareRealtimeDB");
        MongoCollection<Document> collection = database.getCollection("equipment.rtdata");

        MongoCursor<Document> cursor = collection.find().iterator();
        final long TOTAL_COUNT = collection.count();

        try {
            Table aircareRealtimeDB = hbaseConnection.getTable(TableName.valueOf("aircareRealtimeDB"));
            ArrayList<Put> htablePuts = new ArrayList<>();

            for (long i = 0; cursor.hasNext(); ) {
                Document doc = cursor.next();
                doc.remove("_id");
                long src = doc.getInteger("src");
                long timestamp = doc.getLong("timestamp");
                long hourlyTimestamp = Math.floorDiv(timestamp, 3600000) * 3600000;
                int qualifier = (int)(Math.floorDiv(timestamp - hourlyTimestamp, 10000) * 10000);
                byte[] rowKey = Bytes.add(Bytes.toBytes(src), Bytes.toBytes(hourlyTimestamp));
                Put put = new Put(rowKey);
                put.addColumn(Bytes.toBytes("rtdata"),
                        Bytes.toBytes(qualifier),
                        timestamp,
                        Bytes.toBytes(JSON.serialize(doc)));
                htablePuts.add(put);
                i++;
                if (i % 10000 == 0) {
                    aircareRealtimeDB.put(htablePuts);
                    htablePuts.clear();
                    System.out.format("%d / %d, %.2f %%\r", i, TOTAL_COUNT, i*100.0/TOTAL_COUNT);
                }
            }

            if (htablePuts.size() != 0) {
                aircareRealtimeDB.put(htablePuts);
                htablePuts.clear();
                System.out.println(Long.toString(TOTAL_COUNT) + " / " + Long.toString(TOTAL_COUNT) + ", " + "100.00 %");
            }
        } finally {
            cursor.close();
            hbaseConnection.close();
        }

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Duration time: " + duration.getSeconds() + " s.");
    }
}
