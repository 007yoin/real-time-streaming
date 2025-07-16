import { useEffect, useRef } from "react";

export function GenericConfirmModal({
  onClose,
  onConfirm,
  title,
  confirmText,
  confirmClass = "confirm",
}) {
  const btnRef = useRef(null);

  useEffect(() => {
    btnRef.current?.focus();

    const handleKeyDown = (e) => {
      if (e.key === "Enter") {
        e.preventDefault();
        onClose();
        onConfirm();
      }
      if (e.key === "Escape") {
        e.preventDefault();
        onClose();
      }
    };

    window.addEventListener("keydown", handleKeyDown);
    return () => window.removeEventListener("keydown", handleKeyDown);
  }, []);

  return (
    <div className="generic-modal">
      <h2>{title}</h2>
      <div className="generic-modal-actions">
        <button type="button" className="modal-btn cancel" onClick={onClose}>
          취소
        </button>
        <button
          type="button"
          className={`modal-btn ${confirmClass}`}
          ref={btnRef}
          onClick={() => {
            onClose();
            onConfirm();
          }}
        >
          {confirmText}
        </button>
      </div>
    </div>
  );
}

export function ModifyModal({ onClose, onConfirm }) {
  const confirmBtnRef = useRef(null);

  useEffect(() => {
    confirmBtnRef.current?.focus();

    const handleKeyDown = (e) => {
      if (e.key === "Enter") {
        e.preventDefault();
        onClose();
        onConfirm();
      }
      if (e.key === "Escape") {
        e.preventDefault();
        onClose();
      }
    };

    window.addEventListener("keydown", handleKeyDown);
    return () => window.removeEventListener("keydown", handleKeyDown);
  }, []);

  return (
    <div className="modify-modal">
      <h2>정말 수정하시겠습니까?</h2>
      <div className="modify-modal-actions">
        <button type="button" className="modal-btn cancel" onClick={onClose}>
          취소
        </button>
        <button
          type="button"
          className="modal-btn confirm-update"
          ref={confirmBtnRef}
          onClick={() => {
            onClose();
            onConfirm();
          }}
        >
          수정
        </button>
      </div>
    </div>
  );
}

export function DeleteModal({ onClose, onConfirm }) {
  const deleteBtnRef = useRef(null);

  useEffect(() => {
    deleteBtnRef.current?.focus();

    const handleKeyDown = (e) => {
      if (e.key === "Enter") {
        e.preventDefault();
        onClose();
        onConfirm();
      }
      if (e.key === "Escape") {
        e.preventDefault();
        onClose();
      }
    };

    window.addEventListener("keydown", handleKeyDown);
    return () => window.removeEventListener("keydown", handleKeyDown);
  }, []);

  return (
    <div className="delete-modal">
      <h2>정말 삭제하시겠습니까?</h2>
      <div className="delete-modal-actions">
        <button type="button" className="modal-btn cancel" onClick={onClose}>
          취소
        </button>
        <button
          type="button"
          className="modal-btn confirm"
          ref={deleteBtnRef}
          onClick={() => {
            onClose();
            onConfirm();
          }}
        >
          삭제
        </button>
      </div>
    </div>
  );
}
