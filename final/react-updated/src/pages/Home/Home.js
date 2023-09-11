import Layout from "../../components/ui/Layout";
const Home = ({ children }) => {
    return (
        <Layout>
            <div>Some awesome screen that looks awe inspiring</div>
            {children}
        </Layout>
    );
}
export default Home;