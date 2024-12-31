import React, { useState } from "react";
import axios from "axios";

const StartChallenge = ({ userEmail }) => {
    const [challengeUrl, setChallengeUrl] = useState("");

    const handleStartChallenge = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/onecompiler/start-challenge", {
                params: { email: "siva@gmail.com" },
            });

            // Set the returned URL to the iframe
            console.log(response.data);
            setChallengeUrl(response.data);
            console.log(challengeUrl);
            window.open(response.data, "_blank");
        } catch (error) {
            console.error("Error starting challenge:", error);
            alert("Failed to load challenge.");
        }
    };

    return (
        <div className="container mt-5">
            <h2>OneCompiler Challenge</h2>
            <button className="btn btn-primary mb-4" onClick={handleStartChallenge}>
                Start Challenge
            </button>


            <div className="iframe-container" style={{ border: "1px solid #ddd", marginTop: "20px" }}>
                <iframe
                    src={challengeUrl}
                    width="100%"
                    height="600px"
                    frameBorder="0"
                    allowFullScreen
                    title="OneCompiler Challenge"
                ></iframe>
            </div>

        </div>
    );
};

export default StartChallenge;
