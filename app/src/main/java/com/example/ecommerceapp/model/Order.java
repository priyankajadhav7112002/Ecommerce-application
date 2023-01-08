package com.example.ecommerceapp.model;

public class Order {
    private String orderNumber;
    private String customerName;
    private String customerPhoneNo;
    private String customerCityName;
    private String customerAddress;
    private String itemExpense;
    private String deliveryCharges;
    private String orderTrackingNo;
    private String courier;
    private String orderPlacingDate;
    private String orderStatus;

    public Order() {
    }

    public Order(String orderNumber, String customerName, String customerPhoneNo, String customerCityName, String customerAddress, String itemExpense, String deliveryCharges, String orderTrackingNo, String courier, String orderPlacingDate, String orderStatus) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.customerPhoneNo = customerPhoneNo;
        this.customerCityName = customerCityName;
        this.customerAddress = customerAddress;
        this.itemExpense = itemExpense;
        this.deliveryCharges = deliveryCharges;
        this.orderTrackingNo = orderTrackingNo;
        this.courier = courier;
        this.orderPlacingDate = orderPlacingDate;
        this.orderStatus = orderStatus;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNo() {
        return customerPhoneNo;
    }

    public void setCustomerPhoneNo(String customerPhoneNo) {
        this.customerPhoneNo = customerPhoneNo;
    }

    public String getCustomerCityName() {
        return customerCityName;
    }

    public void setCustomerCityName(String customerCityName) {
        this.customerCityName = customerCityName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getItemExpense() {
        return itemExpense;
    }

    public void setItemExpense(String itemExpense) {
        this.itemExpense = itemExpense;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getOrderTrackingNo() {
        return orderTrackingNo;
    }

    public void setOrderTrackingNo(String orderTrackingNo) {
        this.orderTrackingNo = orderTrackingNo;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getOrderPlacingDate() {
        return orderPlacingDate;
    }

    public void setOrderPlacingDate(String orderPlacingDate) {
        this.orderPlacingDate = orderPlacingDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
