package org.learnbyexample;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ReconCache {
    private Map<String, CacheValue> reconCacheMap;
    public ReconCache() {
        this.reconCacheMap = new HashMap<>();
    }
    public Map<String, CacheValue> getReconCacheMap() {
        return reconCacheMap;
    }

}
class ReconResult {
    String reconId;
    String key;
    int execQty;
    int allocQty;
    int diffQty;
    private String statusText;
    public String getStatusText() {
        return statusText;
    }
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public ReconResult(String reconId, String key, int execQty, int allocQty, int diffQty) {
        this.reconId = reconId;
        this.key = key;
        this.execQty = execQty;
        this.allocQty = allocQty;
        this.diffQty = diffQty;
    }
    public ReconResult(String reconId, String key, int execQty, int allocQty) {
        this.reconId = reconId;
        this.key = key;
        this.execQty = execQty;
        this.allocQty = allocQty;
        this.diffQty=allocQty-execQty;
        if (diffQty<0) {
            statusText="Long on Street";
        }
        else if (diffQty>0) {
            statusText="Short on Street";
        }
        else {
            statusText="Match!!!";
        }
    }

    @Override
    public String toString() {
        return "ReconResult{" +
                "reconId='" + reconId + '\'' +
                ", key='" + key + '\'' +
                ", execQty=" + execQty +
                ", allocQty=" + allocQty +
                ", diffQty=" + diffQty +
                ", status=" + statusText+
                '}';
    }
}
class CacheValue {
    private int executionQuantity;
    private int allocationQuantity;

    private Map<String, Integer> execIdAndCorrespondingQtyMap=new HashMap<>();
    private Map<String, Integer> cxlExecIdAndCorrespondingQtyMap=new HashMap<>();;
    private Map<String, Integer> allocIdAndCorrespondingQtyMap=new HashMap<>();;
    private Map<String, Integer> cxlAllocIdAndCorrespondingQtyMap=new HashMap<>();;


    public int getExecutionQuantity() {
        return executionQuantity;
    }

    public void setExecutionQuantity(int executionQuantity) {
        this.executionQuantity = executionQuantity;
    }

    public int getAllocationQuantity() {
        return allocationQuantity;
    }

    public void setAllocationQuantity(int allocationQuantity) {
        this.allocationQuantity = allocationQuantity;
    }

    public Map<String, Integer> getExecIdAndCorrespondingQtyMap() {
        return execIdAndCorrespondingQtyMap;
    }

    public void setExecIdAndCorrespondingQtyMap(Map<String, Integer> execIdAndCorrespondingQtyMap) {
        this.execIdAndCorrespondingQtyMap = execIdAndCorrespondingQtyMap;
    }

    public Map<String, Integer> getCxlExecIdAndCorrespondingQtyMap() {
        return cxlExecIdAndCorrespondingQtyMap;
    }

    public void setCxlExecIdAndCorrespondingQtyMap(Map<String, Integer> cxlExecIdAndCorrespondingQtyMap) {
        this.cxlExecIdAndCorrespondingQtyMap = cxlExecIdAndCorrespondingQtyMap;
    }

    public Map<String, Integer> getAllocIdAndCorrespondingQtyMap() {
        return allocIdAndCorrespondingQtyMap;
    }

    public void setAllocIdAndCorrespondingQtyMap(Map<String, Integer> allocIdAndCorrespondingQtyMap) {
        this.allocIdAndCorrespondingQtyMap = allocIdAndCorrespondingQtyMap;
    }

    public Map<String, Integer> getCxlAllocIdAndCorrespondingQtyMap() {
        return cxlAllocIdAndCorrespondingQtyMap;
    }

    public void setCxlAllocIdAndCorrespondingQtyMap(Map<String, Integer> cxlAllocIdAndCorrespondingQtyMap) {
        this.cxlAllocIdAndCorrespondingQtyMap = cxlAllocIdAndCorrespondingQtyMap;
    }

