import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://127.0.0.1:8080',
  withCredentials: true
});

const REFRESH_URL = '/api/refresh';
const excludeTokenUrl = ['/api/member/login', '/api/member/signUp'];

instance.interceptors.request.use(async function (config) {
  const access_token = localStorage.getItem("accessToken");
  const refresh_token = localStorage.getItem("refreshToken");

  if (!access_token && !refresh_token) {
    config.headers.Authorization = null;
    config.headers.RefreshToken = null;
    return config;
  }

  if (config.headers && access_token && refresh_token) {
    config.headers.Authorization = `Bearer ${access_token}`;
    config.headers.RefreshToken = `Bearer ${refresh_token}`;
  }

  return config;
});

instance.interceptors.response.use(
  function (response) {
    return response;
  },
  async function (error) {
    const originalConfig = error.config;
    // const alreadyRefreshed = originalRequest._alreadyRefreshed;
    
    if (error.response?.status === 401 && !originalConfig._retry) {
      originalConfig._retry = true;
      const refreshToken = localStorage.getItem("refreshToken");
      const accessToken = localStorage.getItem("accessToken");
      try {
        if (accessToken && refreshToken) {
          const response = await axios({
            url: `${instance.defaults.baseURL}${REFRESH_URL}`,
            method: 'GET',
            headers: {
              RefreshToken: refreshToken,
              Authorization: accessToken
            },
            data: {
              refreshToken: refreshToken
            }
          })
          // .post(`${instance.defaults.baseURL}${REFRESH_URL}`, {
          //   refreshToken: refreshToken
          // });
          const newAccessToken = response.data.accessToken;
          const newRefreshToken = response.data.refreshToken;

          localStorage.setItem("accessToken", newAccessToken);
          localStorage.setItem("refreshToken", newRefreshToken);

          originalConfig.headers.Authorization = `Bearer ${newAccessToken}`;
          originalConfig.headers.RefreshToken = `Bearer ${newRefreshToken}`;

          return axios(originalConfig);
        }
      } catch (error) {
        console.error('Error refreshing token:', error);
        throw error;
      }
    }
  
    return Promise.reject(error);
  }
);

export default instance;
