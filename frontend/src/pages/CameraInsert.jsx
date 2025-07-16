import "../css/CameraInsert.css";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { insertCamera } from "../api/cameraApi.js";
import { toast } from "react-toastify";
import axiosInstance from "../api/axiosInstance.js";

export default function CameraInsert() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    description: "",
    categoryLarge: "",
    categoryMedium: "",
    categorySmall: "",
    streamingUrl: "",
    cameraType: "",
    address: "",
    latitude: "",
    longitude: "",
  });

  const [largeCategories, setLargeCategories] = useState([]);
  const [mediumCategories, setMediumCategories] = useState([]);
  const [smallCategories, setSmallCategories] = useState([]);
  const [cameraTypes, setCameraTypes] = useState([]);

  useEffect(() => {
    axiosInstance
      .get("/camCategory/large")
      .then((res) => setLargeCategories(res.data))
      .catch((err) => {
        console.error("대분류 조회 실패:", err);
        toast.error("대분류 카테고리 로딩 실패", { containerId: "global" });
      });

    axiosInstance
      .get("/camType")
      .then((res) => setCameraTypes(res.data))
      .catch((err) => {
        console.error("카메라 유형 조회 실패:", err);
        toast.error("카메라 유형 로딩 실패", { containerId: "global" });
      });
  }, []);

  useEffect(() => {
    if (!form.categoryLarge) {
      setMediumCategories([]);
      setSmallCategories([]);
      setForm((prev) => ({
        ...prev,
        categoryMedium: "",
        categorySmall: "",
      }));
      return;
    }

    axiosInstance
      .get(`/camCategory/medium/${form.categoryLarge}`)
      .then((res) => {
        setMediumCategories(res.data);
        setSmallCategories([]);
        setForm((prev) => ({
          ...prev,
          categoryMedium: "",
          categorySmall: "",
        }));
      })
      .catch((err) => {
        console.error("중분류 조회 실패:", err);
        toast.error("중분류 카테고리 로딩 실패", { containerId: "global" });
      });
  }, [form.categoryLarge]);

  useEffect(() => {
    if (!form.categoryMedium) {
      setSmallCategories([]);
      setForm((prev) => ({ ...prev, categorySmall: "" }));
      return;
    }

    if (!form.categoryLarge) return;

    axiosInstance
      .get(`/camCategory/small/${form.categoryMedium}/${form.categoryLarge}`)
      .then((res) => {
        setSmallCategories(res.data);
        setForm((prev) => ({ ...prev, categorySmall: "" }));
      })
      .catch((err) => {
        console.error("소분류 조회 실패:", err);
        toast.error("소분류 카테고리 로딩 실패", { containerId: "global" });
      });
  }, [form.categoryMedium]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: value === "" ? null : value,
    }));
  };

  const determineCategoryId = () => {
    if (form.categorySmall) return form.categorySmall;
    if (form.categoryMedium) return form.categoryMedium;
    if (form.categoryLarge) return form.categoryLarge;
    return null;
  };

  const handleSubmit = async () => {
    const finalCategoryId = determineCategoryId();
    const cameraTypeId = form.cameraType;

    if (!form.name || !form.streamingUrl || !cameraTypeId || !finalCategoryId) {
      toast.error("필수 항목을 모두 입력하세요.", { containerId: "global" });
      return;
    }

    const dto = {
      name: form.name,
      description: form.description,
      cameraCategoryId: finalCategoryId,
      cameraTypeId: cameraTypeId,
      streamingUrl: form.streamingUrl,
      address: form.address,
      latitude: form.latitude,
      longitude: form.longitude,
    };

    try {
      await insertCamera(dto);
      toast.success("등록되었습니다.", { containerId: "global" });
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
                대분류 <span style={{ color: "red" }}>*</span>
              </th>
              <td>
                <select
                  name="categoryLarge"
                  value={form.categoryLarge || ""}
                  onChange={handleChange}
                >
                  <option value="">선택</option>
                  {largeCategories.map((cat) => (
                    <option key={cat.categoryId} value={cat.categoryId}>
                      {cat.name}
                    </option>
                  ))}
                </select>
              </td>

              <th>중분류</th>
              <td>
                <select
                  name="categoryMedium"
                  value={form.categoryMedium}
                  onChange={handleChange}
                  disabled={!form.categoryLarge}
                >
                  <option value="">선택</option>
                  {mediumCategories.map((cat) => (
                    <option key={cat.categoryId} value={cat.categoryId}>
                      {cat.name}
                    </option>
                  ))}
                </select>
              </td>
            </tr>

            <tr>
              <th>소분류</th>
              <td>
                <select
                  name="categorySmall"
                  value={form.categorySmall}
                  onChange={handleChange}
                  disabled={!form.categoryMedium}
                >
                  <option value="">선택</option>
                  {smallCategories.map((cat) => (
                    <option key={cat.categoryId} value={cat.categoryId}>
                      {cat.name}
                    </option>
                  ))}
                </select>
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
                <select
                  name="cameraType"
                  value={form.cameraType}
                  onChange={handleChange}
                >
                  <option value="">선택</option>
                  {cameraTypes.map((type) => (
                    <option key={type.cameraTypeId} value={type.cameraTypeId}>
                      {type.name}
                    </option>
                  ))}
                </select>
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

      <div className="ci-scroll-bottom-gap" />
    </>
  );
}
