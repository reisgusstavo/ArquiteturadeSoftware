CREATE DATABASE IF NOT EXISTS dbacademia CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE dbacademia;

CREATE TABLE IF NOT EXISTS planos (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    nome          VARCHAR(50)    NOT NULL UNIQUE,
    valor_mensal  DECIMAL(10,2)  NOT NULL,
    descricao     VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS alunos (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    nome             VARCHAR(100) NOT NULL,
    cpf              VARCHAR(11)  NOT NULL UNIQUE,
    email            VARCHAR(100),
    telefone         VARCHAR(20),
    data_nascimento  DATE,
    ativo            BOOLEAN      NOT NULL DEFAULT TRUE,
    data_cadastro    DATETIME     NOT NULL
);

CREATE TABLE IF NOT EXISTS matriculas (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    aluno_id     INT           NOT NULL,
    plano_id     INT           NOT NULL,
    extras       VARCHAR(200),
    descricao    VARCHAR(300)  NOT NULL,
    valor_total  DECIMAL(10,2) NOT NULL,
    data_inicio  DATE          NOT NULL,
    FOREIGN KEY (aluno_id) REFERENCES alunos(id) ON DELETE CASCADE,
    FOREIGN KEY (plano_id) REFERENCES planos(id)
);

CREATE TABLE IF NOT EXISTS pagamentos (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    matricula_id      INT           NOT NULL,
    metodo            VARCHAR(20)   NOT NULL,
    valor             DECIMAL(10,2) NOT NULL,
    codigo_transacao  VARCHAR(80),
    aprovado          BOOLEAN       NOT NULL,
    data_hora         DATETIME      NOT NULL,
    FOREIGN KEY (matricula_id) REFERENCES matriculas(id) ON DELETE CASCADE
);

INSERT IGNORE INTO planos (nome, valor_mensal, descricao) VALUES
 ('Mensal',     99.90, 'Acesso livre à musculação por 1 mês'),
 ('Trimestral', 89.90, 'Acesso livre à musculação, valor por mês, 3 meses'),
 ('Anual',      79.90, 'Acesso livre à musculação, valor por mês, 12 meses');
