import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import IntroVideo from './../../Video/IntroVideo.mp4';
import './Background.css';

const BackgroundVideo = () => {
  const navigate = useNavigate();

  return (
    <div className="video-container">
      <video id="background-video" src={IntroVideo} autoPlay muted loop className="fullscreen-video"/>
    </div>
  );
};

export default BackgroundVideo;
