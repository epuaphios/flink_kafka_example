FROM flink:1.14.0-scala_2.12-java11

COPY target/flink-examples-0.0.1.jar /opt/flink/usrlib/my-job.jar

ENV JOB_CLASS_NAME=com.example.main

CMD ["/opt/flink/bin/flink", "run", "-c", "$JOB_CLASS_NAME", "/opt/flink/usrlib/my-job.jar"]
