import React, { useState, useContext } from 'react';
import { Container, Button } from 'react-bootstrap';
import { UserContext } from '../../context/UserContext';
import { useSwipeable } from 'react-swipeable'; // Import react-swipeable
import './Preference.css'; // Import the CSS file



const cuisines = [
  {
    id: '1',
    name: 'Italian',
    image: "https://c.ndtvimg.com/2021-04/umk8i7ko_pasta_625x300_01_April_21.jpg?im=FaceCrop,algorithm=dnn,width=1200,height=886"
  },

  {
    id: '2',
    name: 'Mexican',
    image: "https://www.nanosrecipes.com/wp-content/uploads/2016/06/Tacos_Green_15-150x150@2x.jpg",
  },
  {
    id: '3',
    name: 'Indian',
    image: "https://www.seema.com/wp-content/uploads/2023/03/Best-Indian-Restaurants-in-the-USA.jpg",
  },
  {
    id: '4',
    name: 'American',
    image: "https://qph.cf2.quoracdn.net/main-qimg-dd54b7f33423c5cda564f7c40e48d2a0-lq",
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
      <h1 className= "welcomeText">Hello There!</h1>
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
