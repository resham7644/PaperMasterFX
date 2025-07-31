package com.example.papermasterfx.papertable;

public class PaperBean {
    String pname;
    String lang,price;

    public PaperBean(String pname, String lang, String price) {
        this.pname = pname;
        this.lang = lang;
        this.price = price;
    }

    public String getPname() {return pname;}

    public void setPname(String pname) {this.pname = pname;}

    public String getLang() {return lang;}

    public void setLang(String lang) {this.lang = lang;}

    public String getPrice() {return price;}

    public void setPrice(String price) {this.price = price;}
}
