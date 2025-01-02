import React, { useState } from "react";
import { Outlet } from "react-router-dom";

import Menu from "./Menu";
import Topbar from "./Topbar";

const Dashboard = () => {
    const [isMenuCollapsed, setIsMenuCollapsed] = useState(false);

    const toggleMenu = () => {
        setIsMenuCollapsed(!isMenuCollapsed);
    };

    return (
        <div className="dashboard-layout">
            {/* Navbar */}
            <Topbar />

            {/* Sidebar and Content Area */}
            <div className="d-flex">
                <Menu isCollapsed={isMenuCollapsed} toggleMenu={toggleMenu} />
                <div
                    className="content-container flex-grow-1"
                    style={{
                        marginLeft: isMenuCollapsed ? "80px" : "250px",
                        transition: "margin-left 0.3s ease",
                    }}
                >
                    <div className="p-4">
                        <Outlet />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
