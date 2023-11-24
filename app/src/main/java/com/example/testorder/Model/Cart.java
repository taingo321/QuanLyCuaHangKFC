package com.example.testorder.Model;

public class Cart {
    private String puid, pname, price, quantity;

    public Cart() {
    }

    public Cart(String puid, String pname, String price, String quantity) {
        this.puid = puid;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