    @Override
    public String toString() {
        return "CacheValue{" +
                "executionQuantity=" + executionQuantity +
                ", allocationQuantity=" + allocationQuantity +
                ", execIdAndCorrespondingQtyMap=" + execIdAndCorrespondingQtyMap +
                ", cxlExecIdAndCorrespondingQtyMap=" + cxlExecIdAndCorrespondingQtyMap +
                ", allocIdAndCorrespondingQtyMap=" + allocIdAndCorrespondingQtyMap +
                ", cxlAllocIdAndCorrespondingQtyMap=" + cxlAllocIdAndCorrespondingQtyMap +
                '}';
    }
}
class Trade {
    private String tradeID;
    private String action;
    private String tradeDate;
    private String settleDate;
    private String managerCode;
    private String symbol;
    private String side;
    private int qty;

    // Constructor
    public Trade(String executionID, String action, String tradeDate, String settleDate,
                 String managerCode, String symbol, String side, int qty) {
        this.tradeID = executionID;
        this.action = action;
        this.tradeDate = tradeDate;
        this.settleDate = settleDate;
        this.managerCode = managerCode;
        this.symbol = symbol;
        this.side = side;
        this.qty = qty;
    }

    // Getters and toString() for debugging
    public String getTradeID() {
        return tradeID;
    }

