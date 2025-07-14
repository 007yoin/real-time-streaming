import '../css/Header.css';
import { clearAccessToken } from '../store/tokenStore';

export function Header() {
  const handleLogout = async (e) => {
    e.preventDefault(); // <a> 태그 기본 동작 방지

    try {
      // 1. 서버에 로그아웃 요청
      await fetch('/api/auth/logout', {
        method: 'POST',
        credentials: 'include', // HttpOnly 쿠키 포함
      });

      // 2. 클라이언트 상태 정리
      clearAccessToken();

      // 3. 로그인 페이지로 이동
      window.location.href = '/login';
    } catch (error) {
      console.error('로그아웃 실패:', error);
    }
  };

  return (
    <header className="main-header">
      <div className="logo">실시간 스트리밍 연계 시스템</div>
      <div className="user-menu">
        <span>⏱ 무제한</span>
        <span><strong>ucube17</strong> 님</span>
        <a href="#">비밀번호 변경</a>
        <a href="#">매뉴얼 보기</a>
        <a href="#" className="logout" onClick={handleLogout}>🔌 로그아웃</a>
      </div>
    </header>
  );
}
