import { useState,useContext } from 'react';
import { Button, ButtonGroup } from 'react-bootstrap';
import Form from 'react-bootstrap/Form';
import Layout from "../../components/ui/Layout";
import { UserContext } from "../../context/UserContext";
import LogoutButton from '../../components/form/LogoutButton';
import { useNavigate } from "react-router-dom";
import {loginUser,registerUser}    from '../../api/AuthService'

const Login = ({ children }) => {
    const navigate = useNavigate();        
    const { setCurrentUser } = useContext(UserContext);
    const userContext = useContext(UserContext);
    const currentUser = userContext.currentUser;
    const [canRegister, setCanRegister] = useState(true);
  

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const doSetUsername = (event) => {
        setUsername(event.target.value);
    };
    const doSetPassword = (event) => {
        setPassword(event.target.value);
    };

    const handleCancel = () => {
        setPassword('');
    };

    const handleLogin = async (event) => {
        if (event !== undefined) event.preventDefault();
        
        const userLogin = { 'username': username, 'password': password };
        await logUserIn(userLogin);
    };
    const logUserIn = async (userToLogin) => {
        const loggedInUser = await loginUser(userToLogin);
        if (loggedInUser) {                  
            loggedInUser.user.isAuthenticated = true;
            localStorage.setItem('user', JSON.stringify(loggedInUser.user));
            localStorage.setItem('token', loggedInUser.token);        
            setCurrentUser(loggedInUser.user);
            navigate("/");
        };
    }
    const handleRegister = async (event) => {
        if (event !== undefined) event.preventDefault();
        setCanRegister(false);
        if (canRegister){
            setCanRegister(false);
            const userRegister = { 'username': username, 'password': password };
            await registerUser(userRegister);
            await logUserIn(userRegister);
            setCanRegister(true);
        }
    };
    return (
        <Layout>
            {(!currentUser || !currentUser.isAuthenticated) &&
                <Form onSubmit={handleLogin}>
                    <Form.Group className="mb-3" controlId="formBasicEmail">
                        <Form.Label>Username</Form.Label>
                        <Form.Control type="email" placeholder="Enter username"
                            value={username} onChange={doSetUsername} />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control type="password" placeholder="Password"
                            value={password} onChange={doSetPassword} />
                    </Form.Group>
                    <ButtonGroup>
                        <Button variant="primary" onClick={handleLogin} type="submit">
                            Login
                        </Button>
                        <Button variant="secondary" onClick={handleCancel}>
                            Cancel
                        </Button>
                        <Button variant="info" onClick={handleRegister} type="submit">
                            Register
                        </Button>
                    </ButtonGroup>
                </Form>
            }
            {currentUser && currentUser.isAuthenticated &&
                <div>
                    <div>You are currently logged in as {currentUser.firstName}</div>
                    <div><LogoutButton></LogoutButton></div>
                </div>

            }
            {children}
        </Layout>
    );
}
export default Login;