    public String getAction() {
        return action;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public String getManagerCode() {
        return managerCode;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSide() {
        return side;
    }

    public int getQty() {
        return qty;
    }

    @Override
    public String toString() {
        return "Execution{" +
                "executionID='" + tradeID + '\'' +
                ", action='" + action + '\'' +
                ", tradeDate='" + tradeDate + '\'' +
                ", settleDate='" + settleDate + '\'' +
                ", managerCode='" + managerCode + '\'' +
                ", symbol='" + symbol + '\'' +
                ", side='" + side + '\'' +
                ", qty=" + qty +
                '}';
    }

    public String toKey() {

        return symbol +
                "|" +
                tradeDate +
                "|" +
                settleDate +
                "|" +
                managerCode +
                "|" +
                side;
    }
}

public class ReconPOC {
    

    public static void loadCacheAndCalculateExecutionPositions(List<Trade> executions, Map<String, CacheValue> cacheMap) {
        for (Trade execution : executions) {

            if (cacheMap.containsKey(execution.toKey())) {
                CacheValue currentExecution = cacheMap.get(execution.toKey());
                if ("N".equalsIgnoreCase(execution.getAction())) {
                    if (currentExecution.getExecIdAndCorrespondingQtyMap().containsKey(execution.getTradeID())) {
                        System.out.println(execution.getTradeID()+" is already present in the map so ignoring");
                        continue;
                    }
                    else
                    {
                        //Different execution
                        currentExecution.getExecIdAndCorrespondingQtyMap().put(execution.getTradeID(), execution.getQty());
                        int totalQty=currentExecution.getExecutionQuantity()+execution.getQty();
                        currentExecution.setExecutionQuantity(totalQty);
                    }
                }
                if ("C".equalsIgnoreCase(execution.getAction())) {
                    if (currentExecution.getExecIdAndCorrespondingQtyMap().containsKey(execution.getTradeID())) {
                        int totalQty=currentExecution.getExecutionQuantity()-execution.getQty();
                        currentExecution.setExecutionQuantity(totalQty);
                        currentExecution.getExecIdAndCorrespondingQtyMap().remove(execution.getTradeID());
                        currentExecution.getCxlExecIdAndCorrespondingQtyMap().put(execution.getTradeID(), execution.getQty());

                    }
                }
                if ("A".equalsIgnoreCase(execution.getAction())) {
                    if (currentExecution.getExecIdAndCorrespondingQtyMap().containsKey(execution.getTradeID())) {
                        currentExecution.getExecIdAndCorrespondingQtyMap().put(execution.getTradeID(), execution.getQty());
                        int totalQty = 0;
                        for (int qty : currentExecution.getExecIdAndCorrespondingQtyMap().values()) {
                            totalQty += qty;
                        }
                        currentExecution.setExecutionQuantity(totalQty);
                    }
                }
            }
            else {
                //New Key - first time
                CacheValue currentExecution = new CacheValue();
                if ("N".equalsIgnoreCase(execution.getAction())) {
                    
                    currentExecution.setExecutionQuantity(execution.getQty());
                }
                currentExecution.getExecIdAndCorrespondingQtyMap().put(execution.getTradeID(), execution.getQty());
                cacheMap.put(execution.toKey(), currentExecution);
            }
        }
    }
    public static void loadCacheAndCalculateAllocPositions(List<Trade> allocs, Map<String, CacheValue> cacheMap) {
        for (Trade alloc : allocs) {

            if (cacheMap.containsKey(alloc.toKey())) {
                CacheValue currentAlloc = cacheMap.get(alloc.toKey());
                if ("N".equalsIgnoreCase(alloc.getAction())) {
                    if (currentAlloc.getAllocIdAndCorrespondingQtyMap().containsKey(alloc.getTradeID())) {
                        System.out.println(alloc.getTradeID()+" is already present in the map so ignoring");
                        continue;
                    }
                    else
                    {
                        //Different alloc
                        currentAlloc.getAllocIdAndCorrespondingQtyMap().put(alloc.getTradeID(), alloc.getQty());
                        int totalQty=currentAlloc.getAllocationQuantity()+alloc.getQty();
                        currentAlloc.setAllocationQuantity(totalQty);
                    }
                }
                if ("C".equalsIgnoreCase(alloc.getAction())) {
                    if (currentAlloc.getAllocIdAndCorrespondingQtyMap().containsKey(alloc.getTradeID())) {
                        int totalQty=currentAlloc.getAllocationQuantity()-alloc.getQty();
                        currentAlloc.setAllocationQuantity(totalQty);
                        currentAlloc.getAllocIdAndCorrespondingQtyMap().remove(alloc.getTradeID());
                        currentAlloc.getCxlAllocIdAndCorrespondingQtyMap().put(alloc.getTradeID(), alloc.getQty());

                    }
                }
                if ("A".equalsIgnoreCase(alloc.getAction())) {
                    if (currentAlloc.getAllocIdAndCorrespondingQtyMap().containsKey(alloc.getTradeID())) {
                        currentAlloc.getAllocIdAndCorrespondingQtyMap().put(alloc.getTradeID(), alloc.getQty());
                        int totalQty = 0;
                        for (int qty : currentAlloc.getAllocIdAndCorrespondingQtyMap().values()) {
                            totalQty += qty;
                        }
                        currentAlloc.setAllocationQuantity(totalQty);
                    }
                }
            }
            else {
                //New Key - first time
                CacheValue currentAlloc = new CacheValue();
                if ("N".equalsIgnoreCase(alloc.getAction())) {

                    currentAlloc.setAllocationQuantity(alloc.getQty());
                }
                currentAlloc.getAllocIdAndCorrespondingQtyMap().put(alloc.getTradeID(), alloc.getQty());
                cacheMap.put(alloc.toKey(), currentAlloc);
            }
        }
    }

    public static void main(String[] args) {
        String nsccFile = "/Users/vivek/IdeaProjects/learnbyexample/kafka/src/main/resources/nscc.txt";
        String allocFile = "/Users/vivek/IdeaProjects/learnbyexample/kafka/src/main/resources/alloc.txt";

        List<Trade> executions = getTrades(nsccFile);
      List<Trade> allocations = getTrades(allocFile);


        ReconCache dbReconCache=new ReconCache();
        ReconCache realTimeReconCache=new ReconCache();

        loadCacheAndCalculateExecutionPositions(executions,dbReconCache.getReconCacheMap());
        loadCacheAndCalculateAllocPositions(allocations,dbReconCache.getReconCacheMap());

        List<ReconResult> reconResults=new ArrayList<>();
        for (Map.Entry<String, CacheValue> entry : dbReconCache.getReconCacheMap().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();

            ReconResult reconResult=new ReconResult("",
                    entry.getKey(),
                    entry.getValue().getExecutionQuantity(),
                    entry.getValue().getAllocationQuantity()
            );

            System.out.println("reconResult: "+reconResult);

        }

    }

    private static List<Trade> getTrades(String fileName) {
        List<Trade> executions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // Read the header line

            while ((line = br.readLine()) != null) { // Continue reading from the second line
                String[] parts = line.split("\\|");

                // Extract values from the split data
                String id = parts[0];
                String action = parts[1];
                String tradeDate = parts[2];
                String settleDate = parts[3];
                String managerCode = parts[4];
                String symbol = parts[5];
                String side = parts[6];
                int qty = Integer.parseInt(parts[7]);

                // Create an Execution object and add it to the list
                Trade execution = new Trade(id, action, tradeDate, settleDate, managerCode, symbol, side, qty);
                executions.add(execution);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return executions;
    }

}