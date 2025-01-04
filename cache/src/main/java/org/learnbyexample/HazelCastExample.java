package org.learnbyexample;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class HazelCastExample {
    public static void main(String[] args) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress("localhost"); // Replace with your cluster address
        HazelcastInstance hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);
        IMap<String, String> map = hazelcastClient.getMap("my-distributed-map1");
        map.put("key1", "value1");
        System.out.println("Value for 'key1': " + map.get("key1"));
        hazelcastClient.shutdown();
    }
}
