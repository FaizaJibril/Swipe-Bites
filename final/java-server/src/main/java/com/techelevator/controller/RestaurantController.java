package com.techelevator.controller;

import com.techelevator.dao.RestaurantDao;
import com.techelevator.model.Restaurant;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin
public class RestaurantController extends BaseController{
    private final RestaurantDao restaurantDao;

    public RestaurantController(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    /*@PostMapping("")
    public void dislikeRestaurant(@PathVariable int id) {

    } */

    @GetMapping
    public List<Restaurant> getAllRestaurantById() {
        return restaurantDao.getAllRestaurant();
    }
    @GetMapping("/{id}")
    public Restaurant getAllRestaurantById(@PathVariable int id) {
        return restaurantDao.getRestaurantById(id);
    }
    @GetMapping("/cuisine/{cuisine}")
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

    @GetMapping("/{id}/like")
    @PreAuthorize("isAuthenticated()")
    public void likedRestaurant(@PathVariable int id, Principal principal) {
        User user = super.getUserFromPrincipal(principal);
    }
    @GetMapping("/{id}/disLiked")
    @PreAuthorize("isAuthenticated()")
    public void disLikedRestaurant(@PathVariable int id, Principal principal) {
        User user = super.getUserFromPrincipal(principal);


    }

    @GetMapping("/by-user-preference/{username}")
    public List<String> getRestaurantNamesByUserPreference(@PathVariable String username) {
        return restaurantDao.getRestaurantNamesByUserPreference(username);
    }

    //@GetMapping
   // public List<Restaurant> getRecommendedRestaurantsByCuisine() {
  //      return restaurantDao.getRestaurantNamesByUserPreference();
   // }



}


