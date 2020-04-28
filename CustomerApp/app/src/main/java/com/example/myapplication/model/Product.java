package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String id;
    private String name;
    private String price;
    private String quantity;

    /**
     * Empty constructor for convenience
     */
    public Product() {}

    public Product(String id, String name, String price, String quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    private Product(Parcel parcel) {
        this(
          parcel.readString(),
          parcel.readString(),
          parcel.readString(),
          parcel.readString()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(quantity);
    }

    public static Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}