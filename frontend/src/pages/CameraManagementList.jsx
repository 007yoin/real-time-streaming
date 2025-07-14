import { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { toast } from 'react-toastify';
import '../css/CameraManagementList.css';

export default function CameraManagementList() {
  const navigate = useNavigate();
  const location = useLocation();

  // ✅ 등록 후 전달된 toast 메시지 출력
  useEffect(() => {
    if (location.state?.toast) {
      toast.success(location.state.toast, { containerId: 'global' });
      navigate(location.pathname, { replace: true, state: {} });
    }
  }, [location, navigate]);

  const handleRegisterClick = () => {
    navigate('/ci');
  };

  return (
    <div className="cml-main-body">
      <section className="cml-section">
        <h3>개별 카메라 관리</h3>
        <div className="cml-filter-panel">
          <div className="cml-filter-grid">
            {[
              ['스트리밍명', 'text'],
              ['기관명', 'text'],
              ['상태코드', 'select', ['선택', '대기', '처리중', '정지', '비활성화', '에이전트정지']],
              ['대분류', 'text'],
              ['중분류', 'text'],
              ['소분류', 'text'],
            ].map(([label, type, options], i) => (
              <div className="cml-filter-item" key={i}>
                <label>{label}</label>
                {type === 'text' ? (
                  <input type="text" />
                ) : (
                  <select>
                    {options.map((opt) => (
                      <option key={opt}>{opt}</option>
                    ))}
                  </select>
                )}
              </div>
            ))}
          </div>
        </div>
        <div className="cml-top-controls">
          <select>
            <option>10</option>
            <option>30</option>
            <option>50</option>
          </select>
          <button className="cml-btn">조회</button>
        </div>
      </section>

      <section className="cml-section">
        <div className="cml-result-header cml-only-count">
          {"총　"}<strong>13821</strong>{'　'}건
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
                <th>소스상태</th>
                <th>상태코드</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><input type="checkbox" /></td>
                <td><a href="#">STREAM_106101</a></td>
                <td><a href="#">북벽_N</a></td>
                <td>충북_충북도청_N</td>
                <td>지자체</td>
                <td>충북</td>
                <td>단양군</td>
                <td><span className="cml-badge cml-wait">대기</span></td>
                <td><span className="cml-badge cml-stop">정지</span></td>
              </tr>
            </tbody>
          </table>

          <div className="cml-pagination">
            <button>&laquo;</button>
            <button>&lsaquo;</button>
            <span className="cml-page-number">1</span>
            <span>2</span>
            <span>3</span>
            <button>&rsaquo;</button>
            <button>&raquo;</button>
          </div>
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
