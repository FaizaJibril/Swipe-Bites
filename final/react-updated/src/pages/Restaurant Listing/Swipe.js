import React, { useState } from 'react';
import { Swipeable } from 'react-swipeable';

const RestaurantCard = ({ restaurant, onSwipeLeft, onSwipeRight }) => {
  const [swipeDirection, setSwipeDirection] = useState(null);

  const handleSwipe = (direction) => {
    setSwipeDirection(direction);
    if (direction === 'left') {
      onSwipeLeft();
    } else if (direction === 'right') {
      onSwipeRight();
    }
  };

  return (
    <Swipeable
      onSwipedLeft={() => handleSwipe('left')}
      onSwipedRight={() => handleSwipe('right')}
    >
      <div className={`restaurant-card ${swipeDirection}`}>
        {/* Restaurant card content */}
        <h3>{restaurant.name}</h3>
        <p>{restaurant.description}</p>
        {/* Add more restaurant details here */}
      </div>
    </Swipeable>
  );
};

export default RestaurantCard;
