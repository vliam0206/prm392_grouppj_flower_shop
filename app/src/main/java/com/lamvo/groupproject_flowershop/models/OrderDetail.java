package com.lamvo.groupproject_flowershop.models;

public class OrderDetail {
    private long id;
    private long orderId;
    private long flowerId;
    private double unitPrice;
    private int quantity;

    public OrderDetail(long orderId, long flowerId, double unitPrice, int quantity) {
        this.orderId = orderId;
        this.flowerId = flowerId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public OrderDetail(long id, long orderId, long flowerId, double unitPrice, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.flowerId = flowerId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(long flowerId) {
        this.flowerId = flowerId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
