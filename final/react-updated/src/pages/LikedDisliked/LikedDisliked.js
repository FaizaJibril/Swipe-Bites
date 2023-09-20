import React, { useState, useEffect } from 'react';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import { liked , booking, deleterestaurant} from '../../api/RestaurantApi';
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


  const bookNow = async (restaurant)=> {
    const timestamp = new Date().toISOString(); // Get the current timestamp
    const user_id = currentUser.id;
    const restaurant_id = restaurant.id;

    // Create a booking object with the required data
    const bookingRequest = {
      bookingDate:timestamp,
      userId:user_id,
      restaurantId:restaurant_id,
    };
    await booking(bookingRequest);
  }
  const removeRestaurant = async (restaurant) => {
    const user_id = currentUser.id;
    const restaurant_id = restaurant.id;
  
    try {
      await deleterestaurant(user_id, restaurant_id);
      console.log('Restaurant removed successfully!');
      let updatedList = likedRestaurants.filter((restaurant)=>{
        return restaurant.id !== restaurant_id;
      });
      setLikedRestaurants(updatedList);
      // Refresh the page after successful removal
     // window.location.reload();
    } catch (error) {
      console.error('Restaurant removal failed:', error);
    }
  }
  
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
                  <Button variant="primary" className="book-button" onClick={() => bookNow(restaurant)} >Book Now</Button>
                  <Button variant="primary" className="book-button" onClick={() => removeRestaurant(restaurant)}> Remove Restaurant</Button>
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
