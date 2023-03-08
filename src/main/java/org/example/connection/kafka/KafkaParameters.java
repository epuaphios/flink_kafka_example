package org.example.connection.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.example.connection.AppParameters;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;




public class KafkaParameters implements Serializable {
//    public static final String BOOTSTRAP_SERVERS = AppParameters.BOOTSTRAP_SERVERS;
//
////    public static class Topics {
////        public static final String MAIN_MUTATIONS = "enabiz-mutation_repeat2";
////    }
//
//    private Map<String, Object> kafkaParams = new HashMap<>();
//
//    private static long increment = 0;
//
//    private static String[] topics = {AppParameters.TOPIC_NAME};
//
//    private static String filePath = "kafkaOffsets.log";
//
//    public static List<String> kafkaTopicList = new ArrayList<>(Arrays.asList(topics));
//
//    public static String getKafkaBootstrap(){
//        return BOOTSTRAP_SERVERS;
//    }
//
//    public KafkaParameters(String consumerGroupId) {
//        kafkaParams.put("bootstrap.servers", BOOTSTRAP_SERVERS);
//        kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        kafkaParams.put("group.id", consumerGroupId);
//        kafkaParams.put("auto.offset.reset", "latest");
//        kafkaParams.put("enable.auto.commit", "false");
//        //kafkaParams.put("request.timeout.ms", "305000"); // session timeout + some
//        kafkaParams.put("max.partition.fetch.bytes", "5048576");
//        kafkaParams.put("session.timeout.ms", "20000");
//        kafkaParams.put("fetch.max.wait.ms", "20500");
//        kafkaParams.put("heartbeat.interval.ms", "6000");
//
//    }
//
//    public Map<String, Object> getKafkaParameters() {
//        return kafkaParams;
//    }
//
//    public static List<String> getKafkaTopicList() {
//        return kafkaTopicList;
//    }

//    public static HashMap<TopicPartition, Long> getOffsetsFromFile() {
//        Pattern numberPattern = Pattern.compile("[0-9]+");
//        HashMap<TopicPartition, Long> fromOffsets = new HashMap<>();
//        String topicName = topics[0];
//        Matcher match;
//        String line;
//        int[] messagesInfo = new int[3];
//
//        try {
////            long numberOfLines = Files.lines(Paths.get("filePath")).count();
////            InputStream inputStream = ClassLoader.getSystemClassLoader().getSystemResourceAsStream(filePath);
////            InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
////            BufferedReader br = new BufferedReader(streamReader);
//            while ((line = br.readLine()) != null) {
//                match = numberPattern.matcher(line);
//                int i = 0;
//                while (match.find()) {
//                    messagesInfo[i] = Integer.valueOf(match.group());
//                    i++;
//                }
//                fromOffsets.put(new TopicPartition(topicName, messagesInfo[0]), (long) messagesInfo[1] + increment);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        fromOffsets.forEach((k, v) -> System.out.println("key: " + k + " value: " + v));
//        return fromOffsets;
//
//    }
    public static int getNumberOfPartitionsInTopic(String topic) throws ExecutionException, InterruptedException {
        AdminClient admin = createAdminClient();
        DescribeTopicsResult describeTopicsResult = admin.describeTopics(Collections.singleton(topic));
        TopicDescription topicDescription = describeTopicsResult.values().get(topic).get();
        return topicDescription.partitions().size();
    }
    public static AdminClient createAdminClient(){
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, AppParameters.BOOTSTRAP_SERVERS);
        return AdminClient.create(config);
    }

}