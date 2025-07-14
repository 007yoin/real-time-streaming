// hooks/authCheck.js
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "../api/axiosInstance";
import { getAccessToken, setAccessToken } from "../store/tokenStore";

export function useAuthCheck() {
  const [loading, setLoading] = useState(true);
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const verify = async () => {
      const token = getAccessToken();

      try {
        if (token) {
          const res = await axios.get("/auth/check", {
            headers: { Authorization: `Bearer ${token}` },
          });

          // 새 access token이 올 수도 있음
          if (res.data && typeof res.data === "string" && res.data.length > 0) {
            setAccessToken(res.data);
          }

          if (location.pathname === "/login") {
            navigate("/sv", { replace: true });
          } else {
            setLoading(false);
          }

          return;
        }

        // access token이 없으면 → refresh 시도
        throw new Error("No access token");
      } catch {
        try {
          const res = await axios.post("/auth/refresh");

          if (res.data) {
            setAccessToken(res.data);
            if (location.pathname === "/login") {
              navigate("/sv", { replace: true });
            } else {
              setLoading(false);
            }

            return;
          }

          throw new Error("refresh 실패");
        } catch {
          // refresh도 실패한 경우
          if (location.pathname !== "/login") {
            navigate("/login", { replace: true });
          } else {
            setLoading(false);
          }
        }
      }
    };

    verify();
  }, [location.pathname, navigate]);

  return loading;
}
