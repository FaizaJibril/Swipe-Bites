import React, { useState, useContext } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import { UserContext } from '../../context/UserContext';
import { loginUser, registerUser } from '../../api/AuthService';

const Login = () => {
  const { setCurrentUser } = useContext(UserContext);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
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
    const userRegister = { username, password };
    await registerUser(userRegister);
    await handleLogin(event);
  };

  return (
    <Container className="mt-5">
      <Row className="justify-content-center">
        <Col xs={12} sm={8} md={6}>
          <div className="text-center mb-4">
            
          </div>
          <Form onSubmit={handleLogin}>
            <Form.Group controlId="formBasicEmail">
              <Form.Control
                type="email"
                placeholder="Email address or phone number"
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
              Log In
            </Button>

            <div className="text-center mt-3">
              <a href="#">Forgot Password?</a>
            </div>
          </Form>

          <hr />

          <div className="text-center">
            <Button variant="success" type="button" block onClick={handleRegister}>
              Create New Account
            </Button>
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default Login;
