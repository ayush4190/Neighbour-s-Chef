package com.Neighbours.chefs.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.Neighbours.chefs.dbutilities.AppDatabase;
import com.Neighbours.chefs.model.CartItem;
import com.Neighbours.chefs.model.FoodDetails;
import com.Neighbours.chefs.services.repository.FoodRepository;

import java.util.List;

public class FoodDetailViewModel extends AndroidViewModel {

    private AppDatabase db;
    private LiveData<List<CartItem>> cartItemsLiveData;
    private LiveData<FoodDetails> foodDetailsLiveData;

    public FoodDetailViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        db = AppDatabase.getDatabase(getApplication().getApplicationContext());
        subscribeToCartChanges();
    }

    private void subscribeToCartChanges() {
        cartItemsLiveData = db.cartItemDao().getCartItems();
    }

    public void subscribeForFoodDetails(String name){
        foodDetailsLiveData = db.foodDetailsDao().getFood(name);
    }

    public LiveData<FoodDetails> getFoodDetailsLiveData(){
        return foodDetailsLiveData;
    }

    public LiveData<List<CartItem>> getCartItemsLiveData() {
        return cartItemsLiveData;
    }

    public void updateCart(FoodDetails foodDetails){
        FoodRepository.getInstance().updateCart(db,foodDetails);
        db.foodDetailsDao().save(foodDetails);
    }
}
