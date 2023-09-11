import React from "react"
import IntroVideo from "./../../Video/IntroVideo.mp4"


const BackgroundVideo = () => {
    return (
        <div className = {"video-container"} >
            <video src={IntroVideo} autoPlay loop muted />
        </div>
    )
}

export default BackgroundVideo; 