import { LinkContainer } from 'react-router-bootstrap';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { UserContext } from "../../context/UserContext";
import { useContext } from "react";
import Logo from "./../../images/Logo.png"

const Header = () => {
    const userContext = useContext(UserContext);
    const currentUser = userContext.currentUser;


	return (
		<>
			<header>				
				
			</header>
		</>
	);
};

export default Header;
