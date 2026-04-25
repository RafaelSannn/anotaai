// ==========================================
// script2.js - VERSÃO OOP COM BACKEND FILTER & BCRYPT
// ==========================================

let users = []; // Serve agora como cache apenas para o Modal de Empresas
let currentLoggedInUser = null;
let currentChatWith = null;
let chatInterval = null; // Variável global para o motor de atualização do chat

const DEFAULT_PROFILE_IMAGE = 'https://via.placeholder.com/150/007bff/FFFFFF?text=Perfil';
const DEFAULT_COMPANY_IMAGE = 'https://via.placeholder.com/150/28a745/FFFFFF?text=Empresa';
const DEFAULT_PUBLICATION_IMAGE = 'https://via.placeholder.com/120/808080/FFFFFF?text=Serviço';

// ==========================================
// 1. CLASSES
// ==========================================
class Usuario {
    constructor(id, nome, email, tipo) {
        this.id = id; this.nome = nome; this.email = email; this.tipo = tipo;
    }
    renderProfile() { console.warn("Implementar nas filhas"); }
}

class Visitante extends Usuario {
    constructor(id, nome, email, profileImage) {
        super(id, nome, email, 'visitante');
        this.profileImage = profileImage || DEFAULT_PROFILE_IMAGE;
    }
    renderProfile() {
        document.getElementById('profileVisitorId').textContent = this.id;
        document.getElementById('profileVisitorEmail').textContent = this.email;
        document.getElementById('editarNomeVisitante').value = this.nome || '';
        document.getElementById('profileVisitorImage').src = this.profileImage;
    }
}

class Empresa extends Usuario {
    constructor(id, nome, email, companyDetails) {
        super(id, nome, email, 'empresa');
        this.companyDetails = companyDetails || {
            name: nome, location: '', category: '', description: '',
            profileImage: DEFAULT_COMPANY_IMAGE, views: 0, images: [], cnpj: ''
        };
    }
    renderProfile() {
        document.getElementById('profileCompanyId').textContent = this.id;
        document.getElementById('profileCompanyEmail').textContent = this.email;
        document.getElementById('editarNomeEmpresa').value = this.companyDetails.name || this.nome;
        document.getElementById('editarLocalizacaoEmpresa').value = this.companyDetails.location || '';
        document.getElementById('editarCategoriaEmpresa').value = this.companyDetails.category || '';
        document.getElementById('descricaoEmpresa').value = this.companyDetails.description || '';
        document.getElementById('companyViewsCount').textContent = this.companyDetails.views || 0;
        document.getElementById('profileCompanyProfileImage').src = this.companyDetails.profileImage || DEFAULT_COMPANY_IMAGE;
    }
}

class UserFactory {
    static criar(data) {
        if (!data) return null;
        if (data.tipo === 'empresa') return new Empresa(data.id, data.nome || data.name, data.email, data.companyDetails);
        return new Visitante(data.id, data.nome || data.name, data.email, data.profileImage);
    }
}

// ==========================================
// 2. INICIALIZAÇÃO
// ==========================================
function loadApp() {
    const rawUser = JSON.parse(localStorage.getItem('currentLoggedInUser'));
    if (rawUser) {
        currentLoggedInUser = UserFactory.criar(rawUser);
        document.getElementById('authContainer').style.display = 'none';
        showDashboard(currentLoggedInUser.tipo);
    } else {
        showForm('login');
    }
}

function showForm(formId) {
    document.querySelectorAll('.form').forEach(f => f.classList.remove('active'));
    document.getElementById(formId + 'Form').classList.add('active');
    document.querySelectorAll('.form-toggle button').forEach(b => b.classList.remove('active'));
    document.getElementById(formId + 'Btn').classList.add('active');
}

function toggleEmpresaCampos(show) { document.getElementById('empresaCampos').style.display = show ? 'block' : 'none'; }
function closeModal(modalId) { document.getElementById(modalId).style.display = 'none'; }

function showDashboard(userType) {
    if (userType === 'visitante') {
        document.getElementById('visitanteContainer').style.display = 'block';
        document.getElementById('empresaContainer').style.display = 'none';
        document.getElementById('visitanteDashboardTitle').textContent = `Bem-vindo(a), ${currentLoggedInUser.nome}!`;
        showPage('exploreCompanies');
    } else {
        document.getElementById('empresaContainer').style.display = 'block';
        document.getElementById('visitanteContainer').style.display = 'none';
        document.getElementById('empresaDashboardTitle').textContent = `Bem-vindo(a), Empresa ${currentLoggedInUser.companyDetails.name}!`;
        showPage('companyProfile');
    }
}

