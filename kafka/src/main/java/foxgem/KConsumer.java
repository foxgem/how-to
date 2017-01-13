package foxgem;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
import java.util.function.Consumer;

public class KConsumer {

    org.apache.kafka.clients.consumer.Consumer<String, String> consumer;
    String id;

    public KConsumer(String topic, String groupId, String id) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));

        this.id = id;
    }

    public void consume(Consumer<String> c) {
        ConsumerRecords<String, String> records = consumer.poll(50);
        records.forEach(record -> {
            c.accept(record.value());
        });
    }

    public void close() {
        consumer.close();
    }

}
