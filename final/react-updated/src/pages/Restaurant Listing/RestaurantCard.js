import React, { useState, useEffect } from 'react';
import { useSwipeable } from 'react-swipeable';
import Card from 'react-bootstrap/Card';
import './RestaurantCard.css';

function RestaurantCard() {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [restaurants, setRestaurants] = useState([]);
  const [isExpanded, setIsExpanded] = useState(false);

  useEffect(() => {
    async function fetchRestaurantData() {
      try {
        const response = await fetch('http://localhost:9003/restaurant');
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

  const handlers = useSwipeable({
    onSwipedLeft: () => {
      setCurrentIndex((prevIndex) =>
        prevIndex === restaurants.length - 1 ? 0 : prevIndex + 1
      );
    },
    onSwipedRight: () => {
      setCurrentIndex((prevIndex) =>
        prevIndex === 0 ? restaurants.length - 1 : prevIndex - 1
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



