import axios from 'axios';
const baseUrl = process.env.REACT_APP_REMOTE_API;

//axios works better with the octet-stream... the upload of a 
//multipart-form is not handled well in the fetch
//and the blob is not consistantly returned in a way that
//the create works.
export const getWebImage = (id) => {
    const url = `${baseUrl}file/${id}`;
    
    let requestOptions = {        
        responseType: 'blob',
        timeout: 30000,
        mode: 'cors',
        credentials: 'same-origin',
        headers: {  
            'Content-Type': 'application/octet-stream',
        }
    };
    const token = localStorage.getItem('token');
    if (token) {
        requestOptions.headers['Authorization'] = `Bearer ${token}`;
    }    
    return axios.get(url, requestOptions).then((response) => {
            let blob = new Blob([response.data]);
            let blobUrl = URL.createObjectURL(blob);
            return blobUrl;
        });
}
export const uploadWebImage = (file, onUploadProgress) => {
    let formData = new FormData();

    formData.append("file", file);
    const url = `${baseUrl}upload/`;
    return axios.post(url, formData, {
        headers: {
            "Content-Type": "multipart/form-data",
            mode: 'cors',
            credentials: 'same-origin'
        },
        onUploadProgress,
    });
}
