FROM openjdk:17
EXPOSE 8100
ADD target/currency-conversion-0.0.1-SNAPSHOT.jar currency-conversion-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","currency-conversion-0.0.1-SNAPSHOT.jar"]