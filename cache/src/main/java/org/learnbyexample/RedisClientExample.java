package org.learnbyexample;

import redis.clients.jedis.Jedis;

public class RedisClientExample {
    public static void main(String[] args) {
        // Replace "localhost" and "6379" with your Redis server's address and port
        String redisHost = "10.0.0.77";
        int redisPort = 6379;

        // Connecting to Redis server
        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            System.out.println("Connected to Redis");

            // Set a key-value pair in Redis

           jedis.set("name1", "Redis1");
            System.out.println("Set key 'name1' to value 'Redis1'");

            // Retrieve the value of the key
            String value = jedis.get("name");
            System.out.println("Retrieved value for key 'name': " + value);

            // Increment a counter
            jedis.set("counter", "0");
            jedis.incr("counter");
            jedis.incr("counter");
            System.out.println("Counter value: " + jedis.get("counter"));

            // Delete a key
            //jedis.del("name");
            //System.out.println("Deleted key 'name'");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
