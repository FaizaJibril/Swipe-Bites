package com.techelevator.model;

public class Review {
        private int userId;
        private int rating;

        public Review(int userId, int rating) {
            this.userId = userId;
            this.rating = rating;
        }


        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }
    }

