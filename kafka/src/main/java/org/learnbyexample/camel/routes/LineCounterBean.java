package org.learnbyexample.camel.routes;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class LineCounterBean {
    
    private final Map<String, AtomicInteger> fileCounters = new ConcurrentHashMap<>();
    private final Map<String, Integer> startLines = new ConcurrentHashMap<>();
    
    public void initializeFile(String fileName, int startLine) {
        fileCounters.put(fileName, new AtomicInteger(0));
        startLines.put(fileName, startLine);
    }
    
    public int incrementAndGet(String fileName) {
        int f= fileCounters.get(fileName).incrementAndGet();
        fileCounters.put(fileName, new AtomicInteger(f));
        return f;
    }
    
    public int getCurrentLine(String fileName) {
        return fileCounters.getOrDefault(fileName, new AtomicInteger(0)).get();
    }
    
    public boolean shouldSkip(String fileName) {
        int currentLine = getCurrentLine(fileName);
        int startLine = startLines.getOrDefault(fileName, 0);
        return currentLine <= startLine;
    }
    
    public void cleanup(String fileName) {
        fileCounters.remove(fileName);
        startLines.remove(fileName);
    }
}