function fazerCadastro() {
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const confirmarSenha = document.getElementById('confirmarSenha').value;

    if (senha !== confirmarSenha) {
        alert("As senhas não conferem!");
        return;
    }

    const tipoCadastro = document.querySelector('input[name="tipoCadastro"]:checked').value;

    let dados = {
        nome: nome,
        email: email,
        senha: senha,
        tipo: tipoCadastro
    };

    if (tipoCadastro === 'empresa') {
        dados.nomeEmpresa = document.getElementById('nomeEmpresa').value;
        dados.localizacao = document.getElementById('localizacao').value;
        dados.cnpj = document.getElementById('cadastroCNPJ').value;
        dados.categoria = document.getElementById('categoriaEmpresa').value;
    }

    fetch('http://localhost/finalmente/php/cadastrar.php', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(dados)
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
    })
    .catch(error => {
        console.error('Erro:', error);
    });
}



function fazerLogin() {
    const email = document.getElementById('loginEmail').value;
    const senha = document.getElementById('loginSenha').value;

    if (email === '' || senha === '') {
        alert('Preencha todos os campos.');
        return;
    }

    const formData = new FormData();
    formData.append('email', email);
    formData.append('senha', senha);

    fetch('backend/login.php', {
        method: 'POST',
        body: formData
    })
    .then(resp => resp.json())
    .then(data => {
        alert(data.mensagem);
        if (data.status === 'sucesso') {
            sessionStorage.setItem('usuarioLogado', JSON.stringify(data.usuario));
            showPage('home'); // Redireciona para a página inicial
        }
    });
}


function logout() {
    sessionStorage.removeItem('usuarioLogado');
    alert('Você saiu da conta.');
    showForm('login');
}


