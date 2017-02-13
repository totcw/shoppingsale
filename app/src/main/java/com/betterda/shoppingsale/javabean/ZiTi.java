package com.betterda.shoppingsale.javabean;

/**
 * 自提列表
 * Created by Administrator on 2016/12/29.
 */

public class ZiTi {
    private String barCode;
    private String barTime;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBarTime() {
        return barTime;
    }

    public void setBarTime(String barTime) {
        this.barTime = barTime;
    }

    @Override
    public String toString() {
        return "ZiTi{" +
                "barCode='" + barCode + '\'' +
                ", barTime='" + barTime + '\'' +
                '}';
    }
}
