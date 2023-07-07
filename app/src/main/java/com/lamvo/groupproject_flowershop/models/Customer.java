package com.lamvo.groupproject_flowershop.models;

public class Customer {
    private long id;
    private String customerName;
    private String email;
    private String avatar;
    private String phoneNumber;
    private String gender;
    private String address;

    public Customer(String customerName, String email, String avatar, String phoneNumber, String gender, String address) {
        this.customerName = customerName;
        this.email = email;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
    }

    public Customer(long id, String customerName, String email, String avatar, String phoneNumber, String gender, String address) {
        this.id = id;
        this.customerName = customerName;
        this.email = email;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
    }

    public Customer(String email) {
        this.email = email;
    }

    public Customer(String email, String name) {
        this.email = email;
        this.customerName = name;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
