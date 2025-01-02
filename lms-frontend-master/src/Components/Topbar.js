import React from "react";
import { Dropdown } from "react-bootstrap";
import "./Topbar.css";
import logo from "../assets/images/CB_Main_Logo.png";

const Topbar = () => {
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light sticky-top">
            <div className="container-fluid">
                {/* Logo */}
                <a href="/dashboard" className="navbar-brand d-flex align-items-center">
                    <img
                        src={logo} // Replace with your logo
                        alt="Logo"
                        className="navbar-logo me-2"
                    />
                    {/* <span>Your LMS</span> */}
                </a>

                {/* Profile Dropdown */}
                <Dropdown>
                    <Dropdown.Toggle variant="light" id="profile-dropdown" className="profile-dropdown">
                        <img
                            src="https://via.placeholder.com/40" // Replace with user's profile picture
                            alt="Profile"
                            className="profile-icon rounded-circle"
                        />
                    </Dropdown.Toggle>

                    <Dropdown.Menu align="end">
                        <Dropdown.Item href="/profile">View Profile</Dropdown.Item>
                        <Dropdown.Item href="/settings">Settings</Dropdown.Item>
                        <Dropdown.Divider />
                        <Dropdown.Item href="/logout">Logout</Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
            </div>
        </nav>
    );
};

export default Topbar;
