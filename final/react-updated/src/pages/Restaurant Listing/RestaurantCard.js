import React, { useState, useEffect } from 'react';
import { useSwipeable } from 'react-swipeable';
import Card from 'react-bootstrap/Card';
import {like, dislike} from '../../api/RestaurantApi';

import './RestaurantCard.css';
import { useParams } from 'react-router-dom';

function RestaurantCard() {
  let { cuisine } = useParams();
  const [currentIndex, setCurrentIndex] = useState(0);
  const [restaurants, setRestaurants] = useState([]);
  const [isExpanded, setIsExpanded] = useState(false);

  useEffect(() => {
    async function fetchRestaurantData() {
      try {
        const response = await fetch((cuisine) ? `http://localhost:9003/restaurant/cuisine/${cuisine}` : 'http://localhost:9003/restaurant');
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setRestaurants(data);
      } catch (error) {
        console.error('Error fetching restaurant data:', error);
      }
    }
    fetchRestaurantData();
  }, []);

  const toggleExpand = () => {
    setIsExpanded(!isExpanded);
  };

  const { id, name, description, priceRange, reviews, photoUrl, address } =
    restaurants[currentIndex] || {};

    // left swipe is disliked and right swipe is liked
  const handlers = useSwipeable({
    onSwipedLeft: async() => {
      // Make an API call to send disliked restaurant details
      try {
        const response = await fetch('http://localhost:9003/restaurant/' + id + '/like', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          }
        });

        if (!response.ok) {
          throw new Error('Failed to send disliked restaurant data');
        }

        // Handle the response from the backend if needed
      } catch (error) {
        console.error('Error sending disliked restaurant data:', error);
      }

      // Move to the next restaurant
      setCurrentIndex((prevIndex) =>
        prevIndex === restaurants.length - 1 ? 0 : prevIndex + 1
      );
    },
    onSwipedRight: async () => {
      // Make an API call to send liked restaurant details
      await like(id);
      // Move to the next restaurant
      setCurrentIndex((prevIndex) =>
        prevIndex === restaurants.length - 1 ? 0 : prevIndex + 1
      );
    },
  });

  function generateDollarSigns(priceRange) {
    const dollarSigns = '$'.repeat(priceRange); // Repeat '$' based on priceRange
    return dollarSigns;
  };

  const truncatedDescription = reviews?.substring(0, 100); // Adjust the character limit

return (
  <div className="centered-container">
    <Card {...handlers} className="restaurant-card">
      <Card.Img
        variant="top"
        src={photoUrl}
        alt={`Image of ${name}`}
        className="image-reduced-height"
      />
      <Card.Body>
      <Card.Title>{name}</Card.Title>
        <Card.Text>
          <span className={`description-text ${isExpanded ? 'expanded' : ''}`}>
            {reviews}
          </span>
          {!isExpanded && reviews?.length > 100 && (
            <button className="expand-button" onClick={toggleExpand}>
              Read More
            </button>
          )}
          {isExpanded && (
            <button className="expand-button" onClick={toggleExpand}>
              Read Less
            </button>
          )}
        </Card.Text>
        <Card.Text>Address: {address}</Card.Text>
        <Card.Text>Price: {generateDollarSigns(priceRange)}</Card.Text>
      </Card.Body>
    </Card>
  </div>
);
          }

export default RestaurantCard;



