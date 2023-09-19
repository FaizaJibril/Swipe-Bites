package com.techelevator.model;

import java.sql.Timestamp;

public class BookingRequest {
    private int userId;
    private int restaurantId;
    private Timestamp bookingDate;

    public BookingRequest(int userId, int restaurantId, Timestamp bookingDate) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.bookingDate = bookingDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
    public Timestamp getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }
}
