package com.lamvo.groupproject_flowershop.models;

public class Customer {
    private long id;
    private String customerName;
    private String email;
    private String password;
    private String avatar;

    public Customer(String customerName, String email, String password, String avatar) {
        this.customerName = customerName;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public Customer(long id, String customerName, String email, String password, String avatar) {
        this.id = id;
        this.customerName = customerName;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
