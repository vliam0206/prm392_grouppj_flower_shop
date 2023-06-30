package com.lamvo.groupproject_flowershop.models;

import java.util.Date;

public class Order {
    private long id;
    private long customerId;
    private Date orderedDate;
    private Date shippedDate;
    private double total;
    private String orderStatus;

    public Order(long customerId, Date orderedDate, Date shippedDate, double total, String orderStatus) {
        this.customerId = customerId;
        this.orderedDate = orderedDate;
        this.shippedDate = shippedDate;
        this.total = total;
        this.orderStatus = orderStatus;
    }

    public Order(long id, long customerId, Date orderedDate, Date shippedDate, double total, String orderStatus) {
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

    public Date getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Date orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
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
