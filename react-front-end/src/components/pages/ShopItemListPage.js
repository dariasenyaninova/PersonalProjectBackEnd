import React, {useEffect, useState} from "react";
import ShopItem from "../elements/ShopItem";
import {ListGroup, ListGroupItem} from "react-bootstrap";
import ProfileGroup from "../elements/ProfileGroup";
import {fetchItems} from "../functions/BackEndFunctions";

export default function ShopItemListPage() {
    const [items, setItems] = useState([]);
    const [isLogged, setLogged] = useState(false);

    useEffect(() => {
        setLogged(localStorage.getItem("token") !== null)
        fetchItems().then(items => setItems(items));
    }, []);

    return (
        <div>
            <h1>ShopItemListPage</h1>
            <ProfileGroup isLogged={isLogged}/>
            <ListGroup>
                {
                    items.map((item, index) => (
                        <ListGroupItem key={index} className="ItemCard">
                            <ShopItem item={item} isLogged={isLogged}/>
                        </ListGroupItem>
                    ))
                }
            </ListGroup>
        </div>
    )
}