import axiosInstance from "./axiosInstance";

/**
 * 카메라 정보를 서버에 전송
 */
export async function insertCamera(form) {
  return (
    await axiosInstance.post("/camera", {
      ...form,
      id: null,
      latitude: form.latitude ? +form.latitude : null,
      longitude: form.longitude ? +form.longitude : null,
    })
  ).data;
}
