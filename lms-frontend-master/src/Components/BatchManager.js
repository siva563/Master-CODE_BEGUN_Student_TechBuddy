// import React, { useEffect, useState } from "react";
// import axios from "axios";

// const BatchManager = () => {
//     const [batches, setBatches] = useState([]);
//     const [formData, setFormData] = useState({
//         batchNo: "",
//         batchName: "",
//         startTime: "",
//         endTime: "",
//         slots: "",
//         noOfDays: "",
//         createdBy: "",
//         batchType: "online",
//         course: ""
//     });
//     const [editingBatchId, setEditingBatchId] = useState(null);
//     const [showForm, setShowForm] = useState(false); // State to control form visibility

//     const courses = ["HTML", "CSS", "JavaScript", "React", "Spring Boot"];

//     useEffect(() => {
//         fetchBatches();
//     }, []);

//     const fetchBatches = async () => {
//         try {
//             const response = await axios.get("http://localhost:8080/api/batch/all");
//             setBatches(response.data);
//         } catch (error) {
//             console.error("Error fetching batches:", error);
//         }
//     };

//     const handleChange = (e) => {
//         setFormData({
//             ...formData,
//             [e.target.name]: e.target.value
//         });
//     };

//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             if (editingBatchId) {
//                 await axios.put(`http://localhost:8080/api/batch/update/${editingBatchId}`, formData);
//                 alert("Batch updated successfully!");
//             } else {
//                 await axios.post("http://localhost:8080/api/batch/create", formData);
//                 alert("Batch created successfully!");
//             }
//             fetchBatches();
//             resetForm();
//             setShowForm(false); // Hide form after submission
//         } catch (error) {
//             console.error("Error saving batch:", error);
//         }
//     };

//     const handleEdit = (batch) => {
//         setEditingBatchId(batch.id);
//         setFormData({
//             batchNo: batch.batchNo,
//             batchName: batch.batchName,
//             startTime: batch.startTime,
//             endTime: batch.endTime,
//             slots: batch.slots,
//             noOfDays: batch.noOfDays,
//             createdBy: batch.createdBy,
//             batchType: batch.batchType.toLowerCase(),
//             course: batch.course
//         });
//         setShowForm(true); // Show form when editing
//     };

//     const handleDelete = async (id) => {
//         if (window.confirm("Are you sure you want to delete this batch?")) {
//             try {
//                 await axios.delete(`http://localhost:8080/api/batch/delete/${id}`);
//                 alert("Batch deleted successfully!");
//                 fetchBatches();
//             } catch (error) {
//                 console.error("Error deleting batch:", error);
//             }
//         }
//     };

//     const resetForm = () => {
//         setEditingBatchId(null);
//         setFormData({
//             batchNo: "",
//             batchName: "",
//             startTime: "",
//             endTime: "",
//             slots: "",
//             noOfDays: "",
//             createdBy: "",
//             batchType: "online",
//             course: ""
//         });
//     };

//     return (
//         <div className="container mt-5">
//             <div className="d-flex justify-content-between align-items-center mb-4">
//                 <h2>Batch Management</h2>
//                 <button className="btn btn-primary" onClick={() => setShowForm(!showForm)}>
//                     {showForm ? "Hide Form" : "Create Batch"}
//                 </button>
//             </div>

