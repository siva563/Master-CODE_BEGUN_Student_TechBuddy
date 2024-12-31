import React, { useState } from "react";
import axios from "axios";

const CreateOneCompilerUser = () => {
    const [user, setUser] = useState({ name: "", email: "" });
    const [response, setResponse] = useState(null);

    const handleChange = (e) => {
        setUser({ ...user, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const result = await axios.post("http://localhost:8080/api/onecompiler/create-user", null, {
                params: {
                    name: user.name,
                    email: user.email,
                },
            });
            setResponse(result.data);
            alert("User created successfully on OneCompiler!");
        } catch (error) {
            console.error("Error creating user:", error);
            alert("Failed to create user.");
        }
    };

    return (
        <div className="container mt-5">
            <h2>Create OneCompiler User</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label>Name:</label>
                    <input
                        type="text"
                        name="name"
                        className="form-control"
                        value={user.name}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label>Email:</label>
                    <input
                        type="email"
                        name="email"
                        className="form-control"
                        value={user.email}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-primary">
                    Create User
                </button>
            </form>

            {response && (
                <div className="mt-4">
                    <h3>Response:</h3>
                    <pre>{JSON.stringify(response, null, 2)}</pre>
                    <img src={response.thumbnail} alt="User Thumbnail" className="img-thumbnail mt-3" />
                </div>
            )}
        </div>
    );
};

export default CreateOneCompilerUser;
