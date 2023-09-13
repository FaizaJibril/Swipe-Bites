import "./RestaurantCard.css";
import React from 'react';
import { useSwipeable } from 'react-swipeable';

const dummyData = {
  name: 'Sample Restaurant',
  description: 'A cozy place to enjoy delicious food.',
  location: '123 Main Street, City',
  price: '$$',
  picture: 'https://via.placeholder.com/150', // Placeholder image URL
};

function RestaurantCard({ restaurant }) {
  const { name, description, location, price, picture } = restaurant || dummyData;

  // Define swipe handlers
  const handlers = useSwipeable({
    onSwipedLeft: () => {
      // Handle left swipe (e.g., dislike the restaurant)
      console.log(`Swiped left on ${name}`);
    },
    onSwipedRight: () => {
      // Handle right swipe (e.g., like the restaurant)
      console.log(`Swiped right on ${name}`);
    },
    // You can add more swipe event handlers as needed
  });

  return (
    <div className="restaurant-card" {...handlers}>
      <img src={picture} alt={`Image of ${name}`} />
      <div className="restaurant-details">
        <h2>{name}</h2>
        <p>{description}</p>
        <p>Location: {location}</p>
        <p>Price: {price}</p>
      </div>
    </div>
  );
}

export default RestaurantCard;

