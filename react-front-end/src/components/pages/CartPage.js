import React, {useEffect, useState} from "react";
import {ListGroup} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import CartItem from "../elements/CartItem";
import {getCartItems, logOut} from "../functions/BackEndFunctions";
import MyButton from "../elements/MyButton";


const CartPage = () => {
    const [items, setItems] = useState([]);
    const navigate = useNavigate();

    let token = localStorage.getItem("token")

    useEffect(() => {

        getCartItems().then((result) => {
            if(result === false) {
                navigate("/login")
                return
            }
            setItems(result)
        })
    });

    const userButtons = (isLogged) => {
        if (isLogged) {
            return (
                <div align="right">
                    <MyButton variant="primary" func={() => navigate("/")} text="Shop"/>
                    <MyButton variant="outline-danger" func={() => logOut().then(() => navigate("/login"))}text="Logout"/>
                </div>
            )
        }
    }


    return (
        <div>
            <h1>Cart</h1>
            {userButtons(token !== null)}
            <ListGroup>
                {items.map((item) => (
                    CartItem(item)
                ))}
            </ListGroup>
        </div>
    )
}

export default CartPage