import React, { useState } from "react";
import { FaGithub, FaLinkedin, FaInstagram } from "react-icons/fa";
import axios from "axios";


const RegistrationForm = () => {


    const [highestQualification, setHighestQualification] = useState("");
    const [branchOptions, setBranchOptions] = useState([]);
    const [villages, setVillages] = useState([]);
    const [user, setUser] = useState({
        firstName: "",
        lastName: "",
        email: "",
        gender: "",
        mobileNumber: "",
        altMobileNumber: "",
        dob: "",
        highestQualification: "",
        branch: "",
        university: "",
        percentage: "",
        passedOutYear: "",
        plusTwoStream: "",
        plusTwoCollege: "",
        plusTwoPercentage: "",
        tenthSchool: "",
        tenthPercentage: "",
        courseName: "",
        courseDuration: "",
        courseFee: "",
        totalFee: "",
        amountPaid: "",
        balanceFee: "",
        githubProfile: "",
        linkedinProfile: "",
        instagramProfile: "",
        profilePic: "",
        resume: "",
        street: "",
        city: "",
        pincode: "",
        state: "",
        district: "",
        block: "",
        village: "",
        role: "Student",
    });

    const handleQualificationChange = (e) => {
        const qualification = e.target.value;
        setHighestQualification(qualification);

        if (qualification === "B.Tech" || qualification === "B.E") {
            setBranchOptions(["CSE", "AI & ML", "IT", "EEE", "ECE"]);
        } else if (qualification === "Bsc") {
            setBranchOptions(["Physics", "Chemistry", "Mathematics", "Biology"]);
        } else {
            setBranchOptions([]);
        }
    };

    const fetchAddressDetails = async (pincode) => {
        if (pincode.length === 6) {
            try {
                const response = await axios.get(`https://api.postalpincode.in/pincode/${pincode}`);
                const data = response.data;

                if (data[0]?.Status === "Success") {
                    const postOffice = data[0].PostOffice[0]; // Taking the first post office
                    const postOffices = data[0].PostOffice;
                    const firstPostOffice = postOffices[0];
                    setVillages(postOffices.map((office) => office.Name));
                    setUser((prevData) => ({
                        ...prevData,
                        state: postOffice.State,
                        district: postOffice.District,
                        block: postOffice.Block || "",
                        village: "",
                    }));
                    // setErrorMessage("");
                } else {
                    // setErrorMessage("Invalid PIN code. Please try again.");
                }
            } catch (error) {
                // setErrorMessage("Error fetching address details. Please try later.");
                console.error(error);
            }
        }
    };

    const handleUserInput = (e) => {
        setUser({ ...user, [e.target.name]: e.target.value });
    };


    const saveUser = (e) => {

        e.preventDefault();
        console.log(JSON.stringify(user));

        // let jwtToken;

        // fetch("http://localhost:8080/api/user/register", {
        //     method: "POST",
        //     headers: {
        //         Accept: "application/json",
        //         "Content-Type": "application/json",
        //         //    Authorization: "Bearer " + jwtToken,
        //     },
        //     body: JSON.stringify(user),
        // })
        //     .then((result) => {
        //         console.log("result", result);
        //         result.json().then((res) => {
        //             if (res.success) {
        //                 toast.success(res.responseMessage, {
        //                     position: "top-center",
        //                     autoClose: 1000,
        //                     hideProgressBar: false,
        //                     closeOnClick: true,
        //                     pauseOnHover: true,
        //                     draggable: true,
        //                     progress: undefined,
        //                 });

        //                 setTimeout(() => {
        //                     navigate("/user/login");
        //                 }, 1000);
        //             } else if (!res.success) {
        //                 toast.error(res.responseMessage, {
        //                     position: "top-center",
        //                     autoClose: 1000,
        //                     hideProgressBar: false,
        //                     closeOnClick: true,
        //                     pauseOnHover: true,
        //                     draggable: true,
        //                     progress: undefined,
        //                 });

        //                 setTimeout(() => {
        //                     window.location.reload(true);
        //                 }, 1000); // Redirect after 3 seconds
        //             } else {
        //                 toast.error("It seems server is down", {
        //                     position: "top-center",
        //                     autoClose: 1000,
        //                     hideProgressBar: false,
        //                     closeOnClick: true,
        //                     pauseOnHover: true,
        //                     draggable: true,
        //                     progress: undefined,
        //                 });

        //                 setTimeout(() => {
        //                     window.location.reload(true);
        //                 }, 1000); // Redirect after 3 seconds
        //             }
        //         });
        //     })
        //     .catch((error) => {
        //         console.error(error);
        //         toast.error("It seems server is down", {
        //             position: "top-center",
        //             autoClose: 1000,
        //             hideProgressBar: false,
        //             closeOnClick: true,
        //             pauseOnHover: true,
        //             draggable: true,
        //             progress: undefined,
        //         });
        //         setTimeout(() => {
        //             window.location.reload(true);
        //         }, 1000); // Redirect after 3 seconds
        //     });
    };

    return (
        <div className="container mt-5">
            <h2 className="text-center mb-4">Student Registration</h2>
            <form onSubmit={saveUser}>
                {/* Personal Details */}

                <div className="row mb-4">

                    <h4 className="mb-3">Personal Details</h4>

                    <div className="col-md-4 mt-3">
                        <label htmlFor="firstName" className="form-label">
                            First Name
                        </label>
                        <input type="text" className="form-control" id="firstName" placeholder="First Name" name="firstName" onChange={handleUserInput} value={user.firstName} />
                    </div>

                    <div className="col-md-4 mt-3">
                        <label htmlFor="lastName" className="form-label">
                            Last Name
                        </label>
                        <input type="text" className="form-control" id="lastName" placeholder="Last Name" name="lastName" onChange={handleUserInput} value={user.lastName} />
                    </div>

                    <div className="col-md-4 mt-3">
                        <label htmlFor="email" className="form-label">
                            Email
                        </label>
                        <input type="email" className="form-control" id="email" placeholder="Email" name="email" onChange={handleUserInput} value={user.email} />
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
                                    onChange={handleUserInput}
                                    checked={user.gender === "Male"}
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
                                    onChange={handleUserInput}
                                    value="Female"
                                    checked={user.gender === "Female"}
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
                                    onChange={handleUserInput}
                                    checked={user.gender === "Other"}
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
                            onChange={handleUserInput}
                            value={user.mobileNumber}
                        />
                    </div>

                    <div className="col-md-4 mt-3">
                        <label htmlFor="altMobileNumber" className="form-label">
                            Alternative Mobile Number
                        </label>
                        <input
                            type="tel"
                            className="form-control"
                            id="altMobileNumber"
                            placeholder="Alternative Mobile Number"
                            name="altMobileNumber"
                            onChange={handleUserInput}
                            value={user.altMobileNumber}
                        />
                    </div>

                    <div className="col-md-4 mt-3 mb-4">
                        <label htmlFor="dob" className="form-label">
                            Date of Birth
                        </label>
                        <input type="date" className="form-control" id="dob" name="dob" onChange={handleUserInput} value={user.dob} />
                    </div>


                    <div className="col-md-4">
                        <label htmlFor="pincode" className="form-label">
                            Pincode
                        </label>
                        <input
                            type="text"
                            className="form-control"
                            id="pincode"
                            name="pincode"
                            value={user.pincode}
                            onChange={(e) => {
                                handleUserInput(e);
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
                            name="state"
                            value={user.state}
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
                            name="district"
                            value={user.district}
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
                            name="block"
                            value={user.block}
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
                            name="village"
                            value={user.village}
                            onChange={handleUserInput}
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
                            name="street"
                            value={user.street}
                            placeholder="Street"
                        />
                    </div>
                </div>

                <hr></hr>

                {/* Education Details */}
                <div className="row mb-4 mt-5">
                    <h4 className="mb-3">Education Details</h4>
                    <div className="col-md-4">
                        <label htmlFor="highestQualification" className="form-label">
                            Highest Qualification
                        </label>
                        <select
                            className="form-select"
                            id="highestQualification"
                            value={user.highestQualification}
                            onChange={handleQualificationChange}
                            name="highestQualification"

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
                            <select className="form-select" id="branch" name="branch" onChange={handleUserInput} value={user.branch} >
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
                            name="university"
                            placeholder="Enter University/College Name"
                            onChange={handleUserInput}
                            value={user.university}
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
                            name="percentage"
                            onChange={handleUserInput}
                            value={user.percentage}
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
                            name="passedOutYear"
                            onChange={handleUserInput}
                            value={user.passedOutYear}
                        />
                    </div>
                </div>
                <div className="row mb-4">
                    {/* <h5 className="mb-3">+2 Details</h5> */}
                    <div className="col-md-4">
                        <label htmlFor="plusTwoStream" className="form-label">
                            Select Stream
                        </label>
                        <select className="form-select" id="plusTwoStream" name="plusTwoStream" onChange={handleUserInput} value={user.plusTwoStream}>
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
                            placeholder="Enter College Name" name="plusTwoCollege" onChange={handleUserInput} value={user.plusTwoCollege}
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
                            placeholder="Enter Percentage" name="plusTwoPercentage" onChange={handleUserInput} value={user.plusTwoPercentage}
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
                            placeholder="Enter School Name" name="tenthSchool" onChange={handleUserInput} value={user.tenthSchool}
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
                            placeholder="Enter Percentage" name="tenthPercentage" onChange={handleUserInput}
                        />
                    </div>
                </div>

                {/* Course Details */}
                <div className="row mb-4">
                    <h4 className="mb-3">Course Details</h4>
                    <div className="col-md-4">
                        <label htmlFor="courseName" className="form-label">
                            Course Name
                        </label>
                        <select className="form-select" id="courseName" name="courseName" onChange={handleUserInput} value={user.courseName}>
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
                        <input type="text" className="form-control" id="courseDuration" placeholder="Duration" name="courseDuration" onChange={handleUserInput} value={user.courseDuration} />
                    </div>
                    <div className="col-md-4">
                        <label htmlFor="courseFee" className="form-label">
                            Fee Status
                        </label>
                        <select className="form-select" id="courseFee" name="courseFee" onChange={handleUserInput} value={user.courseFee}>
                            <option value="">Select Status</option>
                            <option value="paid">Paid</option>
                            <option value="unpaid">Unpaid</option>
                        </select>
                    </div>
                </div>

                {/* Fee Details */}
                <div className="row mb-4">
                    <h4 className="mb-3">Fee Details</h4>
                    <div className="col-md-4">
                        <label htmlFor="totalFee" className="form-label">
                            Total Fee
                        </label>
                        <input type="number" className="form-control" id="totalFee" placeholder="Total Fee" name="totalFee" onChange={handleUserInput} value={user.totalFee} />
                    </div>
                    <div className="col-md-4">
                        <label htmlFor="amountPaid" className="form-label">
                            Amount Paid
                        </label>
                        <input type="number" className="form-control" id="amountPaid" placeholder="Amount Paid" name="amountPaid" onChange={handleUserInput} value={user.amountPaid} />
                    </div>
                    <div className="col-md-4">
                        <label htmlFor="balanceFee" className="form-label">
                            Balance Fee
                        </label>
                        <input type="number" className="form-control" id="balanceFee" placeholder="Balance Fee" name="balanceFee" onChange={handleUserInput} value={user.balanceFee} />
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
                            name="githubProfile"
                            onChange={handleUserInput}
                            value={user.githubProfile}
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
                            name="linkedinProfile"
                            onChange={handleUserInput}
                            value={user.linkedinProfile}
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
                            name="instagramProfile"
                            onChange={handleUserInput}
                            value={user.instagramProfile}
                        />
                    </div>
                </div>

                <div className="row mb-4">
                    <h5 className="mb-3">Uploads</h5>
                    <div className="col-md-6">
                        <label htmlFor="profilePic" className="form-label">
                            Profile Picture
                        </label>
                        <input type="file" className="form-control" id="profilePic" accept="image/*" name="profilePic" onChange={handleUserInput} value={user.profilePic} />
                    </div>
                    <div className="col-md-6">
                        <label htmlFor="resume" className="form-label">
                            Resume
                        </label>
                        <input type="file" className="form-control" id="resume" accept=".pdf,.doc,.docx" name="resume" onChange={handleUserInput} value={user.resume} />
                    </div>
                </div>

                {/* Submit Button */}
                <div className="text-center mt-5 mb-5">
                    <button type="submit" className="btn bg-custom text-white">
                        Register
                    </button>
                </div>
            </form>
        </div>
    );
};

export default RegistrationForm;
