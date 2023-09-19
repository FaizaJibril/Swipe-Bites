import React, { useState, useEffect } from 'react';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import { like, dislike, liked } from '../../api/RestaurantApi';
import { getUser } from '../../api/UserApi';
import './LikedDisliked.css';
import { UserContext } from '../../context/UserContext';
import { useContext } from 'react';

function LikedDisliked() {
  const [likedRestaurants, setLikedRestaurants] = useState([]);
  const [expandedRestaurant, setExpandedRestaurant] = useState(null);
  const userContext = useContext(UserContext);
  const currentUser = userContext.currentUser;

  useEffect(() => {
    liked(currentUser.id)
      .then((data) => setLikedRestaurants(data))
      .catch((error) => console.error('Error fetching liked restaurants:', error));
  }, [currentUser.id]);

  const handleExpand = (restaurant) => {
    setExpandedRestaurant((prevRestaurant) => (prevRestaurant === restaurant ? null : restaurant));
  };

  const generatePriceRangeSymbols = (priceRange) => {
    const dollarSigns = '$'.repeat(priceRange);
    return dollarSigns;
  };

  return (
    <div className="centered-container">
      <h1>Liked Restaurants</h1>
      <div className="restaurant-list">
        {likedRestaurants.map((restaurant) => (
          <Card
            key={restaurant.id}
            className="restaurant-card"
            onClick={() => handleExpand(restaurant)}
          >
            <Card.Img
              variant="top"
              src={restaurant.photoUrl}
              alt={`Image of ${restaurant.name}`}
              className="image-reduced-height"
            />
            <Card.Body>
              <Card.Title>{restaurant.name}</Card.Title>
              {expandedRestaurant === restaurant && (
                <div>
                  <Card.Text>{restaurant.reviews}</Card.Text>
                  <Card.Text>Address: {restaurant.address}</Card.Text>
                  <Card.Text>Price: {generatePriceRangeSymbols(restaurant.priceRange)}</Card.Text>
                  <Button variant="primary" className="book-button">Book Now</Button>
                </div>
              )}
            </Card.Body>
          </Card>
        ))}
      </div>
    </div>
  );
}

export default LikedDisliked;
