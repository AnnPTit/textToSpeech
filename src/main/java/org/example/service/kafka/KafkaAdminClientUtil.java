package org.example.service.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class KafkaAdminClientUtil {
    private final AdminClient adminClient;

    public KafkaAdminClientUtil(AdminClient adminClient) {
        this.adminClient = adminClient;
    }

    public void createTopic(String topicName, int numPartitions, short replicationFactor) {
        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        adminClient.createTopics(Collections.singletonList(newTopic));
    }

    public Set<String> getAllTopics() {
        ListTopicsResult topicsResult = adminClient.listTopics();
        try {
            return topicsResult.names().get(); // Lấy danh sách tên các topic
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Không thể lấy danh sách topic", e);
        }
    }

    // Lấy danh sách các broker
    public Set<String> getAllBrokers() {
        DescribeClusterResult clusterResult = adminClient.describeCluster();
        try {
            return clusterResult.nodes().get().stream()
                    .map(node -> node.host() + ":" + node.port()) // Lấy host và port của từng broker
                    .collect(Collectors.toSet());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Không thể lấy danh sách broker", e);
        }
    }

    // Lấy Cluster ID
    public String getClusterId() {
        DescribeClusterResult clusterResult = adminClient.describeCluster();
        try {
            return clusterResult.clusterId().get(); // Lấy Cluster ID
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Không thể lấy Cluster ID", e);
        }
    }

    public void close() {
        adminClient.close();
    }
}
