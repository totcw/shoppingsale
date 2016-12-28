package com.betterda.shoppingsale.order.model;




import java.util.List;

/**
 * Created by Administrator on 2016/5/27.
 */
public class OrderAll {
    private String id;//订单号
    private String time; //订单时间
    private String type; //交易状态
    private List<Bus> list;
    private String state;//状态
    private String refund_status;//退款状态
    private float amount;//订单金额
    private float freight;//运费
    private String way;//配送方式
    private String name;//收货人
    private String address;//收货地址
    private String number;//姓名
    private String voucher;//代金卷

    public float getFreight() {
        return freight;
    }

    public void setFreight(float freight) {
        this.freight = freight;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Bus> getList() {
        return list;
    }

    public void setList(List<Bus> list) {
        this.list = list;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }
}
