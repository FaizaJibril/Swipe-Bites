import React, { useState, useContext } from 'react';
import { Container, Button } from 'react-bootstrap';
import { UserContext } from '../../context/UserContext';
import { useSwipeable } from 'react-swipeable'; // Import react-swipeable
import './Preference.css'; // Import the CSS file



const cuisines = [
  {
    id: '1',
    name: 'Italian',
    image: 'src/images/italian food.jpg',
  },

  {
    id: '2',
    name: 'Mexican',
    image: 'https://via.placeholder.com/150',
  },
  {
    id: '3',
    name: 'Indian',
    image: 'https://via.placeholder.com/150',
  },
  {
    id: '4',
    name: 'American',
    image: 'https://via.placeholder.com/150',
  },
  // Add more cuisines as needed
];

function Preference() {
  const { currentUser, updateUserPreferences } = useContext(UserContext);
  const [currentIndex, setCurrentIndex] = useState(0);

  // Define swipe handlers
  const handlers = useSwipeable({
    onSwipedLeft: () => {
      // Handle left swipe (if needed)
      if (currentIndex < cuisines.length - 1) {
        setCurrentIndex(currentIndex + 1);
      }
    },
    onSwipedRight: () => {
      // Handle right swipe (if needed)
      if (currentIndex > 0) {
        setCurrentIndex(currentIndex - 1);
      }
    },
  });

  const handlePreferenceSelection = (cuisineId) => {
    updateUserPreferences(cuisineId);
    // Move to the next card on selection (if needed)
    if (currentIndex < cuisines.length - 1) {
      setCurrentIndex(currentIndex + 1);
    }
  };

  return (
    <div className="container" {...handlers}>
      <h1>Welcome There!</h1>
      <p className="headerText">Swipe to Select Your Cuisine</p>
      <div className="cuisineContainer">
        {cuisines.map((cuisine, index) => (
          <div
            key={cuisine.id}
            className={`cardContainer ${
              currentIndex === index ? 'selected' : ''
            }`}
            onClick={() => handlePreferenceSelection(cuisine.id)}
          >
            <img className="cardImage" src={cuisine.image} alt={cuisine.name} />
            <p className="cuisineName">{cuisine.name}</p>
            {currentIndex === index && <p className="selectedText">Selected</p>}
          </div>
        ))}
      </div>
      {currentIndex === cuisines.length - 1 && (
        <button className="nextButton">Next</button>
      )}
    </div>
  );
}

export default Preference;
