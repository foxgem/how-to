package foxgem;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.function.Consumer;

public class HBaseClientExamples {

    public static void main(String[] args) throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.addResource("hbase-site.xml");
        connect(config, connection -> {
            try {
                Admin admin = connection.getAdmin();
                if (tableExists(admin, "test")) {
                    log("@@@@ Table test exists, and here is the content ...");
                    scanTable(connection, "test");
                } else {
                    log("%%%% Table test does not exists, now create and initialize it.");
                    createTable(connection, "test", "cf1", "cf2");
                    putTable(connection, "test", "row1", "cf1", "p1", "v1-1-1");
                    putTable(connection, "test", "row1", "cf2", "p1", "v1-2-1");
                    putTable(connection, "test", "row2", "cf1", "p1", "v1-1-1");

                    log("%%%% Done!");
                    log("%%%% Please rerun this program again to check the data put.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void log(String message) {
        System.out.println(message);
    }

    private static void connect(Configuration conf, Consumer<Connection> handler) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(conf)) {
            handler.accept(connection);
        }
    }

    private static boolean tableExists(Admin admin, String name) throws IOException {
        return admin.tableExists(TableName.valueOf(name));
    }

    private static void scanTable(Connection connection, String name) throws IOException {
        TableName tableName = TableName.valueOf(name);
        HTableDescriptor hTableDescriptor = connection.getAdmin().getTableDescriptor(tableName);
        Scan scan = new Scan();
        hTableDescriptor.getFamilies().forEach(hColumnDescriptor -> scan.addFamily(hColumnDescriptor.getName()));

        Table table = connection.getTable(tableName);
        try (ResultScanner rs = table.getScanner(scan)) {
            rs.forEach(result -> {
                result.listCells().forEach(cell -> {
                    log(new String(CellUtil.cloneRow(cell)) + " => "
                            + new String(CellUtil.cloneFamily(cell)) + ":" + new String(CellUtil.cloneQualifier(cell)) + " = "
                            + new String(CellUtil.cloneValue(cell))
                    );
                });
            });
        }
    }

    private static void createTable(Connection connection, String name, String... columnFamily) throws IOException {
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(name));

        for (String cf : columnFamily) {
            hTableDescriptor.addFamily(new HColumnDescriptor(cf));
        }
        connection.getAdmin().createTable(hTableDescriptor);
    }

    private static void putTable(Connection connection, String name, String rowkey,
                                 String cf, String qualifier, String value) throws IOException {
        Put put = new Put(rowkey.getBytes());
        put.addColumn(cf.getBytes(), qualifier.getBytes(), value.getBytes());
        connection.getTable(TableName.valueOf(name)).put(put);
    }

}
