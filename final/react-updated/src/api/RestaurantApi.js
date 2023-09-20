import { utilFetchWrapper } from "../services/utilFetchWrapper";
const fetchWrapper = utilFetchWrapper();



export const like = (id) => {
    return fetchWrapper.post(`restaurant/${id}/like`, null);
  }


  export const dislike = (id) => {
    return fetchWrapper.post(`restaurant/${id}/disLiked`, null);
  }

  export const liked = (userid) => {
    return fetchWrapper.get(`restaurant/${userid}/liked`, null);
  }

  
  export const booking = (bookingRequest) => {
    return fetchWrapper.post(`restaurant/book-table`, bookingRequest);
  }
  export const deleterestaurant = (userid, id) => {
    return fetchWrapper.delete(`restaurant/liked/${userid}/${id}`, null);
  }

