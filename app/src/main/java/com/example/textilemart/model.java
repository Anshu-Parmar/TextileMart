package com.example.textilemart;

public class model {
    String Price, ProductName, Quantity,Userid,Ppurl;

    public model() {

    }


    public model(String price, String productName, String quantity, String userid, String ppurl ) {
        Price = price;
        ProductName = productName;
        Quantity = quantity;
        Userid=userid;
        Ppurl=ppurl;
//        Pimg=pimg;
    }

    public String getPrice() {
        return Price;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public void setPrice(String price) {
        Price = price;
    }


    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPpurl() {
        return Ppurl;
    }

    public void setPpurl(String ppurl) {
        Ppurl = ppurl;
    }

}
