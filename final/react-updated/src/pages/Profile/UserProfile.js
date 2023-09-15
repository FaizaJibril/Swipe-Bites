import React, { useContext, useState } from 'react';
import { UserContext } from '../../context/UserContext';
import './UserProfile.css'; 

const UserProfile = () => {
  const userContext = useContext(UserContext);
  const currentUser = userContext.currentUser;

  const [isEditing, setIsEditing] = useState(false);
  const [updatedName, setUpdatedName] = useState(currentUser.name);
  const [updatedCuisine, setUpdatedCuisine] = useState(currentUser.defaultCuisine);

  const handleEditProfile = () => {
    setIsEditing(true);
  };

  const handleSaveProfile = () => {
    // In a real application, you would make an API call to update the user's profile
    // For now, let's just simulate the update by setting the new data
    const updatedUser = {
      ...currentUser,
      name: updatedName,
      defaultCuisine: updatedCuisine,
    };

    // Update the user's profile in the UserContext
    userContext.setCurrentUser(updatedUser);

    // Exit edit mode
    setIsEditing(false);
  };

  return (
    <div className="user-profile">
      <h2>User Profile</h2>
      <div className="user-info">
        <img src={currentUser.image} alt={currentUser.name} />
        {isEditing ? (
          <div className="edit-profile">
            <label htmlFor="name">Name:</label>
            <input
              type="text"
              id="name"
              value={updatedName}
              onChange={(e) => setUpdatedName(e.target.value)}
            />
            <label htmlFor="cuisine">Default Cuisine:</label>
            <input
              type="text"
              id="cuisine"
              value={updatedCuisine}
              onChange={(e) => setUpdatedCuisine(e.target.value)}
            />
            <button onClick={handleSaveProfile}>Save Profile</button>
          </div>
        ) : (
          <div>
            <h3>{currentUser.name}</h3>
            <p>Email: {currentUser.email}</p>
            <p>Default Cuisine: {currentUser.defaultCuisine}</p>
            <button onClick={handleEditProfile}>Edit Profile</button>
          </div>
        )}
      </div>
    </div>
  );
};

export default UserProfile;
