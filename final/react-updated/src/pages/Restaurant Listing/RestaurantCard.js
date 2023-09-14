import React, { useState, useEffect } from 'react';
import { useSwipeable } from 'react-swipeable';
import Card from 'react-bootstrap/Card';

function RestaurantCard() {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [restaurants, setRestaurants] = useState([]);

  useEffect(() => {
    async function fetchRestaurantData() {
      try {
        const response = await fetch('http://localhost:9003/restaurant'); // Use quotes around the URL
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setRestaurants(data); // Assuming the API returns an array of restaurants
      } catch (error) {
        console.error('Error fetching restaurant data:', error);
      }
    }
    fetchRestaurantData();
  }, []); // Make sure to pass an empty dependency array to fetch data only once

  const { id, name, priceRange, reviews, photoUrl, address } =
    restaurants[currentIndex] || {};

  // Define swipe handlers
  const handlers = useSwipeable({
    onSwipedLeft: () => {
      // Handle left swipe (e.g., dislike the restaurant)
      setCurrentIndex((prevIndex) =>
        prevIndex === restaurants.length - 1 ? 0 : prevIndex + 1
      );
    },
    onSwipedRight: () => {
      // Handle right swipe (e.g., like the restaurant)
      setCurrentIndex((prevIndex) =>
        prevIndex === 0 ? restaurants.length - 1 : prevIndex - 1
      );
    },
    // You can add more swipe event handlers as needed
  });

  return (
    <Card {...handlers} style={{ width: '20rem', margin: '0 auto' }}>
      <Card.Img variant="top" src={photoUrl} alt={`Image of ${name}`} />
      <Card.Body>
        <Card.Title>{name}</Card.Title>
        <Card.Text>{reviews}</Card.Text>
        <Card.Text>Address: {address}</Card.Text>
        <Card.Text>Price: {priceRange}</Card.Text>
      </Card.Body>
    </Card>
  );
}

export default RestaurantCard;
