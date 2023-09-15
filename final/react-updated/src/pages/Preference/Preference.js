// Preference.js
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useSwipeable } from 'react-swipeable';
import './Preference.css';

const cuisines = [
  {
    id: '1',
    name: 'Italian',
    image: "https://c.ndtvimg.com/2021-04/umk8i7ko_pasta_625x300_01_April_21.jpg?im=FaceCrop,algorithm=dnn,width=1200,height=886",
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
  const navigate = useNavigate();
  const [currentIndex, setCurrentIndex] = useState(0);

  const handlers = useSwipeable({
    onSwipedLeft: () => {
      if (currentIndex < cuisines.length - 1) {
        setCurrentIndex(currentIndex + 1);
      }
    },
    onSwipedRight: () => {
      if (currentIndex > 0) {
        setCurrentIndex(currentIndex - 1);
      }
    },
  });

  const handlePreferenceSelection = (cuisineId) => {
    const selectedCuisine = cuisines.find((cuisine) => cuisine.id === cuisineId);
    navigate(`/restaurant/${selectedCuisine.name}`);
  };

  return (
    <div className="container" {...handlers}>
      <h1 className= "welcomeText">Hello There!</h1>
      <p className="headerText">Swipe to Select Your Cuisine</p>
      <div className="cuisineContainer">
        {cuisines.map((cuisine, index) => (
          <div
            key={cuisine.id}
            className={`cardContainer ${currentIndex === index ? 'selected' : ''}`}
            onClick={() => handlePreferenceSelection(cuisine.id)}
          >
            <img className="cardImage" src={cuisine.image} alt={cuisine.name} />
            <p className="cuisineName">{cuisine.name}</p>
            {currentIndex === index && <p className="selectedText">Selected</p>}
          </div>
        ))}
      </div>
      {currentIndex === cuisines.length - 1 && (
        <Link to="/restaurant">
          <button className="nextButton">Next</button>
        </Link>
      )}
    </div>
  );
}

export default Preference;
