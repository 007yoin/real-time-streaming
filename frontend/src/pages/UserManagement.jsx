// src/pages/UserManagement.jsx
import '../css/UserManagement.css';

export default function UserManagement() {
	return (
		<>
			{/* 섹션: 사용자 정보 */}
			<section className="um-section">
				<h3>사용자 관리</h3>
				<table className="um-table">
					<tbody>
						<tr>
							<th>사용자 ID</th>
							<td><input type="text" disabled /></td>
							<th>비밀번호 변경</th>
							<td><input type="text" /></td>
						</tr>
						<tr>
							<th>
								사용자명 <span style={{ color: 'red' }}>*</span>
							</th>
							<td><input type="text" /></td>
							<th>비밀번호 변경 확인</th>
							<td><input type="text" /></td>
						</tr>
						<tr>
							<th>
								권한 <span style={{ color: 'red' }}>*</span>
							</th>
							<td>
								<select>
									<option value="ADMIN">ADMIN</option>
									<option value="USER">USER</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>비고</th>
							<td colSpan="3">
								<textarea rows="3" />
							</td>
						</tr>
					</tbody>
				</table>
			</section>

			{/* 하단 버튼 */}
			<div className="um-bottom-actions">
				<button className="um-btn um-gray">목록</button>
				<button className="um-btn um-blue">수정</button>
			</div>
		</>
	);
}
