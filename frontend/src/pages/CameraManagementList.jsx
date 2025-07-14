import { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { toast } from 'react-toastify';
import axiosInstance from '../api/axiosInstance';
import '../css/CameraManagementList.css';
import { AnimatePresence, motion } from 'framer-motion';

export default function CameraManagementList() {
  const navigate = useNavigate();
  const location = useLocation();

  const [cameraList, setCameraList] = useState([]);
  const [totalElements, setTotalElements] = useState(0);
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [initialLoading, setInitialLoading] = useState(true);
  const [isFetching, setIsFetching] = useState(false);

  useEffect(() => {
    if (location.state?.toast) {
      toast.success(location.state.toast, { containerId: 'global' });
      navigate(location.pathname, { replace: true, state: {} });
    }
  }, [location, navigate]);

  const fetchCameras = (page, size, isInitial = false) => {
    if (isInitial) setInitialLoading(true);
    else setIsFetching(true);

    axiosInstance
      .get('/camera/recent', { params: { page, size } })
      .then((res) => {
        setCameraList(res.data.content);
        setTotalElements(res.data.totalElements);
      })
      .catch((err) => {
        if (!err.isAuthFailure) {
          console.error('카메라 조회 실패', err);
          toast.error('카메라 데이터를 불러오지 못했습니다.', { containerId: 'global' });
        }
      })
      .finally(() => {
        if (isInitial) setInitialLoading(false);
        else setIsFetching(false);
      });
  };

  useEffect(() => {
    fetchCameras(page, pageSize, true);
  }, []);

  useEffect(() => {
    if (!initialLoading) {
      fetchCameras(page, pageSize);
    }
  }, [page, pageSize]);

  const handleRegisterClick = () => {
    navigate('/ci');
  };

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < Math.ceil(totalElements / pageSize)) {
      setPage(newPage);
    }
  };

  const totalPages = Math.ceil(totalElements / pageSize);
  const visiblePageCount = 5;
  const half = Math.floor(visiblePageCount / 2);

  const statusLabelMap = {
    PENDING: '대기',
    PROCESSING: '처리중',
    STOPPED: '정지',
    DISABLED: '비활성화',
    AGENT_STOPPED: '에이전트정지',
  };

  const statusClassMap = {
    PENDING: 'cml-wait',
    PROCESSING: 'cml-processing',
    STOPPED: 'cml-stop',
    DISABLED: 'cml-disabled',
    AGENT_STOPPED: 'cml-agent-stop',
  };

  return (
    <div className="cml-main-body">
      <section className="cml-section">
        <h3>개별 카메라 관리</h3>
        <div className="cml-filter-panel">
          <div className="cml-filter-grid">
            {[['스트리밍명', 'text'], ['기관명', 'text'], ['상태코드', 'select', ['선택', '대기', '처리중', '정지', '비활성화', '에이전트정지']],
              ['대분류', 'text'], ['중분류', 'text'], ['소분류', 'text']].map(([label, type, options], i) => (
              <div className="cml-filter-item" key={i}>
                <label>{label}</label>
                {type === 'text' ? (
                  <input type="text" />
                ) : (
                  <select>
                    {options.map((opt) => <option key={opt}>{opt}</option>)}
                  </select>
                )}
              </div>
            ))}
          </div>
        </div>
        <div className="cml-top-controls">
          <select
            value={pageSize}
            onChange={(e) => {
              setPageSize(parseInt(e.target.value));
              setPage(0);
            }}
          >
            <option value={10}>10</option>
            <option value={30}>30</option>
            <option value={50}>50</option>
          </select>
          <button className="cml-btn" onClick={() => fetchCameras(page, pageSize)}>조회</button>
        </div>
      </section>

      <section className="cml-section">
        <div className="cml-result-header cml-only-count">
          총 <strong>{totalElements}</strong> 건
        </div>

        <div className="cml-table-container">
          <table>
            <thead>
              <tr>
                <th><input type="checkbox" /></th>
                <th>스트리밍번호</th>
                <th>스트리밍명</th>
                <th>기관명</th>
                <th>대분류</th>
                <th>중분류</th>
                <th>소분류</th>
                <th>상태코드</th>
              </tr>
            </thead>
            <AnimatePresence mode="wait">
              <motion.tbody
                key={`page-${page}-${isFetching}`} // 중요: 키가 페이지 또는 fetch 상태 바뀔 때마다 바뀌게
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                transition={{ duration: 0.3 }}
              >
                {cameraList.length > 0 ? (
                  cameraList.map((camera) => (
                    <tr key={camera.id}>
                      <td><input type="checkbox" /></td>
                      <td><a href="#">STREAM_{camera.id}</a></td>
                      <td><a href="#">{camera.name}</a></td>
                      <td>{camera.systemName}</td>
                      <td>{camera.categoryLarge}</td>
                      <td>{camera.categoryMedium}</td>
                      <td>{camera.categorySmall}</td>
                      <td>
                        <span className={`cml-badge ${statusClassMap[camera.status] || 'cml-default'}`}>
                          {statusLabelMap[camera.status] || '알수없음'}
                        </span>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="8" style={{ textAlign: 'center' }}>
                      {isFetching ? '로딩 중...' : '표시할 카메라가 없습니다.'}
                    </td>
                  </tr>
                )}
              </motion.tbody>
            </AnimatePresence>
          </table>

          {totalPages > 0 && (
            <div className="cml-pagination">
              <button onClick={() => handlePageChange(0)} disabled={page === 0}>&laquo;</button>
              <button onClick={() => handlePageChange(page - 1)} disabled={page === 0}>&lsaquo;</button>
              {Array.from({ length: visiblePageCount }, (_, i) => {
                const pageNum = page - half + i;
                const isValid = pageNum >= 0 && pageNum < totalPages;
                return (
                  <span
                    key={i}
                    className={`cml-page-number ${pageNum === page ? 'cml-active' : ''} ${!isValid ? 'cml-disabled' : ''}`}
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

        <div className="cml-bottom-actions">
          <div className="cml-left-buttons">
            <button className="cml-btn cml-blue">엑셀 업로드 샘플</button>
            <button className="cml-btn cml-blue">엑셀파일 스트리밍 등록</button>
            <button className="cml-btn cml-blue">스트리밍 데이터 다운로드</button>
          </div>
          <div className="cml-right-buttons">
            <button className="cml-btn cml-red">정지</button>
            <button className="cml-btn cml-blue">시작</button>
            <button className="cml-btn cml-blue" onClick={handleRegisterClick}>등록</button>
          </div>
        </div>
      </section>
    </div>
  );
}