function showPage(pageId) {
    document.querySelectorAll('.profile-page').forEach(p => p.classList.remove('active'));
    const pageEl = document.getElementById(pageId);
    if (pageEl) pageEl.classList.add('active');

    document.querySelectorAll('.dashboard-nav button').forEach(b => b.classList.remove('active'));
    const activeBtn = document.querySelector(`.dashboard-nav button[onclick="showPage('${pageId}')"]`);
    if (activeBtn) activeBtn.classList.add('active');

    if (pageId === 'visitorProfile' || pageId === 'companyProfile') currentLoggedInUser.renderProfile();
    else if (pageId === 'exploreCompanies') renderCompanies();
    else if (pageId === 'visitorServicePublications' || pageId === 'companyServicePublications') renderPublications(currentLoggedInUser.tipo);
    else if (pageId === 'companyAllVisitorPublications') renderAllVisitorPublications();
    else if (pageId === 'visitorChats' || pageId === 'companyChats') renderChatList();
}

// ==========================================
// 3. AUTENTICAÇÃO COM API (Erros tratados)
// ==========================================
function fazerCadastro() {
    const nome = document.getElementById('nome').value.trim();
    const email = document.getElementById('email').value.trim();
    const senha = document.getElementById('senha').value;
    const confSenha = document.getElementById('confirmarSenha').value;

    if (senha !== confSenha) return alert("As palavras-passe não conferem!");

    const formData = new FormData();
    formData.append('nome', nome);
    formData.append('email', email);
    formData.append('senha', senha);

    const tipoCadastro = document.querySelector('input[name="tipoCadastro"]:checked').value;
    formData.append('tipo', tipoCadastro);

    if (tipoCadastro === 'empresa') {
        formData.append('localizacao', document.getElementById('localizacao').value);
        formData.append('cnpj', document.getElementById('cadastroCNPJ').value);
        formData.append('categoria', document.getElementById('categoriaEmpresa').value);
    }

    fetch('/php/register.php', { method: 'POST', body: formData })
    .then(res => res.json())
    .then(data => {
        alert(data.mensagem);
        if (data.status === 'sucesso') {
            document.getElementById('senha').value = '';
            document.getElementById('confirmarSenha').value = '';
            document.getElementById('loginEmail').value = email;
            showForm('login');
        }
    }).catch(err => console.error(err));
}

function fazerLogin() {
    const formData = new FormData();
    formData.append('email', document.getElementById('loginEmail').value);
    formData.append('senha', document.getElementById('loginSenha').value);

    fetch('/php/login.php', { method: 'POST', body: formData })
    .then(res => res.json())
    .then(data => {
        if (data.status === 'sucesso') {
            document.getElementById('loginSenha').value = '';
            currentLoggedInUser = UserFactory.criar(data.usuario);
            localStorage.setItem('currentLoggedInUser', JSON.stringify(currentLoggedInUser));
            loadApp();
        } else alert(data.mensagem);
    });
}

function logout() {
    currentLoggedInUser = null;
    localStorage.removeItem('currentLoggedInUser');
    document.getElementById('visitanteContainer').style.display = 'none';
    document.getElementById('empresaContainer').style.display = 'none';
    document.getElementById('authContainer').style.display = 'block';
    showForm('login');
}

// ==========================================
// 4. FILTRO INTELIGENTE NO BACKEND
// ==========================================
function renderCompanies(categoria = '') {
    const div = document.getElementById('listaEmpresas');
    if(!div) return;

    const url = categoria ? `/php/usuarios/empresa/${categoria}` : `/php/usuarios/empresas`;

    fetch(url)
    .then(res => res.json())
    .then(empresas => {
        users = empresas; // Cache
        if (empresas.length === 0) return div.innerHTML = '<p>Nenhuma empresa encontrada com os filtros atuais.</p>';

        div.innerHTML = empresas.map(e => `
            <div class="company-card">
                <h3>${e.nome}</h3>
                <p><strong>Categoria:</strong> ${e.categoria || 'N/A'}</p>
                <p><strong>Localização:</strong> ${e.localizacao || 'N/A'}</p>
                <button onclick="iniciarChatGenerico(${e.id}, '${e.nome}')">Entrar em Contacto</button>
                <button onclick="openCompanyDetailsModal(${e.id})" style="background-color: #6c757d; margin-left: 5px;">Ver Perfil</button>
            </div>
        `).join('');
    });
}

function filtrarEmpresas() {
    const categoria = document.getElementById('filtroCategoria').value;
    renderCompanies(categoria);
}

