import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import MyButton from "../elements/MyButton";

const HomePage = () => {
    const token = localStorage.getItem("token");
    const navigate = useNavigate();

    useEffect(() => {
        if (token !== null) {
            navigate("/shop");
        }
    }, [token, navigate]); // Added dependencies to useEffect

    const handleSubmit = () => {
        // Assuming this function handles the login logic
        // Implementation omitted for brevity
    };

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', minHeight: '100vh' }}>
            <h1>Welcome to Our Website!</h1>
            <p>Please choose an option:</p>
            <MyButton variant="info" func={() => navigate("/")} text="Go to Shopping"  />
            <div style={{ display: 'flex', justifyContent: 'center' }}>
                <MyButton variant="outline-primary" func={() => handleSubmit()} text="Login" />
                <MyButton variant="outline-primary" func={() => navigate("/registration")} text="Registration" />
            </div>
        </div>
    );
};
export default HomePage;

