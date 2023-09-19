import { LinkContainer } from 'react-router-bootstrap';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { UserContext } from '../../context/UserContext';
import { useContext } from 'react';
import Logo from './../../images/Logo.png';
import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import "./Header.css";

const Header = () => {
  const userContext = useContext(UserContext);
  const currentUser = userContext.currentUser;

  const navigate = useNavigate();
  const location = useLocation();

  const isLoginPage = location.pathname === '/';
  const isRegistrationPage = location.pathname === '/Register';

  const handleLogout = () => {
    userContext.logout();
    navigate('/');
  };

  return (
    <>
      <header>
        {currentUser && !isLoginPage && !isRegistrationPage && ( // Display Navbar when the user is logged in and not on the login page
          <Navbar bg="light" variant="light" expand="lg">
            <Container>
                <Navbar.Brand>
                <div className="header-logo">
                  <img
                    src={Logo} // Import your logo image here
                    alt="Logo"
                    className="logo" // Add a custom class for styling
                  />
                  </div>
                </Navbar.Brand>
              <Navbar.Toggle aria-controls="basic-navbar-nav" className="custom-toggle-button" />
              <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="justify-content-center">
                  <NavDropdown title="Menu" id="basic-nav-dropdown" className="custom-dropdown">
                    <LinkContainer to="/UserProfile">
                      <NavDropdown.Item>User Profile</NavDropdown.Item>
                    </LinkContainer>
                    <NavDropdown.Divider />
                    <NavDropdown.Item onClick={handleLogout}>Logout</NavDropdown.Item>
                  </NavDropdown>
                </Nav>
              </Navbar.Collapse>
            </Container>
          </Navbar>
        )}
      </header>
    </>
  );
};

export default Header;