//             {showForm && (
//                 <form className="card p-4 mb-5 shadow" onSubmit={handleSubmit}>
//                     <div className="row">
//                         <div className="col-md-6 mb-3">
//                             <label className="form-label">Batch No:</label>
//                             <input
//                                 type="text"
//                                 name="batchNo"
//                                 className="form-control"
//                                 value={formData.batchNo}
//                                 onChange={handleChange}
//                                 required
//                             />
//                         </div>
//                         <div className="col-md-6 mb-3">
//                             <label className="form-label">Batch Name:</label>
//                             <input
//                                 type="text"
//                                 name="batchName"
//                                 className="form-control"
//                                 value={formData.batchName}
//                                 onChange={handleChange}
//                                 required
//                             />
//                         </div>
//                         <div className="col-md-6 mb-3">
//                             <label className="form-label">Start Time:</label>
//                             <input
//                                 type="datetime-local"
//                                 name="startTime"
//                                 className="form-control"
//                                 value={formData.startTime}
//                                 onChange={handleChange}
//                                 required
//                             />
//                         </div>
//                         <div className="col-md-6 mb-3">
//                             <label className="form-label">End Time:</label>
//                             <input
//                                 type="datetime-local"
//                                 name="endTime"
//                                 className="form-control"
//                                 value={formData.endTime}
//                                 onChange={handleChange}
//                                 required
//                             />
//                         </div>
//                         <div className="col-md-6 mb-3">
//                             <label className="form-label">Slots:</label>
//                             <input
//                                 type="number"
//                                 name="slots"
//                                 className="form-control"
//                                 value={formData.slots}
//                                 onChange={handleChange}
//                                 required
//                             />
//                         </div>
//                         <div className="col-md-6 mb-3">
//                             <label className="form-label">No of Days:</label>
//                             <input
//                                 type="number"
//                                 name="noOfDays"
//                                 className="form-control"
//                                 value={formData.noOfDays}
//                                 onChange={handleChange}
//                                 required
//                             />
//                         </div>
//                         <div className="col-md-6 mb-3">
//                             <label className="form-label">Created By:</label>
//                             <input
//                                 type="text"
//                                 name="createdBy"
//                                 className="form-control"
//                                 value={formData.createdBy}
//                                 onChange={handleChange}
//                                 required
//                             />
//                         </div>
//                         {/* <div className="col-md-6 mb-3">
//                             <label className="form-label">Batch Type:</label>
//                             <select
//                                 name="batchType"
//                                 className="form-select"
//                                 value={formData.batchType}
//                                 onChange={handleChange}
//                                 required
//                             >
//                                 <option value="online">Online</option>
//                                 <option value="offline">Offline</option>
//                             </select>
//                         </div> */}
//                         <div className="col-md-6 mb-3">
//                             <label>Batch Type:</label>
//                             <select
//                                 name="batchType"
//                                 className="form-select"
//                                 value={formData.batchType}
//                                 onChange={handleChange}
//                                 required
//                             >
//                                 <option value="ONLINE">Online</option>
//                                 <option value="OFFLINE">Offline</option>
//                             </select>
//                         </div>

//                         <div className="col-md-12 mb-3">
//                             <label className="form-label">Course:</label>
//                             <select
//                                 name="course"
//                                 className="form-select"
//                                 value={formData.course}
//                                 onChange={handleChange}
//                                 required
//                             >
//                                 <option value="">Select Course</option>
//                                 {courses.map((course, index) => (
//                                     <option key={index} value={course}>
//                                         {course}
//                                     </option>
//                                 ))}
//                             </select>
//                         </div>
//                     </div>
//                     <div className="text-center">
//                         <button type="submit" className="btn btn-primary me-2">
//                             {editingBatchId ? "Update Batch" : "Create Batch"}
//                         </button>
//                         {editingBatchId && (
//                             <button type="button" className="btn btn-secondary" onClick={resetForm}>
//                                 Cancel
//                             </button>
//                         )}
//                     </div>
//                 </form>
//             )}

