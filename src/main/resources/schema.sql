
/* ------------------------------------------- ------------------------------------- */

CREATE TABLE tipo_relatorio(
                               id SERIAL NOT NULL,
                               descricao VARCHAR(100) NOT NULL UNIQUE,
                               PRIMARY KEY(id)

);

/* ------------------------------------------- ------------------------------------- */

CREATE TABLE area_cientifica (
                                 id SERIAL,
                                 descricao VARCHAR(150) NOT NULL UNIQUE,
                                 PRIMARY KEY(id)
);


/* ------------------------------------------- ------------------------------------- */

CREATE TABLE tipo_pessoa (
                             id SERIAL NOT NULL,
                             descricao VARCHAR(70) NOT NULL UNIQUE,
                             PRIMARY KEY(id)
);


/* ------------------------------------------- ---------------------------------------- */

CREATE table  tipo_estado (
                              id SERIAL NOT NULL,
                              descricao VARCHAR NOT NULL,
                              PRIMARY KEY(id)
);


/* ------------------------------------------- ---------------------------------------- */

CREATE table  estado (
                         id SERIAL NOT NULL,
                         descricao VARCHAR NOT NULL,
                         tipo_estado INTEGER NOT NULL,
                         FOREIGN KEY (tipo_estado) REFERENCES tipo_estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         PRIMARY KEY(id)
);

/* ------------------------------------------- ---------------------------------------- */

CREATE TABLE cargo (
                       id SERIAL NOT NULL,
                       descricao VARCHAR(50) NOT NULL UNIQUE,
                       PRIMARY KEY(id)
);


/*------------------------------------------- ----------------------------------------*/

