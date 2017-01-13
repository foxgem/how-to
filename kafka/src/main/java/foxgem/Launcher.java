package foxgem;

import java.io.IOException;
import java.util.Date;

public class Launcher {


    public static void main(String[] args) throws IOException {
//        sendAndLeave();
//        startOne();
//        startTwoInOneGroup();
    }

    private static void sendAndLeave() {
        KProducer producer = new KProducer();

        producer.send("test", String.format("Hello, today is %tF", new Date()));
        producer.send("test", String.format("And time is %tT ", new Date()));
        producer.send("test", "Have a good day, bye!");

        producer.close();
    }

    private static void startOneConsumer(String topic, String consumerId) {
        KConsumer consumer = new KConsumer(topic, "group.test", consumerId);
        new Thread(() -> {
            System.out.println(String.format("%s is stared ...", consumerId));
            while (true) {
                consumer.consume(message -> System.out.println(String.format("%s receives %s", consumer.id, message)));
                try {
                    Thread.sleep(Double.valueOf(Math.random() * 100).intValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void startOne() {
        startOneConsumer("test", "consumer");
    }

    private static void startTwoInOneGroup() {
        startOneConsumer("test.cluster", "group.1");
        startOneConsumer("test.cluster", "group.2");
    }
}
