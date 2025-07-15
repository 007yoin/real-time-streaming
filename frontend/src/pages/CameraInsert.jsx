import "../css/CameraInsert.css";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { insertCamera } from "../api/cameraApi.js";
import { toast } from "react-toastify";

export default function CameraInsert() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    name: "",
    description: "",
    systemName: "",
    categoryLarge: "",
    categoryMedium: "",
    categorySmall: "",
    streamingUrl: "",
    cameraType: "",
    address: "",
    latitude: "",
    longitude: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async () => {
    try {
      await insertCamera(form);
      toast.success("등록되었습니다.", {
        containerId: "global",
      });
      navigate("/cml");
    } catch (err) {
      console.error("등록 실패:", err);
      toast.error(
        err.response?.data?.message || "등록 중 오류가 발생했습니다.",
        {
          containerId: "global",
        }
      );
    }
  };

  return (
    <>
      <section className="ci-section">
        <h3>스트리밍 관리</h3>
        <table className="ci-table">
          <tbody>
            <tr>
              <th>
                스트리밍명 <span style={{ color: "red" }}>*</span>
              </th>
              <td>
                <input
                  type="text"
                  name="name"
                  value={form.name}
                  onChange={handleChange}
                />
              </td>
            </tr>
            <tr>
              <th>비고</th>
              <td>
                <textarea
                  name="description"
                  rows="3"
                  value={form.description}
                  onChange={handleChange}
                />
              </td>
            </tr>
          </tbody>
        </table>
      </section>

      <section className="ci-section stream-info-scroll">
        <h3>스트리밍 정보</h3>
        <table className="ci-table">
          <tbody>
            <tr>
              <th>
                시스템명 <span style={{ color: "red" }}>*</span>
              </th>
              <td>
                <input
                  type="text"
                  name="systemName"
                  value={form.systemName}
                  onChange={handleChange}
                />
              </td>
            </tr>
            <tr>
              <th>
                대분류 <span style={{ color: "red" }}>*</span>
              </th>
              <td>
                <input
                  type="text"
                  name="categoryLarge"
                  value={form.categoryLarge}
                  onChange={handleChange}
                />
              </td>
              <th>중분류</th>
              <td>
                <input
                  type="text"
                  name="categoryMedium"
                  value={form.categoryMedium}
                  onChange={handleChange}
                />
              </td>
            </tr>
            <tr>
              <th>소분류</th>
              <td>
                <input
                  type="text"
                  name="categorySmall"
                  value={form.categorySmall}
                  onChange={handleChange}
                />
              </td>
              <th>
                스트리밍 URL <span style={{ color: "red" }}>*</span>
              </th>
              <td>
                <input
                  type="text"
                  name="streamingUrl"
                  value={form.streamingUrl}
                  onChange={handleChange}
                />
              </td>
            </tr>
            <tr>
              <th>
                유형 <span style={{ color: "red" }}>*</span>
              </th>
              <td>
                <input
                  type="text"
                  name="cameraType"
                  value={form.cameraType}
                  onChange={handleChange}
                />
              </td>
              <th>주소</th>
              <td>
                <input
                  type="text"
                  name="address"
                  value={form.address}
                  onChange={handleChange}
                />
              </td>
            </tr>
            <tr>
              <th>위도</th>
              <td>
                <input
                  type="text"
                  name="latitude"
                  value={form.latitude}
                  onChange={handleChange}
                />
              </td>
              <th>경도</th>
              <td>
                <input
                  type="text"
                  name="longitude"
                  value={form.longitude}
                  onChange={handleChange}
                />
              </td>
            </tr>
          </tbody>
        </table>
      </section>

      <div className="ci-bottom-actions">
        <button className="ci-btn ci-gray" onClick={() => navigate("/cml")}>
          목록
        </button>
        <button className="ci-btn ci-blue" onClick={handleSubmit}>
          등록
        </button>
      </div>

      {/* 푸터와 딱 붙는 것 방지용 더미 영역 */}
      <div className="ci-scroll-bottom-gap" />
    </>
  );
}
