import '../css/Side.css';
import { useLocation, NavLink } from 'react-router-dom';
import { useState, useEffect } from 'react';
import React from 'react';

const menuData = [
	{
		title: '정보관리',
		items: [
			{ label: '사용자 관리', href: '/uml' },
			{ label: '연계 서버 관리', href: '/server' },
		],
	},
	{
		title: '스트리밍관리',
		items: [
			{ label: '스트리밍 뷰어', href: '/sv' },
			{ label: '개별 카메라 관리', href: '/cml' },
		],
	},
	{
		title: '이력 관리',
		items: [
			{ label: '에러 관리', href: '/errors' },
			{ label: '로그인 이력 관리', href: '/logins' },
			{ label: '개별 사용자 로그 관리', href: '/userlogs' },
		],
	},
];

function SideComponent() {
	const location = useLocation();
	const [openIndex, setOpenIndex] = useState(null);

	useEffect(() => {
		const index = menuData.findIndex(section =>
			section.items.some(item => location.pathname.startsWith(item.href))
		);
		if (index !== -1) setOpenIndex(index);
	}, [location.pathname]);

	const toggleMenu = (index) => {
		setOpenIndex(prev => (prev === index ? null : index));
	};

	return (
		<aside className="sidebar">
			<ul className="menu">
				{menuData.map((section, index) => (
					<li key={index} className={`menu-item ${openIndex === index ? 'open' : ''}`}>
						<div
							className={`menu-title ${openIndex === index ? 'active' : ''}`}
							onClick={() => toggleMenu(index)}
						>
							{section.title}
							<span className="arrow"></span>
						</div>
						<ul className="submenu">
							{section.items.map(item => (
								<li key={item.href}>
									<NavLink
										to={item.href}
										className={({ isActive }) => isActive ? 'active' : ''}
									>
										{item.label}
									</NavLink>
								</li>
							))}
						</ul>
					</li>
				))}
			</ul>
		</aside>
	);
}

export const Side = React.memo(SideComponent);
