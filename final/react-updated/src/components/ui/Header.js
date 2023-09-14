import { LinkContainer } from 'react-router-bootstrap';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { UserContext } from "../../context/UserContext";
import { useContext } from "react";
import Logo from "./../../images/Logo.png"
import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom'; 

const Header = () => {
  const userContext = useContext(UserContext);
  const currentUser = userContext.currentUser;

  console.log(currentUser);

  const navigate = useNavigate();
  const location = useLocation();

  const isLoginPage = location.pathname === '/';

  const handleLogout = () => {
    userContext.logout(); 
    navigate('/');
  };

  return (
    <>
      <header>
        {currentUser && !isLoginPage && ( // Display Navbar when user is logged in and not on the login page
          <Nav>
            <Navbar bg="light" variant="light" expand="lg">
              <Container>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                  <Nav className="justify-content-center">
                    <NavDropdown title="Drop Down" id="basic-nav-dropdown">
                      <LinkContainer to="/nonexistant">
                        <NavDropdown.Item>User Profile</NavDropdown.Item>
                      </LinkContainer>
                      <LinkContainer to="/nonexistant">
                        <NavDropdown.Item>Account settings</NavDropdown.Item>
                      </LinkContainer>
                      <NavDropdown.Divider />
                      <NavDropdown.Item onClick={handleLogout}>Logout</NavDropdown.Item>
                    </NavDropdown>
                  </Nav>
                </Navbar.Collapse>
              </Container>
            </Navbar>
          </Nav>
        )}
      </header>
    </>
  );
};

export default Header;
