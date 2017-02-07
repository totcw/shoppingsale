package com.betterda.shoppingsale.javabean;

/**
 * 商品库存
 * Created by Administrator on 2017/2/7.
 */

public class Stock {
    private String productId;
    private String productName;
    private String salePrice;
    private String littlePicture;
    private String inventoryCount;//数量

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getLittlePicture() {
        return littlePicture;
    }

    public void setLittlePicture(String littlePicture) {
        this.littlePicture = littlePicture;
    }

    public String getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(String inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", salePrice='" + salePrice + '\'' +
                ", littlePicture='" + littlePicture + '\'' +
                ", inventoryCount='" + inventoryCount + '\'' +
                '}';
    }
}
