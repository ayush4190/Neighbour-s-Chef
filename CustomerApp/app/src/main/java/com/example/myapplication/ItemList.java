package com.example.myapplication;

public class ItemList {


    private String mImage_url, product_name;
    private Double price;
    private int id;

    public ItemList() {
    }

    public ItemList(String mImage_url, String product_name, Double price, int id) {
        this.mImage_url = mImage_url;
        this.product_name = product_name;
        this.price = price;
        this.id = id;
    }

    public ItemList(String product_name, Double price, int id) {
        this.product_name = product_name;
        this.price = price;
        this.id = id;
    }

    public String getmImage_url() {
        return mImage_url;
    }

    public void setmImage_url(String mImage_url) {
        this.mImage_url = mImage_url;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


}
