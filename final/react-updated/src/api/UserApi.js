import { utilFetchWrapper } from "../services/utilFetchWrapper";
const fetchWrapper = utilFetchWrapper();

export const getUser = () => {
    return fetchWrapper.get('user/current');
}
export const getUserDetail = (id) =>{    
    return fetchWrapper.get(`user/${id}`);
}
export const saveUserDetail = (user) => {
    return fetchWrapper.post('user',user);
}
export const logoutUser = () => {
    return fetchWrapper.get('userLogout');
}