function openCompanyDetailsModal(id) {
    const company = users.find(u => u.id === id);
    if (!company) return;

    document.getElementById('modalCompanyContent').innerHTML = `
        <img class="profile-image-display" src="${company.profileImage || DEFAULT_COMPANY_IMAGE}">
        <h3>${company.nome}</h3>
        <p><strong>Email:</strong> ${company.email}</p>
        <p><strong>Localização:</strong> ${company.localizacao || 'Não informada'}</p>
        <p><strong>Categoria:</strong> ${company.categoria}</p>
        <p><strong>Descrição:</strong> ${company.descricao || ''}</p>
    `;
    currentChatWith = { targetUserId: company.id, targetUserName: company.nome };
    document.getElementById('companyDetailsModal').style.display = 'flex';
}

function startChatFromCompanyDetails() {
    closeModal('companyDetailsModal');
    openChat(currentChatWith.targetUserId, currentChatWith.targetUserName);
}

// ==========================================
// 5. CRUD DE PUBLICAÇÕES
// ==========================================
function openAddPublicationModal() {
    document.getElementById('pubTitulo').value = ''; document.getElementById('pubDescricao').value = '';
    document.getElementById('addPublicationModal').style.display = 'flex';
}
function openAddCompanyPublicationModal() {
    document.getElementById('companyPubTitulo').value = ''; document.getElementById('companyPubDescricao').value = '';
    document.getElementById('addCompanyPublicationModal').style.display = 'flex';
}

function processarEEnviarPublicacao(tituloId, descId, imgId, modalId) {
    const titulo = document.getElementById(tituloId).value;
    const descricao = document.getElementById(descId).value;
    const imgFile = document.getElementById(imgId) ? document.getElementById(imgId).files[0] : null;

    if (!titulo || !descricao) return alert('Preenche o título e a descrição.');

    const autor = currentLoggedInUser.tipo === 'visitante' ? currentLoggedInUser.nome : currentLoggedInUser.companyDetails.name;
    const payload = {
        titulo: titulo, descricao: descricao, tipoAutor: currentLoggedInUser.tipo,
        nomeAutor: autor, usuarioId: currentLoggedInUser.id, imagens: ""
    };

    const dispararPost = () => {
        fetch('/api/publicacoes', {
            method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload)
        })
        .then(() => {
            alert('Guardado!');
            closeModal(modalId);
            renderPublications(currentLoggedInUser.tipo);
        });
    };

    if (imgFile) {
        const reader = new FileReader();
        reader.onload = e => { payload.imagens = e.target.result; dispararPost(); };
        reader.readAsDataURL(imgFile);
    } else {
        dispararPost();
    }
}

function adicionarPublicacao() { processarEEnviarPublicacao('pubTitulo', 'pubDescricao', 'pubImagens', 'addPublicationModal'); }
function adicionarCompanyPublication() { processarEEnviarPublicacao('companyPubTitulo', 'companyPubDescricao', 'companyPubImagens', 'addCompanyPublicationModal'); }

function renderPublications(tipo) {
    const containerId = tipo === 'visitante' ? 'visitorPublications' : 'companyPublications';
    const div = document.getElementById(containerId);
    if (!div) return;

    fetch(`/api/publicacoes/usuario/${currentLoggedInUser.id}`)
    .then(res => res.json())
    .then(pubs => {
        if (pubs.length === 0) return div.innerHTML = '<p>Ainda não tens publicações.</p>';
        div.innerHTML = pubs.map(p => `
            <div class="publication-entry">
                <h4>${p.titulo}</h4>
                <p class="meta">Publicado em: ${new Date(p.dataCriacao).toLocaleString()}</p>
                <p>${p.descricao}</p>
                ${p.imagens ? `<div class="image-gallery"><img src="${p.imagens}"></div>` : ''}
                <button style="background-color: #dc3545; margin-top: 10px;" onclick="excluirPublicacao(${p.id}, '${tipo}')">Excluir</button>
            </div>
        `).join('');
    });
}

function excluirPublicacao(id, tipo) {
    if (confirm('Eliminar permanentemente da Base de Dados?')) {
        fetch(`/api/publicacoes/${id}`, { method: 'DELETE' }).then(() => renderPublications(tipo));
    }
}

