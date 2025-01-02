import React, { useEffect, useState } from "react";

const DashboardContent = () => {
    const [dashboardData, setDashboardData] = useState({
        totalBatches: 0,
        totalStudents: 0,
        totalCourses: 0,
    });

    useEffect(() => {
        // Simulating an API call
        const fetchDashboardData = async () => {
            const mockData = {
                totalBatches: 10,
                totalStudents: 250,
                totalCourses: 5,
            };
            setDashboardData(mockData);
        };

        fetchDashboardData();
    }, []);

    return (
        <div>
            <h1>Dashboard Overview</h1>
            <div className="row">
                <div className="col-md-4">
                    <div className="card p-3">
                        <h3>Total Batches</h3>
                        <p>{dashboardData.totalBatches}</p>
                    </div>
                </div>
                <div className="col-md-4">
                    <div className="card p-3">
                        <h3>Total Students</h3>
                        <p>{dashboardData.totalStudents}</p>
                    </div>
                </div>
                <div className="col-md-4">
                    <div className="card p-3">
                        <h3>Total Courses</h3>
                        <p>{dashboardData.totalCourses}</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default DashboardContent;
