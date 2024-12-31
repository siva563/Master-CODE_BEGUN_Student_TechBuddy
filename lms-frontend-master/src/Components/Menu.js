import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import { FaTachometerAlt, FaUsers, FaBook, FaDollarSign, FaTasks } from "react-icons/fa";
import logo from "../assets/images/CB_Main_Logo.png";
import "./Menu.css";


const Menu = () => {
    const [isCollapsed, setIsCollapsed] = useState(false);

    const toggleSidebar = () => {
        setIsCollapsed(!isCollapsed);
    };

    return (
        <div className={`sidebar ${isCollapsed ? "collapsed" : ""}`}>
            {/* Logo Section */}
            <div className="sidebar-header">
                <img
                    src={logo} // Replace with your logo URL
                    alt="Logo"
                    className="sidebar-logo"
                />
                {/* {!isCollapsed && <h4 className="sidebar-title">Your LMS</h4>} */}
            </div>

            {/* Navigation Items */}
            <ul className="list-unstyled sidebar-menu">
                <li>
                    <NavLink to="/dashboard" className="sidebar-item">
                        <FaTachometerAlt className="sidebar-icon" />
                        {!isCollapsed && <span>Dashboard</span>}
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/batches" className="sidebar-item">
                        <FaUsers className="sidebar-icon" />
                        {!isCollapsed && <span>Batches</span>}
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/students" className="sidebar-item">
                        <FaBook className="sidebar-icon" />
                        {!isCollapsed && <span>Students</span>}
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/fees" className="sidebar-item">
                        <FaDollarSign className="sidebar-icon" />
                        {!isCollapsed && <span>Fees</span>}
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/assignments" className="sidebar-item">
                        <FaTasks className="sidebar-icon" />
                        {!isCollapsed && <span>Assignments</span>}
                    </NavLink>
                </li>
            </ul>

            {/* Collapse Button */}
            <button className="btn btn-light toggle-btn" onClick={toggleSidebar}>
                {isCollapsed ? "▶" : "◀"}
            </button>
        </div>
    );
}

export default Menu;