function renderAllVisitorPublications() {
    const list = document.getElementById('allVisitorPublicationsList');
    if(!list) return;
    fetch(`/api/publicacoes/tipo/visitante`)
    .then(res => res.json())
    .then(pubs => {
        if (pubs.length === 0) return list.innerHTML = '<p>Nenhuma publicação.</p>';
        list.innerHTML = pubs.map(pub => `
            <div class="publication-entry">
                <h4>${pub.titulo}</h4>
                <p class="meta">Por: ${pub.nomeAutor}</p>
                <p>${pub.descricao}</p>
                ${pub.imagens ? `<div class="image-gallery"><img src="${pub.imagens}"></div>` : ''}
                <button onclick="iniciarChatGenerico(${pub.usuarioId}, '${pub.nomeAutor}')" style="margin-top: 10px;">Entrar em Contacto</button>
            </div>
        `).join('');
    });
}

// ==========================================
// 6. CHAT E MENSAGENS
// ==========================================
function renderChatList() {
    const list = document.getElementById(currentLoggedInUser.tipo === 'visitante' ? 'visitorChatList' : 'companyChatList');
    if (!list) return;

    fetch(`/api/mensagens/usuario/${currentLoggedInUser.id}`)
    .then(res => res.json())
    .then(mensagens => {
        if (mensagens.length === 0) return list.innerHTML = '<p>Ainda não tens mensagens na Base de Dados.</p>';

        const chatsAgrupados = {};
        mensagens.forEach(msg => {
            const idOutro = msg.remetenteId === currentLoggedInUser.id ? msg.destinatarioId : msg.remetenteId;
            const nomeOutro = msg.remetenteId === currentLoggedInUser.id ? "Conversa" : msg.remetenteNome;
            chatsAgrupados[idOutro] = { id: idOutro, nome: nomeOutro, ultima: msg };
        });

        list.innerHTML = Object.values(chatsAgrupados).map(chat => `
            <li class="chat-list-item" onclick="openChat(${chat.id}, '${chat.nome}')">
                <div class="chat-info">
                    <img src="${DEFAULT_PROFILE_IMAGE}">
                    <div><h4>ID #${chat.id}</h4><p>${chat.ultima.conteudo || '📷 Imagem'}</p></div>
                </div>
            </li>
        `).join('');
    });
}

function iniciarChatGenerico(alvoId, alvoNome) {
    closeModal('visitorPublicationDetailsModal');
    openChat(alvoId, alvoNome);
}

function openChat(targetUserId, targetUserName) {
    currentChatWith = { id: targetUserId, nome: targetUserName };
    document.getElementById('chatWithUserName').textContent = targetUserName || `Utilizador #${targetUserId}`;

    // Faz a primeira busca imediatamente
    const buscarMensagens = () => {
        fetch(`/api/mensagens/conversa/${currentLoggedInUser.id}/${targetUserId}`)
        .then(res => res.json())
        .then(mensagens => {
            renderChatMessages(mensagens);
            document.getElementById('chatOverlay').style.display = 'flex';
        });
    };

    buscarMensagens();

    // Cria o loop que busca mensagens novas a cada 3 segundos (3000 ms)
    if (chatInterval) clearInterval(chatInterval);
    chatInterval = setInterval(buscarMensagens, 3000);
}

function renderChatMessages(mensagens) {
    const div = document.getElementById('chatMessages');

    div.innerHTML = mensagens.map(msg => `
        <div class="message ${msg.remetenteId === currentLoggedInUser.id ? 'sent' : 'received'}">
            <span class="sender-name">${msg.remetenteId === currentLoggedInUser.id ? 'Tu' : msg.remetenteNome}</span>
            ${msg.conteudo ? `<span class="message-text">${msg.conteudo}</span>` : ''}
            ${msg.imagemBase64 ? `<img src="${msg.imagemBase64}">` : ''}
        </div>
    `).join('');

    div.scrollTop = div.scrollHeight;
}

function closeChat() {
    document.getElementById('chatOverlay').style.display = 'none';
    currentChatWith = null;
    // Desliga o motor quando fecha o chat para não gastar internet
    if (chatInterval) clearInterval(chatInterval);
}

function handleImageSend() {
    sendMessage();
}

function sendMessage() {
    const input = document.getElementById('chatMessageInput');
    const imgUpload = document.getElementById('chatImageUpload');

    // Se não tem texto E não tem imagem, não faz nada
    if (!input.value.trim() && (!imgUpload.files || imgUpload.files.length === 0)) return;

    const payload = {
        remetenteId: currentLoggedInUser.id,
        remetenteNome: currentLoggedInUser.nome,
        destinatarioId: currentChatWith.id,
        conteudo: input.value.trim(),
        imagemBase64: null
    };

    const enviarRest = () => {
        fetch('/api/mensagens', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        })
        .then(() => {
            input.value = '';
            imgUpload.value = '';
            openChat(currentChatWith.id, currentChatWith.nome);
            renderChatList();
        })
        .catch(err => console.error("Erro ao enviar mensagem:", err));
    };

    if (imgUpload.files && imgUpload.files[0]) {
        const reader = new FileReader();
        reader.onload = e => {
            payload.imagemBase64 = e.target.result;
            enviarRest();
        };
        reader.readAsDataURL(imgUpload.files[0]);
    } else {
        enviarRest();
    }
}

