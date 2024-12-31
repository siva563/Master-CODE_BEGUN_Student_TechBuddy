import React from "react";
import { Outlet } from "react-router-dom";
import Bottom from "./Bottom";
import Topbar from "./Topbar";
//import Leftmenu from "./Leftmenu";
import Menu from "./Menu";


const Dashboard = ({ }) => {
    return (
        <div className="dashboard">
            {/* Sidebar */}
            <Menu />

            {/* Main Content */}
            <div className="main-content">
                <Topbar />
                <div className="content-area">
                    <Outlet />
                </div>
                <Bottom />
            </div>
        </div>
    );
};

export default Dashboard;
