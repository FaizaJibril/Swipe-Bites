import React, { useEffect, useState } from 'react';
import {getWebImage} from "../../services/imageWrapper";

const WebImage = ({imageId, altText, cssClass}) => {
    const [imageData, setData] = useState(undefined);
    const [loading, setLoading] = useState(true);

    let finalAltText = altText !== undefined ? altText : imageId;
    let finalClassName = cssClass !== undefined ? cssClass : "";
    const getData = async (id) => {
        let image = await getWebImage(id)
        setData(image);
    }

    useEffect(() => {
        if (loading && imageId && (imageId > 0)) {
            getData(imageId)
                .catch(error => {
                    console.error('Error fetching image:', error);
                    setLoading(false);
                });
        }
    }, [loading,imageId]);

    return (
    <>
        {
            (imageData) &&
            <img src={imageData} alt={finalAltText} className={finalClassName} />
        }
    </>
    );
}
export default WebImage;
