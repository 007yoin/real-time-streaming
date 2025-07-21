import { useParams } from "react-router-dom";
import CameraForm from "./CameraForm";

export default function CameraFormWrapper() {
  const { cameraId } = useParams();
  return <CameraForm cameraId={cameraId} />;
}
