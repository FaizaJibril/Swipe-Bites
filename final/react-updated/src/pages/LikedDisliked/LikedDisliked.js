// LikedDislikedRestaurants.js
import React, { useState } from 'react';
import './LikedDisliked.css'; // Import the CSS file
import { Link } from 'react-router-dom';

function LikedDisliked() {
  const [likedRestaurants, setLikedRestaurants] = useState([]);
  const [dislikedRestaurants, setDislikedRestaurants] = useState([]);

  // Function to add a restaurant to the liked list
  const addToLiked = (restaurant) => {
    setLikedRestaurants([...likedRestaurants, restaurant]);
  };

  // Function to add a restaurant to the disliked list
  const addToDisliked = (restaurant) => {
    setDislikedRestaurants([...dislikedRestaurants, restaurant]);
  };

  // Function to remove a restaurant from the liked list
  const removeFromLiked = (restaurant) => {
    const updatedLiked = likedRestaurants.filter((r) => r.id !== restaurant.id);
    setLikedRestaurants(updatedLiked);
  };

  // Function to remove a restaurant from the disliked list
  const removeFromDisliked = (restaurant) => {
    const updatedDisliked = dislikedRestaurants.filter((r) => r.id !== restaurant.id);
    setDislikedRestaurants(updatedDisliked);
  };

  return (
    <div className="container">
      <h1 className="page-title">Liked and Disliked Restaurants</h1>

      <div className="restaurant-list">
        <div className="list">
          <h2>Liked Restaurants</h2>
          <ul>
            {likedRestaurants.map((restaurant) => (
              <li key={restaurant.id}>
                <span className="restaurant-name">{restaurant.name}</span>
                <button className="remove-button" onClick={() => removeFromLiked(restaurant)}>
                  Remove
                </button>
              </li>
            ))}
          </ul>
        </div>

        <div className="list">
          <h2>Disliked Restaurants</h2>
          <ul>
            {dislikedRestaurants.map((restaurant) => (
              <li key={restaurant.id}>
                <span className="restaurant-name">{restaurant.name}</span>
                <button className="remove-button" onClick={() => removeFromDisliked(restaurant)}>
                  Remove
                </button>
              </li>
            ))}
          </ul>
        </div>
      </div>

      <Link to="/" className="back-link">
        Back to Preference
      </Link>
    </div>
  );
}

export default LikedDisliked;
