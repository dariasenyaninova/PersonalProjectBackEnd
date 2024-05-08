export async function fetchItems() {
    try {
        const response = await fetch('http://localhost:8080/api/v1/shop/all-items')
        if (!response.ok) {
            throw new Error('Failed to fetch items')
        }
        return await response.json()
    } catch (error) {
        console.error(error)
        return []
    }
}

export async function handleAddToCart(item) {
    try {
        let token = localStorage.getItem("token")

        const formData = new URLSearchParams()
        formData.append('itemId', item.id)
        formData.append('token', token)

        const response = await fetch('http://localhost:8080/api/v1/shop/cart/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData
        })

        if (!response.ok) {
            throw new Error('Network response was not ok')
        }
        return await response.text()
    } catch (error) {
        console.error('There was an error!', error)
    }
}

export async function getCartItems() {
    let token = localStorage.getItem("token")
    if(token === null) {
        // navigate("/login")
        return false
    }

    try {
        const response = await fetch('http://localhost:8080/api/v1/shop/cart?token=' + token);
        if (!response.ok) {
            throw new Error('Failed to fetch items');
        }
        return await response.json()
    } catch (error) {
        return false
    }


}
export async function logOut() {
    try {
        let token = localStorage.getItem("token")
        if (token === null) return false
        const formData = new URLSearchParams()
        formData.append('token', token)

        const response = await fetch('http://localhost:8080/api/v1/shop/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData
        })

        if (!response.ok) {
            throw new Error('Network response was not ok')
        }

        const data = await response.text()
        if (data === "you logged out") {
            localStorage.removeItem("token")

            return true
        }
        console.log(data)
    } catch (error) {
        console.error('There was an error!', error)
        return false
    }
}

export async function login(email, password) {
    const formData = {
        email: email,
        password: password
    };

    try {
        const response = await fetch('http://localhost:8080/api/v1/shop/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        if (!response.ok) {
            throw new Error('Failed to login user');
        }

        const token = await response.text();

        if (token === "no authorized") {
            console.log("no authorized");
            return false
        } else {
            localStorage.setItem("token", token);
            console.log('User successfully login ' + token)
            return true
        }
    } catch (error) {
        console.error('Error login user:', error.message);
        return false
    }
}

export async function editItem(item) {
    try {

        const response = await fetch('http://localhost:8080/api/v1/shop/edit-item', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item)
        })

        if (!response.ok) {
            throw new Error('Network response was not ok')
        }
        return await response.text()
    } catch (error) {
        console.error('There was an error!', error)
    }
}

