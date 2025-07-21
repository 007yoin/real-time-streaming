import { useEffect, useRef, useState } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import axiosInstance from "../api/axiosInstance";
import "../css/UserManagement.css";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";
import { toast } from "react-toastify";
import { ModifyModal, DeleteModal } from "../components/ConfirmModal";

console.log("DeleteModal", DeleteModal);

export default function UserManagement() {
  const [searchParams] = useSearchParams();
  const userId = searchParams.get("userId");
  const navigate = useNavigate();

  const [user, setUser] = useState({
    loginId: "",
    name: "",
    role: "USER",
    description: "",
    isDeleted: false,
  });

  const [password, setPassword] = useState("");
  const [passwordCheck, setPasswordCheck] = useState("");

  useEffect(() => {
    if (userId) {
      axiosInstance
        .get(`/user/${userId}`)
        .then((res) => {
          setUser(res.data);
        })
        .catch((err) => {
          console.error("사용자 정보 조회 실패", err);
        });
    }
  }, [userId]);

  const performUpdate = () => {
    const updatedUser = {
      ...user,
      password: password || undefined,
    };

    axiosInstance
      .put(`/user/${userId}`, updatedUser)
      .then(() => {
        toast.success("수정 완료", { containerId: "global" });
        navigate("/uml");
      })
      .catch((err) => {
        console.error("수정 실패", err);
        toast.error("수정 실패", { containerId: "global" });
      });
  };

  const handleUpdate = () => {
    if (password && password !== passwordCheck) {
      toast.error("비밀번호 확인이 일치하지 않습니다", {
        containerId: "global",
      });
      return;
    }

    confirmAlert({
      customUI: ({ onClose }) => (
        <ModifyModal
          onClose={onClose}
          onConfirm={() => {
            performUpdate();
            onClose();
          }}
        />
      ),
    });
  };

  const handleDelete = () => {
    if (!userId) return;

    const handleConfirm = () => {
      axiosInstance
        .delete(`/user/${userId}`)
        .then(() => {
          toast.success("삭제 완료", { containerId: "global" });
          navigate("/uml");
        })
        .catch((err) => {
          console.error("삭제 실패", err);
          toast.error("삭제 실패", { containerId: "global" });
        });
    };

    confirmAlert({
      customUI: ({ onClose }) => (
        <DeleteModal
          onClose={onClose}
          onConfirm={() => {
            handleConfirm();
            onClose();
          }}
        />
      ),
    });
  };

  return (
    <>
      <section className="um-section">
        <h3>사용자 관리</h3>
        <table className="um-table">
          <tbody>
            <tr>
              <th>사용자 ID</th>
              <td>
                <input
                  type="text"
                  value={user.loginId}
                  disabled
                  className="um-input"
                />
              </td>
              <th>비밀번호 변경</th>
              <td>
                <input
                  type="password"
                  className="um-input"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </td>
            </tr>
            <tr>
              <th>
                사용자명 <span style={{ color: "red" }}>*</span>
              </th>
              <td>
                <input
                  type="text"
                  className="um-input"
                  value={user.name}
                  onChange={(e) => setUser({ ...user, name: e.target.value })}
                />
              </td>
              <th>비밀번호 변경 확인</th>
              <td>
                <input
                  type="password"
                  className="um-input"
                  value={passwordCheck}
                  onChange={(e) => setPasswordCheck(e.target.value)}
                />
              </td>
            </tr>
            <tr>
              <th>
                권한 <span style={{ color: "red" }}>*</span>
              </th>
              <td>
                <select
                  className="um-input"
                  value={user.role}
                  onChange={(e) => setUser({ ...user, role: e.target.value })}
                >
                  <option value="ADMIN">ADMIN</option>
                  <option value="USER">USER</option>
                </select>
              </td>
            </tr>
            <tr>
              <th>비고</th>
              <td colSpan="3">
                <textarea
                  className="um-input"
                  rows="3"
                  value={user.description}
                  onChange={(e) =>
                    setUser({ ...user, description: e.target.value })
                  }
                ></textarea>
              </td>
            </tr>
          </tbody>
        </table>
      </section>

      <div className="um-bottom-actions">
        <button className="um-btn um-gray" onClick={() => navigate("/uml")}>
          목록
        </button>
        <button className="um-btn um-blue" onClick={handleUpdate}>
          수정
        </button>
        <button className="um-btn um-red" onClick={handleDelete}>
          삭제
        </button>
      </div>
    </>
  );
}
