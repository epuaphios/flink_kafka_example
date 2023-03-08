package org.example.connection.scylla;

import com.datastax.driver.core.*;
import org.apache.kafka.common.TopicPartition;
import org.example.connection.AppParameters;
import org.example.connection.kafka.KafkaParameters;


import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ScyllaSessionBuild {

    private static Cluster cluster = null;

    private static Session sessionScylla = null;

    public ScyllaSessionBuild() {
        getCluster();
        getSession();
    }

    public static void getCluster() {
        if (cluster == null) {
            cluster = Cluster.builder().addContactPoints(ScyllaUtils.SCYLLA_MASTERS).withSocketOptions(new SocketOptions().setConnectTimeoutMillis(30000).setReadTimeoutMillis(30000).setTcpNoDelay(true)).withPort(ScyllaUtils.SCYLLA_PORT).build();
        }
    }

    public static Session getSession() {
        if (sessionScylla == null) {
            return sessionScylla = cluster.connect(ScyllaUtils.SCYLLA_KEYSPACES);
        }
        else {
            return sessionScylla;
        }
    }

//    public static void errorSys (String sysTakimNo, Long cas,String topic ,Integer partition ,Long offset,String e,Session sessionScylla) {
//        sessionScylla.execute("UPDATE "+ AppParameters.SCYLLA_KEYSPACES+".errorsys SET cas =  "+ cas +" , error = '"+e+"' where partition = " + partition + " and offset= "+offset+" and topic = '"+ topic +"' and systakipno = '"+ sysTakimNo+"'");
//    }


    public static HashMap<TopicPartition, Long> getLastCommittedOffsets(String topic, String appname){
        HashMap<TopicPartition,Long> fromOffsets = new HashMap<>();
            try {
                int ActualNumberOfPartitionsForTopic = KafkaParameters.getNumberOfPartitionsInTopic(topic);
                for (int partition=0; partition< ActualNumberOfPartitionsForTopic; partition++){
                    Long fromOffset = 0L;
                    ResultSet result = sessionScylla.execute("select offset from "+ AppParameters.SCYLLA_KEYSPACES+".offset_value where topic = '" + topic +"' and appname = '" + appname + "' and partition = " + partition+"");
                    if(!result.isExhausted()) {
                        Row row = result.one();
                        fromOffset = row.getLong("offset");
                    }
                    if (fromOffset == null) {
                        fromOffsets.put(new TopicPartition(topic,partition) ,fromOffset);
                    } else {
                        fromOffsets.put(new TopicPartition(topic, partition),fromOffset);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        return fromOffsets;
    }

    public static void closeScyllaSession() {
        if (cluster != null) {
            cluster.close();
            cluster = null;
        }
    }
}
