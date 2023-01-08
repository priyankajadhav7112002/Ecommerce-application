package com.example.ecommerceapp.model;


import com.hishd.tinycart.model.Item;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Item, Serializable {
    private String id,name,image,description,price;
    private boolean show;
    private int stock;

    public Product() {

    }

    public Product(String name, String image, String description, String price, String id,int stock, boolean show) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.id = id;
        this.stock = stock;
        this.show = show;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String status) {
        this.description = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public BigDecimal getItemPrice() {
        return new BigDecimal(price);
    }

    @Override
    public String getItemName() {
        return name;
    }
}
