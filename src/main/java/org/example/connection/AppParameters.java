package org.example.connection;

public class AppParameters {
    public static String DATABASE_NAME="";
    public static String META_DATABASE_NAME="";

    public static String APP_NAME;
//    public static  String BOOTSTRAP_SERVERS = "test-cluster-kafka-external-bootstrap.kafka-strimzi:9094";
    public static  String BOOTSTRAP_SERVERS = "172.18.241.143:31064";
//    public static String  KUDU_MASTERS ="ubuny:7051,ubuny:7052,ubuny:7053";
    public static String  KUDU_MASTERS ="kudu-master-0.kudu-masters.apache-kudu:7051,kudu-master-1.kudu-masters.apache-kudu:7051,kudu-master-2.kudu-masters.apache-kudu:7051";
//
    public static String TOPIC_NAME;
    public static  String SCYLLA_MASTERS="172.18.241.170";
//    public static  String SCYLLA_MASTERS="simple-cluster-client.scylla";
//    public static  int SCYLLA_PORT=9042;
    public static  int SCYLLA_PORT=30003;
    public static String SCYLLA_KEYSPACES="catalog1";
    public static int DURATION_JOB=60;
    public static String JOB_BATCH_SIZE;


}
