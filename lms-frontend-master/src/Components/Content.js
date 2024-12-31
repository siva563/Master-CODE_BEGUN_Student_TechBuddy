import React from "react";

const Content = ({ children }) => {
    return (
        <div className="content p-4">
            {children}
        </div>
    );
};

export default Content;
