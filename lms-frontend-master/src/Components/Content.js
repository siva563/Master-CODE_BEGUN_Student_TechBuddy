import React from "react";
import "./Content.css";
const Content = ({ data }) => {
    return (
        <div className="content-scrollable">
            <h2>{data.title}</h2>
            {data.type === "table" && (
                <table className="table table-bordered">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Details</th>
                        </tr>
                    </thead>
                    <tbody>
                        {data.items.map((item, index) => (
                            <tr key={index}>
                                <td>{index + 1}</td>
                                <td>{item.name}</td>
                                <td>{item.details}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
            {data.type === "text" && <p>{data.content}</p>}
        </div>
    );
};

export default Content;