// ==========================================
// 7. ATUALIZAÇÃO DE PERFIL
// ==========================================

let pendingVisitorImage = null;
let pendingCompanyImage = null;

function handleProfileImageUpload(inputId, imgId, type) {
    const file = document.getElementById(inputId).files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = e => {
        document.getElementById(imgId).src = e.target.result;
        if (type === 'visitor') pendingVisitorImage = e.target.result;
        else pendingCompanyImage = e.target.result;
    };
    reader.readAsDataURL(file);
}

function salvarVisitante() {
    const nome = document.getElementById('editarNomeVisitante').value.trim();
    const formData = new FormData();
    formData.append('id', currentLoggedInUser.id);
    if (nome) formData.append('nome', nome);
    if (pendingVisitorImage) formData.append('profileImage', pendingVisitorImage);

    fetch('/php/perfil.php', { method: 'PUT', body: formData })
    .then(res => res.json())
    .then(data => {
        alert(data.mensagem);
        if (data.status === 'sucesso') {
            if (nome) currentLoggedInUser.nome = nome;
            if (pendingVisitorImage) currentLoggedInUser.profileImage = pendingVisitorImage;
            pendingVisitorImage = null;
            localStorage.setItem('currentLoggedInUser', JSON.stringify(currentLoggedInUser));
            currentLoggedInUser.renderProfile();
        }
    }).catch(err => console.error('Erro ao salvar perfil:', err));
}

function salvarEmpresa() {
    const nome = document.getElementById('editarNomeEmpresa').value.trim();
    const localizacao = document.getElementById('editarLocalizacaoEmpresa').value.trim();
    const categoria = document.getElementById('editarCategoriaEmpresa').value;
    const descricao = document.getElementById('descricaoEmpresa').value.trim();

    const formData = new FormData();
    formData.append('id', currentLoggedInUser.id);
    if (nome) formData.append('nome', nome);
    formData.append('localizacao', localizacao);
    formData.append('categoria', categoria);
    formData.append('descricao', descricao);
    if (pendingCompanyImage) formData.append('profileImage', pendingCompanyImage);

    fetch('/php/perfil.php', { method: 'PUT', body: formData })
    .then(res => res.json())
    .then(data => {
        alert(data.mensagem);
        if (data.status === 'sucesso') {
            if (nome) { currentLoggedInUser.nome = nome; currentLoggedInUser.companyDetails.name = nome; }
            currentLoggedInUser.companyDetails.location = localizacao;
            currentLoggedInUser.companyDetails.category = categoria;
            currentLoggedInUser.companyDetails.description = descricao;
            if (pendingCompanyImage) currentLoggedInUser.companyDetails.profileImage = pendingCompanyImage;
            pendingCompanyImage = null;
            localStorage.setItem('currentLoggedInUser', JSON.stringify(currentLoggedInUser));
            currentLoggedInUser.renderProfile();
        }
    }).catch(err => console.error('Erro ao salvar empresa:', err));
}

function mostrarImagensEmpresa() {
    const files = document.getElementById('imagensEmpresa').files;
    const preview = document.getElementById('previewImagensEmpresa');
    preview.innerHTML = '';
    Array.from(files).forEach(file => {
        const reader = new FileReader();
        reader.onload = e => {
            const img = document.createElement('img');
            img.src = e.target.result;
            img.style.cssText = 'width:80px;height:80px;object-fit:cover;margin:4px;border-radius:4px;';
            preview.appendChild(img);
        };
        reader.readAsDataURL(file);
    });
}

// ==========================================
// INICIALIZAÇÃO E EVENTOS DE TECLADO (ENTER)
// ==========================================
document.addEventListener('DOMContentLoaded', () => {
    loadApp();

    // Enter no Login
    document.getElementById('loginSenha').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') fazerLogin();
    });

    // Enter no Cadastro
    document.getElementById('confirmarSenha').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') fazerCadastro();
    });

    // Enter no Chat
    document.getElementById('chatMessageInput').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') sendMessage();
    });
});