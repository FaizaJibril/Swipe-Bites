import React, { useState } from 'react';
import { useSwipeable } from 'react-swipeable';
import Card from 'react-bootstrap/Card';
import { UserContext } from '../../context/UserContext';
import { loginUser, registerUser } from '../../api/AuthService';
import { Link, useHistory } from 'react-router-dom';

const Login = () => {
    const { setCurrentUser } = useContext(UserContext);
    const [fullname, setFullname] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [preference, setPreference] = useState('');
    
    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
      };
    
      const handlePasswordChange = (event) => {
        setPassword(event.target.value);
      };
      const handleFullnameChange = (event) => {
        setFullname(event.target.value);
      };
      const handlePreferenceChange = (event) => {
        setPreference(event.target.value);
      };
    const handleLogin = async (event) => {
    event.preventDefault();
    const userLogin = { username, password };
    const loggedInUser = await loginUser(userLogin);
    if (loggedInUser) {
      loggedInUser.user.isAuthenticated = true;
      localStorage.setItem('user', JSON.stringify(loggedInUser.user));
      localStorage.setItem('token', loggedInUser.token);
      setCurrentUser(loggedInUser.user);
      history.push('/Preference');
    }
    

  };
};
  const MoodSelection = () => {
    const [mood, setMood] = useState('');
    const [cuisine, setCuisine] = useState(''); // Get the cuisine value from the previous step
  
    const handleMoodChange = (event) => {
      setMood(event.target.value);
    };
};
