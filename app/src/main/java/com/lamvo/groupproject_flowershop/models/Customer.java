package com.lamvo.groupproject_flowershop.models;

import com.lamvo.groupproject_flowershop.constants.AppConstants;

public class Customer {
    private long id;
    private String uid;
    private String customerName;
    private String email;
    private String avatar;
    private String phoneNumber;
    private String gender;
    private String address;


    public Customer(String email) {
        this.email = email;
    }
    public Customer(String email, String name) {
        this.customerName = name;
        this.email = email;
    }
    public Customer(String uid, String email, String name) {
        this.uid = uid;
        this.customerName = name;
        this.email = email;
        this.avatar = AppConstants.DEFAULT_AVATAR;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
