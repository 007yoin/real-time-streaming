import { useState, useEffect } from "react";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { registerUser } from "../api/userApi.js";
import { login } from "../api/loginApi.js";
import { setAccessToken } from "../store/tokenStore.js";

import "react-toastify/dist/ReactToastify.css";
import "../css/Login.css";

export default function Login() {
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({
    loginId: "",
    name: "",
    password: "",
    passwordConfirm: "",
  });

  const navigate = useNavigate();

  const loginHandle = async (e) => {
    e.preventDefault();
    const { loginId, password } = formData;

    try {
      const result = await login({ loginId, password });
      setAccessToken(result);
      toast.success("로그인 성공!");
      navigate("/sv");
    } catch (err) {
      toast.error(err.response?.data?.message || "로그인 실패");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSignup = async (e) => {
    e.preventDefault();
    const { loginId, name, password, passwordConfirm } = formData;

    if (password !== passwordConfirm) {
      toast.error("비밀번호가 일치하지 않습니다.");
      return;
    }

    try {
      const result = await registerUser({ loginId, name, password });

      toast.success(`회원가입 성공! ID: ${result.loginId}`);
      setShowModal(false);
    } catch (err) {
      toast.error(err.response.data.message);
    }
  };

  useEffect(() => {
    const escHandler = (e) => {
      if (e.key === "Escape") setShowModal(false);
    };
    window.addEventListener("keydown", escHandler);
    return () => window.removeEventListener("keydown", escHandler);
  }, []);

  return (
    <>
      <div className="login-background">
        {/* 회원가입 모달 */}
        {showModal && (
          <div className="login-modal">
            <div className="login-modal-content">
              <span
                className="login-close-btn"
                onClick={() => setShowModal(false)}
              >
                &times;
              </span>
              <h2>회원가입</h2>
              <form onSubmit={handleSignup} className="login-signup-form">
                <div className="login-input-row">
                  <input
                    type="text"
                    name="loginId"
                    placeholder="아이디 입력"
                    required
                    onChange={handleChange}
                  />
                  <button type="button" className="login-check-id">
                    중복확인
                  </button>
                </div>
                <input
                  type="text"
                  name="name"
                  placeholder="이름 입력"
                  required
                  onChange={handleChange}
                />
                <input
                  type="password"
                  name="password"
                  placeholder="비밀번호 입력"
                  required
                  onChange={handleChange}
                />
                <input
                  type="password"
                  name="passwordConfirm"
                  placeholder="비밀번호 확인"
                  required
                  onChange={handleChange}
                />
                <button type="submit" className="login-signup-submit">
                  가입하기
                </button>
              </form>
            </div>
          </div>
        )}

        {/* 로그인 화면 */}
        <h1 className="login-title">실시간 스트리밍 연계 시스템</h1>
        <div className="login-container">
          <div className="login-box">
            <h2>로그인</h2>
            <form onSubmit={loginHandle}>
              <input
                type="text"
                name="loginId"
                placeholder="사용자ID입력"
                required
                onChange={handleChange}
              />
              <input
                type="password"
                name="password"
                placeholder="비밀번호입력"
                required
                onChange={handleChange}
              />
              <button type="submit">로그인</button>
            </form>
            <a
              href="#"
              className="login-signup-btn"
              onClick={() => setShowModal(true)}
            >
              회원가입
            </a>
          </div>
        </div>
      </div>

      <ToastContainer
        position="top-right"
        autoClose={1700}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        pauseOnHover
        draggable
        theme="colored"
      />
    </>
  );
}
