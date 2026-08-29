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

export default api;