CREATE TABLE sala (
                      id SERIAL NOT NULL,
                      nome_sala VARCHAR(100) NOT NULL UNIQUE,
                      capacidade INTEGER NOT NULL,
                      PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE tipo_documento (
                                id SERIAL NOT NULL,
                                descricao VARCHAR(70) NOT NULL UNIQUE,
                                PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE tipo_avaliacao (
                                id SERIAL NOT NULL,
                                descricao VARCHAR NOT NULL UNIQUE,
                                PRIMARY KEY(id)
);



/*------------------------------------------- ----------------------------------------*/

CREATE TABLE tipo_transacao (
                                id SERIAL NOT NULL,
                                descricao varchar(100) NOT NULL UNIQUE,
                                PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE departamento (
                              id SERIAL NOT NULL,
                              descricao VARCHAR(100) NOT NULL UNIQUE,
                              sigla VARCHAR(10) NOT NULL UNIQUE,
                              PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE categoria_financeira (
                                      id SERIAL NOT NULL,
                                      descricao VARCHAR(100) NOT NULL UNIQUE,
                                      PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE sector_trabalho (
                                 id SERIAL NOT NULL,
                                 descricao VARCHAR(150) NOT NULL UNIQUE,
                                 PRIMARY KEY(id)
);


/*------------------------------------------- ----------------------------------------*/

CREATE TABLE tipo_material (
                               id SERIAL NOT NULL,
                               descricao VARCHAR(100) NOT NULL UNIQUE,
                               PRIMARY KEY(id)
);



/*------------------------------------------- ----------------------------------------*/

CREATE TABLE tipo_pagamento (
                                id SERIAL NOT NULL,
                                descricao VARCHAR(80) UNIQUE,
                                PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE provincia (
                           id SERIAL NOT NULL,
                           nome_provincia VARCHAR(30) UNIQUE,
                           PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE distrito (
                          id SERIAL NOT NULL,
                          nome_distrito VARCHAR(50) NOT NULL,
                          provincia INTEGER NOT NULL,
                          FOREIGN KEY (provincia) REFERENCES provincia(id) ON DELETE CASCADE ON UPDATE CASCADE,
                          PRIMARY KEY(id)
);

/* ------------------------------------------- ------------------------------------- */

-- utilizador

/* ------------------------------------------- ------------------------------------- */

CREATE TABLE aluno (
                       id bigint NOT NULL,
                       nome_completo VARCHAR(100) NOT NULL,
                       data_nascimento DATE NOT NULL,
                       distrito_nascimento INTEGER,
                       sexo varchar (1) NOT NULL,
                       bilhete_identificacao VARCHAR(13),
                       religiao VARCHAR(78),
                       grupo_sanguineo VARCHAR(3),
                       endereco VARCHAR (255),
                       data_registo TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       estado INTEGER NOT NULL,
                       escola_anterior text,
                       nome_do_pai VARCHAR(150),
                       nome_da_mae VARCHAR(150),
                       numero_telefone_principal BIGINT,
                       foreign key (id) references utilizador (id),
                       FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                       FOREIGN KEY (distrito_nascimento) REFERENCES distrito(id) ON DELETE CASCADE ON UPDATE CASCADE,
                       PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE encarregado_educacao (
                                      id SERIAL NOT NULL,
                                      nome_completo VARCHAR(150) NOT NULL,
                                      data_nascimento DATE NOT NULL,
                                      distrito_nascimento INTEGER,
                                      sexo varchar (1) NOT NULL,
                                      local_trabalho VARCHAR(255),
                                      sector_trabalho INTEGER,
                                      endereco VARCHAR (255),
                                      email VARCHAR(75),
                                      grupo_sanguineo VARCHAR(3),
                                      numero_telefone_principal BIGINT NOT NULL,
                                      numero_telefone_alternativo BIGINT,
                                      foreign key (id) references utilizador (id),
                                      FOREIGN KEY (distrito_nascimento) REFERENCES distrito(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                      PRIMARY KEY(id)
);


/*------------------------------------------- ----------------------------------------*/

CREATE TABLE encarregado_aluno (
                                   encarregado INTEGER NOT NULL,
                                   aluno INTEGER NOT NULL,
                                   grau_parentesco VARCHAR(100) NOT NULL,
                                   PRIMARY KEY (encarregado, aluno),
                                   FOREIGN KEY (encarregado) REFERENCES encarregado_educacao(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                   FOREIGN KEY (aluno) REFERENCES aluno(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE professor (
                           id SERIAL NOT NULL,
                           nome_completo VARCHAR(100) NOT NULL,
                           data_nascimento DATE NOT NULL,
                           distrito_nascimento INTEGER,
                           sexo varchar (1) NOT NULL,
                           endereco VARCHAR (255) NOT NULL,
                           email VARCHAR(75) NOT NULL,
                           numero_telefone_principal BIGINT NOT NULL,
                           numero_telefone_alternativo BIGINT,
                           data_contracto TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           estado INTEGER NOT NULL,
                           estado_civil VARCHAR(10),
                           bilhete_identificacao VARCHAR(13),
                           religiao VARCHAR(78),
                           grupo_sanguineo VARCHAR(3),
                           area_formacao INTEGER NOT NULL,
                           foreign key (id) references utilizador (id),
                           FOREIGN KEY (area_formacao) REFERENCES area_cientifica(id)  ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (distrito_nascimento) REFERENCES distrito(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE funcionario (
                             id SERIAL NOT NULL,
                             nome_completo VARCHAR(100) NOT NULL,
                             data_nascimento DATE NOT NULL,
                             distrito_nascimento INTEGER,
                             sexo varchar (1) NOT NULL,
                             endereco VARCHAR (255) NOT NULL,
                             email VARCHAR(75) NOT NULL,
                             numero_telefone_principal BIGINT NOT NULL,
                             numero_telefone_alternativo BIGINT,
                             data_contracto TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             cargo INTEGER NOT NULL,
                             departamento INTEGER NOT NULL,
                             estado INTEGER NOT NULL,
                             estado_civil VARCHAR(10),
                             bilhete_identificacao VARCHAR(13),
                             religiao VARCHAR(78),
                             grupo_sanguineo VARCHAR(3),
                             area_formacao INTEGER NOT NULL,
                             foreign key (id) references utilizador (id),
                             FOREIGN KEY (area_formacao) REFERENCES area_cientifica(id)  ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (distrito_nascimento) REFERENCES distrito(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (departamento) REFERENCES departamento(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (cargo) REFERENCES cargo(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE turma (
                       id SERIAL NOT NULL,
                       nome_turma VARCHAR(100) NOT NULL,
                       ano_lectivo integer NOT NULL,
                       professor_responsavel INTEGER NOT NULL,
                       estado INTEGER NOT NULL,
                       FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                       FOREIGN KEY (professor_responsavel) REFERENCES professor(id) ON DELETE CASCADE ON UPDATE CASCADE,
                       PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE disciplina (
                            id SERIAL NOT NULL,
                            nome_disciplina VARCHAR(100) NOT NULL UNIQUE,
                            PRIMARY KEY(id)
);


/*------------------------------------------- ----------------------------------------*/

CREATE TABLE professor_disciplina (
                                      professor INTEGER NOT NULL,
                                      ano_lectivo INTEGER,
                                      disciplina INTEGER NOT NULL,
                                      classe integer NOT NULL,
                                      FOREIGN KEY (professor) REFERENCES professor(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                      FOREIGN KEY (disciplina) REFERENCES disciplina(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                      PRIMARY KEY (professor, disciplina, classe)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE disciplina_aluno (
                                  disciplina INTEGER NOT NULL,
                                  ano_lectivo INTEGER,
                                  aluno INTEGER NOT NULL,
                                  data_aula date NOT NULL,
                                  hora_aula time NOT NULL,
                                  FOREIGN KEY (disciplina) REFERENCES disciplina(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  FOREIGN KEY (aluno) REFERENCES aluno(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  PRIMARY KEY (disciplina, aluno)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE material (
                          id SERIAL NOT NULL,
                          tipo_material INTEGER NOT NULL,
                          nome_material VARCHAR(100) NOT NULL UNIQUE,
                          quantidade INTEGER,
                          estado INTEGER NOT NULL,
                          FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                          FOREIGN KEY (tipo_material) REFERENCES tipo_material(id) ON DELETE CASCADE ON UPDATE CASCADE,
                          PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE pagamento (
                           id SERIAL NOT NULL,
                           referencia VARCHAR(100) NOT NULL,
                           aluno INTEGER not null,
                           valor DECIMAL(10, 2) NOT NULL,
                           tipo_pagamento INTEGER NOT NULL,
                           data_pagamento TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           responsavel INTEGER NOT NULL,
                           observacao TEXT,
                           estado INTEGER NOT NULL,
                           FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (aluno) REFERENCES aluno(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (tipo_pagamento) REFERENCES tipo_pagamento(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (responsavel) REFERENCES funcionario (id) ON DELETE CASCADE ON UPDATE CASCADE,
                           PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE matricula (
                           id SERIAL NOT NULL,
                           ano_lectivo INTEGER NOT NULL,
                           aluno INTEGER NOT NULL,
                           estado INTEGER NOT NULL,
                           FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (aluno) REFERENCES aluno(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE comunicados (
                             id SERIAL NOT NULL,
                             titulo VARCHAR(200) NOT NULL,
                             conteudo TEXT NOT NULL,
                             data_publicacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             responsavel INTEGER NOT NULL,
                             destinatario integer NOT NULL,
                             estado INTEGER NOT NULL,
                             FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (responsavel) REFERENCES funcionario(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (destinatario) REFERENCES tipo_pessoa(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE turma_aluno (
                             aluno INTEGER REFERENCES aluno(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             ano_lectivo INTEGER NOT NULL,
                             turma INTEGER REFERENCES turma(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             PRIMARY KEY (aluno, turma)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE horario (
                         id SERIAL NOT NULL,
                         sala INTEGER NOT NULL,
                         turma INTEGER NOT NULL,
                         disciplina INTEGER NOT NULL,
                         professor INTEGER NOT NULL,
                         dia_semana VARCHAR(10) NOT NULL,
                         hora_inicio TIME NOT NULL,
                         hora_termino TIME NOT NULL,
                         FOREIGN KEY (sala) REFERENCES sala(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (turma) REFERENCES turma(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (disciplina) REFERENCES disciplina(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (professor) REFERENCES professor(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE avaliacao (
                           id SERIAL NOT NULL,
                           aluno INTEGER NOT NULL,
                           tipo_avaliacao INTEGER NOT NULL,
                           trimestre INTEGER NOT NULL,
                           disciplina INTEGER NOT NULL ,
                           observacao text NOT NULL,
                           estado INTEGER NOT NULL,
                           FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (aluno) REFERENCES aluno(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (disciplina) REFERENCES disciplina(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (tipo_avaliacao) REFERENCES tipo_avaliacao(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           PRIMARY KEY(id)
);

/*---------------------------------------- Avaliacao do Aluno -------------------------------------*/

CREATE TABLE avaliacao_aluno (
                                 id SERIAL NOT NULL,
                                 aluno INTEGER NOT NULL,
                                 trimestre integer NOT NULL,
                                 ano_lectivo integer NOT NULL,
                                 data_lancamento TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 nota FLOAT NOT NULL DEFAULT 0,
                                 observacao text,
                                 estado INTEGER NOT NULL,
                                 FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 FOREIGN KEY (aluno)  REFERENCES aluno(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 PRIMARY KEY(id)
);

/*------------------------------------------- Presencas do Aluno ----------------------------------------*/

CREATE TABLE presencas_aluno (
                                 id SERIAL NOT NULL,
                                 aluno INTEGER NOT NULL,
                                 turma INTEGER NOT NULL,
                                 disciplina INTEGER NOT NULL,
                                 data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 estado INTEGER NOT NULL,
                                 FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 FOREIGN KEY (aluno)  REFERENCES aluno(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 FOREIGN KEY (turma) REFERENCES turma(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 FOREIGN KEY (disciplina) REFERENCES disciplina(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 PRIMARY KEY(id)
);

/*------------------------------------------- Presencas do Aluno ----------------------------------------*/

CREATE TABLE pauta_trimestral (
                                  id SERIAL NOT NULL,
                                  disciplina INTEGER NOT NULL,
                                  aluno INTEGER NOT NULL,
                                  trimestre integer NOT NULL,
                                  ano_lectivo integer,
                                  nota_final INTEGER NOT NULL,
                                  professor INTEGER NOT NULL,
                                  data_publicacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  observacao text,
                                  estado INTEGER NOT NULL,
                                  FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  FOREIGN KEY (aluno)  REFERENCES aluno(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  FOREIGN KEY (disciplina) REFERENCES disciplina(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  FOREIGN KEY (professor) REFERENCES professor(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE pauta_final (
                             id SERIAL NOT NULL,
                             disciplina INTEGER NOT NULL,
                             aluno INTEGER NOT NULL,
                             ano_lectivo integer,
                             nota_final INTEGER NOT NULL,
                             professor INTEGER NOT NULL,
                             data_publicacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             Resultado VARCHAR (30) NOT NULL,
                             estado_pauta integer NOT NULL,
                             FOREIGN KEY (aluno)  REFERENCES aluno(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (disciplina) REFERENCES disciplina(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (estado_pauta) REFERENCES estado (id) ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (professor) REFERENCES professor(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             PRIMARY KEY(id)
);

/*------------------------------------------- Aula ----------------------------------------*/

CREATE TABLE aula (
                      id SERIAL NOT NULL,
                      disciplina INTEGER NOT NULL,
                      titulo VARCHAR(255),
                      ano_lectivo INTEGER NOT NULL,
                      classe INTEGER NOT NULL,
                      resumo text NOT NULL,
                      data_aula  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      conteudo text NOT NULL,
                      estado INTEGER NOT NULL,
                      FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                      FOREIGN KEY (disciplina) REFERENCES disciplina(id) ON DELETE CASCADE ON UPDATE CASCADE,
                      PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE veiculo (
                         id SERIAL NOT NULL,
                         codigo_veiculo VARCHAR(19),
                         marca_veiculo VARCHAR(30),
                         motorista INTEGER NOT NULL,
                         estado INTEGER NOT NULL,
                         FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (motorista) REFERENCES funcionario(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE documento (
                           id SERIAL NOT NULL,
                           titulo VARCHAR(200) NOT NULL,
                           tipo_documento INTEGER NOT NULL,
                           conteudo TEXT NOT NULL,
                           data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           autor INTEGER NOT NULL,
                           estado INTEGER NOT NULL,
                           FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (autor) REFERENCES funcionario(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (tipo_documento) REFERENCES tipo_documento(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE receita (
                         id SERIAL NOT NULL,
                         descricao VARCHAR(200) NOT NULL,
                         valor DECIMAL(10, 2) NOT NULL,
                         data_receita TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         categoria INTEGER NOT NULL,
                         responsavel INTEGER NOT NULL,
                         estado INTEGER NOT NULL,
                         FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (categoria) REFERENCES categoria_financeira(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (responsavel) REFERENCES funcionario(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE despesa (
                         id SERIAL NOT NULL,
                         descricao VARCHAR(200) NOT NULL,
                         valor DECIMAL(10, 2) NOT NULL,
                         data_despesa TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         categoria INTEGER NOT NULL,
                         responsavel INTEGER NOT NULL,
                         estado INTEGER NOT NULL,
                         FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (categoria) REFERENCES categoria_financeira(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (responsavel) REFERENCES funcionario(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE orcamento (
                           id SERIAL NOT NULL,
                           ano INTEGER NOT NULL,
                           valor_total DECIMAL(15, 2) NOT NULL,
                           data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           responsavel INTEGER NOT NULL,
                           estado INTEGER NOT NULL,
                           FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (responsavel) REFERENCES funcionario(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE transacao (
                           id SERIAL NOT NULL,
                           tipo_transacao INTEGER NOT NULL, -- 'Receita' ou 'Despesa'
                           valor DECIMAL(10, 2) NOT NULL,
                           data_transacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           categoria INTEGER NOT NULL,
                           responsavel INTEGER NOT NULL,
                           descricao TEXT NOT NULL,
                           estado INTEGER NOT NULL,
                           FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (categoria) REFERENCES categoria_financeira(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (responsavel) REFERENCES funcionario(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (tipo_transacao) REFERENCES tipo_transacao(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           PRIMARY KEY(id)
);


/*------------------------------------------- ----------------------------------------*/

CREATE TABLE folha_pagamento (
                                 id SERIAL NOT NULL,
                                 funcionario INTEGER NOT NULL,
                                 salario_bruto DECIMAL(10, 2) NOT NULL,
                                 descontos DECIMAL(10, 2),
                                 salario_liquido DECIMAL(10, 2) NOT NULL,
                                 mes_referencia DATE NOT NULL,
                                 data_pagamento TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 estado INTEGER NOT NULL,
                                 FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 FOREIGN KEY (funcionario) REFERENCES funcionario(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE ativo (
                       id SERIAL NOT NULL,
                       descricao VARCHAR(200) NOT NULL,
                       tipo VARCHAR(100) NOT NULL,
                       data_aquisicao DATE NOT NULL,
                       valor_aquisicao DECIMAL(10, 2) NOT NULL,
                       localizacao VARCHAR(255),
                       estado INTEGER NOT NULL,
                       FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                       PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE manutencao_ativo (
                                  id SERIAL NOT NULL,
                                  ativo integer NOT NULL,
                                  descricao VARCHAR(255) NOT NULL,
                                  data_manutencao DATE NOT NULL,
                                  custo DECIMAL(10, 2),
                                  responsavel INTEGER NOT NULL,
                                  estado INTEGER NOT NULL,
                                  FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  FOREIGN KEY (ativo) REFERENCES ativo(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  FOREIGN KEY (responsavel) REFERENCES funcionario(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE contrato (
                          id SERIAL NOT NULL,
                          descricao VARCHAR(255) NOT NULL,
                          tipo VARCHAR(100) NOT NULL,
                          data_inicio DATE NOT NULL,
                          data_fim DATE,
                          valor_total DECIMAL(15, 2) NOT NULL,
                          fornecedor VARCHAR(255) NOT NULL,
                          responsavel INTEGER NOT NULL,
                          estado INTEGER NOT NULL,
                          FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                          FOREIGN KEY (responsavel) REFERENCES funcionario(id) ON DELETE CASCADE ON UPDATE CASCADE,
                          PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE evento (
                        id SERIAL NOT NULL,
                        nome VARCHAR(200) NOT NULL,
                        descricao TEXT,
                        data_inicio TIMESTAMP NOT NULL,
                        data_fim TIMESTAMP,
                        localizacao VARCHAR(255),
                        responsavel INTEGER NOT NULL,
                        estado INTEGER NOT NULL,
                        FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                        FOREIGN KEY (responsavel) REFERENCES funcionario(id) ON DELETE CASCADE ON UPDATE CASCADE,
                        PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE participante_evento (
                                     id SERIAL NOT NULL,
                                     evento INTEGER NOT NULL,
                                     nome_participante VARCHAR(150) NOT NULL,
                                     tipo_participante INTEGER NOT NULL, -- 'Aluno', 'Professor', 'Funcionario', 'Externo'
                                     FOREIGN KEY (evento) REFERENCES evento(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                     FOREIGN KEY  (tipo_participante) REFERENCES tipo_pessoa(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                     PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE relatorio_financeiro (
                                      id SERIAL NOT NULL,
                                      tipo_relatorio INTEGER NOT NULL, -- 'Mensal', 'Anual', etc.
                                      ano INTEGER NOT NULL,
                                      mes INTEGER,
                                      data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      responsavel INTEGER NOT NULL,
                                      estado INTEGER NOT NULL,
                                      FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                      FOREIGN KEY (tipo_relatorio) REFERENCES tipo_relatorio(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                      FOREIGN KEY (responsavel) REFERENCES funcionario(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                      PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE detalhe_relatorio_financeiro (
                                              id SERIAL NOT NULL,
                                              relatorio INTEGER NOT NULL,
                                              descricao TEXT NOT NULL,
                                              valor DECIMAL(15, 2) NOT NULL,
                                              FOREIGN KEY (relatorio) REFERENCES relatorio_financeiro(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                              PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE fornecedor (
                            id SERIAL NOT NULL,
                            nome VARCHAR(200) NOT NULL,
                            endereco VARCHAR(255),
                            telefone VARCHAR(15),
                            email VARCHAR(100),
                            descricao TEXT,
                            estado INTEGER NOT NULL,
                            FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                            PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE ordem_compra (
                              id SERIAL NOT NULL,
                              fornecedor INTEGER NOT NULL,
                              data_ordem DATE NOT NULL,
                              data_entrega DATE,
                              valor_total DECIMAL(15, 2) NOT NULL,
                              responsavel INTEGER NOT NULL,
                              estado INTEGER NOT NULL,
                              FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                              FOREIGN KEY (fornecedor) REFERENCES fornecedor(id) ON DELETE CASCADE ON UPDATE CASCADE,
                              FOREIGN KEY (responsavel) REFERENCES funcionario(id) ON DELETE CASCADE ON UPDATE CASCADE,
                              PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/

CREATE TABLE item_ordem_compra (
                                   id SERIAL NOT NULL,
                                   ordem_compra INTEGER NOT NULL,
                                   material INTEGER NOT NULL,
                                   descricao VARCHAR(200) NOT NULL,
                                   quantidade INTEGER NOT NULL,
                                   valor_unitario DECIMAL(10, 2) NOT NULL,
                                   FOREIGN KEY (ordem_compra) REFERENCES ordem_compra(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                   FOREIGN KEY (material) REFERENCES material(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                   PRIMARY KEY(id)
);

/*----------------------------------------- Requisicao de Material ----------------------------------*/

CREATE TABLE requisicao_material (
                                     id SERIAL NOT NULL,
                                     data_requisicao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     material INTEGER NOT NULL,
                                     quantidade INTEGER NOT NULL,
                                     requisitor INTEGER NOT NULL,
                                     observacao TEXT,
                                     estado INTEGER NOT NULL,
                                     FOREIGN KEY (estado) REFERENCES estado(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                     FOREIGN KEY (requisitor) REFERENCES funcionario (id) ON DELETE CASCADE ON UPDATE CASCADE,
                                     FOREIGN KEY (material) REFERENCES material (id) ON DELETE CASCADE ON UPDATE CASCADE,
                                     PRIMARY KEY(id)
);

/*------------------------------------------- ----------------------------------------*/
