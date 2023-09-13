import React, { useState, useContext } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import { UserContext } from '../../context/UserContext';
import { loginUser, registerUser } from '../../api/AuthService';
import { Link } from 'react-router-dom';

const Register = () => {
    const { setCurrentUser } = useContext(UserContext);
    const [fullname, setFullname] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    
    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
      };
    
      const handlePasswordChange = (event) => {
        setPassword(event.target.value);
      };
      const handleFullnameChange = (event) => {
        setFullname(event.target.value);
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
        }
      };
    
      const handleRegister = async (event) => {
        event.preventDefault();
        const userRegister = { username, password, fullname};
        await registerUser(userRegister);
        await handleLogin(event);
      };
      
    // // Send a POST request to your backend API for user registration
    // const response = await fetch('/api/register', {
    //     method: 'POST',
    //     headers: {
    //       'Content-Type': 'application/json',
    //     },
    //     body: JSON.stringify({ email, password }),
    //   });
  
    //   if (response.status === 201) {
    //     // Successful registration, handle redirection or user state update
    //     console.log('Registration successful!');
    //   } else {
    //     // Handle registration error (e.g., email already exists)
    //     console.error('Registration failed.');
    //   }
    // };
  
    return(
        <Container className="mt-5">
        <Row className="justify-content-center">
          <Col xs={12} sm={8} md={6}>
            <div className="text-center mb-4">
            <h1 className= "h3 mb-3 font-weight-normal">Sign Up</h1>
            </div>
            <Form onSubmit={handleRegister}>

            <Form.Group controlId="formBasicName">
                <Form.Control
                  type="name"
                  placeholder="Full Name"
                  value={fullname}
                  onChange={handleFullnameChange}
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

                  

    
              <Button variant="primary" type="submit" block>
                Register
              </Button>

            </Form>
    
            <hr />
    
            <div className="text-center">
                            <Link to="/">
                                <Button variant="success" type="button" block>
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
