-- Tabela Cliente
CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

-- Tabela Service_Order
CREATE TABLE service_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    destinacao_ordem_de_servico VARCHAR(255),
    detalhamento TEXT,
    data_criacao TIMESTAMP,
    ultima_atualizacao TIMESTAMP,
    local_do_evento VARCHAR(255),
    horario_do_evento VARCHAR(255),
    status VARCHAR(50),
    prioridade VARCHAR(50),
    cliente_id BIGINT,
    ativo BOOLEAN,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);
