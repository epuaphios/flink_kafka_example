FROM flink:1.16.0-scala_2.12-java11

COPY target/flink-examples-0.0.1-jar-with-dependencies.jar /opt/flink/usrlib/flink-examples-0.0.1-jar-with-dependencies.jar

ENV JOB_CLASS_NAME=org.example.main

CMD ["/opt/flink/bin/flink", "run", "-c", "$JOB_CLASS_NAME", "/opt/flink/usrlib/flink-examples-0.0.1-jar-with-dependencies.jar"]
