package com.example.papermasterfx.customerboard;

public class CustomerBean {
    String mobile,name,email,address,paper;

    public CustomerBean(String name, String mobile, String email, String address,String paper) {
        this.mobile = mobile;
        this.name = name;
        this.email = email;
        this.address = address;
        this.paper = paper;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
