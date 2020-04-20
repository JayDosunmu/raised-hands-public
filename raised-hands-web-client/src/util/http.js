import axios from 'axios';

const http = axios.create({
    baseURL: process.env.REACT_APP_API_URL || 'api.raisedhands.io',
    headers: { 'Content-Type': 'application/json' },
})

http.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token')
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        Promise.reject(error);
    }
);

export default http;
