package org.learnbyexample;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class CoherenceClient {
    public static void main(String[] args) {
        try {
            System.setProperty("tangosol.coherence.cacheconfig", "coherence-operational-config.xml");
            NamedCache<String, String> cache = CacheFactory.getCache("dist-extend");
            System.out.println("Connected to Coherence cache server.");
            cache.put("key1", "value1");
            String value = cache.get("key1");
            System.out.println("Retrieved key1 -> " + value + " from the cache.");
        } catch (Exception e) {
            System.err.println("Error connecting to Coherence cache: " + e.getMessage());
            e.printStackTrace();
        } finally {
            CacheFactory.shutdown();
            System.out.println("Coherence client shut down.");
        }
    }
}


