import { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { toast } from "react-toastify";
import axiosInstance from "../api/axiosInstance";
import "../css/CameraManagementList.css";
import { AnimatePresence, motion } from "framer-motion";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";
import { DeleteModal, GenericConfirmModal } from "../components/ConfirmModal";

export default function CameraManagementList() {
  const navigate = useNavigate();
  const location = useLocation();

  const [cameraList, setCameraList] = useState([]);
  const [totalElements, setTotalElements] = useState(0);
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [pendingPageSize, setPendingPageSize] = useState(10);
  const [isFetching, setIsFetching] = useState(false);
  const [initialLoading, setInitialLoading] = useState(true);
  const [selectedCameraIds, setSelectedCameraIds] = useState([]);
  const [animationKey, setAnimationKey] = useState(0);

  const [largeCategories, setLargeCategories] = useState([]);
  const [mediumCategories, setMediumCategories] = useState([]);
  const [smallCategories, setSmallCategories] = useState([]);

  const [searchCond, setSearchCond] = useState({
    name: "",
    active: "",
    status: "",
    categoryId: "",
    largeCategory: "",
    mediumCategory: "",
    smallCategory: "",
  });

  const statusEnumMap = {
    대기: "PENDING",
    처리중: "PROCESSING",
    정지: "STOPPED",
    비활성화: "DISABLED",
    에이전트정지: "AGENT_STOPPED",
  };

  const handleCameraClick = (cameraId) => {
    navigate(`/ci/${cameraId}`);
  };

  const handleActivateSelected = () => {
    if (selectedCameraIds.length === 0) {
      toast.warn("활성화할 카메라를 선택하세요", { containerId: "global" });
      return;
    }

    confirmAlert({
      customUI: ({ onClose }) => (
        <GenericConfirmModal
          onClose={onClose}
          onConfirm={() => {
            axiosInstance
              .post("/cameras/activate", selectedCameraIds)
              .then(() => {
                toast.success("활성화 완료", { containerId: "global" });
                setSelectedCameraIds([]);
                fetchCameras(page, pageSize, searchCond);
                setAnimationKey((prev) => prev + 1);
              })
              .catch(() => {
                toast.error("활성화 실패", { containerId: "global" });
              });
          }}
          title="정말 활성화하시겠습니까?"
          confirmText="활성"
          confirmClass="cml-green"
        />
      ),
    });
  };

  const handleDeactivateSelected = () => {
    if (selectedCameraIds.length === 0) {
      toast.warn("비활성화할 카메라를 선택하세요", { containerId: "global" });
      return;
    }

    confirmAlert({
      customUI: ({ onClose }) => (
        <GenericConfirmModal
          onClose={onClose}
          onConfirm={() => {
            axiosInstance
              .post("/cameras/deactivate", selectedCameraIds)
              .then(() => {
                setSelectedCameraIds([]);
                toast.success("비활성화 완료", { containerId: "global" });
                fetchCameras(page, pageSize, searchCond);
              })
              .catch(() => {
                toast.error("비활성화 실패", { containerId: "global" });
              });
          }}
          title="정말 비활성화하시겠습니까?"
          confirmText="비활성"
          confirmClass="cml-gray"
        />
      ),
    });
  };

  const handleDeleteSelected = () => {
    if (selectedCameraIds.length === 0) {
      toast.warn("삭제할 카메라를 선택하세요", { containerId: "global" });
      return;
    }

    confirmAlert({
      customUI: ({ onClose }) => (
        <DeleteModal
          onClose={onClose}
          onConfirm={() => {
            axiosInstance
              .delete("/cameras", { data: selectedCameraIds })
              .then(() => {
                setSelectedCameraIds([]);
                toast.success("삭제 완료", { containerId: "global" });
                setAnimationKey((prev) => prev + 1);
                fetchCameras(page, pageSize, searchCond);
              })
              .catch(() => {
                toast.error("삭제 실패", { containerId: "global" });
              });
          }}
        />
      ),
    });
  };

  const handleCategoryChange = (field, value) => {
    setSearchCond((prev) => {
      if (prev[field] === value) return prev;

      const updated = { ...prev, [field]: value };

      if (field === "largeCategory") {
        updated.mediumCategory = "";
        updated.smallCategory = "";
      } else if (field === "mediumCategory") {
        updated.smallCategory = "";
      }

      return updated;
    });
  };

  useEffect(() => {
    const newCategoryId =
      searchCond.smallCategory ||
      searchCond.mediumCategory ||
      searchCond.largeCategory ||
      "";

    if (searchCond.categoryId !== newCategoryId) {
      setSearchCond((prev) => ({ ...prev, categoryId: newCategoryId }));
    }
  }, [
    searchCond.smallCategory,
    searchCond.mediumCategory,
    searchCond.largeCategory,
  ]);

  useEffect(() => {
    axiosInstance
      .get("/camCategory/large")
      .then((res) => setLargeCategories(res.data))
      .catch((err) => {
        console.error("대분류 로딩 실패", err);
        toast.error("대분류 카테고리 로딩 실패", { containerId: "global" });
      });
  }, []);

  useEffect(() => {
    if (!searchCond.largeCategory) {
      setMediumCategories([]);
      setSmallCategories([]);
      setSearchCond((prev) => {
        if (prev.mediumCategory === "" && prev.smallCategory === "")
          return prev;
        return {
          ...prev,
          mediumCategory: "",
          smallCategory: "",
        };
      });
      return;
    }

    axiosInstance
      .get(`/camCategory/medium/${searchCond.largeCategory}`)
      .then((res) => {
        setMediumCategories(res.data);
        setSmallCategories([]);
        setSearchCond((prev) => ({
          ...prev,
          mediumCategory: "",
          smallCategory: "",
        }));
      })
      .catch((err) => {
        console.error("중분류 로딩 실패", err);
        toast.error("중분류 카테고리 로딩 실패", { containerId: "global" });
      });
  }, [searchCond.largeCategory]);

  useEffect(() => {
    if (!searchCond.mediumCategory || !searchCond.largeCategory) {
      setSmallCategories([]);
      setSearchCond((prev) => ({
        ...prev,
        smallCategory: "",
      }));
      return;
    }

    axiosInstance
      .get(
        `/camCategory/small/${searchCond.mediumCategory}/${searchCond.largeCategory}`
      )
      .then((res) => {
        setSmallCategories(res.data);
        setSearchCond((prev) => ({
          ...prev,
          smallCategory: "",
        }));
      })
      .catch((err) => {
        console.error("소분류 로딩 실패", err);
        toast.error("소분류 카테고리 로딩 실패", { containerId: "global" });
      });
  }, [searchCond.mediumCategory]);

  useEffect(() => {
    if (location.state?.toast) {
      toast.success(location.state.toast, { containerId: "global" });
      navigate(location.pathname, { replace: true, state: {} });
    }
  }, [location, navigate]);

  const fetchCameras = (page, size, cond, isInitial = false) => {
    if (isInitial) setInitialLoading(true);
    else setIsFetching(true);

    const params = {
      page,
      size,
      name: cond.name || undefined,
      active:
        cond.active === "활성"
          ? true
          : cond.active === "비활성"
          ? false
          : undefined,
      status:
        cond.status === "" || cond.status === "선택"
          ? undefined
          : statusEnumMap[cond.status],
      categoryId: cond.categoryId || undefined,
    };

    axiosInstance
      .get("/camera/search", { params })
      .then((res) => {
        setCameraList(res.data.content);
        setTotalElements(res.data.totalElements);
      })
      .catch((err) => {
        console.error("카메라 조회 실패", err);
        toast.error("카메라 데이터를 불러오지 못했습니다.", {
          containerId: "global",
        });
      })
      .finally(() => {
        if (isInitial) setInitialLoading(false);
        else setIsFetching(false);
      });
  };

  useEffect(() => {
    fetchCameras(page, pageSize, searchCond, true);
  }, []);

  useEffect(() => {
    if (!initialLoading) {
      fetchCameras(page, pageSize, searchCond);
    }
  }, [page, pageSize]);

  const handleRegisterClick = () => navigate("/ci");

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < Math.ceil(totalElements / pageSize)) {
      setPage(newPage);
    }
  };

  const handleInputChange = (field, value) => {
    setSearchCond((prev) => ({ ...prev, [field]: value }));
  };

  const handleEnterKey = (e) => {
    if (e.key === "Enter") handleQueryClick();
  };

  const handleQueryClick = () => {
    setPage(0);
    setPageSize(pendingPageSize);
    setAnimationKey((prev) => prev + 1);
    fetchCameras(0, pendingPageSize, searchCond);
  };

  const totalPages = Math.ceil(totalElements / pageSize);
  const visiblePageCount = 5;
  const half = Math.floor(visiblePageCount / 2);

  const statusLabelMap = {
    PENDING: "대기",
    PROCESSING: "처리중",
    STOPPED: "정지",
    DISABLED: "비활성화",
    AGENT_STOPPED: "에이전트정지",
  };

  const statusClassMap = {
    PENDING: "cml-wait",
    PROCESSING: "cml-processing",
    STOPPED: "cml-stop",
    DISABLED: "cml-disabled",
    AGENT_STOPPED: "cml-agent-stop",
  };

  return (
    <div className="cml-main-body">
      <section className="cml-section">
        <h3>개별 카메라 관리</h3>
        <div className="cml-filter-panel">
          <div className="cml-filter-grid">
            {[
              ["스트리밍명", "name"],
              [
                "활성상태",
                "active",
                [
                  { label: "선택", value: "" },
                  { label: "활성", value: "활성" },
                  { label: "비활성", value: "비활성" },
                ],
              ],
              [
                "상태코드",
                "status",
                [
                  { label: "선택", value: "" },
                  { label: "대기", value: "대기" },
                  { label: "처리중", value: "처리중" },
                  { label: "정지", value: "정지" },
                  { label: "비활성화", value: "비활성화" },
                  { label: "에이전트정지", value: "에이전트정지" },
                ],
              ],
              [
                "대분류",
                "largeCategory",
                largeCategories.map((c) => ({
                  label: c.name,
                  value: c.categoryId,
                })),
              ],
              [
                "중분류",
                "mediumCategory",
                mediumCategories.map((c) => ({
                  label: c.name,
                  value: c.categoryId,
                })),
              ],
              [
                "소분류",
                "smallCategory",
                smallCategories.map((c) => ({
                  label: c.name,
                  value: c.categoryId,
                })),
              ],
            ].map(([label, field, options]) => (
              <div className="cml-filter-item" key={field}>
                <label>{label}</label>
                {options ? (
                  <select
                    value={searchCond[field] || ""}
                    onChange={(e) =>
                      handleCategoryChange(field, e.target.value)
                    }
                  >
                    <option value="">선택</option>
                    {options.map((opt) =>
                      typeof opt === "string" ? (
                        <option key={opt} value={opt}>
                          {opt}
                        </option>
                      ) : (
                        <option key={opt.value} value={opt.value}>
                          {opt.label}
                        </option>
                      )
                    )}
                  </select>
                ) : (
                  <input
                    type="text"
                    value={searchCond[field]}
                    onChange={(e) => handleInputChange(field, e.target.value)}
                    onKeyDown={handleEnterKey}
                  />
                )}
              </div>
            ))}
          </div>
        </div>
        <div className="cml-top-controls">
          <select
            value={pendingPageSize}
            onChange={(e) => setPendingPageSize(parseInt(e.target.value))}
          >
            {[10, 50, 200, 1000, 2000].map((num) => (
              <option key={num} value={num}>
                {num}
              </option>
            ))}
          </select>
          <button className="cml-btn" onClick={handleQueryClick}>
            조회
          </button>
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
                <th>
                  <input
                    type="checkbox"
                    checked={
                      cameraList.length > 0 &&
                      selectedCameraIds.length === cameraList.length
                    }
                    onChange={(e) => {
                      if (e.target.checked) {
                        setSelectedCameraIds(cameraList.map((c) => c.id));
                      } else {
                        setSelectedCameraIds([]);
                      }
                    }}
                  />
                </th>
                <th>스트리밍번호</th>
                <th>스트리밍명</th>
                <th>대분류</th>
                <th>중분류</th>
                <th>소분류</th>
                <th>유형</th>
                <th>활성상태</th>
                <th>상태코드</th>
              </tr>
            </thead>
            <AnimatePresence mode="wait">
              <motion.tbody
                key={`page-${page}-size-${pageSize}-query-${animationKey}`}
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                transition={{ duration: 0.3 }}
              >
                {cameraList.length > 0 ? (
                  cameraList.map((camera) => (
                    <tr key={camera.id}>
                      <td>
                        <input
                          type="checkbox"
                          checked={selectedCameraIds.includes(camera.id)}
                          onChange={(e) => {
                            setSelectedCameraIds((prev) =>
                              e.target.checked
                                ? [...prev, camera.id]
                                : prev.filter((id) => id !== camera.id)
                            );
                          }}
                        />
                      </td>
                      <td>STREAM_{camera.id}</td>
                      <td
                        className="clickable"
                        onClick={() => handleCameraClick(camera.id)}
                      >
                        {camera.name}
                      </td>

                      <td>{camera.largeCategoryName || ""}</td>
                      <td>{camera.mediumCategoryName || ""}</td>
                      <td>{camera.smallCategoryName || ""}</td>
                      <td>{camera.typeName || ""}</td>
                      <td>
                        <span
                          className={`cml-badge ${
                            camera.active ? "cml-active" : "cml-inactive"
                          }`}
                        >
                          {camera.active ? "활성" : "비활성"}
                        </span>
                      </td>
                      <td>
                        <span
                          className={`cml-badge ${
                            statusClassMap[camera.status] || "cml-default"
                          }`}
                        >
                          {statusLabelMap[camera.status] || "알수없음"}
                        </span>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="9" style={{ textAlign: "center" }}>
                      {isFetching ? "로딩 중..." : "표시할 카메라가 없습니다."}
                    </td>
                  </tr>
                )}
              </motion.tbody>
            </AnimatePresence>
          </table>

          {totalPages > 0 && (
            <div className="cml-pagination">
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
                    className={`cml-page-number ${
                      pageNum === page ? "cml-active" : ""
                    } ${!isValid ? "cml-disabled" : ""}`}
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

        <div className="cml-bottom-actions">
          <div className="cml-left-buttons">
            <button className="cml-btn cml-blue">엑셀 업로드 샘플</button>
            <button className="cml-btn cml-blue">엑셀파일 스트리밍 등록</button>
            <button className="cml-btn cml-blue">
              스트리밍 데이터 다운로드
            </button>
          </div>
          <div className="cml-right-buttons">
            <button
              className="cml-btn cml-gray"
              onClick={handleDeactivateSelected}
            >
              비활성
            </button>
            <button
              className="cml-btn cml-green"
              onClick={handleActivateSelected}
            >
              활성
            </button>
            <button className="cml-btn cml-blue" onClick={handleRegisterClick}>
              등록
            </button>
            <button
              className="cml-btn cml-red"
              onClick={handleDeleteSelected}
              disabled={selectedCameraIds.length === 0}
            >
              삭제
            </button>
          </div>
        </div>
      </section>
    </div>
  );
}
