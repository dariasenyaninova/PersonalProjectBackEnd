import React from "react";
import {Button} from "react-bootstrap";

export default function MyButton({text, variant, func}) {
    return (
        <Button variant={variant} onClick={func} className="Button">{text}</Button>
    )
}