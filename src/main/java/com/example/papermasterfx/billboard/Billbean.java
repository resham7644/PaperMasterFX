package com.example.papermasterfx.billboard;

public class Billbean {
    String mobile;
    String days;
    String amount;

    public Billbean(String mobile,String days, String amount) {
        this.days = days;
        this.mobile = mobile;
        this.amount = amount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
