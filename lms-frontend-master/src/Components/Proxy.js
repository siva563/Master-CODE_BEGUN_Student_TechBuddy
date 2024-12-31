import React from "react";

function Proxy() {
    return (
        <div>
            <h1>Proxy</h1>
            <iframe
                src={`http://localhost:8080/api/onecompiler/proxy-challenge?email=${userEmail}`}
                width="100%"
                height="600px"
                frameBorder="0"
                allowFullScreen
                title="OneCompiler Challenge"
            ></iframe>

        </div>
    )
}


export default Proxy;