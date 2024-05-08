import React from "react";
import MyButton from "./MyButton";
import {logOut} from "../functions/BackEndFunctions";
import {useNavigate} from "react-router-dom";


export default function ProfileGroup({isLogged}){
    const navigate = useNavigate();
    if (isLogged) {
        return (
            <div align="right">
                <MyButton variant="success" func={() => navigate("/cart")} text="Cart"/>
                <MyButton variant="outline-danger" func={() => logOut().then(() => navigate("/login"))} text="Log out"/>
            </div>
        )
    }
    return (
        <div align="right">
            <MyButton func={() => navigate("/login")} text="Log in"/>
            <MyButton func={() => navigate("/registration")} text="Registration"/>
        </div>
    )
}