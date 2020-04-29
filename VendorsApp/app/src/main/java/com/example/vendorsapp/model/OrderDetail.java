package com.example.vendorsapp.model;

public class OrderDetail {

    private String itemName , quantity , total_price , address ,phone_no , date , name ;

    public OrderDetail() {
    }

    public OrderDetail(String itemName, String quantity, String total_price, String address, String phone_no, String date, String name) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.total_price = total_price;
        this.address = address;
        this.phone_no = phone_no;
        this.date = date;
        this.name = name;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
