import React, { useState, useEffect } from "react";
import axios from "axios";
import { FaGithub, FaLinkedin, FaInstagram } from "react-icons/fa";

const StudentManager = () => {
    const [students, setStudents] = useState([]);
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        mobileNumber: "",
        batch: "",
        courseName: "",
        alternativeMobileNumber: "",
        gender: "Male",
        dateOfBirth: "",
        address: {
            pincode: "",
            state: "",
            district: "",
            street: "",
            city: "",
            block: "",
            village: "",
        },
        education: {
            highestQualification: "",
            branch: "",
            percentage: "",
            passedOutYear: "",
            intermediateGroup: "",
            intermediateCollege: "",
            intermediatePercentage: "",
            sscPercentage: "",
            university: "",
            college: "",
            school: "",
        },
        resume: "",
        socialLinks: {
            linkedinProfile: "",
            githubProfile: "",
            instagramProfile: "",
        },
        profilePicture: "",
        status: "Active",
    });
    const [editingStudentId, setEditingStudentId] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [batches, setBatches] = useState([]);
    // const [courses, setCourses] = useState(["HTML", "CSS", "JavaScript", "React", "Spring Boot"]);
    const [villages, setVillages] = useState([]);
    const [highestQualification, setHighestQualification] = useState("");
    const [branchOptions, setBranchOptions] = useState([]);

    useEffect(() => {
        fetchStudents();
        fetchBatches();
    }, []);

    const fetchStudents = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/students/all");
            console.log(response.data);
            setStudents(response.data);
        } catch (error) {
            console.error("Error fetching students:", error);
        }
    };

    const fetchBatches = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/batches/getAllBatches");
            setBatches(response.data);
        } catch (error) {
            console.error("Error fetching batches:", error);
        }
    };

    const handleQualificationChange = (e) => {
        const qualification = e.target.value;
        setHighestQualification(qualification);
        formData.education.highestQualification = qualification;

        if (qualification === "B.Tech" || qualification === "B.E") {
            setBranchOptions(["CSE", "AI & ML", "IT", "EEE", "ECE"]);
        } else if (qualification === "Bsc") {
            setBranchOptions(["Physics", "Chemistry", "Mathematics", "Biology"]);
        } else {
            setBranchOptions([]);
        }
    };

    // const fetchAddressDetails = async (pincode) => {
    //     if (pincode.length === 6) {
    //         try {
    //             const response = await axios.get(`https://api.postalpincode.in/pincode/${pincode}`);
    //             const data = response.data;
    //             formData.address.pincode = pincode;
    //             if (data[0]?.Status === "Success") {
    //                 const postOffice = data[0].PostOffice[0]; // Taking the first post office
    //                 const postOffices = data[0].PostOffice;
    //                 const firstPostOffice = postOffices[0];
    //                 setVillages(postOffices.map((office) => office.Name));
    //                 setFormData((prev) => ({
    //                     ...prev,
    //                     ['address']: {
    //                         state: postOffice.State,
    //                         district: postOffice.District,
    //                         block: postOffice.Block || "",
    //                         village: "",
    //                         street: ""
    //                     }
    //                 }));

    //             } else {

    //             }
    //         } catch (error) {

    //             console.error(error);
    //         }
    //     }
    // };

    const fetchAddressDetails = async (pincode) => {
        if (pincode.length === 6) {
            try {
                const response = await axios.get(`https://api.postalpincode.in/pincode/${pincode}`);
                const data = response.data;
                if (data[0]?.Status === "Success") {
                    const postOffice = data[0].PostOffice[0]; // Taking the first post office
                    const postOffices = data[0].PostOffice;
                    setVillages(postOffices.map((office) => office.Name));

                    setFormData((prev) => ({
                        ...prev,
                        address: {
                            ...prev.address,
                            pincode: pincode,
                            state: postOffice.State,
                            district: postOffice.District,
                            block: postOffice.Block || "",
                            village: "",
                            street: "",
                        },
                    }));
                }
            } catch (error) {
                console.error("Error fetching address details:", error);
            }
        }
    };


    // const handleChange = (e) => {

    //     const { name, value, type, files } = e.target;


    //     if (type === "file") {
    //         setFormData({
    //             ...formData,
    //             [name]: "dGVzdCBkYXRhCg==", // Store the file object
    //         });

    //     }

    //     else if (name.includes(".")) {
    //         const [mainField, subField] = name.split(".");
    //         setFormData((prev) => ({
    //             ...prev,
    //             [mainField]: {
    //                 ...prev[mainField],
    //                 [subField]: value,
    //             },
    //         }));
    //     }
    //     else {
    //         setFormData({
    //             ...formData,
    //             [name]: value,
    //         });
    //     }
    // };

    const handleChange = async (e) => {
        const { name, value, type, files } = e.target;

        if (type === "file") {
            const file = files[0];
            const base64 = await toBase64(file);
            setFormData((prev) => ({
                ...prev,
                [name]: base64, // Store the Base64 string
            }));
        } else if (name.includes(".")) {
            const [mainField, subField] = name.split(".");
            setFormData((prev) => ({
                ...prev,
                [mainField]: {
                    ...prev[mainField],
                    [subField]: value,
                },
            }));
        } else {
            setFormData({
                ...formData,
                [name]: value,
            });
        }
    };

    const toBase64 = (file) => {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => resolve(reader.result.split(",")[1]); // Extract Base64
            reader.onerror = (error) => reject(error);
        });
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(JSON.stringify(formData));
        formData.resume = "dGVzdCBkYXRhCg==";
        formData.profilePicture = "dGVzdCBkYXRhCg==";
        try {
            if (editingStudentId) {
                await axios.put(`http://localhost:8080/api/students/update/${editingStudentId}`, formData);
                alert("Student updated successfully!");
            } else {
                await axios.post("http://localhost:8080/api/students/create", formData);
                alert("Student created successfully!");
            }
            fetchStudents();
            resetForm();
            setShowForm(false);
        } catch (error) {
            console.error("Error saving student:", error);
        }
    };

    const handleEdit = (student) => {
        setEditingStudentId(student.id);
        setFormData(student);
        setShowForm(true);
    };

    const handleDelete = async (id) => {
        if (window.confirm("Are you sure you want to delete this student?")) {
            try {
                await axios.delete(`http://localhost:8080/api/students/delete/${id}`);
                alert("Student deleted successfully!");
                fetchStudents();
            } catch (error) {
                console.error("Error deleting student:", error);
            }
        }
    };

    const resetForm = () => {
        setEditingStudentId(null);
        setFormData({
            firstName: "",
            lastName: "",
            email: "",
            mobileNumber: "",
            batch: "",
            course: "",
            alternativeMobileNumber: "",
            gender: "Male",
            dateOfBirth: "",
            address: { pincode: "", state: "", district: "" },
            education: {
                highestQualification: "",
                branch: "",
                percentage: "",
                intermediateGroup: "",
                intermediatePercentage: "",
                sscPercentage: "",
                university: "",
                college: "",
                school: "",
            },
            resume: "",
            socialLinks: { linkedin: "", github: "", instagram: "" },
            profilePicture: "",
            status: "Active",
        });
    };

    return (
        <div className="container mt-5">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 style={{ textAlign: 'center' }}>Student Management</h2>
                <button className="btn btn-primary" onClick={() => setShowForm(!showForm)}>
                    {showForm ? "Hide Form" : "Add Student"}
                </button>
            </div>

            {showForm && (
                <form className="card p-4 mb-4" onSubmit={handleSubmit}>
                    <h3>{editingStudentId ? "Edit Student" : "Add Student"}</h3>

                    <div className="row ">
                        <h4 className="mb-3 text-center">Personal Details</h4>

                        <div className="col-md-4 mt-3">
                            <label htmlFor="firstName" className="form-label">
                                First Name
                            </label>
                            <input type="text" className="form-control" id="firstName" placeholder="First Name" name="firstName" onChange={handleChange} value={formData.firstName} />
                        </div>

                        <div className="col-md-4 mt-3">
                            <label htmlFor="lastName" className="form-label">
                                Last Name
                            </label>
                            <input type="text" className="form-control" id="lastName" placeholder="Last Name" name="lastName" onChange={handleChange} value={formData.lastName} />
                        </div>

                        <div className="col-md-4 mt-3">
                            <label htmlFor="email" className="form-label">
                                Email
                            </label>
                            <input type="email" className="form-control" id="email" placeholder="Email" name="email" onChange={handleChange} value={formData.email} />
                        </div>

                        <div className="col-md-4 mt-3">
                            <label className="form-label">Gender</label>
                            <div className="d-flex gap-3">
                                <div className="form-check">
                                    <input
                                        className="form-check-input"
                                        type="radio"
                                        name="gender"
                                        id="genderMale"
                                        value="Male"
                                        onChange={handleChange}
                                        checked={formData.gender === "Male"}
                                    />
                                    <label className="form-check-label" htmlFor="genderMale">
                                        Male
                                    </label>
                                </div>
                                <div className="form-check">
                                    <input
                                        className="form-check-input"
                                        type="radio"
                                        name="gender"
                                        id="genderFemale"
                                        onChange={handleChange}
                                        value="Female"
                                        checked={formData.gender === "Female"}
                                    />
                                    <label className="form-check-label" htmlFor="genderFemale">
                                        Female
                                    </label>
                                </div>
                                <div className="form-check">
                                    <input
                                        className="form-check-input"
                                        type="radio"
                                        name="gender"
                                        id="genderOther"
                                        value="Other"
                                        onChange={handleChange}
                                        checked={formData.gender === "Other"}
                                    />
                                    <label className="form-check-label" htmlFor="genderOther">
                                        Other
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div className="col-md-4 mt-3">
                            <label htmlFor="mobileNumber" className="form-label">
                                Mobile Number
                            </label>
                            <input
                                type="tel"
                                className="form-control"
                                id="mobileNumber"
                                placeholder="Mobile Number"
                                name="mobileNumber"
                                onChange={handleChange}
                                value={formData.mobileNumber}
                            />
                        </div>


                        <div className="col-md-4 mt-3">
                            <label htmlFor="alternativeMobileNumber" className="form-label">
                                Alternative Mobile Number
                            </label>
                            <input
                                type="tel"
                                className="form-control"
                                id="alternativeMobileNumber"
                                placeholder="Alternative Mobile Number"
                                name="alternativeMobileNumber"
                                onChange={handleChange}
                                value={formData.alternativeMobileNumber}
                            />
                        </div>

                        <div className="col-md-4 mt-3 mb-4">
                            <label htmlFor="dob" className="form-label">
                                Date of Birth
                            </label>
                            <input type="date" className="form-control" id="dateOfBirth" name="dateOfBirth" onChange={handleChange} value={formData.dateOfBirth} />
                        </div>

                        <div className="col-md-4">
                            <label htmlFor="pincode" className="form-label">
                                Pincode
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                id="pincode"
                                name="address.pincode"
                                value={formData.address.pincode}
                                onChange={(e) => {
                                    handleChange(e);
                                    fetchAddressDetails(e.target.value);
                                }}
                                placeholder="Enter PIN code"
                                required
                            />
                        </div>

                        <div className="col-md-4">
                            <label htmlFor="state" className="form-label">
                                State
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                id="state"
                                name="address.state"
                                value={formData.address.state}
                                readOnly
                                placeholder="State will populate automatically"
                            />
                        </div>

                        <div className="col-md-4">
                            <label htmlFor="district" className="form-label">
                                District
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                id="district"
                                name="address.district"
                                value={formData.address.district}
                                readOnly
                                placeholder="District will populate automatically"
                            />
                        </div>

                        <div className="col-md-4">
                            <label htmlFor="block" className="form-label">
                                Block
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                id="block"
                                name="address.block"
                                value={formData.address.block}
                                readOnly
                                placeholder="Block will populate automatically"
                            />
                        </div>

                        <div className="col-md-4">
                            <label htmlFor="village" className="form-label">
                                Village
                            </label>
                            <select
                                className="form-select"
                                id="village"
                                name="address.village"
                                value={formData.address.village}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Select Village</option>
                                {villages.map((village, index) => (
                                    <option key={index} value={village}>
                                        {village}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="col-md-4">
                            <label htmlFor="village" className="form-label">
                                Street
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                id="street"
                                name="address.street"
                                value={formData.address.street}
                                placeholder="Street"
                                onChange={handleChange}
                            />
                        </div>
                    </div>

                    <div className="row mb-4 mt-5">
                        <h4 className="mb-3">Education Details</h4>
                        <div className="col-md-4">
                            <label htmlFor="highestQualification" className="form-label">
                                Highest Qualification
                            </label>
                            <select
                                className="form-select"
                                id="highestQualification"
                                value={formData.education.highestQualification}
                                onChange={handleQualificationChange}
                                name="education.highestQualification"

                            >
                                <option value="">Select Qualification</option>
                                <option value="B.Tech">B.Tech</option>
                                <option value="B.E">B.E</option>
                                <option value="MCA">MCA</option>
                                <option value="MBA">MBA</option>
                                <option value="M.Tech">M.Tech</option>
                                <option value="Msc">Msc</option>
                                <option value="Bsc">Bsc</option>
                                <option value="B.Com">B.Com</option>
                            </select>
                        </div>

                        {branchOptions.length > 0 && (
                            <div className="col-md-4">
                                <label htmlFor="branch" className="form-label">
                                    Select Branch
                                </label>
                                <select className="form-select" id="branch" name="education.branch" onChange={handleChange} value={formData.education.branch} >
                                    <option value="">Select Branch</option>
                                    {branchOptions.map((branch, index) => (
                                        <option key={index} value={branch}>
                                            {branch}
                                        </option>
                                    ))}
                                </select>
                            </div>
                        )}


                        <div className="col-md-4">
                            <label htmlFor="university" className="form-label">
                                University/College
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                id="university"
                                name="education.university"
                                placeholder="Enter University/College Name"
                                onChange={handleChange}
                                value={formData.education.university}
                            />
                        </div>
                        <div className="col-md-4">
                            <label htmlFor="university" className="form-label">
                                College
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                id="college"
                                name="education.college"
                                placeholder="Enter University/College Name"
                                onChange={handleChange}
                                value={formData.education.college}
                            />
                        </div>

                        <div className="col-md-4">
                            <label htmlFor="percentage" className="form-label">
                                Percentage/CGPA
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                id="percentage"
                                placeholder="Enter Percentage/CGPA"
                                name="education.percentage"
                                onChange={handleChange}
                                value={formData.education.percentage}
                            />
                        </div>
                        <div className="col-md-4">
                            <label htmlFor="passedOutYear" className="form-label">
                                Passed Out Year
                            </label>
                            <input
                                type="number"
                                className="form-control"
                                id="passedOutYear"
                                placeholder="Enter Passed Out Year"
                                name="education.passedOutYear"
                                onChange={handleChange}
                                value={formData.education.passedOutYear}
                            />
                        </div>
                    </div>

                    <div className="row mb-4">
                        {/* <h5 className="mb-3">+2 Details</h5> */}
                        <div className="col-md-4">
                            <label htmlFor="plusTwoStream" className="form-label">
                                Select Stream
                            </label>
                            <select className="form-select" id="plusTwoStream" name="education.intermediateGroup" onChange={handleChange} value={formData.education.intermediateGroup}>
                                <option value="">Select Stream</option>
                                <option value="MPC">MPC</option>
                                <option value="BiPC">BiPC</option>
                                <option value="CEC">CEC</option>
                            </select>
                        </div>
                        <div className="col-md-4">
                            <label htmlFor="plusTwoCollege" className="form-label">
                                College Name
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                id="plusTwoCollege"
                                placeholder="Enter College Name" name="education.intermediateCollege" onChange={handleChange} value={formData.education.intermediateCollege}
                            />
                        </div>
                        <div className="col-md-4">
                            <label htmlFor="plusTwoPercentage" className="form-label">
                                Percentage
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                id="plusTwoPercentage"
                                placeholder="Enter Percentage" name="education.intermediatePercentage" onChange={handleChange} value={formData.education.intermediatePercentage}
                            />
                        </div>
                    </div>

                    <div className="row mb-4">
                        {/* <h5 className="mb-3">10th Details</h5> */}
                        <div className="col-md-4">
                            <label htmlFor="tenthSchool" className="form-label">
                                SSC School Name
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                id="tenthSchool"
                                placeholder="Enter School Name" name="education.school" onChange={handleChange} value={formData.education.school}
                            />
                        </div>
                        <div className="col-md-4">
                            <label htmlFor="tenthPercentage" className="form-label">
                                SSC Percentage
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                id="tenthPercentage"
                                placeholder="Enter Percentage" name="education.sscPercentage" onChange={handleChange}
                                value={formData.education.sscPercentage}
                            />
                        </div>
                    </div>



                    <div className="row mb-4">
                        <h4 className="mb-3">Course Details</h4>
                        <div className="col-md-4">
                            <label htmlFor="village" className="form-label">
                                Select Batch
                            </label>
                            <select
                                className="form-select"
                                id="batch"
                                name="batch"
                                value={formData.batch}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Select Batch</option>
                                {batches.map((batch, index) => (
                                    <option key={index} value={batch.batchName}>
                                        {batch.batchName}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="col-md-4">
                            <label htmlFor="courseName" className="form-label">
                                Course Name
                            </label>
                            <select className="form-select" id="courseName" name="courseName" onChange={handleChange} value={formData.courseName}>
                                <option value="">Select Course</option>
                                <option value="jafs">Java Full Stack</option>
                                <option value="pafs">Python Full Stack</option>
                                <option value="mern">MERN</option>
                                <option value="mean">MEAN</option>
                                <option value="bedp">Java Developer</option>
                                <option value="fedp">Front End Developer</option>
                                <option value="rjdp">React JS Developer</option>
                                <option value="andp">Angular Developer</option>
                                <option value="vudp">Vue JS Developer</option>
                                <option value="dsdp">Data science</option>
                                <option value="dadp">Data Analyst</option>
                                <option value="aiml">AI and ML</option>
                                <option value="sapc">SAP</option>
                            </select>
                        </div>
                        <div className="col-md-4">
                            <label htmlFor="courseDuration" className="form-label">
                                Course Duration
                            </label>
                            <input type="text" className="form-control" id="courseDuration" placeholder="Duration" name="courseDuration" onChange={handleChange} value={formData.courseDuration} />
                        </div>
                        <div className="col-md-4">
                            <label htmlFor="courseFee" className="form-label">
                                Fee Status
                            </label>
                            <select className="form-select" id="courseFee" name="courseFee" onChange={handleChange} value={formData.courseFee}>
                                <option value="">Select Status</option>
                                <option value="paid">Paid</option>
                                <option value="unpaid">Unpaid</option>
                            </select>
                        </div>
                    </div>

                    <div className="row mb-4">
                        <h4 className="mb-3">Fee Details</h4>
                        <div className="col-md-4">
                            <label htmlFor="totalFee" className="form-label">
                                Total Fee
                            </label>
                            <input type="number" className="form-control" id="totalFee" placeholder="Total Fee" name="totalFee" onChange={handleChange} value={formData.totalFee} />
                        </div>
                        <div className="col-md-4">
                            <label htmlFor="amountPaid" className="form-label">
                                Amount Paid
                            </label>
                            <input type="number" className="form-control" id="amountPaid" placeholder="Amount Paid" name="amountPaid" onChange={handleChange} value={formData.amountPaid} />
                        </div>
                        <div className="col-md-4">
                            <label htmlFor="balanceFee" className="form-label">
                                Balance Fee
                            </label>
                            <input type="number" className="form-control" id="balanceFee" placeholder="Balance Fee" name="balanceFee" onChange={handleChange} value={formData.balanceFee} />
                        </div>
                    </div>

                    <div className="row mb-4">
                        <h5 className="mb-3">Social Profiles</h5>
                        <div className="col-md-4">
                            <label htmlFor="githubProfile" className="form-label">
                                <FaGithub className="me-2" /> GitHub Profile
                            </label>
                            <input
                                type="url"
                                className="form-control"
                                id="githubProfile"
                                placeholder="Enter GitHub Profile URL"
                                name="socialLinks.githubProfile"
                                onChange={handleChange}
                                value={formData.socialLinks.githubProfile}
                            />
                        </div>
                        <div className="col-md-4">
                            <label htmlFor="linkedinProfile" className="form-label">
                                <FaLinkedin className="me-2" /> LinkedIn Profile
                            </label>
                            <input
                                type="url"
                                className="form-control"
                                id="linkedinProfile"
                                placeholder="Enter LinkedIn Profile URL"
                                name="socialLinks.linkedinProfile"
                                onChange={handleChange}
                                value={formData.socialLinks.linkedinProfile}
                            />
                        </div>
                        <div className="col-md-4">
                            <label htmlFor="instagramProfile" className="form-label">
                                <FaInstagram className="me-2" /> Instagram Profile
                            </label>
                            <input
                                type="url"
                                className="form-control"
                                id="instagramProfile"
                                placeholder="Enter Instagram Profile URL"
                                name="socialLinks.instagramProfile"
                                onChange={handleChange}
                                value={formData.socialLinks.instagramProfile}
                            />
                        </div>
                    </div>

                    <div className="row mb-4">
                        <h5 className="mb-3">Uploads</h5>
                        <div className="col-md-6">
                            <label htmlFor="profilePic" className="form-label">
                                Profile Picture
                            </label>
                            <input type="file" className="form-control" id="profilePic" accept="image/*" name="profilePic" onChange={handleChange} />
                        </div>
                        <div className="col-md-6">
                            <label htmlFor="resume" className="form-label">
                                Resume
                            </label>
                            <input type="file" className="form-control" id="resume" accept=".pdf,.doc,.docx" name="resume" onChange={handleChange} />
                        </div>
                    </div>

                    <button type="submit" className="btn btn-success mt-3">
                        Save
                    </button>
                </form>
            )}

            <h3 className="mt-4">All Students</h3>
            <table className="table table-bordered table-hover">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Mobile</th>
                        <th>Batch</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {students.map((student) => (
                        <tr key={student.id}>
                            <td>{`${student.firstName} ${student.lastName}`}</td>
                            <td>{student.email}</td>
                            <td>{student.mobileNumber}</td>
                            <td>{student.batch}</td>
                            <td>
                                <button className="btn btn-warning btn-sm" onClick={() => handleEdit(student)}>
                                    Edit
                                </button>
                                <button className="btn btn-danger btn-sm" onClick={() => handleDelete(student.id)}>
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

export default StudentManager;
