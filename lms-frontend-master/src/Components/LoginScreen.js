import React from 'react';
import leftSideImage from '../assets/images/LoginScreen_leftside.png';

const LoginScreen = () => {
    return (
        <div className="container d-flex align-items-center justify-content-center">
            <div className="row w-100 mt-2">
                {/* Left Column - Image */}
                <div className="col-md-6 d-none d-md-flex align-items-center justify-content-center">
                    <img
                        src={leftSideImage}
                        alt="Login Visual"
                        className="img-fluid rounded"
                    />
                </div>

                {/* Right Column - Login Form */}
                <div className="col-md-6 d-flex align-items-center justify-content-center bg-white">
                    <div className="w-75">
                        <h2 className="text-center mb-4">Welcome Coding!</h2>
                        <p className="text-center text-muted mb-4">
                            Please login to your account.
                        </p>
                        <form>
                            <div className="mb-3">
                                <label htmlFor="email" className="form-label">
                                    Email
                                </label>
                                <input
                                    type="email"
                                    className="form-control"
                                    id="email"
                                    placeholder="Enter your email"
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="password" className="form-label">
                                    Password
                                </label>
                                <input
                                    type="password"
                                    className="form-control"
                                    id="password"
                                    placeholder="Enter your password"
                                    required
                                />
                            </div>
                            <button type="submit" className="btn bg-custom text-white w-100 mb-3">
                                Login
                            </button>
                        </form>
                        <p className="text-center">
                            Don't have an account? <a href="/signup">Sign up</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LoginScreen;

