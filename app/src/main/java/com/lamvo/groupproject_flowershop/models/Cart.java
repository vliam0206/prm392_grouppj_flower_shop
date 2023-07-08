package com.lamvo.groupproject_flowershop.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cart {
    @PrimaryKey
    private long id;
    private long idFlower;
    private long idCustomer;
    @ColumnInfo(name = "flower_name")
    private String flowerName;
    private String description;
    @ColumnInfo(name = "image_url")
    private String imageUrl;
    @ColumnInfo(name = "unit_price")
    private double unitPrice;
    private int quantity;

    public Cart(long id, String flowerName, String description, String imageUrl, double unitPrice, int quantity, long idFlower, long idCustomer) {
        this.id = id;
        this.flowerName = flowerName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.idFlower = idFlower;
        this.idCustomer = idCustomer;
    }

    public long getIdFlower() {
        return idFlower;
    }

    public long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public void setIdFlower(long idFlower) {
        this.idFlower = idFlower;
    }

    public long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}
