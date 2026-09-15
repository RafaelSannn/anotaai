-- =====================================================
-- SCRIPT DE DADOS INICIAIS PARA TESTES
-- =====================================================
-- Execute: sudo mariadb -u root < setup-dados-teste.sql
-- =====================================================

USE anotaai;

-- Limpar dados existentes
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE mensagens;
TRUNCATE TABLE publicacoes;
TRUNCATE TABLE usuarios;
SET FOREIGN_KEY_CHECKS = 1;

-- =====================================================
-- VISITANTES (Senha: 123456)
-- Hash BCrypt de "123456"
-- =====================================================

INSERT INTO usuarios (nome, email, senha, tipo) VALUES
('João Silva',   'joao@email.com',  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'visitante'),
('Maria Santos', 'maria@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'visitante'),
('Pedro Costa',  'pedro@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'visitante');

-- =====================================================
-- EMPRESAS (Senha: 123456)
-- =====================================================

INSERT INTO usuarios (nome, email, senha, tipo, categoria, cnpj, descricao) VALUES
('TechFix Reparos',    'contato@techfix.com',     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'empresa', 'Manutenção e Reparos', '12345678000190', 'Especializada em conserto de eletrônicos e eletrodomésticos'),
('CleanPro Limpeza',   'contato@cleanpro.com',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'empresa', 'Limpeza',              '23456789000191', 'Serviços profissionais de limpeza residencial e comercial'),
('Beleza & Estilo',    'contato@belezaestilo.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'empresa', 'Beleza e Estética',    '34567890000192', 'Salão de beleza completo com manicure, cabeleireiro e estética'),
('AutoMaster Oficina', 'contato@automaster.com',  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'empresa', 'Automotivo',           '45678901000193', 'Oficina mecânica especializada em carros nacionais e importados'),
('EduTech Cursos',     'contato@edutech.com',     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'empresa', 'Educação',             '56789012000194', 'Cursos online e presenciais de tecnologia e programação');

-- =====================================================
-- PUBLICAÇÕES
-- Campos: titulo, descricao, tipo_autor, nome_autor, usuario_id
-- =====================================================

-- Publicações de Visitantes (IDs 1, 2)
INSERT INTO publicacoes (titulo, descricao, tipo_autor, nome_autor, usuario_id) VALUES
('Preciso de eletricista urgente', 'Tomadas da sala pararam de funcionar. Preciso com urgência!',     'visitante', 'João Silva',   1),
('Procuro manicure a domicílio',   'Gostaria de contratar manicure para fazer em casa aos sábados.',   'visitante', 'Maria Santos', 2);

-- Publicações de Empresas (IDs 4, 5, 6, 7, 8)
INSERT INTO publicacoes (titulo, descricao, tipo_autor, nome_autor, usuario_id) VALUES
('Promoção: Conserto de Smartphone', 'Troca de tela com 20% de desconto nesta semana! Todas as marcas.',          'empresa', 'TechFix Reparos',    4),
('Limpeza Pós-Obra',                 'Especializados em limpeza pós-construção. Equipe treinada.',                 'empresa', 'CleanPro Limpeza',   5),
('Curso Intensivo de React',          'Aprenda React em 8 semanas. Próxima turma dia 20. Vagas limitadas!',        'empresa', 'EduTech Cursos',     8),
('Revisão Completa de Veículos',     'Pacote: troca de óleo, filtros, alinhamento e balanceamento por R$ 299.',    'empresa', 'AutoMaster Oficina', 7);

-- =====================================================
-- VERIFICAÇÃO FINAL
-- =====================================================

SELECT '========================================' AS '';
SELECT 'DADOS DE TESTE INSERIDOS COM SUCESSO!' AS '';
SELECT '========================================' AS '';
SELECT '' AS '';
SELECT CONCAT('  Visitantes:  ', COUNT(*)) AS '' FROM usuarios WHERE tipo = 'visitante';
SELECT CONCAT('  Empresas:    ', COUNT(*)) AS '' FROM usuarios WHERE tipo = 'empresa';
SELECT CONCAT('  Publicações: ', COUNT(*)) AS '' FROM publicacoes;
SELECT '' AS '';
SELECT 'LOGINS DE TESTE (senha: 123456):' AS '';
SELECT '  joao@email.com           (visitante)' AS '';
SELECT '  maria@email.com          (visitante)' AS '';
SELECT '  pedro@email.com          (visitante)' AS '';
SELECT '  contato@techfix.com      (empresa)'   AS '';
SELECT '  contato@cleanpro.com     (empresa)'   AS '';
SELECT '  contato@belezaestilo.com (empresa)'   AS '';
SELECT '  contato@automaster.com   (empresa)'   AS '';
SELECT '  contato@edutech.com      (empresa)'   AS '';
SELECT '' AS '';
SELECT 'Acesse: http://localhost:8080' AS '';
SELECT '========================================' AS '';
