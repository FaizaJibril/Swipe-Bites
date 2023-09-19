import { utilFetchWrapper } from "../services/utilFetchWrapper";
const fetchWrapper = utilFetchWrapper();

export const getUser = (id) => {
    return fetchWrapper.get(`user/${id}`);
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
export const updateUserDetail = (user) => {
    return fetchWrapper.put(`user/${user.id}`,user);
}