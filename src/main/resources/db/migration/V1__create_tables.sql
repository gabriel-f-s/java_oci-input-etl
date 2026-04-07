CREATE TABLE cnaes (
                       id        BIGSERIAL PRIMARY KEY,
                       codigo    VARCHAR(10) NOT NULL,
                       descricao VARCHAR(255),
                       CONSTRAINT uk_cnaes_codigo UNIQUE (codigo)
);

CREATE TABLE motivos (
                         id        BIGSERIAL PRIMARY KEY,
                         codigo    VARCHAR(10) NOT NULL,
                         descricao VARCHAR(255),
                         CONSTRAINT uk_motivos_codigo UNIQUE (codigo)
);

CREATE TABLE municipios (
                            id        BIGSERIAL PRIMARY KEY,
                            codigo    VARCHAR(10) NOT NULL,
                            descricao VARCHAR(255),
                            CONSTRAINT uk_municipios_codigo UNIQUE (codigo)
);

CREATE TABLE naturezas (
                           id        BIGSERIAL PRIMARY KEY,
                           codigo    VARCHAR(10) NOT NULL,
                           descricao VARCHAR(255),
                           CONSTRAINT uk_naturezas_codigo UNIQUE (codigo)
);

CREATE TABLE paises (
                        id        BIGSERIAL PRIMARY KEY,
                        codigo    VARCHAR(10) NOT NULL,
                        descricao VARCHAR(255),
                        CONSTRAINT uk_paises_codigo UNIQUE (codigo)
);

CREATE TABLE qualificacoes (
                               id        BIGSERIAL PRIMARY KEY,
                               codigo    VARCHAR(10) NOT NULL,
                               descricao VARCHAR(255),
                               CONSTRAINT uk_qualificacoes_codigo UNIQUE (codigo)
);

CREATE TABLE empresas (
                          id                          BIGSERIAL PRIMARY KEY,
                          cnpj_basico                 VARCHAR(8) NOT NULL,
                          razao_social                VARCHAR(255),
                          natureza_juridica_id        BIGINT,
                          qualificacao_responsavel_id BIGINT,
                          capital_social              DECIMAL(15,2),
                          porte_empresa               VARCHAR(100),
                          ente_federativo_responsavel VARCHAR(100),
                          CONSTRAINT uk_empresas_cnpj_basico UNIQUE (cnpj_basico)
);

CREATE TABLE estabelecimentos (
                                  id                           BIGSERIAL PRIMARY KEY,
                                  cnpj_basico                  VARCHAR(8) NOT NULL,
                                  cnpj_ordem                   VARCHAR(4),
                                  cnpj_dv                      VARCHAR(2),
                                  identificador_matriz_filial  VARCHAR(20),
                                  nome_fantasia                VARCHAR(255),
                                  situacao_cadastral           VARCHAR(50),
                                  data_situacao_cadastral      DATE,
                                  motivo_situacao_cadastral_id BIGINT,
                                  nome_cidade_exterior         VARCHAR(100),
                                  pais_id                      BIGINT,
                                  data_inicio_atividade        DATE,
                                  cnae_fiscal_principal_id     BIGINT,
                                  tipo_logradouro              VARCHAR(50),
                                  logradouro                   VARCHAR(255),
                                  numero                       VARCHAR(20),
                                  complemento                  VARCHAR(255),
                                  bairro                       VARCHAR(60),
                                  cep                          VARCHAR(8),
                                  uf                           VARCHAR(2),
                                  municipio_id                 BIGINT,
                                  ddd1                         VARCHAR(4),
                                  telefone1                    VARCHAR(15),
                                  ddd2                         VARCHAR(4),
                                  telefone2                    VARCHAR(15),
                                  ddd_fax                      VARCHAR(4),
                                  fax                          VARCHAR(15),
                                  correio_eletronico           VARCHAR(255),
                                  situacao_especial            VARCHAR(255),
                                  data_situacao_especial       DATE,
                                  CONSTRAINT uk_estabelecimentos_cnpj_completo UNIQUE (cnpj_basico, cnpj_ordem, cnpj_dv)
);

