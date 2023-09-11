import Layout from "../../components/ui/Layout";
import { useState,useEffect,Children } from "react";

const Home = ({ children }) => {
    return (
        <Layout>
            <h1> Flavour Match </h1>
            <div class="element"></div>
            <div>Some awesome screen that looks awe inspiring</div>
            {children}
        </Layout>
    );
}
export default Home;