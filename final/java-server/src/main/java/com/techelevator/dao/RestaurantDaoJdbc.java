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
    public List<String> getRestaurantNamesByUserPreference(String username) {
        String sql = "SELECT r.name FROM restaurant r JOIN app_users u ON r.cuisine = u.preferences WHERE u.username = ?";
        return jdbcTemplate.queryForList(sql, String.class, username);
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
    public List<Restaurant> getRestaurantCuisine(String cuisine) {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurant WHERE cuisine = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cuisine);

        while (results.next()) {
            Restaurant restaurant = mapRowToRestaurant(results);
            restaurants.add(restaurant);
        }

        return restaurants;
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
    public void insertPreference(String preferences) {

    }


    //pass it into the front end, line 87-88 cusine preference passed in

    public List<Restaurant> getRecommendedRestaurantsByCuisine(long userId) {
        List<Restaurant> recommendedRestaurants = new ArrayList<>();

        // get the user's cuisine preference from the app_users table
        String cuisinePreferenceSql = "SELECT preferences FROM app_users WHERE user_id = ?";
        String cuisinePreference = jdbcTemplate.queryForObject(cuisinePreferenceSql, String.class, userId);

        // retrieve restaurants that match the user's cuisine preference
        String sql = "SELECT * FROM restaurant WHERE cuisine = ?";
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(sql, cuisinePreference);

        while (resultSet.next()) {
            recommendedRestaurants.add(mapRowToRestaurant(resultSet));
        }

        return recommendedRestaurants;
    }


    public List<Restaurant> getLikedRestaurantsByUserId(long userId) {
        List<Restaurant> likedRestaurants = new ArrayList<>();

        String likedRestaurantIdsSql = "SELECT restaurant_id FROM liked_restaurants WHERE user_id = ?";
        List<Integer> likedRestaurantIds = jdbcTemplate.queryForList(likedRestaurantIdsSql, Integer.class, userId);


        for (Integer restaurantId : likedRestaurantIds) {
            String restaurantSql = "SELECT * FROM restaurant WHERE id = ?";
            SqlRowSet resultSet = jdbcTemplate.queryForRowSet(restaurantSql, restaurantId);
            if (resultSet.next()) {
                likedRestaurants.add(mapRowToRestaurant(resultSet));
            }
        }

        return likedRestaurants;
    }

    public List<Restaurant> getDislikedRestaurantsByUserId(long userId) {
        List<Restaurant> dislikedRestaurants = new ArrayList<>();

        String dislikedRestaurantIdsSql = "SELECT restaurant_id FROM disliked_restaurants WHERE user_id = ?";
        List<Integer> dislikedRestaurantIds = jdbcTemplate.queryForList(dislikedRestaurantIdsSql, Integer.class, userId);

        // iterates through the disliked restaurant IDs and fetchs the restaurant details
        for (Integer restaurantId : dislikedRestaurantIds) {
            String restaurantSql = "SELECT * FROM restaurant WHERE id = ?";
            SqlRowSet resultSet = jdbcTemplate.queryForRowSet(restaurantSql, restaurantId);
            if (resultSet.next()) {
                dislikedRestaurants.add(mapRowToRestaurant(resultSet));
            }
        }

        return dislikedRestaurants;
    }

    @Override
    public void likedRestaurant(long userId, int restaurantId) {
        String sql = "INSERT INTO liked_restaurant (user_id, restaurant_id)" + " VALUES (?, ?)";
            jdbcTemplate.update(sql, userId, restaurantId);
    }
    @Override
    public void disLikedRestaurant(long userId, int restaurantId) {
        String sql = "INSERT INTO disliked_restaurant (user_id, restaurant_id)" + " VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, restaurantId);
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
