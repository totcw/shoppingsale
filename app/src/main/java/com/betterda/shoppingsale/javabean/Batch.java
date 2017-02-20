package com.betterda.shoppingsale.javabean;

/**
 * 入库
 * Created by Administrator on 2017/2/20.
 */

public class Batch {
    private String  batchCode;
    private String  receiveTime;

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "batchCode='" + batchCode + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                '}';
    }
}
