import Card from 'react-bootstrap/Card';
import React from "react";
import {handleAddToCart} from "../functions/BackEndFunctions";
import MyButton from "./MyButton";
import {useNavigate} from "react-router-dom";

export default function ShopItem({item, isLogged}) {
    const navigate = useNavigate();
    return (
        <Card.Body>
            <Card.Title>{item.name}</Card.Title>
            <Card.Text>{item.description}</Card.Text>
            <Card.Text>Price: {item.price}</Card.Text>
            {buyButton(isLogged, item, navigate)}
        </Card.Body>
    );
}

const buyButton = (isLogged, item, navigate) => {
    if (!isLogged) return null
    return (
        <div align="right">
            <MyButton func={() => navigate("/edit", {state: {item: item, isLogged: isLogged}})} variant="outline-warning" text="Edit"/>
            <MyButton func={() => handleAddToCart(item)} variant="primary" text="Buy"/>
        </div>
    )
}
