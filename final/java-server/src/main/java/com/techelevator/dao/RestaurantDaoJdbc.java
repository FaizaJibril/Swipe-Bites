package com.techelevator.dao;

import com.techelevator.model.Restaurant;
import com.techelevator.model.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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

    public List<Restaurant> getRecommendedRestaurantsByCuisine(int userId) {
        List<Restaurant> recommendedRestaurants = new ArrayList<>();

        // Get the user's cuisine preference from the app_users table
        String cuisinePreferenceSql = "SELECT preferences FROM app_users WHERE user_id = ?";
        String cuisinePreference = jdbcTemplate.queryForObject(cuisinePreferenceSql, String.class, userId);

        // Retrieve restaurants that match the user's cuisine preference and are not liked or disliked
        String sql = "SELECT * FROM restaurant WHERE cuisine = ? AND id NOT IN " +
                "(SELECT restaurant_id FROM liked_restaurants WHERE user_id = ?) " +
                "AND id NOT IN (SELECT restaurant_id FROM disliked_restaurants WHERE user_id = ?)";
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(sql, cuisinePreference, userId, userId);


        while (resultSet.next()) {
            recommendedRestaurants.add(mapRowToRestaurant(resultSet));
        }

        return recommendedRestaurants;
    }


    public List<Restaurant> getLikedRestaurantsByUserId(int userId) {
        List<Restaurant> likedRestaurants = new ArrayList<>();
        // RESTAURANT LIST TO OTHER SIDE
        String likedRestaurantIdsSql = "SELECT distinct r.* " +
                " FROM restaurant r " +
                " join liked_restaurants lr " +
                "   on lr.restaurant_id = r.restaurant_id " +
                " WHERE lr.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(likedRestaurantIdsSql,userId);
        while (results.next()){
            likedRestaurants.add(mapRowToRestaurant(results));
        }
        return likedRestaurants;
    }

    @Override
    public List<Restaurant> getRecommendedRestaurantsByLikes(int userId, int limit) {
        return null;
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


    public void likedRestaurant(int userId, int restaurantId) {
        String sql = "INSERT INTO liked_restaurants (user_id, restaurant_id)" + " VALUES (?, ?)";
            jdbcTemplate.update(sql, userId, restaurantId);

    }
    @Override
    public void disLikedRestaurant(long userId, int restaurantId) {
        String sql = "INSERT INTO disliked_restaurants (user_id, restaurant_id)" + " VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, restaurantId);
    }

    public List<Review> getReviewsForRestaurant(int restaurantId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "insert into review_restaurant (user_id, review, restaurant_id)" + " VALUES (?,?,?)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, restaurantId);

        while (results.next()) {
            int userId = results.getInt("user_id");
            int rating = results.getInt("rating");
            // You can create a Review object or use a simple data structure to represent a review
            Review review = new Review(userId, rating);
            reviews.add(review);
        }

        return reviews;
    }

    public void createBooking(int userId, int restaurantId, Timestamp bookingDate) {
        String sql = "INSERT INTO booking_table (user_id, restaurant_id, booking_date) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, restaurantId, bookingDate);
    }

    public void deleteLikedRestaurant(int userId, int restaurantId) {
        String sql = "DELETE FROM liked_restaurants where user_id = ? and restaurant_id = ?";
        jdbcTemplate.update(sql, userId, restaurantId);
    }

    @Override
    public void deleteDislikedRestaurant(int userId, int restaurantId) {

    }


    public List<Restaurant> recommendations (int userId, int restaurantId) {

        List <Restaurant> recommendations = new ArrayList<>();
        String sql = "SELECT * from liked_restaurants where restaurant_id not in (select restaurant_id from booking_table)";
return null;
    }


 /*   public Restaurant getRandomLikedRestaurantByUserId(int userId) {
        List<Restaurant> likedRestaurants = getLikedRestaurantsByUserId(userId);

        if (!likedRestaurants.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(likedRestaurants.size());
            return likedRestaurants.get(randomIndex);
        }

        return null;
    }
*/




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
