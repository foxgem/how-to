快速上手

1. 下载并解压
1. 启动ZK
   - bin/zookeeper-server-start.sh config/zookeeper.properties
1. 启动Kafka
   - bin/kafka-server-start.sh config/server.properties
1. 创建Topic
   - bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
1. 查看Topic
   - bin/kafka-topics.sh --list --zookeeper localhost:2181
1. 发送消息
   - bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
1. 接收消息
   - bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test

程序例子

运行：

~~~
gradle run
~~~

其中main中有三个函数：
- sendAndLeave()，producer例子
- startOne()，consumer例子
- startTwoInOneGroup()，consumer group的例子，注意这个例子中用到的topic有两个partition，即同一个group内consumer个数与partition数量一致，才能出现效果。