import React from "react";
import { NavLink } from "react-router-dom";
import { FaTachometerAlt, FaUsers, FaBook, FaBars } from "react-icons/fa";
import "./Menu.css";
const Menu = ({ isCollapsed, toggleMenu }) => {
    return (
        <div
            className={`menu bg-light d-flex flex-column ${isCollapsed ? "collapsed" : ""}`}
            style={{
                width: isCollapsed ? "80px" : "250px",
                height: "calc(100vh - 60px)", // Adjust for navbar
                position: "fixed",
                top: "60px", // Below the navbar
                left: "0",
                transition: "width 0.3s ease",
                borderRight: "1px solid #ddd",
            }}
        >
            {/* Toggle Button */}
            <div
                className="toggle-header d-flex align-items-center justify-content-center py-3 border-bottom"
                onClick={toggleMenu}
                style={{ cursor: "pointer" }}
            >
                {isCollapsed ? (
                    <FaBars size={24} />
                ) : (
                    <span style={{ fontWeight: "bold", fontSize: "18px" }}>CodeBegun</span>
                )}
            </div>

            {/* Menu Items */}
            <ul className="list-unstyled flex-grow-1">
                <li>
                    <NavLink
                        to="/dashboard"
                        end
                        className={({ isActive }) =>
                            `nav-link d-flex align-items-center ${isActive ? "active" : ""}`
                        }
                    >
                        <FaTachometerAlt className="me-2" />
                        {!isCollapsed && <span>Dashboard</span>}
                    </NavLink>
                </li>
                <li>
                    <NavLink
                        to="/dashboard/batches"
                        className={({ isActive }) =>
                            `nav-link d-flex align-items-center ${isActive ? "active" : ""}`
                        }
                    >
                        <FaUsers className="me-2" />
                        {!isCollapsed && <span>Batches</span>}
                    </NavLink>
                </li>
                <li>
                    <NavLink
                        to="/dashboard/students"
                        className={({ isActive }) =>
                            `nav-link d-flex align-items-center ${isActive ? "active" : ""}`
                        }
                    >
                        <FaBook className="me-2" />
                        {!isCollapsed && <span>Students</span>}
                    </NavLink>
                </li>
            </ul>
        </div>
    );
};

export default Menu;
