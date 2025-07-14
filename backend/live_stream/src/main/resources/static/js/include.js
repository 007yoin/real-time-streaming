function includeHTML(selector, url, callback) {
    fetch(url)
        .then(res => {
            if (!res.ok) throw new Error(`Failed to load ${url}`);
            return res.text();
        })
        .then(data => {
            const el = document.querySelector(selector);
            if (el) {
                el.innerHTML = data;
                if (callback) callback(); // ✅ 로드 후 실행
            }
        })
        .catch(err => console.error(err));
}

document.addEventListener('DOMContentLoaded', () => {
    includeHTML('#include-header', '/components/header.html');

    includeHTML('#include-sidebar', '/components/sidebar.html', () => {
        const currentPath = window.location.pathname;

        // 메뉴 클릭 토글
        document.querySelectorAll('.menu-title').forEach(title => {
            title.addEventListener('click', () => {
                const item = title.closest('.menu-item');
                item.classList.toggle('open');
            });
        });

        // 현재 경로에 맞는 메뉴 자동 열기
        document.querySelectorAll('.menu-item').forEach(menuItem => {
            const links = menuItem.querySelectorAll('a');

            links.forEach(link => {
                const href = link.getAttribute('href');
                if (href && (currentPath === href || currentPath.startsWith(href + '/'))) {
                    menuItem.classList.add('open');
                    link.classList.add('active');

                    const title = menuItem.querySelector('.menu-title');
                    if (title) title.classList.add('active');
                }
            });
        });
    });
});
