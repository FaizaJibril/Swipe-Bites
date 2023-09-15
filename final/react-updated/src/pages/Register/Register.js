import React, { useState, useContext } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import { UserContext } from '../../context/UserContext';
import { loginUser, registerUser } from '../../api/AuthService';
import { Link, useNavigate } from 'react-router-dom';

const Register = () => {
  const { setCurrentUser } = useContext(UserContext);
  const [fullName, setFullName] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [preferences, setPreferences] = useState('');
  const navigate = useNavigate();

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleFullNameChange = (event) => {
    setFullName(event.target.value);
  };

  const handlePreferenceChange = (event) => {
    setPreferences(event.target.value);
  };

  const handleRegister = async (event) => {
    event.preventDefault();

    // Client-side validation
    if (!fullName || !username || !password || !preferences) {
      alert('All fields are required.');
      return;
    }

    const userRegister = { username, password, fullName, preferences };
    try {
      await registerUser(userRegister);

      // After successful registration, you can redirect or log in the user
      const userLogin = { username, password };
      const loggedInUser = await loginUser(userLogin);
      
      if (loggedInUser) {
        loggedInUser.user.isAuthenticated = true;
        localStorage.setItem('user', JSON.stringify(loggedInUser.user));
        localStorage.setItem('token', loggedInUser.token);
        setCurrentUser(loggedInUser.user);
        navigate("/login"); 
      }
    } catch (error) {
      alert('Registration failed. Please try again.');
      console.error(error);
    }
  };

  return (
    <Container className="mt-5">
      <Row className="justify-content-center">
        <Col xs={12} sm={8} md={6}>
          <div className="text-center mb-4">
            <h1 className="h3 mb-3 font-weight-normal sign-up">Sign Up</h1>
          </div>
          <Form onSubmit={handleRegister}>
            <Form.Group controlId="formBasicName">
              <Form.Control
                type="text"
                placeholder="Full Name"
                value={fullName}
                onChange={handleFullNameChange}
                required
              />
            </Form.Group>
            <Form.Group controlId="formBasicEmail">
              <Form.Control
                type="email"
                placeholder="Email Address"
                value={username}
                onChange={handleUsernameChange}
                required
              />
            </Form.Group>
            <Form.Group controlId="formBasicPassword">
              <Form.Control
                type="password"
                placeholder="Password"
                value={password}
                onChange={handlePasswordChange}
                required
              />
            </Form.Group>
            <Form.Group controlId="formBasicPreference">
              <Form.Control
                className="grey-text"
                as="select"
                name="cuisine"
                value={preferences}
                onChange={handlePreferenceChange}
                required
              >
                <option value="" disabled hidden>
                  Select Cuisine
                </option>
                <option value="italian">Italian</option>
                <option value="mexican">Mexican</option>
                <option value="indian">Indian</option>
                <option value="American Dinner">American Dinner</option>
                <option value="Healthy">Healthy</option>
                <option value="sushi">sushi</option>
              </Form.Control>
            </Form.Group>
            <Button className="primary-button" type="submit" block>
              Register
            </Button>
          </Form>
          <hr />
          <div className="text-center">
            <Link to="/">
              <Button className="secondary-button" type="submit" block>
                Already have an account?
              </Button>
            </Link>
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default Register;
