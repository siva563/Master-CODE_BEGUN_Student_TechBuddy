import React, { useState, useEffect } from "react";
import axios from "axios";

const ChallengePage = ({ userEmail }) => {
    const [challengeUrl, setChallengeUrl] = useState(""); // Stores the challenge URL
    const [isLoading, setIsLoading] = useState(true);    // Tracks the loading state

    useEffect(() => {
        const fetchChallengeUrl = async () => {
            try {
                // Start loading
                setIsLoading(true);

                // Fetch the challenge URL from the backend
                const response = await axios.get("http://localhost:8080/api/onecompiler/start-challenge", {
                    params: { email: "siva@gmail.com" },
                });

                // Update the challenge URL state
                setChallengeUrl(response.data);
                console.log(challengeUrl);
            } catch (error) {
                console.error("Error fetching challenge URL:", error);
                alert("Failed to load the challenge.");
            } finally {
                // Stop loading
                setIsLoading(false);
            }
        };

        fetchChallengeUrl();
    }, [userEmail]);

    return (
        <div className="challenge-container">
            <header className="challenge-header">
                <h2>Your LMS Challenge</h2>
            </header>

            {/* Loading Indicator */}
            {isLoading && <p>Loading challenge, please wait...</p>}

            {/* Render iframe only when the URL is ready */}
            {!isLoading && challengeUrl && (
                <iframe
                    src={challengeUrl}
                    width="100%"
                    height="600px"
                    frameBorder="0"
                    allowFullScreen
                    title="OneCompiler Challenge"
                ></iframe>
            )}

            {/* Error Message */}
            {!isLoading && !challengeUrl && <p>Failed to load the challenge. Please try again later.</p>}
        </div>
    );
};

export default ChallengePage;
