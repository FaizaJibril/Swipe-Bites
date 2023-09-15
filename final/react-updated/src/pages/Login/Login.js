import React, { useState, useContext } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import { UserContext } from '../../context/UserContext';
import { loginUser, registerUser } from '../../api/AuthService';
import { Link,useNavigate } from 'react-router-dom';
import "./Login.css"

const Login = () => {
  const { setCurrentUser } = useContext(UserContext);
    const [fullname, setFullname] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [preference, setPreference] = useState('');
    const navigate = useNavigate();
    
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
      navigate("/Preference");
    }
  };

  const handleRegister = async (event) => {
    event.preventDefault();
    const userRegister = { username, password,  fullname,preference};
    await registerUser(userRegister);
    await handleLogin(event);
  };

  return (
    <Container className="mt-5">
      <Row className="justify-content-center">
        <Col xs={12} sm={8} md={6}>
          <div className="text-center mb-4">
            <h1 className="h3 mb-3 font-weight-normal welcome-heading">Welcome!</h1>
            <h2 className="h3 mb-3 font-weight-normal login-heading">Log into your account</h2>
          </div>
          <Form onSubmit={handleLogin}>
            <Form.Group controlId="formBasicEmail">
              <Form.Control
                type="email"
                placeholder="Email address"
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

            
                <Button className="primary-button" type="submit" block >
                    Log In
                </Button>
           
            <div className="text-center mt-3 forgot-password">
              <a href="#">Forgot Password?</a>
            </div>
          </Form>

          <hr />

          <div className="text-center">
            <Link to="/Register">
              <Button className="secondary-button" type="button" block>
                Create New Account
              </Button>
            </Link>
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default Login;