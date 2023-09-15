package com.techelevator.dao;


import com.techelevator.model.Restaurant;
import java.util.List;

public interface RestaurantDao {
    List<Restaurant> getAllRestaurant();
    Restaurant getRestaurantById(int id);

    Restaurant getRestaurantCuisine(String cuisine);

    void createRestaurant(Restaurant restaurant);
    void updateRestaurant(Restaurant restaurant);
    void deleteRestaurant(int id);

   List<String> getRestaurantNamesByUserPreference(String username);

    void insertPreference(String preferences);



}
