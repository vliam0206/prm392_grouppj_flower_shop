package com.lamvo.groupproject_flowershop.models;

import java.io.Serializable;

public class Flower implements Serializable {
    private long id;
    private String flowerName;
    private String description;
    private String imageUrl;
    private double unitPrice;
    private int unitInStock;
    public Flower(){
    }
    public Flower(String flowerName, String description, String imageUrl, double unitPrice, int unitInStock) {
        this.flowerName = flowerName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.unitPrice = unitPrice;
        this.unitInStock = unitInStock;
    }

    public Flower(long id, String flowerName, String description, String imageUrl, double unitPrice, int unitInStock) {
        this.id = id;
        this.flowerName = flowerName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.unitPrice = unitPrice;
        this.unitInStock = unitInStock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(int unitInStock) {
        this.unitInStock = unitInStock;
    }
}
