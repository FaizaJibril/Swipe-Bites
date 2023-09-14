package com.techelevator.dao;

import com.techelevator.model.Restaurant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantDaoJdbc implements RestaurantDao{

    private JdbcTemplate jdbcTemplate;
    public RestaurantDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Restaurant> getAllRestaurant() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurant";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()) {
            restaurants.add(mapRowToRestaurant(results));
        }
            return restaurants;
        }


    @Override
    public Restaurant getRestaurantById(int id) {
        String sql = "SELECT * FROM restaurant WHERE id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);

        if (result.next()) {
            return mapRowToRestaurant(result);
        }
        return null;
    }

    @Override
    public void createRestaurant(Restaurant restaurant) {
        String sql = "INSERT INTO restaurant (name, cuisine, price_range, reviews, photo_url, address)" + " VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, restaurant.getName(), restaurant.getCuisine(), restaurant.getPriceRange(), restaurant.getPhotoUrl(), restaurant.getReviews(), restaurant.getAddress());


    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        String sql = "UPDATE restaurant SET name = ?, cuisine = ?, price_range = ?, reviews = ?, photo_url = ?, address = ? WHERE id = ?";
        jdbcTemplate.update(sql, restaurant.getName(), restaurant.getCuisine(), restaurant.getPriceRange(), restaurant.getPhotoUrl(), restaurant.getReviews(), restaurant.getAddress(), restaurant.getId());

    }

    @Override
    public void deleteRestaurant(int id) {
        String sql = "DELETE FROM restaurant WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    @Override
    public List<String> getRestaurantNamesByUserPreference(String username) {
        String sql = "SELECT r.name FROM restaurant r JOIN app_users u ON r.cuisine = u.preferences WHERE u.username = ?";
        return jdbcTemplate.queryForList(sql, String.class, username);
    }

    @Override
    public void insertPreference(String preferences) {

    }

    private Restaurant mapRowToRestaurant(SqlRowSet rowSet) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(rowSet.getInt("restaurant_id"));
        restaurant.setName(rowSet.getString("name"));
        restaurant.setCuisine(rowSet.getString("cuisine"));
        restaurant.setPriceRange(rowSet.getInt("price_range"));
        restaurant.setReviews(rowSet.getString("reviews"));
        restaurant.setPhotoUrl(rowSet.getString("photo_url"));
        restaurant.setAddress(rowSet.getString("address"));
        return restaurant;
    }

}
