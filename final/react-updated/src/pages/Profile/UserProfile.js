import React, { useContext, useState, useEffect } from 'react';
import { UserContext } from '../../context/UserContext';
import './UserProfile.css';

const UserProfile = () => {
  const userContext = useContext(UserContext);
  const currentUser = userContext.currentUser;

  const [isEditing, setIsEditing] = useState(false);
  const [userData, setUserData] = useState({
    fullName: currentUser.fullName,
    email: currentUser.username,
    username: currentUser.username,
    password: '', // You can include the password here
    preferences: currentUser.preferences,
  });

  useEffect(() => {
    // Fetch user details from the API using the user's ID
    fetch(`http://localhost:9003/user/${currentUser.id}`)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          throw new Error('Failed to fetch user details');
        }
      })
      .then((data) => {
        // Handle the fetched data here
        console.log(data);
        // Update userData state with the fetched data
        setUserData({
          ...userData,
          fullName: data.fullName,
          email: data.username,
          username: data.username,
          // Update other fields as needed
          preferences: data.preferences,
        });
      })
      .catch((error) => {
        console.error('Error fetching user details:', error);
      });
  }, [currentUser.id]);

  const handleEditProfile = () => {
    setIsEditing(true);
  };

  const handleSaveProfile = () => {
    // Make a PUT request to update the user's profile with the edited data
    fetch(`/user/${currentUser.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData), // Send the edited user data
    })
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          throw new Error('Failed to update user profile');
        }
      })
      .then((data) => {
        // Update the user's profile in the UserContext
        userContext.setCurrentUser(data);
        // Exit edit mode
        setIsEditing(false);
      })
      .catch((error) => {
        console.error('Error updating user profile:', error);
      });
  };

  return (
    <div className="user-profile">
      <h2>User Profile</h2>
      <div className="user-info">
        {isEditing ? (
          <div className="edit-profile">
            <label htmlFor="fullName">Name:</label>
            <input
              type="text"
              id="fullName"
              value={userData.fullName}
              onChange={(e) =>
                setUserData({ ...userData, fullName: e.target.value })
              }
            />
            <label htmlFor="email">Email:</label>
            <input
              type="text"
              id="username"
              value={userData.username}
              onChange={(e) =>
                setUserData({ ...userData, username: e.target.value })
              }
            />
            <label htmlFor="password">Password:</label>
            <input
              type="password"
              id="password"
              value={userData.password}
              onChange={(e) =>
                setUserData({ ...userData, password: e.target.value })
              }
            />
            <label htmlFor="preferences">Default Cuisine:</label>
            <input
              type="text"
              id="preferences"
              value={userData.preferences}
              onChange={(e) =>
                setUserData({
                  ...userData,
                  preferences: e.target.value,
                })
              }
            />
            <button onClick={handleSaveProfile}>Save Profile</button>
          </div>
        ) : (
          <div>
            <h3>{userData.fullName}</h3>
            <p>Email: {userData.username}</p>
            <p>Default Cuisine: {userData.preferences}</p>
            <button onClick={handleEditProfile}>Edit Profile</button>
          </div>
        )}
      </div>
    </div>
  );
};

export default UserProfile;
