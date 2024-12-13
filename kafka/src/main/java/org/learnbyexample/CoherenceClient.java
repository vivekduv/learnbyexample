package org.learnbyexample;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
public class CoherenceClient {




        public static void main(String[] args) {
            // Ensure Coherence is correctly set up and available
            try {

                //distributed-cache
                // Connect to the Coherence cluster
                NamedCache<String, String> cache = CacheFactory.getCache("dist-extend");

                // Basic cache operations
                System.out.println("Connected to Coherence cache server.");

                // Put a value in the cache
                //cache.put("key1", "value1");
              //  System.out.println("Added key1 -> value1 to the cache.");

                // Retrieve the value
                String value = cache.get("key1");
                System.out.println("Retrieved key1 -> " + value + " from the cache.");

                // Remove the value
              //  cache.remove("key1");
             //   System.out.println("Removed key1 from the cache.");
            } catch (Exception e) {
                System.err.println("Error connecting to Coherence cache: " + e.getMessage());
                e.printStackTrace();
            } finally {
                // Shut down the Coherence connection
                CacheFactory.shutdown();
                System.out.println("Coherence client shut down.");
            }
        }
    }


