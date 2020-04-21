package com.example.myapplication;

public class ItemList {


    private String mImage_url, product_name, price, id;

    public ItemList() {
    }

    public ItemList(String mImage_url, String product_name, String price, String id) {
        this.mImage_url = mImage_url;
        this.product_name = product_name;
        this.price = price;
        this.id = id;
    }

    public ItemList(String product_name, String price, String id) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
