package org.learnbyexample;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class HazelCastExample {
    public static void main(String[] args) {
        // Configure the Hazelcast Client
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress("localhost"); // Replace with your cluster address

        // Create a Hazelcast Client Instance
        HazelcastInstance hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);

        // Access a distributed map
        IMap<String, String> map = hazelcastClient.getMap("my-distributed-map");

        // Perform operations
       // map.put("key", "valuexxxxx");
        System.out.println("Value for 'key': " + map.get("key"));

        // Shutdown the client
        hazelcastClient.shutdown();
    }
}
