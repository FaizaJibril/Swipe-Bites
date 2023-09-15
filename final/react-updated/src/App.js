import React, { useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ErrorBoundary from './ErrorBoundary';
import { UserProvider } from './context/UserContext';
import Home from './pages/Home/Home';
import Login from './pages/Login/Login';
import Preference from './pages/Preference/Preference'
import Register from './pages/Register/Register';
import RestaurantCard from "./pages/Restaurant Listing/RestaurantCard"; // Correctly import the RestaurantCard component
import './App.css';

const MyApp = () => {
  const [restaurants, setRestaurants] = useState([]); // Replace with actual restaurant data

  const handleSwipeLeft = () => {
    // Handle left swipe action, e.g., dislike the restaurant
    // Remove the current restaurant from the list or perform other actions
  };

  const handleSwipeRight = () => {
    // Handle right swipe action, e.g., like the restaurant
    // Add the current restaurant to a "liked" list or perform other actions
  };

  return (
    <div className="wrapper">
      <ErrorBoundary fallback={<p>Something went wrong</p>}>
        <UserProvider>
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/Preference" element={<Preference />} />
              <Route
                path="/restaurant"
                element={
                  <RestaurantCard
                    restaurant={restaurants[0]} // Pass the restaurant data
                    onSwipeLeft={handleSwipeLeft}
                    onSwipeRight={handleSwipeRight}
                  />
                }
              />
            </Routes>
          </BrowserRouter>
        </UserProvider>
      </ErrorBoundary>
    </div>
  );
};

export default MyApp;
