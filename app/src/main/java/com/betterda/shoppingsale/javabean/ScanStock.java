package com.betterda.shoppingsale.javabean;

/**
 * 扫码入库
 * Created by Administrator on 2017/2/7.
 */

public class ScanStock {
    private String productId; //商品id
    private String quantity;//入库的数量

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ScanStock{" +
                "productId='" + productId + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
