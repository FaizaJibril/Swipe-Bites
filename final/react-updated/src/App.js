import { UserProvider } from './context/UserContext';
import ErrorBoundary from './ErrorBoundary';
import Login from './pages/Login/Login';
import Home from './pages/Home/Home';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
const MyApp = () => {  

  return (
    
    <div className="wrapper">
      <ErrorBoundary fallback={<p>Something went wrong</p>}>
        <UserProvider>
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/Login" element={<Login />} />
            </Routes>
          </BrowserRouter>
        </UserProvider>
      </ErrorBoundary>
    </div>
  );
}

export default MyApp;