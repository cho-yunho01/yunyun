import axios from "axios";

const api = axios.create({
    baseURL : "/api"
})


api.interceptors.request.use(config => {
    const token = localStorage.getItem('accessToken');

    console.log("요청 URL:", config.url);
    console.log("토큰:", token);

    if(token && config.url){
        config.headers.Authorization = `Bearer ${token}`
    }

    return config
})

api.interceptors.response.use(
    response => {
        return response
    },
    error => {
        if(error.response){
            const {code, message} = error.response.data;
            console.log("에러 코드", code);
            console.log("메세지", message);
        }

        return Promise.reject(error);
    }
)
export default api;