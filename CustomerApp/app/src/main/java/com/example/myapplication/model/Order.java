package com.example.myapplication.model;

public class Order {
    private int id;
    private String name;
    private int quantity;
    private double total;
    private String category;
    private String date;
    private String address;
    private String status;

    public Order() {}

    public Order(int id, String name, int quantity, double total, String category, String date, String address, String status) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.total = total;
        this.category = category;
        this.date = date;
        this.address = address;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
