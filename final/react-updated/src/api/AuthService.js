import { utilFetchWrapper } from "../services/utilFetchWrapper";
const fetchWrapper = utilFetchWrapper();


export const loginUser = (login) => {
    return fetchWrapper.post('login', login);
  }
export const registerUser = (user) => {
    return fetchWrapper.post('register', user);
  }


