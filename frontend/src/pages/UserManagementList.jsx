import { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../api/axiosInstance";
import "../css/UserManagementList.css";
import { AnimatePresence, motion } from "framer-motion";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";
import { toast } from "react-toastify";
import { DeleteModal } from "../components/ConfirmModal";

export default function UserManagementList() {
  const navigate = useNavigate();
  const [users, setUsers] = useState([]);
  const [selectedUserIds, setSelectedUserIds] = useState([]);
  const [totalElements, setTotalElements] = useState(0);
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [pendingPageSize, setPendingPageSize] = useState(10);
  const [isFetching, setIsFetching] = useState(false);
  const [fetchTrigger, setFetchTrigger] = useState(0);

  const [filters, setFilters] = useState({
    loginId: "",
    name: "",
    role: "",
  });

  const fetchUsers = () => {
    setIsFetching(true);
    axiosInstance
      .get("/user/recent", {
        params: {
          page,
          size: pageSize,
          loginId: filters.loginId || undefined,
          name: filters.name || undefined,
          role: filters.role || undefined,
        },
      })
      .then((res) => {
        setUsers(res.data.content);
        setTotalElements(res.data.totalElements);
      })
      .catch((err) => {
        console.error("사용자 조회 실패", err);
      })
      .finally(() => {
        setIsFetching(false);
      });
  };

  useEffect(() => {
    fetchUsers();
  }, [page, pageSize, fetchTrigger]);

  const handleQueryClick = () => {
    setPage(0);
    setPageSize(pendingPageSize);
    setFetchTrigger((prev) => prev + 1);
  };

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < Math.ceil(totalElements / pageSize)) {
      setPage(newPage);
    }
  };

  const handleEnterKey = (e) => {
    if (e.key === "Enter") {
      handleQueryClick();
    }
  };

  const handleUserClick = (userId) => {
    navigate(`/um?userId=${userId}`);
  };

  const toggleCheckbox = (userId) => {
    setSelectedUserIds((prev) =>
      prev.includes(userId)
        ? prev.filter((id) => id !== userId)
        : [...prev, userId]
    );
  };

  const handleDeleteSelected = () => {
    if (selectedUserIds.length === 0) {
      toast.warn("삭제할 사용자를 선택하세요", { containerId: "global" });
      return;
    }

    confirmAlert({
      customUI: ({ onClose }) => (
        <DeleteModal
          onClose={onClose}
          onConfirm={() => {
            axiosInstance
              .delete("/users", { data: selectedUserIds })
              .then(() => {
                toast.success("삭제 완료", { containerId: "global" });
                setSelectedUserIds([]);
                setFetchTrigger((prev) => prev + 1);
              })
              .catch(() => {
                toast.error("삭제 실패", { containerId: "global" });
              });
          }}
        />
      ),
    });
  };

  const totalPages = Math.ceil(totalElements / pageSize);
  const visiblePageCount = 5;
  const half = Math.floor(visiblePageCount / 2);

  return (
    <div className="uml-main-body">
      <section className="uml-section">
        <h3>사용자 관리</h3>
        <div className="uml-filter-panel">
          <div className="uml-filter-grid">
            <div className="uml-filter-item">
              <label>사용자 ID</label>
              <input
                type="text"
                value={filters.loginId}
                onChange={(e) =>
                  setFilters({ ...filters, loginId: e.target.value })
                }
                onKeyDown={handleEnterKey}
              />
            </div>
            <div className="uml-filter-item">
              <label>사용자명</label>
              <input
                type="text"
                value={filters.name}
                onChange={(e) =>
                  setFilters({ ...filters, name: e.target.value })
                }
                onKeyDown={handleEnterKey}
              />
            </div>
            <div className="uml-filter-item">
              <label>권한</label>
              <select
                value={filters.role}
                onChange={(e) =>
                  setFilters({ ...filters, role: e.target.value })
                }
                onKeyDown={handleEnterKey}
              >
                <option value="">전체</option>
                <option value="ADMIN">ADMIN</option>
                <option value="USER">USER</option>
              </select>
            </div>
          </div>
        </div>

        <div className="uml-top-controls">
          <select
            value={pendingPageSize}
            onChange={(e) => setPendingPageSize(parseInt(e.target.value))}
          >
            <option value={10}>10</option>
            <option value={30}>30</option>
            <option value={50}>50</option>
          </select>
          <button className="uml-btn uml-blue" onClick={handleQueryClick}>
            조회
          </button>
        </div>
      </section>

      <section className="uml-section">
        <div className="uml-result-header uml-only-count">
          총 <strong>{totalElements}</strong> 건
        </div>

        <div className="uml-table-container">
          <table>
            <thead>
              <tr>
                <th>
                  <input
                    type="checkbox"
                    onChange={(e) =>
                      setSelectedUserIds(
                        e.target.checked ? users.map((u) => u.id) : []
                      )
                    }
                    checked={
                      users.length > 0 &&
                      selectedUserIds.length === users.length
                    }
                  />
                </th>
                <th>사용자 ID</th>
                <th>사용자명</th>
                <th>권한</th>
              </tr>
            </thead>
 <AnimatePresence mode="wait">
   <motion.tbody
     key={`page-${page}-size-${pageSize}-fetch-${fetchTrigger}`}
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                transition={{ duration: 0.3 }}
              >
                {users.length > 0 ? (
                  users.map((user) => (
                    <tr key={user.id}>
                      <td>
                        <input
                          type="checkbox"
                          checked={selectedUserIds.includes(user.id)}
                          onChange={() => toggleCheckbox(user.id)}
                        />
                      </td>
                      <td
                        className="clickable"
                        onClick={() => handleUserClick(user.id)}
                      >
                        {user.loginId}
                      </td>
                      <td
                        className="clickable"
                        onClick={() => handleUserClick(user.id)}
                      >
                        {user.name}
                      </td>
                      <td
                        className="clickable"
                        onClick={() => handleUserClick(user.id)}
                      >
                        {user.role}
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="4" style={{ textAlign: "center" }}>
                      {isFetching ? "로딩 중..." : "표시할 사용자가 없습니다."}
                    </td>
                  </tr>
                )}
              </motion.tbody>
            </AnimatePresence>
          </table>

          {totalPages > 0 && (
            <div className="uml-pagination">
              <button onClick={() => handlePageChange(0)} disabled={page === 0}>
                &laquo;
              </button>
              <button
                onClick={() => handlePageChange(page - 1)}
                disabled={page === 0}
              >
                &lsaquo;
              </button>
              {Array.from({ length: visiblePageCount }, (_, i) => {
                const pageNum = page - half + i;
                const isValid = pageNum >= 0 && pageNum < totalPages;
                return (
                  <span
                    key={i}
                    className={`uml-page-number ${
                      pageNum === page ? "uml-active" : ""
                    } ${!isValid ? "uml-disabled" : ""}`}
                    onClick={() => isValid && handlePageChange(pageNum)}
                  >
                    {isValid ? pageNum + 1 : ""}
                  </span>
                );
              })}
              <button
                onClick={() => handlePageChange(page + 1)}
                disabled={page + 1 >= totalPages}
              >
                &rsaquo;
              </button>
              <button
                onClick={() => handlePageChange(totalPages - 1)}
                disabled={page + 1 >= totalPages}
              >
                &raquo;
              </button>
            </div>
          )}
        </div>

        <div className="uml-bottom-actions">
          <div className="uml-left-buttons" />
          <div className="uml-right-buttons">
            <button className="uml-btn uml-red" onClick={handleDeleteSelected}>
              삭제
            </button>
          </div>
        </div>
      </section>
    </div>
  );
}
