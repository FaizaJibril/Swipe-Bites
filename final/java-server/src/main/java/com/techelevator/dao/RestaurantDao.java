package com.techelevator.dao;


import com.techelevator.model.Restaurant;
import java.util.List;

public interface RestaurantDao {
    List<Restaurant> getAllRestaurant();
    Restaurant getRestaurantById(int id);

    List<Restaurant> getRestaurantCuisine(String cuisine);

    void createRestaurant(Restaurant restaurant);
    void updateRestaurant(Restaurant restaurant);
    void deleteRestaurant(int id);

   List<String> getRestaurantNamesByUserPreference(String username);

    void insertPreference(String preferences);


    void likedRestaurant(long userId, int restaurantId);

    void disLikedRestaurant(long userId, int restaurantId);

    List<Restaurant> getLikedRestaurantsByUserId(int id);
}
