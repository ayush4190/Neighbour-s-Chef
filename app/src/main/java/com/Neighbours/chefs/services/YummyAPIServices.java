package com.Neighbours.chefs.services;

import com.Neighbours.chefs.model.FoodDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface YummyAPIServices {

    @GET("/FoodDetails.json")
    Call<List<FoodDetails>> getFoodData();
}
