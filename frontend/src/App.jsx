import "./css/App.css";
import { Header } from "./components/Header.jsx";
import { Side } from "./components/Side.jsx";
import { Footer } from "./components/Footer.jsx";
import { Routes, Route, useLocation } from "react-router-dom";

import StreamingViewer from "./pages/StreamingViewer.jsx";
import CameraManagementList from "./pages/CameraManagementList.jsx";
import UserManagementList from "./pages/UserManagementList.jsx";
import UserManagement from "./pages/UserManagement.jsx";
import CameraForm from "./pages/CameraForm.jsx";
// import CameraFormWrapper from "./pages/CameraFormWrapper";
import LoginPage from "./pages/Login.jsx";

import { AnimatePresence, motion } from "framer-motion";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export function App() {
  const location = useLocation();
  const isLoginPage = location.pathname === "/login";

  const pageVariants = {
    initial: { opacity: 0, y: 10 },
    animate: {
      opacity: 1,
      y: 0,
      transition: { duration: 0.3, ease: [0.4, 0, 0.2, 1] },
    },
    exit: {
      opacity: 0,
      y: -10,
      transition: { duration: 0.2, ease: [0.4, 0, 0.2, 1] },
    },
  };

  return (
    <>
      <ToastContainer
        containerId="global"
        position="top-right"
        autoClose={1500}
        hideProgressBar={false}
        closeOnClick
        pauseOnHover
        draggable
        theme="colored"
      />

      {isLoginPage ? (
        <Routes>
          <Route path="/login" element={<LoginPage />} />
        </Routes>
      ) : (
        <div className="app-container">
          <Header />
          <div className="content-wrapper">
            <Side />
            <div className="main-area">
              <main className="page-container">
                <AnimatePresence mode="wait">
                  <motion.div
                    key={location.pathname}
                    variants={pageVariants}
                    initial="initial"
                    animate="animate"
                    exit="exit"
                    className="page-wrapper"
                  >
                    <Routes location={location} key={location.pathname}>
                      <Route path="/sv" element={<StreamingViewer />} />
                      <Route path="/cml" element={<CameraManagementList />} />
                      <Route path="/uml" element={<UserManagementList />} />
                      <Route path="/um" element={<UserManagement />} />
                      {/* 등록 */}
                      <Route path="/ci" element={<CameraForm />} />

                      {/* 수정 */}
                      <Route
                        path="/ci/:cameraId"
                        element={<CameraFormWrapper />}
                      />

                      <Route path="/" element={<h1>🏠 홈입니다</h1>} />
                      <Route
                        path="*"
                        element={<h1>❌ 페이지를 찾을 수 없습니다</h1>}
                      />
                    </Routes>
                  </motion.div>
                </AnimatePresence>
              </main>
              <Footer />
            </div>
          </div>
        </div>
      )}
    </>
  );
}
