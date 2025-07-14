// src/api/cameraApi.js
import axiosInstance from './axiosInstance';

/**
 * 카메라 정보를 서버에 전송 (간결 버전)
 * @param {Object} form - 입력 폼 데이터 (CameraInsert.jsx에서 그대로 전달)
 */
export async function insertCamera(form) {
  return (
    await axiosInstance.post('/camera', {
      ...form,
      id: null,
      latitude: form.latitude ? +form.latitude : null,
      longitude: form.longitude ? +form.longitude : null,
    })
  ).data;
}
