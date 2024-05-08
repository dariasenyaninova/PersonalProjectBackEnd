import React, {useState} from "react";
import {Form} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {login} from "../functions/BackEndFunctions";
import MyButton from "../elements/MyButton";

const LoginPage = () => {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const navigate = useNavigate();

    const handleSubmit = async () => {
        login(email, password).then((res) => {
            if(res){
                navigate("/")
            }
        })
    };

    return (
        <div>
            <h1>LoginPage</h1>
            <Form>
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
                <MyButton variant="primary" func={() => handleSubmit()} text="Login"/>
                <MyButton variant="outline-primary" func={() => navigate("/registration")} text="Registration"/>
            </Form>
        </div>
    )
}

export default LoginPage;