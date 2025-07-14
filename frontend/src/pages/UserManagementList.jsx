import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../api/axiosInstance';
import '../css/UserManagementList.css';
import { AnimatePresence, motion } from 'framer-motion';

export default function UserManagementList() {
  const navigate = useNavigate();

  const [users, setUsers] = useState([]);
  const [totalElements, setTotalElements] = useState(0);
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [pendingPageSize, setPendingPageSize] = useState(10);
  const [isFetching, setIsFetching] = useState(false);

  // ★ 追加: ボタン押下用カウンタ
  const [fetchCount, setFetchCount] = useState(0);

  const [filters, setFilters] = useState({
    loginId: '',
    name: '',
    role: ''
  });

  const fetchUsers = () => {
    setIsFetching(true);
    axiosInstance
      .get('/user/recent', {
        params: {
          page,
          size: pageSize,
          loginId: filters.loginId || null,
          name: filters.name || null,
          role: filters.role || null,
        }
      })
      .then(res => {
        setUsers(res.data.content);
        setTotalElements(res.data.totalElements);
      })
      .catch(err => {
        console.error('사용자 조회 실패', err);
      })
      .finally(() => {
        setIsFetching(false);
      });
  };

  // page, pageSize, fetchCount が変わるたびに fetch
  useEffect(() => {
    fetchUsers();
  }, [page, pageSize, fetchCount]);

  const handleQueryClick = () => {
    setPage(0);
    setPageSize(pendingPageSize);
    // ★ カウンタを増やして key を変化させる
    setFetchCount(prev => prev + 1);
  };

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < Math.ceil(totalElements / pageSize)) {
      setPage(newPage);
    }
  };

  const handleEnterKey = (e) => {
    if (e.key === 'Enter') {
      handleQueryClick();
    }
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
                onChange={(e) => setFilters({ ...filters, loginId: e.target.value })}
                onKeyDown={handleEnterKey}
              />
            </div>
            <div className="uml-filter-item">
              <label>사용자명</label>
              <input
                type="text"
                value={filters.name}
                onChange={(e) => setFilters({ ...filters, name: e.target.value })}
                onKeyDown={handleEnterKey}
              />
            </div>
            <div className="uml-filter-item">
              <label>권한</label>
              <select
                value={filters.role}
                onChange={(e) => setFilters({ ...filters, role: e.target.value })}
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
          <button className="uml-btn uml-blue" onClick={handleQueryClick}>조회</button>
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
                <th><input type="checkbox" /></th>
                <th>사용자 ID</th>
                <th>사용자명</th>
                <th>권한</th>
              </tr>
            </thead>
            <AnimatePresence mode="wait">
              <motion.tbody
                // ★ key に fetchCount を含める
                key={`page-${page}-fetch-${fetchCount}`}
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                transition={{ duration: 0.3 }}
              >
                {users.length > 0 ? (
                  users.map((user, index) => (
                    <tr key={index}>
                      <td><input type="checkbox" /></td>
                      <td>{user.loginId}</td>
                      <td>{user.name}</td>
                      <td>{user.role}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="4" style={{ textAlign: 'center' }}>
                      {isFetching ? '로딩 중...' : '표시할 사용자가 없습니다.'}
                    </td>
                  </tr>
                )}
              </motion.tbody>
            </AnimatePresence>
          </table>

          {totalPages > 0 && (
            <div className="uml-pagination">
              <button onClick={() => handlePageChange(0)} disabled={page === 0}>&laquo;</button>
              <button onClick={() => handlePageChange(page - 1)} disabled={page === 0}>&lsaquo;</button>
              {Array.from({ length: visiblePageCount }, (_, i) => {
                const pageNum = page - half + i;
                const isValid = pageNum >= 0 && pageNum < totalPages;
                return (
                  <span
                    key={i}
                    className={`uml-page-number ${pageNum === page ? 'uml-active' : ''} ${!isValid ? 'uml-disabled' : ''}`}
                    onClick={() => isValid && handlePageChange(pageNum)}
                  >
                    {isValid ? pageNum + 1 : ''}
                  </span>
                );
              })}
              <button onClick={() => handlePageChange(page + 1)} disabled={page + 1 >= totalPages}>&rsaquo;</button>
              <button onClick={() => handlePageChange(totalPages - 1)} disabled={page + 1 >= totalPages}>&raquo;</button>
            </div>
          )}
        </div>

        <div className="uml-bottom-actions">
          <div className="uml-left-buttons" />
          <div className="uml-right-buttons">
            <button className="uml-btn uml-blue">등록</button>
            <button className="uml-btn uml-red">삭제</button>
          </div>
        </div>
      </section>
    </div>
  );
}
