// api/axiosInstance.js
import axios from "axios";
import { getAccessToken, setAccessToken, clearAccessToken } from "../store/tokenStore";
import { toast } from "react-toastify";

const instance = axios.create({
  baseURL: "/api",
  withCredentials: true, // refresh token 전송에 필요
});

// ✅ 요청 인터셉터 - accessToken 자동 헤더 주입
instance.interceptors.request.use(
  (config) => {
    const token = getAccessToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// ✅ 응답 인터셉터 - accessToken 만료 시 자동 갱신
instance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    const isExpired =
      error.response?.status === 401 &&
      error.response?.data === "토큰이 만료되었습니다.";

    if (isExpired && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refreshResponse = await axios.post(
          "/auth/refresh",
          {},
          { withCredentials: true }
        );

        const newAccessToken = refreshResponse.data;
        if (newAccessToken) {
          setAccessToken(newAccessToken);
          originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
          return instance(originalRequest); // 🔄 재요청
        }
      } catch (refreshError) {
        clearAccessToken();
        toast.error("세션이 만료되었습니다. 다시 로그인해주세요.");
        // Optional: window.location.href = "/login";
      }
    }

    return Promise.reject(error);
  }
);

export default instance;
