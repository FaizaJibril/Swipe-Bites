import { LinkContainer } from 'react-router-bootstrap';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { UserContext } from "../../context/UserContext";
import { useContext } from "react";

const Header = () => {
    const userContext = useContext(UserContext);
    const currentUser = userContext.currentUser;


	return (
		<>
			<header>				
				<Nav>
					<Navbar bg="light" variant="light" expand="lg">
						<Container>
							<Navbar.Brand>
								Logo
							</Navbar.Brand>
							<Navbar.Toggle aria-controls="basic-navbar-nav" />
							<Navbar.Collapse id="basic-navbar-nav">
								<Nav className="justify-content-center">
									<LinkContainer to="/">
										<Nav.Link>Home</Nav.Link>
									</LinkContainer>
									<LinkContainer to="/Login">
										<Nav.Link>Login</Nav.Link>
									</LinkContainer>
									{(currentUser) &&
										<NavDropdown title="Drop Down" id="basic-nav-dropdown">
											<LinkContainer to="/nonexistant">
												<NavDropdown.Item>Some Menu</NavDropdown.Item>
											</LinkContainer>
											<LinkContainer to="/nonexistant">
												<NavDropdown.Item>Some Other Menu</NavDropdown.Item>
											</LinkContainer>
										</NavDropdown>
									}
								</Nav>
							</Navbar.Collapse>
						</Container>
					</Navbar>
				</Nav>
			</header>
		</>
	);
};

export default Header;
