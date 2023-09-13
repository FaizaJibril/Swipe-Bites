package com.techelevator.controller;

import com.techelevator.dao.RestaurantDao;
import com.techelevator.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")

public class RestaurantController {
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

}
