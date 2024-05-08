import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import React from "react";
import {ListGroupItem} from "react-bootstrap";

function ShopItem(item) {
    const token = localStorage.getItem("token");

    return (
        <ListGroupItem key={item.id} className="ItemCard">
            <Card.Body>
                <Card.Title>{item.name}</Card.Title>
                <Card.Text>{item.description}</Card.Text>
                <Card.Text>Price: {item.price}</Card.Text>
                {deleteButton(item, token)}
            </Card.Body>
        </ListGroupItem>
    );
}

async function removeItemFromCart(item, token) {
    try {
        const formData = new URLSearchParams()
        formData.append('itemId', item.id)
        formData.append('token', token)

        const response = await fetch('http://localhost:8080/api/v1/shop/cart/remove', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData
        })

        if (!response.ok) {
            throw new Error('Network response was not ok')
        }

        const data = await response.text()
        window.location.reload()
        console.log(data)
    } catch (error) {
        console.error('There was an error!', error)
    }
}

const deleteButton = (item, token) => {
    return (
        <div align="right">
            <Button onClick={() => removeItemFromCart(item, token)} className="Button" variant="danger">Delete</Button>
        </div>
    )
}

export default ShopItem;