//             <h3 className="mb-3 text-center">Existing Batches</h3>
//             <table className="table table-bordered table-striped table-hover">
//                 <thead className="table-dark">
//                     <tr>
//                         <th>Batch No</th>
//                         <th>Batch Name</th>
//                         <th>Start Time</th>
//                         <th>End Time</th>
//                         <th>Slots</th>
//                         <th>No of Days</th>
//                         <th>Created By</th>
//                         <th>Batch Type</th>
//                         <th>Course</th>
//                         <th>Actions</th>
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {batches.map((batch) => (
//                         <tr key={batch.id}>
//                             <td>{batch.batchNo}</td>
//                             <td>{batch.batchName}</td>
//                             <td>{batch.startTime}</td>
//                             <td>{batch.endTime}</td>
//                             <td>{batch.slots}</td>
//                             <td>{batch.noOfDays}</td>
//                             <td>{batch.createdBy}</td>
//                             <td>{batch.batchType}</td>
//                             <td>{batch.course}</td>
//                             <td>
//                                 <button className="btn btn-warning btn-sm me-2" onClick={() => handleEdit(batch)}>
//                                     Edit
//                                 </button>
//                                 <button className="btn btn-danger btn-sm" onClick={() => handleDelete(batch.id)}>
//                                     Delete
//                                 </button>
//                             </td>
//                         </tr>
//                     ))}
//                 </tbody>
//             </table>
//         </div>
//     );
// };

// export default BatchManager;


import React, { useEffect, useState } from "react";
import axios from "axios";

const BatchManager = () => {
    const [batches, setBatches] = useState([]);
    const [formData, setFormData] = useState({
        batchName: "",
        startDate: "",
        endDate: "",
        batchType: "OFFLINE", // Default value
        courseType: "",
        frontendTrainerName: "",
        backendTrainerName: "",
        batchTimings: ""
    });
    const [editingBatchId, setEditingBatchId] = useState(null);
    const [showForm, setShowForm] = useState(false); // State to control form visibility

    const courses = ["HTML", "CSS", "JavaScript", "React", "Spring Boot"];

    useEffect(() => {
        fetchBatches();
    }, []);

    const fetchBatches = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/batches/getAllBatches");
            setBatches(response.data);
            console.log(response.data);
        } catch (error) {
            console.error("Error fetching batches:", error);
        }
    };

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (editingBatchId) {
                await axios.put(`http://localhost:8080/api/batches/${editingBatchId}`, formData);
                alert("Batch updated successfully!");
            } else {
                await axios.post("http://localhost:8080/api/batches/createBatch", formData);
                alert("Batch created successfully!");
            }
            fetchBatches();
            resetForm();
            setShowForm(false); // Hide form after submission
        } catch (error) {
            console.error("Error saving batch:", error);
        }
    };

    const handleEdit = (batch) => {
        console.log(batch);
        setEditingBatchId(batch.id);


        // const formatDate = (dateString) => {
        //     if (!dateString) return "";
        //     const date = new Date(dateString);
        //     return date.toISOString().slice(0, 10); // Format as "YYYY-MM-DD"
        // };

        const formatDateFromArray = (dateArray) => {
            if (!Array.isArray(dateArray) || dateArray.length < 3) return "";
            const [year, month, day] = dateArray;
            return `${year}-${String(month).padStart(2, "0")}-${String(day).padStart(2, "0")}`;
        };
    

        // Format batchTimings array
        const formatBatchTimings = (timings) => {
            if (!Array.isArray(timings) || timings.length < 2) return "";
            const [hours, minutes] = timings;
            return `${hours.toString().padStart(2, "0")}:${minutes.toString().padStart(2, "0")}`; // Format as "HH:mm"
        };
        setFormData({
            batchName: batch.batchName,
            startDate: formatDateFromArray(batch.startDate),
            endDate: formatDateFromArray(batch.endDate),
            batchType: batch.batchType,
            courseType: batch.courseType,
            frontendTrainerName: batch.frontendTrainerName,
            backendTrainerName: batch.backendTrainerName,
            batchTimings: formatBatchTimings(batch.batchTimings)
        });
        setShowForm(true); // Show form when editing
    };

    const handleDelete = async (id) => {
        if (window.confirm("Are you sure you want to delete this batch?")) {
            try {
                await axios.delete(`http://localhost:8080/api/batches/${id}`);
                alert("Batch deleted successfully!");
                fetchBatches();
            } catch (error) {
                console.error("Error deleting batch:", error);
            }
        }
    };

    const resetForm = () => {
        setEditingBatchId(null);
        setFormData({
            batchName: "",
            startDate: "",
            endDate: "",
            batchType: "ONLINE",
            courseType: "",
            frontendTrainerName: "",
            backendTrainerName: "",
            batchTimings: ""
        });
    };

    const formatTimeArray = (timeArray) => {
        if (!Array.isArray(timeArray) || timeArray.length < 2) return "Invalid Time";

        const [hours, minutes] = timeArray;
        const date = new Date();
        date.setHours(hours, minutes, 0); // Set hours and minutes

        return date.toLocaleTimeString("en-US", {
            hour: "2-digit",
            minute: "2-digit",
            hour12: true, // Change to false for 24-hour format
        });
    };

    return (
        <div className="container mt-5">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2>Batch Management</h2>
                <button className="btn btn-primary" onClick={() => setShowForm(!showForm)}>
                    {showForm ? "Hide Form" : "Create Batch"}
                </button>
            </div>

            {showForm && (
                <form className="card p-4 mb-5 shadow" onSubmit={handleSubmit}>
                    <div className="row">
                        <div className="col-md-6 mb-3">
                            <label className="form-label">Batch Name:</label>
                            <input
                                type="text"
                                name="batchName"
                                className="form-control"
                                value={formData.batchName}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="col-md-6 mb-3">
                            <label className="form-label">Start Date:</label>
                            <input
                                type="date"
                                name="startDate"
                                className="form-control"
                                value={formData.startDate}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="col-md-6 mb-3">
                            <label className="form-label">End Date:</label>
                            <input
                                type="date"
                                name="endDate"
                                className="form-control"
                                value={formData.endDate}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="col-md-6 mb-3">
                            <label className="form-label">Batch Type:</label>
                            <select
                                name="batchType"
                                className="form-select"
                                value={formData.batchType}
                                onChange={handleChange}
                                required
                            >
                                <option value="ONLINE">Online</option>
                                <option value="OFFLINE">Offline</option>
                            </select>
                        </div>
                        <div className="col-md-6 mb-3">
                            <label className="form-label">Course:</label>
                            <select
                                name="courseType"
                                className="form-select"
                                value={formData.courseType}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Select Course</option>
                                {courses.map((course, index) => (
                                    <option key={index} value={course}>
                                        {course}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="col-md-6 mb-3">
                            <label className="form-label">Frontend Trainer Name:</label>
                            <input
                                type="text"
                                name="frontendTrainerName"
                                className="form-control"
                                value={formData.frontendTrainerName}
                                onChange={handleChange}
                            />
                        </div>
                        <div className="col-md-6 mb-3">
                            <label className="form-label">Backend Trainer Name:</label>
                            <input
                                type="text"
                                name="backendTrainerName"
                                className="form-control"
                                value={formData.backendTrainerName}
                                onChange={handleChange}
                            />
                        </div>
                        <div className="col-md-6 mb-3">
                            <label className="form-label">Backend Timings:</label>
                            <input
                                type="time"
                                name="batchTimings"
                                className="form-control"
                                value={formData.batchTimings}
                                onChange={handleChange}
                            />
                        </div>
                    </div>
                    <div className="text-center">
                        <button type="submit" className="btn btn-primary me-2">
                            {editingBatchId ? "Update Batch" : "Create Batch"}
                        </button>
                        {editingBatchId && (
                            <button type="button" className="btn btn-secondary" onClick={resetForm}>
                                Cancel
                            </button>
                        )}
                    </div>
                </form>
            )}

            <h3 className="mb-3 text-center">Existing Batches</h3>
            <table className="table table-bordered table-striped table-hover">
                <thead className="table-dark">
                    <tr>
                        <th>Batch Name</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Batch Time</th>
                        <th>Batch Type</th>
                        <th>course Type</th>
                        <th>Frontend Trainer</th>
                        <th>Backend Trainer</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {batches.map((batch) => (
                        <tr key={batch.id}>
                            <td>{batch.batchName}</td>
                            <td>{new Date(batch.startDate).toLocaleString()}</td>
                            <td>{new Date(batch.endDate).toLocaleString()}</td>
                            <td> {formatTimeArray(batch.batchTimings)}</td>
                            <td>{batch.batchType}</td>
                            <td>{batch.courseType}</td>
                            <td>{batch.frontendTrainerName}</td>
                            <td>{batch.backendTrainerName}</td>
                            <td>
                                <button className="btn btn-warning btn-sm me-2" onClick={() => handleEdit(batch)}>
                                    Edit
                                </button>
                                <button className="btn btn-danger btn-sm" onClick={() => handleDelete(batch.id)}>
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default BatchManager;
