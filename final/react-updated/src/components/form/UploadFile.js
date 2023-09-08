import React, { useState} from "react";
import {uploadWebImage} from "../../services/imageWrapper";

const UploadFiles = (props) => {
    const [selectedFiles, setSelectedFiles] = useState(undefined);
    const [currentFile, setCurrentFile] = useState(undefined);
    const [progress, setProgress] = useState(0);
    const [message, setMessage] = useState("");

    const selectFile = (event) => {        
        if (event !== undefined) event.preventDefault();
        setSelectedFiles(event.target.files);        
    }

    const upload = () => {
        let currentFile = selectedFiles[0];
        setProgress(0);
        setCurrentFile(currentFile);
        //this calls back multiple times
        uploadWebImage(currentFile, (event) => {
                setProgress(Math.round((100 * event.loaded) / event.total));
            }).then((response) => {
                props.parentCallback(response.data);
            })
            .catch(() => {
                setProgress(0);
                setMessage("Could not upload the file!");
                setCurrentFile(undefined);
            });

            setSelectedFiles(undefined);
        }

        return (
            <div>
                {currentFile && (
                    <div className="progress">
                        <div
                            className="progress-bar progress-bar-info progress-bar-striped"
                            role="progressbar"
                            aria-valuenow={progress}
                            aria-valuemin="0"
                            aria-valuemax="100"
                            style={{ width: progress + "%" }}
                        >
                            {progress}%
                        </div>
                    </div>
                )}

                <label className="btn btn-default">
                    <input type="file" onChange={selectFile} />
                </label>

                <button
                    className="btn btn-success"
                    disabled={!selectedFiles}
                    onClick={upload}
                >Upload</button>

                <div className="alert alert-light" role="alert">
                    {message}
                </div>

            </div>
        );
}

export default UploadFiles;