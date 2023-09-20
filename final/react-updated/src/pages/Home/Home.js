import React from 'react';
import Layout from '../../components/ui/Layout';
import BackgroundVideo from '../../components/ui/BackgroundVideo';
import Login from '../Login/Login';
import Logo from './../../images/Logo.png';

const Home = () => {
  return (
    <Layout>
      <BackgroundVideo />
      <div className="login-box">
        <div className="logo-container">
          <img src={Logo} alt="Logo" className="logo" />
        </div>
        <Login />
      </div>
    </Layout>
  );
};

export default Home;
