import './App.css';
import React from "react";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ShopItemListPage from "./components/pages/ShopItemListPage";
import LoginPage from "./components/pages/LoginPage";
import RegistrationPage from "./components/pages/RegistrationPage";
import CartPage from "./components/pages/CartPage";
import HomePage from "./components/pages/HomePage";
import EditItemPage from "./components/pages/EditItemPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/shop" element={<HomePage />} />
                <Route path="/" element={<ShopItemListPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/registration" element={<RegistrationPage />} />
                <Route path="/cart" element={<CartPage />} />
                <Route path="/edit" element={<EditItemPage />} />
            </Routes>
        </Router>
    );
}

export default App;
