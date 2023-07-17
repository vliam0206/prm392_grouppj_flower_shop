package com.lamvo.groupproject_flowershop.models;

import java.util.Date;

public class Order {
    private long id;
    private long customerId;
    private String orderedDate;
    private String shippedDate;
    private double total;
    private String orderStatus;

    public Order(long customerId, String orderedDate, String shippedDate, double total, String orderStatus) {
        this.customerId = customerId;
        this.orderedDate = orderedDate;
        this.shippedDate = shippedDate;
        this.total = total;
        this.orderStatus = orderStatus;
    }

    public Order(long id, long customerId, String orderedDate, String shippedDate, double total, String orderStatus) {
        this.id = id;
        this.customerId = customerId;
        this.orderedDate = orderedDate;
        this.shippedDate = shippedDate;
        this.total = total;
        this.orderStatus = orderStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(String shippedDate) {
        this.shippedDate = shippedDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
