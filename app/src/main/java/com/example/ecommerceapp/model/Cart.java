package com.example.ecommerceapp.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Cart implements Serializable {
    private String cartId;
    private String productName;
    private String productImage;
    private String productPrice;
    private int productQty;
    private int productStock;
    private String sellerUid;
    public static double totalPrice;
    private String orderNumber;

    public Cart() {
    }

    public Cart(String cartId, String productName, String productImage, String productPrice, int productQty,int productStock, String sellerUid ,String orderNumber) {
        this.cartId = cartId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productQty = productQty;
        this.productStock = productStock;
        this.sellerUid = sellerUid;
        this.orderNumber = orderNumber;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public String getSellerUid() {
        return sellerUid;
    }

    public void setSellerUid(String sellerUid) {
        this.sellerUid = sellerUid;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
