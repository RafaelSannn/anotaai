// ==========================================
// SISTEMA DE TEMA CLARO/ESCURO
// ==========================================

/**
 * Inicializa o tema com base na preferência salva no localStorage
 */
function initTheme() {
    const savedTheme = localStorage.getItem('theme') || 'light';
    applyTheme(savedTheme);
}

/**
 * Aplica o tema especificado ao documento
 * @param {string} theme - 'light' ou 'dark'
 */
function applyTheme(theme) {
    const html = document.documentElement;
    const themeIcon = document.getElementById('themeIcon');
    const themeText = document.getElementById('themeText');
    
    if (theme === 'dark') {
        html.setAttribute('data-theme', 'dark');
        if (themeIcon) themeIcon.textContent = '☀️';
        if (themeText) themeText.textContent = 'Claro';
    } else {
        html.setAttribute('data-theme', 'light');
        if (themeIcon) themeIcon.textContent = '🌙';
        if (themeText) themeText.textContent = 'Escuro';
    }
    
    // Salva a preferência
    localStorage.setItem('theme', theme);
}

/**
 * Alterna entre tema claro e escuro
 */
function toggleTheme() {
    const html = document.documentElement;
    const currentTheme = html.getAttribute('data-theme') || 'light';
    const newTheme = currentTheme === 'light' ? 'dark' : 'light';
    
    applyTheme(newTheme);
}

/**
 * Retorna o tema atual
 * @returns {string} 'light' ou 'dark'
 */
function getCurrentTheme() {
    return document.documentElement.getAttribute('data-theme') || 'light';
}

// Inicializa o tema quando o DOM estiver pronto
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initTheme);
} else {
    // DOM já está pronto
    initTheme();
}
