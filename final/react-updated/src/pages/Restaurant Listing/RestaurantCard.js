import React from 'react';
//import StarRating from './StarRating';
import "./RestaurantCard.css";
//import { FaUtensils, FaMapMarker, FaPhone } from 'react-icons/fa';


const dummyData = [
  { id: 1, name: 'Restaurant 1', description: 'A great place to eat.' },
  { id: 2, name: 'Restaurant 2', description: 'Delicious food in a cozy atmosphere.' },
  { id: 3, name: 'Restaurant 3', description: 'Experience the finest dining.' },
];

const Restaurant = () => {
  return (
    <div>
      <h1>Restaurant List</h1>
      <ul>
        {dummyData.map((restaurant) => (
          <li key={restaurant.id}>
            {restaurant.name} - {restaurant.description}
            {/* Add more restaurant details here */}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Restaurant;
