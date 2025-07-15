// hooks/authCheck.js
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "../api/axiosInstance";
import {
  getAccessToken,
  setAccessToken,
  clearAccessToken,
} from "../store/tokenStore";

export function useAuthCheck() {
  const [loading, setLoading] = useState(true);
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const verify = async () => {
      const token = getAccessToken();
      console.log("[authCheck] 시작: current path =", location.pathname);
      console.log("[authCheck] 현재 accessToken =", token);

      try {
        if (token) {
          console.log("[authCheck] accessToken 존재, /auth/check 요청");
          const res = await axios.get("/auth/check", {
            headers: { Authorization: `Bearer ${token}` },
          });

          if (res.data && typeof res.data === "string" && res.data.length > 0) {
            console.log("[authCheck] 새 accessToken 발급됨:", res.data);
            setAccessToken(res.data);
          }

          if (location.pathname === "/login") {
            console.log("[authCheck] 로그인 페이지에 있으므로 /sv로 리디렉션");
            navigate("/sv", { replace: true });
          } else {
            console.log("[authCheck] 인증 성공, loading 해제");
            setLoading(false);
          }

          return;
        }

        // access token 없음
        console.warn("[authCheck] accessToken 없음 → refresh 시도");
        throw new Error("No access token");
      } catch (checkErr) {
        console.warn("[authCheck] /auth/check 실패, 원인:", checkErr);

        try {
          console.log("[authCheck] /auth/refresh 요청 시도");
          const res = await axios.post(
            "/auth/refresh",
            {},
            { withCredentials: true }
          );

          if (res.data) {
            console.log("[authCheck] refresh 성공, 새 accessToken =", res.data);
            setAccessToken(res.data);

            if (location.pathname === "/login") {
              console.log("[authCheck] 로그인 페이지였음 → /sv 리디렉션");
              navigate("/sv", { replace: true });
            } else {
              console.log("[authCheck] refresh 후 인증 완료, loading 해제");
              setLoading(false);
            }

            return;
          }

          console.warn("[authCheck] refresh 응답 없음 or 비어있음");
          throw new Error("Refresh failed");
        } catch (refreshErr) {
          console.error(
            "[authCheck] /auth/refresh 실패 → 인증 불가:",
            refreshErr
          );
          clearAccessToken();

          if (location.pathname !== "/login") {
            console.log("[authCheck] 현재 로그인 아님 → /login 리디렉션");
            navigate("/login", { replace: true });
          } else {
            console.log("[authCheck] 이미 /login, loading 해제");
            setLoading(false); // ✅ 오직 login 페이지일 때만 해제
          }

          // 여기서 setLoading(false) ❌ 하면 인증 실패 상태에서 페이지 뜨게 됨!
        }
      }
    };

    verify();
  }, [location.pathname, navigate]);

  return loading;
}