CREATE TABLE simples (
                         id                    BIGSERIAL PRIMARY KEY,
                         cnpj_basico           VARCHAR(8) NOT NULL,
                         opcao_pelo_simples    VARCHAR(10),
                         data_opcao_simples    DATE,
                         data_exclusao_simples DATE,
                         opcao_pelo_mei        VARCHAR(10),
                         data_opcao_mei        DATE,
                         data_exclusao_mei     DATE,
                         CONSTRAINT uk_simples_cnpj_basico UNIQUE (cnpj_basico)
);

CREATE TABLE socios (
                        id                                  BIGSERIAL PRIMARY KEY,
                        cnpj_basico                         VARCHAR(8) NOT NULL,
                        identificador_de_socio              SMALLINT,
                        nome_socio                          VARCHAR(255),
                        cnpj_cpf_do_socio                   VARCHAR(14),
                        qualificacao_socio_id               BIGINT,
                        data_entrada_sociedade              DATE,
                        pais_id                             BIGINT,
                        representante_legal                 VARCHAR(14),
                        nome_do_representante               VARCHAR(255),
                        qualificacao_representante_legal_id BIGINT,
                        faixa_etaria                        VARCHAR(5)
);

CREATE TABLE estabelecimento_cnae (
                                      cnae_id            BIGINT NOT NULL,
                                      estabelecimento_id BIGINT NOT NULL,
                                      CONSTRAINT pk_estabelecimento_cnae PRIMARY KEY (cnae_id, estabelecimento_id)
);

ALTER TABLE empresas ADD CONSTRAINT FK_EMPRESAS_ON_NATUREZA_JURIDICA FOREIGN KEY (natureza_juridica_id) REFERENCES naturezas (id);
ALTER TABLE empresas ADD CONSTRAINT FK_EMPRESAS_ON_QUALIFICACAO_RESPONSAVEL FOREIGN KEY (qualificacao_responsavel_id) REFERENCES qualificacoes (id);

ALTER TABLE estabelecimentos ADD CONSTRAINT FK_ESTABELECIMENTOS_ON_CNAE_FISCAL_PRINCIPAL FOREIGN KEY (cnae_fiscal_principal_id) REFERENCES cnaes (id);
ALTER TABLE estabelecimentos ADD CONSTRAINT FK_ESTABELECIMENTOS_ON_MOTIVO_SITUACAO_CADASTRAL FOREIGN KEY (motivo_situacao_cadastral_id) REFERENCES motivos (id);
ALTER TABLE estabelecimentos ADD CONSTRAINT FK_ESTABELECIMENTOS_ON_MUNICIPIOS FOREIGN KEY (municipio_id) REFERENCES municipios (id);
ALTER TABLE estabelecimentos ADD CONSTRAINT FK_ESTABELECIMENTOS_ON_PAIS FOREIGN KEY (pais_id) REFERENCES paises (id);

ALTER TABLE socios ADD CONSTRAINT FK_SOCIOS_ON_PAIS FOREIGN KEY (pais_id) REFERENCES paises (id);
ALTER TABLE socios ADD CONSTRAINT FK_SOCIOS_ON_QUALIFICACAO_REPRESENTANTE_LEGAL FOREIGN KEY (qualificacao_representante_legal_id) REFERENCES qualificacoes (id);
ALTER TABLE socios ADD CONSTRAINT FK_SOCIOS_ON_QUALIFICACAO_SOCIO FOREIGN KEY (qualificacao_socio_id) REFERENCES qualificacoes (id);

ALTER TABLE estabelecimento_cnae ADD CONSTRAINT fk_estcna_on_cnae FOREIGN KEY (cnae_id) REFERENCES cnaes (id);
ALTER TABLE estabelecimento_cnae ADD CONSTRAINT fk_estcna_on_estabelecimentos FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimentos (id);