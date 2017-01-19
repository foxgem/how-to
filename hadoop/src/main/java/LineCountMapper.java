import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class LineCountMapper
        extends Mapper<Object, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private final static Text lines = new Text("lines");

    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException {
        context.write(lines, one);
    }
}