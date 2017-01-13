package foxgem;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class WordCounter {

    public static void main(String[] args) {
        String file = "/Users/foxgem/projects/how-to/spark/build.gradle";

        SparkConf conf = new SparkConf().setAppName("Words Counter");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> input = sc.textFile(file);
        JavaPairRDD<String, Integer> counts = input.flatMap(line -> Arrays.asList(line.split(" ")).iterator())
                .mapToPair(w -> new Tuple2<String, Integer>(w, 1))
                .reduceByKey((x, y) -> x + y);
        counts.saveAsTextFile("/Users/foxgem/projects/how-to/spark/out");
    }

}
