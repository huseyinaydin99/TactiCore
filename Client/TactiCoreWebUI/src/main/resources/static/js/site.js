/* ============================================================
   TactiCore — Site JavaScript
   ============================================================ */

document.addEventListener('DOMContentLoaded', () => {
    initNavbar();
    initStaggerAnimation();
});

/* ── Navbar: toggler + scroll shadow ───────────────────────── */
function initNavbar() {
    const navbar   = document.getElementById('mainNavbar');
    const toggler  = document.getElementById('navToggler');
    const navMenu  = document.getElementById('navMenu');
    const icon     = document.getElementById('togglerIcon');

    if (toggler && navMenu) {
        toggler.addEventListener('click', () => {
            const isOpen = navMenu.classList.toggle('open');
            if (icon) {
                icon.className = isOpen ? 'bi bi-x-lg' : 'bi bi-list';
            }
        });

        // Dışarı tıklayınca kapat
        document.addEventListener('click', (e) => {
            if (!navbar.contains(e.target)) {
                navMenu.classList.remove('open');
                if (icon) icon.className = 'bi bi-list';
            }
        });
    }

    if (navbar) {
        window.addEventListener('scroll', () => {
            navbar.classList.toggle('scrolled', window.scrollY > 10);
        }, { passive: true });
    }
}

/* ── Stagger animation for .match-card elements ────────────── */
function initStaggerAnimation() {
    const cards = document.querySelectorAll('.match-card');
    cards.forEach((card, index) => {
        card.style.animationDelay = `${index * 0.05}s`;
    });
}
