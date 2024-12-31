import React from "react";


const CodeEditor = () => {
//     const problemStatement = `
//     Problem Statement:
//     Write a program that takes an integer input and prints whether it is even or odd.
//   `;

    return (

        <div className="container-fluid mt-4">
            <div className="row d-flex align-items-stretch" style={{
                border: "2px solid #000", // Border style, width, and color
                borderRadius: "8px", // Optional: Rounded corners
                padding: "16px", // Optional: Space inside the border
                margin: "16px", // Optional: Space outside the border
            }}>
                {/* Problem Statement */}
                {/* <div className="col-md-4 p-3 d-flex flex-column" style={{ height: "550px" }}>
                    <h5>Problem Statement</h5>
                    <pre className="p-3 bg-white rounded flex-grow-" style={{
                        overflowY: "auto", // Enables vertical scrolling
                        overflowX: "hidden", // Disables horizontal scrolling
                        wordBreak: "break-word", // Breaks long words to prevent horizontal scrolling
                        whiteSpace: "pre-wrap", // Preserves spacing and wraps text
                    }}>{problemStatement}</pre>
                </div> */}

                {/* Code Editor */}
                <div className="col-md-12 d-flex flex-column" style={{ height: "550px" }}>
                    <h5>Code Editor</h5>
                    {/* Embed OneCompiler */}
                    {/* <iframe
                        src="https://onecompiler.com/embed?code="
                        title="OneCompiler Code Editor"
                        width="100%"
                        height="100%"
                        style={{ flexGrow: 1, border: "1px solid #ccc", borderRadius: "8px" }}
                        frameBorder="0"
                        allow="clipboard-write"
                    ></iframe> */}


                    <iframe
                        frameBorder="0"
                        style={{ flexGrow: 1, border: "1px solid #ccc", borderRadius: "8px" }}
                        height="450px"
                        width="100%"
                        src="https://onecompiler.com/challenges/434csr87j/t1?apiKey=codebegun8d6wbju4edc7anacjf7opsv823lvw4zbjbpjw3xns8g3awbq15zczbx0cxalizdh0rp5lmnzlek2gj389um632snlrufzfr5yydpq1i9dioc539golhm99ks&userApiToken=tks44aoi0roky6heamlmypqnm5lmhcv191ye2r50swmkmx0rvzr0w6k6279pw9zr89deg51n8f37ecyc9y92qoc770oho7o1"
                        title="OneCompiler Code Editor"
                        allow="clipboard-write"
                    ></iframe>
                    <div className="mt-3 text-end">
                        {/* <button className="btn btn-secondary me-2">Save</button>
                        <button className="btn btn-primary me-2">Run</button> */}
                        <button className="btn btn-success">Submit</button>
                    </div>
                </div>
            </div>
        </div>

    );
};

export default CodeEditor;
