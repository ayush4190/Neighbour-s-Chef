package com.example.myapplication.model;

import java.util.ArrayList;

public class ShoppingCartItems {
    private static ArrayList<Integer> foodsId;
    private static ShoppingCartItems instance = null;
    private int totalNumber;
    private int totalPrice;


    public ShoppingCartItems(int totalNumber, int totalPrice) {
        this.totalNumber = totalNumber;
        this.totalPrice = totalPrice;
    }

    public ShoppingCartItems() {
    }

    public ShoppingCartItems(ArrayList<Integer> foodsId,  int totalNumber, int totalPrice){
        this.foodsId = foodsId;
        this.totalNumber = totalNumber;
        this.totalPrice = totalPrice;
    }

    public void clear(){
        totalNumber = 0;
        totalPrice = 0;
        foodsId.clear();
    }

    public ArrayList<Integer> getFoodInCart(){
        return foodsId;
    }

    public void setNull(){
        instance = null;
    }














}

