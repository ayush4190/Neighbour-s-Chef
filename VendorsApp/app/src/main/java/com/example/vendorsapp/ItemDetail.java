package com.example.vendorsapp;

import android.widget.ImageView;

public class ItemDetail {
    private  String product_name ,product_price , product_quantity , product_id ;


    public ItemDetail() {
    }

    public ItemDetail(String product_name, String product_price, String product_quantity, String product_id) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_quantity = product_quantity;
        this.product_id = product_id;

    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

//    public String getImage_url() {
//        return image_url;
//    }

//    public void setImage_url(String image_url) {
//        this.image_url = image_url;
//    }
}
