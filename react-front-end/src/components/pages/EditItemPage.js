import Card from 'react-bootstrap/Card';
import React, {useState} from "react";
import {editItem} from "../functions/BackEndFunctions";
import MyButton from "../elements/MyButton";
import {useLocation, useNavigate} from "react-router-dom";
import {Form} from "react-bootstrap";

export default function EditItemPage() {
    const navigate = useNavigate();
    const {state} = useLocation();
    const {item, isLogged} = state


    return (
        <Card.Body className="ItemCard">
            <Form.Group>
                <Form.Label>Name</Form.Label>
                <Form.Control placeholder={item.name} onChange={(e) => item.name = e.target.value}/>

            </Form.Group>
            <Form.Group>
                <Form.Label>Description</Form.Label>
                <Form.Control placeholder={item.description} onChange={(e) => item.description = e.target.value}/>
            </Form.Group>

            <Form.Group>
                <Form.Label>Price</Form.Label>
                <Form.Control placeholder={item.price} onChange={(e) => item.price = e.target.value}/>
            </Form.Group>

            {buttons(isLogged, item ,navigate)}
        </Card.Body>
    );
}

//{id: item.id, name: name, description: description, price: price}
//  const [name, setName] = useState('')
//     const [description, setDescription] = useState('')
//     const [price, setPrice] = useState('')
const buttons = (isLogged, item, navigate) => {
    return (
        <div align="right" >
            <MyButton func={() => editItem(item).then(navigate("/"))} variant="primary" text="Save"/>
            <MyButton func={() => navigate("/")} variant="primary" text="Cancel"/>
        </div>
    )
}