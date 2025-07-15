import "../css/streamingViewer.css";

// 임시 이미지
import example1 from "../assets/images/example1.png";
import example2 from "../assets/images/example2.png";
import example3 from "../assets/images/example3.png";
import example4 from "../assets/images/example4.jfif";
const images = [example1, example2, example3, example4];

export default function StreamingViewer() {
  return (
    <div className="sv-main-body">
      <div className="sv-left-panel">
        <div className="sv-left-content">
          {/* 즐겨찾기 섹션 */}
          <section className="sv-section sv-toolbar">
            <h3>즐겨찾기</h3>
            <div className="sv-toolbar-row">
              <input type="text" placeholder="입력" />
              <button>즐겨찾기 저장</button>
            </div>
            <div className="sv-toolbar-row">
              <select>
                <option>선택</option>
              </select>
              <button>불러오기</button>
              <button>즐겨찾기 제거</button>
            </div>
          </section>

          {/* 스트리밍 현황 */}
          <section className="sv-section sv-status">
            <h3>스트리밍 현황</h3>
            <div className="sv-status-grid">
              {[
                ["pause", "대기", 10274],
                ["play", "조회중", 110],
                ["stop", "정지", 2565],
                ["client-stop", "클라이언트정지", 156],
                ["inactive", "비활성화", 716],
                ["error", "에러", 0],
              ].map(([icon, label, count]) => (
                <div className="sv-status-item" key={label}>
                  <span className={`sv-icon ${icon}`}></span>
                  <span>{label}</span>
                  <span className="sv-count">{count}</span>
                </div>
              ))}
            </div>
          </section>

          {/* 분할 버튼 */}
          <section className="sv-section sv-split-buttons">
            {[1, 4, 9, 16].map((n) => (
              <button key={n}>{n}분할</button>
            ))}
          </section>

          {/* 스트리밍 목록 */}
          <section className="sv-section sv-stream-list">
            <h3>스트리밍목록</h3>
            <div className="sv-filter-tabs">
              <button className="sv-tab sv-active">지역별</button>
              <button className="sv-tab">용도별</button>
              <input type="text" placeholder="검색" />
              <button className="sv-search-btn">검색</button>
            </div>
            <div className="sv-tree-view">
              <ul>
                <li>📁 유관기관 [7903 / 6375 / 1528]</li>
                <li>📁 지자체 [5202 / 3994 / 1208]</li>
              </ul>
            </div>
            <div className="sv-action-buttons">
              <button>적용</button>
              <button>초기화</button>
            </div>
          </section>
        </div>
      </div>

      <div className="sv-right-panel">
        <section className="sv-section sv-video-grid">
          {[1, 2, 3, 4].map((n) => (
            <div className="sv-video-box" key={n}>
              <img src={images[n - 1]} alt={`카메라 예시${n}`} />
            </div>
          ))}
        </section>
      </div>
    </div>
  );
}
