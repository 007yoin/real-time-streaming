import { useAuthCheck } from "./hooks/authCheck";
import { App } from "./App";

export function AuthGate() {
  const loading = useAuthCheck();

  if (loading) return null; // 인증 확인 중일 땐 아무것도 렌더링 안 함
  return <App />;
}
