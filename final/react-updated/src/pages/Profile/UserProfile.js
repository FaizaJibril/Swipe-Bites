import React, { useContext, useState, useEffect } from 'react';
import { UserContext } from '../../context/UserContext';
import './UserProfile.css';
import { useNavigate } from 'react-router';
import { updateUserDetail, getUser } from '../../api/UserApi';

const UserProfile = () => {
  const userContext = useContext(UserContext);
  const currentUser = userContext.currentUser;
  const navigate = useNavigate();

  const [isEditing, setIsEditing] = useState(false);
  const [userData, setUserData] = useState({
    id: currentUser?.id,
    fullName: currentUser?.fullName,
    email: currentUser?.username,
    username: currentUser?.username,
    password: '', // You can include the password here
    preferences: currentUser?.preferences,
  });

  const [isProfileUpdated, setIsProfileUpdated] = useState(false);

  useEffect(() => {
    const getUserProfile = async () => {
      if (currentUser?.id > 0) {
        const localUser = await getUser(currentUser.id);
        setUserData({
          ...userData,
          fullName: localUser.fullName,
          preferences: localUser.preferences,
        });
      }
    };
    getUserProfile();
  }, [currentUser?.id]);

  const handleEditProfile = () => {
    setIsEditing(true);
  };

  const handleSaveProfile = async () => {
    userData.id = currentUser.id;
    const updatedUser = await updateUserDetail(userData);

    if (updatedUser) {
      setIsProfileUpdated(true);
      setUserData(updatedUser); // Update the user data with the response
    } else {
      setIsProfileUpdated(false);
    }

    setIsEditing(false);
  };

  return (
    <div className="user-profile-card">
      <div className="card">
        <div className="card-header">
          <h2>User Profile</h2>
        </div>
        <div className="card-body">
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
              {/* Email input is disabled and cannot be changed */}
              <label htmlFor="email">Email:</label>
              <input
                type="text"
                id="email"
                value={userData.email}
                className="disabled-input"
                readOnly
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
              <select
                id="preferences"
                value={userData.preferences}
                onChange={(e) =>
                  setUserData({
                    ...userData,
                    preferences: e.target.value,
                  })
                }
              >
                <option value="italian">Italian</option>
                <option value="mexican">Mexican</option>
                <option value="indian">Indian</option>
                <option value="American Dinner">American Dinner</option>
                <option value="Healthy">Healthy</option>
                <option value="sushi">Sushi</option>
              </select>
              <button onClick={handleSaveProfile}>Save Profile</button>
            </div>
          ) : (
            <div>
              <h3>{userData.fullName}</h3>
              <p>{userData.email}</p>
              <p>Default Cuisine: {userData.preferences}</p>
              <button onClick={handleEditProfile}>Edit Profile</button>
            </div>
          )}

          {isProfileUpdated && (
            <div>
              <p>Profile updated successfully!</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default UserProfile;
