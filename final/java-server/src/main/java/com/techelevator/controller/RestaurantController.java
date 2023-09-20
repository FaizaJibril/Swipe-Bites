package com.techelevator.controller;

import com.techelevator.dao.RestaurantDao;
import com.techelevator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin
public class RestaurantController extends BaseController {
    private final RestaurantDao restaurantDao;


    public RestaurantController(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    @GetMapping
    @CrossOrigin
    public List<Restaurant> getAllRestaurantById() {
        return restaurantDao.getAllRestaurant();

    }

    @GetMapping("/{id}")
    @CrossOrigin
    public Restaurant getAllRestaurantById(@PathVariable int id) {
        return restaurantDao.getRestaurantById(id);
    }

    @GetMapping("/cuisine/{cuisine}")
    @CrossOrigin
    //CUISINE
    public List<Restaurant> getRestaurantCuisine(@PathVariable String cuisine) {
        return restaurantDao.getRestaurantCuisine(cuisine);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRestaurant(@RequestBody Restaurant restaurant) {
        restaurantDao.createRestaurant(restaurant);
    }

    @PutMapping("/{id}")
    public void updateRestaurant(@PathVariable int id, @RequestBody Restaurant restaurant) {
        restaurant.setId(id);
        restaurantDao.updateRestaurant(restaurant);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable int id) {
        restaurantDao.deleteRestaurant(id);
    }

    @PostMapping("/{id}/like")
    @PreAuthorize("isAuthenticated()")
    //Right SWIPE
    public void likedRestaurant(@PathVariable int id, Principal principal) {
        User user = super.getUserFromPrincipal(principal);
        restaurantDao.likedRestaurant((int)user.getId(),id);
    }

    @PostMapping("/{id}/disLiked")
    @PreAuthorize("isAuthenticated()")
    //Left SWIPE
    public void disLikedRestaurant(@PathVariable int id, Principal principal) {
        User user = super.getUserFromPrincipal(principal);
        restaurantDao.disLikedRestaurant((int)user.getId(),id);

    }

    @GetMapping("/{id}/liked")
    @CrossOrigin
    @PreAuthorize("isAuthenticated()")
    //LIST
    //GETTING LIKED RESTAURANTS TO OTHER SIDE
    public List<Restaurant> getLikedRestaurants(@PathVariable int id) {
        List<Restaurant> likedRestaurants = restaurantDao.getLikedRestaurantsByUserId(id);

        return likedRestaurants;
    }

    @GetMapping("/by-user-preference/{username}")
    public List<String> getRestaurantNamesByUserPreference(@PathVariable String username) {
        return restaurantDao.getRestaurantNamesByUserPreference(username);
    }

    @GetMapping("/recommendations/{userId}")
    public List<Restaurant> getRecommendedRestaurants(@PathVariable int userId, @RequestParam int limit) {
        List<Restaurant> recommendedRestaurants = restaurantDao.getRecommendedRestaurantsByLikes(userId, limit);
        return recommendedRestaurants;
    }

    @CrossOrigin
    @PostMapping("/restaurant-reviews/{restaurantId}")
    //reviews API
    public List<Review> getReviewsForRestaurant(@PathVariable int restaurantId) {
        return restaurantDao.getReviewsForRestaurant(restaurantId);
    }

    @PostMapping("/book-table")
    @CrossOrigin
    public void createBooking(@RequestBody BookingRequest bookingRequest) {
        int userId = bookingRequest.getUserId();
        int restaurantId = bookingRequest.getRestaurantId();
        Timestamp bookingDate = bookingRequest.getBookingDate();
        restaurantDao.createBooking(userId, restaurantId, bookingDate);

    }

    @DeleteMapping("/liked/{userId}/{restaurantId}")
    //This is to delete from list of restaurants
    public void deleteLikedRestaurant(@PathVariable int userId, @PathVariable int restaurantId) {
        restaurantDao.deleteLikedRestaurant(userId, restaurantId);
    }

    @DeleteMapping("/{userId}/refresh/{restaurantId}")
    //Refresh
    public void deletePreferenceRestaurant(@PathVariable int userId, @PathVariable int restaurantId) {
        restaurantDao.deleteLikedRestaurant(userId, restaurantId);
        restaurantDao.deleteDislikedRestaurant(userId, restaurantId);
    }


    @DeleteMapping("/refresh/{userId}/{restaurantId}")
    public void refreshRestaurants(@PathVariable int userId, @PathVariable int restaurantId) {
        restaurantDao.deleteLikedRestaurant(userId, restaurantId);
        restaurantDao.deleteDislikedRestaurant(userId, restaurantId);
    }

    @GetMapping("/restaurants/recommendations")
    public List<Restaurant> getRecommendations(@RequestParam int userId) {
        List<Restaurant> recommendations = restaurantDao.recommendations(userId);
  //this code returns a random restaurant recommendation from the list of recommendations in the dao
        if (!recommendations.isEmpty()) {

            Random random = new Random();
            int randomIndex = random.nextInt(recommendations.size());
            return Collections.singletonList(recommendations.get(randomIndex));
        } else {
            return null;

        }
    }
}

















