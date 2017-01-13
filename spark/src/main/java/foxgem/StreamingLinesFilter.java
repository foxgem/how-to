package foxgem;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class StreamingLinesFilter {

    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf().setAppName("Streaming Lines Filter");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));
        JavaDStream<String> lines = jssc.socketTextStream("localhost", 7777);
        lines.filter(line -> line.contains("error")).print();

        jssc.start();
        jssc.awaitTermination();
    }

}
