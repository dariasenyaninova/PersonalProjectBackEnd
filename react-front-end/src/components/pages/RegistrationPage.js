import React, {useState} from "react";
import {Form} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import MyButton from "../elements/MyButton";

const RegistrationPage = () => {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [passwordConfirm, setPasswordConfirm] = useState('')

    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        if(password !== passwordConfirm){
            return;
        }

        const formData = {
            email: email,
            password: password
        };

        try {
            const response = await fetch('http://localhost:8080/api/v1/shop/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                throw new Error('Failed to register user');
            }

            const responseText = await response.text();

            if(responseText !== "Registration complete!"){
                console.log("no authorized");
            }else{
                navigate("/login");
            }
            console.log('User successfully register ')
        } catch (error) {
            console.error('Error register user:', error.message);
        }
    };

    return (
        <div>
            <h1>LoginPage</h1>
            <Form onSubmit={handleSubmit} >
                <Form.Group>
                    <Form.Label>Email address</Form.Label>
                    <Form.Control
                        type="email"
                        placeholder="Enter email"
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        placeholder="Enter password"
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Confirm password</Form.Label>
                    <Form.Control
                        type="password"
                        placeholder="Confirm password"
                        onChange={(e) => setPasswordConfirm(e.target.value)}
                    />
                </Form.Group>
                <MyButton variant="primary" type="submit" text="Registration"/>
                <MyButton variant="outline-primary" func={() => navigate("/login")} text="Login"/>
            </Form>
        </div>
    )
}

export default RegistrationPage;