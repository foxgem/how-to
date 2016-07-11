# Vert.x Circuit Breaker Example

This example shows how to use Circuit Breaker to protect invocations on external objects that may fail.

There are two classes in this project:

- MainVerticle, which will invoke operations on a MockSystem instance.
- MockSystem, which is a mock remote system. It has three operations:
  - succeed, which will never fail
  - fail, which will never succeed
  - maySucceed, which will return one of three results: success, failure and timeout.

To run this example, please try the following command:

~~~
gradle shadowJar && java -jar build/libs/circuitbreaker-0.1-fat.jar
~~~
