package org.learnbyexample;

import redis.clients.jedis.Jedis;

public class RedisClientExample {
    public static void main(String[] args) {
        String redisHost = "localhost";
        int redisPort = 6379;

        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            System.out.println("Connected to Redis");
            jedis.set("name1", "Redis1");
            System.out.println("Set key 'name1' to value 'Redis1'");

            String value = jedis.get("name1");
            System.out.println("Retrieved value for key 'name1': " + value);
            jedis.set("counter", "0");
            jedis.incr("counter");
            jedis.incr("counter");
            System.out.println("Counter value: " + jedis.get("counter"));

            //jedis.del("name");
            //System.out.println("Deleted key 'name'");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
