import axios from "axios";
import {
  getAccessToken,
  setAccessToken,
  clearAccessToken,
} from "../store/tokenStore";
import { toast } from "react-toastify";

// ✅ 개발/배포 환경에 따른 Gateway 주소
const apiBaseUrl =
  import.meta.env.MODE === "development"
    ? "" // vite proxy가 알아서 gateway로 전달
    : import.meta.env.VITE_API_BASE_URL || "http://localhost:8000";

console.log("[axiosInstance] API Base URL:", apiBaseUrl);

const instance = axios.create({
  baseURL: apiBaseUrl, // "/api" 제거
  withCredentials: true,
});

// ✅ 요청 인터셉터
instance.interceptors.request.use(
  (config) => {
    const token = getAccessToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
      console.log("[axiosInstance] 요청에 토큰 추가됨:", token);
    }
    return config;
  },
  (error) => {
    console.error("[axiosInstance] 요청 인터셉터 에러:", error);
    return Promise.reject(error);
  }
);

// ✅ 응답 인터셉터
instance.interceptors.response.use(
  (response) => {
    console.log(
      "[axiosInstance] 응답 성공:",
      response.config.url,
      "Status:",
      response.status
    );
    return response;
  },
  async (error) => {
    const originalRequest = error.config;
    const url = originalRequest?.url;

    const status = error.response?.status;
    const data = error.response?.data;

    console.warn("[axiosInstance] 응답 에러 발생:", url);
    console.warn("→ 상태 코드:", status);
    console.warn("→ 응답 메시지:", data);

    if (url === "/access-router") {
      console.warn("[axiosInstance] /access-router 에러 → catch로 전달");
      return Promise.reject(error);
    }

    const isExpired = status === 401;
    console.log("[axiosInstance] 토큰 만료 판단:", isExpired);

    if (isExpired && !originalRequest._retry) {
      originalRequest._retry = true;
      console.log("[axiosInstance] refresh 시도");

      try {
        const refreshResponse = await instance.post(
          "/auth/refresh",
          {},
          { withCredentials: true }
        );

        console.log("[axiosInstance] refresh 응답:", refreshResponse.data);

        const newAccessToken = refreshResponse.data;
        if (newAccessToken) {
          setAccessToken(newAccessToken);
          originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
          return instance(originalRequest); // 재시도
        }
      } catch (refreshErr) {
        console.error("[axiosInstance] refresh 실패:", refreshErr);
        clearAccessToken();
        toast.error("세션이 만료되었습니다. 다시 로그인해주세요.");

        refreshErr.isAuthFailure = true;

        if (refreshErr.response?.status === 400) {
          window.location.reload();
        }

        return Promise.reject(refreshErr);
      }
    }

    return Promise.reject(error);
  }
);

export default instance;
