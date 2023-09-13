import React, { useState } from 'react';
import { useSwipeable } from 'react-swipeable';
import Card from 'react-bootstrap/Card';

const dummyData = [
  {
    name: 'Sample Restaurant 1',
    description: 'A cozy place to enjoy delicious food.',
    location: '123 Main Street, City',
    price: '$$',
    picture: 'https://via.placeholder.com/150', // Placeholder image URL
  },
  {
    name: 'Sample Restaurant 2',
    description: 'Another great place for dining.',
    location: '456 Elm Street, Town',
    price: '$$$',
    picture: 'https://via.placeholder.com/150', // Placeholder image URL
  },
  // Add more dummy restaurant data as needed
];

function RestaurantCard() {
  const [currentIndex, setCurrentIndex] = useState(0);
  const { name, description, location, price, picture } = dummyData[currentIndex];

  // Define swipe handlers
  const handlers = useSwipeable({
    onSwipedLeft: () => {
      // Handle left swipe (e.g., dislike the restaurant)
      setCurrentIndex((prevIndex) =>
        prevIndex === dummyData.length - 1 ? 0 : prevIndex + 1
      );
    },
    onSwipedRight: () => {
      // Handle right swipe (e.g., like the restaurant)
      setCurrentIndex((prevIndex) =>
        prevIndex === 0 ? dummyData.length - 1 : prevIndex - 1
      );
    },
    // You can add more swipe event handlers as needed
  });

  return (
    <Card {...handlers} style={{  width: '20rem', margin: '0 auto' }}>
      <Card.Img variant="top" src={picture} alt={`Image of ${name}`} />
      <Card.Body>
        <Card.Title>{name}</Card.Title>
        <Card.Text>{description}</Card.Text>
        <Card.Text>Location: {location}</Card.Text>
        <Card.Text>Price: {price}</Card.Text>
      </Card.Body>
    </Card>
  );
}

export default RestaurantCard;
