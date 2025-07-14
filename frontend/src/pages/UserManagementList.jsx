import '../css/UserManagementList.css';

export default function UserManagementList() {
  return (
    <div className="uml-main-body">
      {/* 🔹 섹션 1: 필터 영역 */}
      <section className="uml-section">
        <h3>사용자 관리</h3>
        <div className="uml-filter-panel">
          <div className="uml-filter-grid">
            <div className="uml-filter-item">
              <label>사용자 ID</label>
              <input type="text" />
            </div>
            <div className="uml-filter-item">
              <label>사용자명</label>
              <input type="text" />
            </div>
            <div className="uml-filter-item">
              <label>권한</label>
              <select>
                <option>ADMIN</option>
                <option>USER</option>
              </select>
            </div>
          </div>
        </div>

        <div className="uml-top-controls">
          <select>
            <option>10</option>
            <option>30</option>
            <option>50</option>
          </select>
          <button className="uml-btn uml-blue">조회</button>

        </div>
      </section>

      {/* 🔹 섹션 2: 결과 및 액션 영역 */}
      <section className="uml-section">
        <div className="uml-result-header uml-only-count">
          {"총　"}<strong>13821</strong>{'　'}건
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
            <tbody>
              <tr>
                <td><input type="checkbox" /></td>
                <td>admin</td>
                <td>관리자</td>
                <td>USER</td>
              </tr>
              <tr>
                <td><input type="checkbox" /></td>
                <td>user1</td>
                <td>user1</td>
                <td>USER</td>
              </tr>
            </tbody>
          </table>

          <div className="uml-pagination">
            <button>&laquo;</button>
            <button>&lsaquo;</button>
            <span className="uml-page-number">1</span>
            <span>2</span>
            <span>3</span>
            <button>&rsaquo;</button>
            <button>&raquo;</button>
          </div>
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
