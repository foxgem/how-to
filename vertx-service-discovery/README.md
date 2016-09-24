# Vertx Service Discovery Example

compile: gradle shadowJar
execute: java -jar build/libs/vsd-0.0.1-fat.jar

Note:

- In order to use discovery service in vertx, you must create a clustered vertx instance.
- For simple load balance, try the following two steps:
  - get all records
  - get an instance from record list with a random index. For more details, check Consumer.java.
