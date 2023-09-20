import React, { useState, useEffect } from 'react';
import { useSwipeable } from 'react-swipeable';
import Card from 'react-bootstrap/Card';
import { like, dislike } from '../../api/RestaurantApi';
import { Link} from 'react-router-dom';
import './RestaurantCard.css';
import { useParams } from 'react-router-dom';
function RestaurantCard() {
  let { cuisine } = useParams();
  const [currentIndex, setCurrentIndex] = useState(0);
  const [restaurants, setRestaurants] = useState([]);
  const [isExpanded, setIsExpanded] = useState(false);
  const [likedRestaurants, setLikedRestaurants] = useState([]);
  const [dislikedRestaurants, setDislikedRestaurants] = useState([]);
  const handleReset = () => {
    // Reload the page to reset the state
    window.location.reload();
  };
  useEffect(() => {
    async function fetchRestaurantData() {
      try {
        const response = await fetch(
          cuisine
            ? `http://localhost:9003/restaurant/cuisine/${cuisine}`
            : 'http://localhost:9003/restaurant'
        );
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
    onSwipedRight: async () => {
      try {
        // Make an API call to send liked restaurant details
        await like(id);
        // Add the liked restaurant to the likedRestaurants state
        setLikedRestaurants([...likedRestaurants, restaurants[currentIndex]]);
        // Remove the liked restaurant from the available restaurants
        const updatedRestaurants = [...restaurants];
        updatedRestaurants.splice(currentIndex, 1);
        setRestaurants(updatedRestaurants);
        // Move to the next restaurant
        setCurrentIndex((prevIndex) =>
          prevIndex === updatedRestaurants.length - 1 ? 0 : prevIndex + 1
        );
      } catch (error) {
        console.error('Error sending liked restaurant data:', error);
      }
    },
    onSwipedLeft: async () => {
      try {
        // Make an API call to send disliked restaurant details
        await dislike(id);
        // Add the disliked restaurant to the dislikedRestaurants state
        setDislikedRestaurants([
          ...dislikedRestaurants,
          restaurants[currentIndex],
        ]);
        // Remove the disliked restaurant from the available restaurants
        const updatedRestaurants = [...restaurants];
        updatedRestaurants.splice(currentIndex, 1);
        setRestaurants(updatedRestaurants);
        // Move to the next restaurant
        setCurrentIndex((prevIndex) =>
          prevIndex === updatedRestaurants.length - 1 ? 0 : prevIndex + 1
        );
      } catch (error) {
        console.error('Error sending disliked restaurant data:', error);
      }
    },
  });
  function generateDollarSigns(priceRange) {
    const dollarSigns = '$'.repeat(priceRange); // Repeat '$' based on priceRange
    return dollarSigns;
  }
  const truncatedDescription = reviews?.substring(0, 100); // Adjust the character limit
  return (
    <div className="centered-container">
      {currentIndex < restaurants.length ? (
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
              <span
                className={`description-text ${isExpanded ? 'expanded' : ''}`}
              >
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
      ) : (
        <div className = "text-center">
          <h1 className= "RestaurantCard h1"> Oops, no more restaurants to display.</h1>
          <button className = "secondary-button" onClick={handleReset}>Reset Selections</button>
          <Link to="/likes">
          <button className = "secondary-button"> View Liked Restaurants</button>
        </Link>
        </div>
      )}
    </div>
  );
}
export default RestaurantCard;