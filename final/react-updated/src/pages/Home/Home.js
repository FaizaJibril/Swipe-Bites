import Layout from "../../components/ui/Layout";
import { useState,useEffect,Children } from "react";
import BackgroundVideo from "../../components/ui/BackgroundVideo";
import React from 'react';
import Login from "../Login/Login"
import Logo from "./../../images/Logo.png"
const Home = ({ children }) => {
    return (
        <Layout>
        
            <div className="login-box">
            <div className="logo-container">
        <img src={Logo} alt="Cover" className="logo" />
      </div>
        <Login />
      </div>
            {children}
        </Layout>
    );
}
export default Home;