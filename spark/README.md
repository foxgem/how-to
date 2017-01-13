运行

- spark
  - gradle jar && spark-submit --class foxgem.WordCounter --master local ./build/libs/spark.jar
- spark streaming
  - nc -lk 7777
  - gradle jar && spark-submit --class foxgem.StreamingLinesFilter --master local[2] ./build/libs/spark.jar