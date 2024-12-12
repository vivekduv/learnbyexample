package org.learnbyexample;

import org.apache.kafka.common.protocol.types.Field;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class NsscCache {
    private Map<String, NsccCacheValue> nsccCacheMap;
    public NsscCache() {
        this.nsccCacheMap = new HashMap<>();
    }
    public Map<String, NsccCacheValue> getNsccCacheMap() {
        return nsccCacheMap;
    }
    public void setNsccCacheMap(Map<String, NsccCacheValue> nsccCacheMap) {
        this.nsccCacheMap = nsccCacheMap;
    }
}
class NsccCacheValue {
    private int nsscRealTimeQty;
    private Map<String, Integer> executionIdAndCorrespondingQtyMap;
    private Map<String, Integer> cancelledExecutionIdAndCorrespondingQtyMap;

    public Map<String, Integer> getCancelledExecutionIdAndCorrespondingQtyMap() {
        return cancelledExecutionIdAndCorrespondingQtyMap;
    }

    public void setCancelledExecutionIdAndCorrespondingQtyMap(Map<String, Integer> cancelledExecutionIdAndCorrespondingQtyMap) {
        this.cancelledExecutionIdAndCorrespondingQtyMap = cancelledExecutionIdAndCorrespondingQtyMap;
    }

    public int getNsscRealTimeQty() {
        return nsscRealTimeQty;
    }

    public void setNsscRealTimeQty(int nsscRealTimeQty) {
        this.nsscRealTimeQty = nsscRealTimeQty;
    }
    public Map<String, Integer> getExecutionIdAndCorrespondingQtyMap() {
        return executionIdAndCorrespondingQtyMap;
    }
    public void setExecutionIdAndCorrespondingQtyMap(Map<String, Integer> executionIdAndCorrespondingQtyMap) {
        this.executionIdAndCorrespondingQtyMap = executionIdAndCorrespondingQtyMap;
    }
    public NsccCacheValue() {
        this.executionIdAndCorrespondingQtyMap = new HashMap<>();
        this.cancelledExecutionIdAndCorrespondingQtyMap = new HashMap<>();
    }
    @Override
    public String toString() {
        return "NsccCacheValue{" +
                "nsscRtQty=" + nsscRealTimeQty +
                ", executionQtyMap=" + executionIdAndCorrespondingQtyMap +
                ", cancelledExecutionQtyMap=" + cancelledExecutionIdAndCorrespondingQtyMap +
                '}';
    }
    
}
class Execution {
    private String executionID;
    private String action;
    private String tradeDate;
    private String settleDate;
    private String managerCode;
    private String symbol;
    private String side;
    private int qty;

    // Constructor
    public Execution(String executionID, String action, String tradeDate, String settleDate,
                     String managerCode, String symbol, String side, int qty) {
        this.executionID = executionID;
        this.action = action;
        this.tradeDate = tradeDate;
        this.settleDate = settleDate;
        this.managerCode = managerCode;
        this.symbol = symbol;
        this.side = side;
        this.qty = qty;
    }

    // Getters and toString() for debugging
    public String getExecutionID() {
        return executionID;
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
                "executionID='" + executionID + '\'' +
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

public class NSCCFileReader {
    

    public static void runRecon(List<Execution> executions, Map<String, NsccCacheValue> nsccCacheMap) {
        for (Execution execution : executions) {

            if (nsccCacheMap.containsKey(execution.toKey())) {
                NsccCacheValue nsccCacheValue = nsccCacheMap.get(execution.toKey());
                if ("N".equalsIgnoreCase(execution.getAction())) {
                    if (nsccCacheValue.getExecutionIdAndCorrespondingQtyMap().containsKey(execution.getExecutionID())) {
                        System.out.println(execution.getExecutionID()+" is already present in the map so ignoring");
                        continue;
                    }
                    else
                    {
                        //Different execution
                        nsccCacheValue.getExecutionIdAndCorrespondingQtyMap().put(execution.getExecutionID(), execution.getQty());
                        int totalQty=nsccCacheValue.getNsscRealTimeQty()+execution.getQty();
                        nsccCacheValue.setNsscRealTimeQty(totalQty);
                    }
                }
                if ("C".equalsIgnoreCase(execution.getAction())) {
                    if (nsccCacheValue.getExecutionIdAndCorrespondingQtyMap().containsKey(execution.getExecutionID())) {
                        int totalQty=nsccCacheValue.getNsscRealTimeQty()-execution.getQty();
                        nsccCacheValue.setNsscRealTimeQty(totalQty);
                        nsccCacheValue.getExecutionIdAndCorrespondingQtyMap().remove(execution.getExecutionID());
                        nsccCacheValue.getCancelledExecutionIdAndCorrespondingQtyMap().put(execution.getExecutionID(), execution.getQty());

                    }
                }
                if ("A".equalsIgnoreCase(execution.getAction())) {
                    if (nsccCacheValue.getExecutionIdAndCorrespondingQtyMap().containsKey(execution.getExecutionID())) {
                        nsccCacheValue.getExecutionIdAndCorrespondingQtyMap().put(execution.getExecutionID(), execution.getQty());
                        int totalQty = 0;
                        for (int qty : nsccCacheValue.getExecutionIdAndCorrespondingQtyMap().values()) {
                            totalQty += qty;
                        }
                        nsccCacheValue.setNsscRealTimeQty(totalQty);
                    }
                }
            }
            else {
                //New Key - first time
                NsccCacheValue nsccReconResultData = new NsccCacheValue();
                if ("N".equalsIgnoreCase(execution.getAction())) {
                    
                    nsccReconResultData.setNsscRealTimeQty(execution.getQty());
                }
                nsccReconResultData.getExecutionIdAndCorrespondingQtyMap().put(execution.getExecutionID(), execution.getQty());
                nsccCacheMap.put(execution.toKey(), nsccReconResultData);
            }
        }
    }
    public static void main(String[] args) {
        String fileName = "/Users/vivek/IdeaProjects/learnbyexample/kafka/src/main/resources/nscc.txt";
        List<Execution> executions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // Read the header line

            while ((line = br.readLine()) != null) { // Continue reading from the second line
                String[] parts = line.split("\\|");

                // Extract values from the split data
                String executionID = parts[0];
                String action = parts[1];
                String tradeDate = parts[2];
                String settleDate = parts[3];
                String managerCode = parts[4];
                String symbol = parts[5];
                String side = parts[6];
                int qty = Integer.parseInt(parts[7]);

                // Create an Execution object and add it to the list
                Execution execution = new Execution(executionID, action, tradeDate, settleDate, managerCode, symbol, side, qty);
                executions.add(execution);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

       // NSCCFileReader nsccFileReader=new NSCCFileReader();
        NsscCache nsccCache=new NsscCache();

        runRecon(executions,nsccCache.getNsccCacheMap());
        //loop nsccCacheMap
        for (Map.Entry<String, NsccCacheValue> entry : nsccCache.getNsccCacheMap().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            System.out.println("[Key: " + key + "] ->" + "[Value: " + value + "]");

        }
    }
    
}