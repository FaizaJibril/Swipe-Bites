package com.techelevator.dao;


import com.techelevator.model.Restaurant;
import com.techelevator.model.Review;

import java.sql.Timestamp;
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
    void likedRestaurant(int userId, int restaurantId);

    void disLikedRestaurant(long userId, int restaurantId);

    List<Restaurant> getLikedRestaurantsByUserId(int id);

    List<Restaurant> getRecommendedRestaurantsByLikes(int userId, int limit);

    List<Review> getReviewsForRestaurant(int restaurantId);

    void createBooking(int userId, int restaurantId, Timestamp bookingDate);

    void deleteLikedRestaurant(int userId, int restaurantId);

    void deleteDislikedRestaurant(int userId, int restaurantId);


    List<Restaurant> recommendations(int userId);
}
