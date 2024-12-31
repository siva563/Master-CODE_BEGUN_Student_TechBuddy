import React from "react";
import { Link } from "react-router-dom";

const Leftmenu = () => {
    return (
        <div className="sidebar bg-dark text-white vh-100">
            <h4 className="text-center py-3">Admin Panel</h4>
            <ul className="nav flex-column">
                <li className="nav-item">
                    <Link to="/dashboard" className="nav-link text-white">
                        Dashboard
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to="/dashboard/batches" className="nav-link text-white">
                        Batches
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to="/students" className="nav-link text-white">
                        Students
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to="/fees" className="nav-link text-white">
                        Fees
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to="/assignments" className="nav-link text-white">
                        Assignments
                    </Link>
                </li>
            </ul>
        </div>
    );
};

export default Leftmenu;
