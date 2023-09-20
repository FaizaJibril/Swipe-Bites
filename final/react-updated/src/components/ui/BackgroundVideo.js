import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import IntroVideo from './../../Video/IntroVideo.mp4';

const BackgroundVideo = () => {
  const navigate = useNavigate();

//   useEffect(() => {
//     const video = document.getElementById('background-video');

//     const handleVideoEnd = () => {
//       // Route to the login page after the video ends
//       navigate('/login');
//     };

//     video.addEventListener('ended', handleVideoEnd);

//     return () => {
//       video.removeEventListener('ended', handleVideoEnd);
//     };
//   }, [navigate]);

  return (
    <div className="video-container">
      <video id="background-video" src={IntroVideo} autoPlay muted />
    </div>
  );
};

export default BackgroundVideo;
