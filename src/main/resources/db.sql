--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

-- Started on 2024-10-05 07:34:37

SET statement_timeout = 0
;

SET lock_timeout = 0
;

SET idle_in_transaction_session_timeout = 0
;

SET client_encoding = 'UTF8'
;

SET standard_conforming_strings = on
;

SELECT pg_catalog.set_config('search_path', '', false)
;

SET check_function_bodies = false
;

SET xmloption = content
;

SET client_min_messages = warning
;

SET row_security = off
;


DROP DATABASE IF EXISTS xicola
;

--
-- TOC entry 5586 (class 1262 OID 24738)
-- Name: xicola
;
 Type: DATABASE
;
 Schema: -
;
 Owner: postgres
--

CREATE DATABASE xicola WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252'
;



ALTER DATABASE xicola OWNER TO postgres
;


\connect xicola

SET statement_timeout = 0
;

SET lock_timeout = 0
;

SET idle_in_transaction_session_timeout = 0
;

SET client_encoding = 'UTF8'
;

SET standard_conforming_strings = on
;

SELECT pg_catalog.set_config('search_path', '', false)
;

SET check_function_bodies = false
;

SET xmloption = content
;

SET client_min_messages = warning
;

SET row_security = off
;


--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public
;
 Type: SCHEMA
;
 Schema: -
;
 Owner: pg_database_owner
--

CREATE SCHEMA public
;



ALTER SCHEMA public OWNER TO pg_database_owner
;


--
-- TOC entry 5587 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public
;
 Type: COMMENT
;
 Schema: -
;
 Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema'
;



SET default_tablespace = ''
;


SET default_table_access_method = heap
;


--
-- TOC entry 254 (class 1259 OID 24938)
-- Name: aluno
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.aluno
(
    id                        bigint                                                NOT NULL,
    nome_completo             character varying(100)                                NOT NULL,
    data_nascimento           date                                                  NOT NULL,
    distrito_nascimento       bigint,
    sexo                      character varying(1)                                  NOT NULL,
    bilhete_identificacao     character varying(13),
    religiao                  character varying(78),
    grupo_sanguineo           character varying(3),
    endereco                  character varying(255),
    data_registo              timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    estado                    bigint                                                NOT NULL,
    escola_anterior           text,
    nome_do_pai               character varying(150),
    nome_da_mae               character varying(150),
    numero_telefone_principal bigint,
    username                  bigint                                                NOT NULL
)
;



ALTER TABLE public.aluno
    OWNER TO postgres
;


--
-- TOC entry 223 (class 1259 OID 24783)
-- Name: area_cientifica
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.area_cientifica
(
    id        bigint                 NOT NULL,
    descricao character varying(150) NOT NULL
)
;



ALTER TABLE public.area_cientifica
    OWNER TO postgres
;


--
-- TOC entry 222 (class 1259 OID 24782)
-- Name: area_cientifica_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.area_cientifica_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.area_cientifica_id_seq OWNER TO postgres
;


--
-- TOC entry 5588 (class 0 OID 0)
-- Dependencies: 222
-- Name: area_cientifica_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.area_cientifica_id_seq OWNED BY public.area_cientifica.id
;



--
-- TOC entry 306 (class 1259 OID 25565)
-- Name: ativo
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.ativo
(
    id              bigint                 NOT NULL,
    descricao       character varying(200) NOT NULL,
    tipo            character varying(100) NOT NULL,
    data_aquisicao  date                   NOT NULL,
    valor_aquisicao numeric(10, 2)         NOT NULL,
    localizacao     character varying(255),
    estado          bigint                 NOT NULL
)
;



ALTER TABLE public.ativo
    OWNER TO postgres
;


--
-- TOC entry 305 (class 1259 OID 25564)
-- Name: ativo_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.ativo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.ativo_id_seq OWNER TO postgres
;


--
-- TOC entry 5589 (class 0 OID 0)
-- Dependencies: 305
-- Name: ativo_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.ativo_id_seq OWNED BY public.ativo.id
;



--
-- TOC entry 290 (class 1259 OID 25391)
-- Name: aula
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.aula
(
    id          bigint                                                NOT NULL,
    disciplina  bigint                                                NOT NULL,
    titulo      character varying(255),
    ano_lectivo integer                                               NOT NULL,
    classe      integer                                               NOT NULL,
    resumo      text                                                  NOT NULL,
    data_aula   timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    conteudo    text                                                  NOT NULL,
    estado      bigint                                                NOT NULL
)
;



ALTER TABLE public.aula
    OWNER TO postgres
;


--
-- TOC entry 289 (class 1259 OID 25390)
-- Name: aula_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.aula_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.aula_id_seq OWNER TO postgres
;


--
-- TOC entry 5590 (class 0 OID 0)
-- Dependencies: 289
-- Name: aula_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.aula_id_seq OWNED BY public.aula.id
;



--
-- TOC entry 280 (class 1259 OID 25255)
-- Name: avaliacao
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.avaliacao
(
    id             bigint  NOT NULL,
    aluno          bigint  NOT NULL,
    tipo_avaliacao bigint  NOT NULL,
    trimestre      integer NOT NULL,
    disciplina     bigint  NOT NULL,
    observacao     text    NOT NULL,
    estado         bigint  NOT NULL
)
;



ALTER TABLE public.avaliacao
    OWNER TO postgres
;


--
-- TOC entry 282 (class 1259 OID 25284)
-- Name: avaliacao_aluno
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.avaliacao_aluno
(
    id              bigint                                                NOT NULL,
    aluno           bigint                                                NOT NULL,
    trimestre       integer                                               NOT NULL,
    ano_lectivo     integer                                               NOT NULL,
    data_lancamento timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    nota            double precision            DEFAULT 0                 NOT NULL,
    observacao      text,
    estado          bigint                                                NOT NULL,
    avaliacao       bigint                                                NOT NULL
)
;



ALTER TABLE public.avaliacao_aluno
    OWNER TO postgres
;


--
-- TOC entry 281 (class 1259 OID 25283)
-- Name: avaliacao_aluno_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.avaliacao_aluno_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.avaliacao_aluno_id_seq OWNER TO postgres
;


--
-- TOC entry 5591 (class 0 OID 0)
-- Dependencies: 281
-- Name: avaliacao_aluno_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.avaliacao_aluno_id_seq OWNED BY public.avaliacao_aluno.id
;



--
-- TOC entry 279 (class 1259 OID 25254)
-- Name: avaliacao_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.avaliacao_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.avaliacao_id_seq OWNER TO postgres
;


--
-- TOC entry 5592 (class 0 OID 0)
-- Dependencies: 279
-- Name: avaliacao_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.avaliacao_id_seq OWNED BY public.avaliacao.id
;



--
-- TOC entry 231 (class 1259 OID 24824)
-- Name: cargo
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.cargo
(
    id        bigint                NOT NULL,
    descricao character varying(50) NOT NULL
)
;



ALTER TABLE public.cargo
    OWNER TO postgres
;


--
-- TOC entry 230 (class 1259 OID 24823)
-- Name: cargo_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.cargo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.cargo_id_seq OWNER TO postgres
;


--
-- TOC entry 5593 (class 0 OID 0)
-- Dependencies: 230
-- Name: cargo_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.cargo_id_seq OWNED BY public.cargo.id
;



--
-- TOC entry 243 (class 1259 OID 24882)
-- Name: categoria_financeira
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.categoria_financeira
(
    id        bigint                 NOT NULL,
    descricao character varying(100) NOT NULL
)
;



ALTER TABLE public.categoria_financeira
    OWNER TO postgres
;


--
-- TOC entry 242 (class 1259 OID 24881)
-- Name: categoria_financeira_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.categoria_financeira_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.categoria_financeira_id_seq OWNER TO postgres
;


--
-- TOC entry 5594 (class 0 OID 0)
-- Dependencies: 242
-- Name: categoria_financeira_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.categoria_financeira_id_seq OWNED BY public.categoria_financeira.id
;



--
-- TOC entry 275 (class 1259 OID 25188)
-- Name: comunicados
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.comunicados
(
    id              bigint                                                NOT NULL,
    titulo          character varying(200)                                NOT NULL,
    conteudo        text                                                  NOT NULL,
    data_publicacao timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    responsavel     bigint                                                NOT NULL,
    destinatario    bigint                                                NOT NULL,
    estado          bigint                                                NOT NULL
)
;



ALTER TABLE public.comunicados
    OWNER TO postgres
;


--
-- TOC entry 274 (class 1259 OID 25187)
-- Name: comunicados_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.comunicados_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.comunicados_id_seq OWNER TO postgres
;


--
-- TOC entry 5595 (class 0 OID 0)
-- Dependencies: 274
-- Name: comunicados_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.comunicados_id_seq OWNED BY public.comunicados.id
;



--
-- TOC entry 310 (class 1259 OID 25601)
-- Name: contrato
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.contrato
(
    id          bigint                 NOT NULL,
    descricao   character varying(255) NOT NULL,
    tipo        character varying(100) NOT NULL,
    data_inicio date                   NOT NULL,
    data_fim    date,
    valor_total numeric(15, 2)         NOT NULL,
    fornecedor  character varying(255) NOT NULL,
    responsavel bigint                 NOT NULL,
    estado      bigint                 NOT NULL
)
;



ALTER TABLE public.contrato
    OWNER TO postgres
;


--
-- TOC entry 309 (class 1259 OID 25600)
-- Name: contrato_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.contrato_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.contrato_id_seq OWNER TO postgres
;


--
-- TOC entry 5596 (class 0 OID 0)
-- Dependencies: 309
-- Name: contrato_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.contrato_id_seq OWNED BY public.contrato.id
;



--
-- TOC entry 241 (class 1259 OID 24871)
-- Name: departamento
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.departamento
(
    id        bigint                 NOT NULL,
    descricao character varying(100) NOT NULL,
    sigla     character varying(10)  NOT NULL
)
;



ALTER TABLE public.departamento
    OWNER TO postgres
;


--
-- TOC entry 240 (class 1259 OID 24870)
-- Name: departamento_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.departamento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.departamento_id_seq OWNER TO postgres
;


--
-- TOC entry 5597 (class 0 OID 0)
-- Dependencies: 240
-- Name: departamento_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.departamento_id_seq OWNED BY public.departamento.id
;



--
-- TOC entry 298 (class 1259 OID 25476)
-- Name: despesa
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.despesa
(
    id           bigint                                                NOT NULL,
    descricao    character varying(200)                                NOT NULL,
    valor        numeric(10, 2)                                        NOT NULL,
    data_despesa timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    categoria    bigint                                                NOT NULL,
    responsavel  bigint                                                NOT NULL,
    estado       bigint                                                NOT NULL
)
;



ALTER TABLE public.despesa
    OWNER TO postgres
;


--
-- TOC entry 297 (class 1259 OID 25475)
-- Name: despesa_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.despesa_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.despesa_id_seq OWNER TO postgres
;


--
-- TOC entry 5598 (class 0 OID 0)
-- Dependencies: 297
-- Name: despesa_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.despesa_id_seq OWNED BY public.despesa.id
;



--
-- TOC entry 318 (class 1259 OID 25679)
-- Name: detalhe_relatorio_financeiro
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.detalhe_relatorio_financeiro
(
    id        bigint         NOT NULL,
    relatorio bigint         NOT NULL,
    descricao text           NOT NULL,
    valor     numeric(15, 2) NOT NULL
)
;



ALTER TABLE public.detalhe_relatorio_financeiro
    OWNER TO postgres
;


--
-- TOC entry 317 (class 1259 OID 25678)
-- Name: detalhe_relatorio_financeiro_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.detalhe_relatorio_financeiro_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.detalhe_relatorio_financeiro_id_seq OWNER TO postgres
;


--
-- TOC entry 5599 (class 0 OID 0)
-- Dependencies: 317
-- Name: detalhe_relatorio_financeiro_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.detalhe_relatorio_financeiro_id_seq OWNED BY public.detalhe_relatorio_financeiro.id
;



--
-- TOC entry 265 (class 1259 OID 25083)
-- Name: disciplina
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.disciplina
(
    id              bigint                 NOT NULL,
    nome_disciplina character varying(100) NOT NULL
)
;



ALTER TABLE public.disciplina
    OWNER TO postgres
;


--
-- TOC entry 267 (class 1259 OID 25106)
-- Name: disciplina_aluno
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.disciplina_aluno
(
    disciplina  integer                   NOT NULL,
    ano_lectivo integer,
    aluno       integer                   NOT NULL,
    data_aula   date                      NOT NULL,
    hora_aula   time(6) without time zone NOT NULL
)
;



ALTER TABLE public.disciplina_aluno
    OWNER TO postgres
;


--
-- TOC entry 264 (class 1259 OID 25082)
-- Name: disciplina_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.disciplina_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.disciplina_id_seq OWNER TO postgres
;


--
-- TOC entry 5600 (class 0 OID 0)
-- Dependencies: 264
-- Name: disciplina_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.disciplina_id_seq OWNED BY public.disciplina.id
;



--
-- TOC entry 253 (class 1259 OID 24927)
-- Name: distrito
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.distrito
(
    id            bigint                NOT NULL,
    nome_distrito character varying(50) NOT NULL,
    provincia     bigint                NOT NULL
)
;



ALTER TABLE public.distrito
    OWNER TO postgres
;


--
-- TOC entry 252 (class 1259 OID 24926)
-- Name: distrito_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.distrito_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.distrito_id_seq OWNER TO postgres
;


--
-- TOC entry 5601 (class 0 OID 0)
-- Dependencies: 252
-- Name: distrito_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.distrito_id_seq OWNED BY public.distrito.id
;



--
-- TOC entry 294 (class 1259 OID 25428)
-- Name: documento
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.documento
(
    id             bigint                                                NOT NULL,
    titulo         character varying(200)                                NOT NULL,
    tipo_documento bigint                                                NOT NULL,
    conteudo       text                                                  NOT NULL,
    data_criacao   timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    autor          bigint                                                NOT NULL,
    estado         bigint                                                NOT NULL
)
;



ALTER TABLE public.documento
    OWNER TO postgres
;


--
-- TOC entry 293 (class 1259 OID 25427)
-- Name: documento_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.documento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.documento_id_seq OWNER TO postgres
;


--
-- TOC entry 5602 (class 0 OID 0)
-- Dependencies: 293
-- Name: documento_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.documento_id_seq OWNED BY public.documento.id
;



--
-- TOC entry 257 (class 1259 OID 24980)
-- Name: encarregado_aluno
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.encarregado_aluno
(
    encarregado     integer                NOT NULL,
    aluno           integer                NOT NULL,
    grau_parentesco character varying(100) NOT NULL
)
;



ALTER TABLE public.encarregado_aluno
    OWNER TO postgres
;


--
-- TOC entry 256 (class 1259 OID 24962)
-- Name: encarregado_educacao
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.encarregado_educacao
(
    id                          bigint                 NOT NULL,
    nome_completo               character varying(150) NOT NULL,
    data_nascimento             date                   NOT NULL,
    distrito_nascimento         bigint,
    sexo                        character varying(10)  NOT NULL,
    local_trabalho              character varying(255),
    sector_trabalho             bigint,
    endereco                    character varying(255),
    email                       character varying(75),
    grupo_sanguineo             character varying(3),
    numero_telefone_principal   bigint                 NOT NULL,
    numero_telefone_alternativo bigint,
    religiao                    character varying(78),
    estado                      bigint                 NOT NULL
)
;



ALTER TABLE public.encarregado_educacao
    OWNER TO postgres
;


--
-- TOC entry 255 (class 1259 OID 24961)
-- Name: encarregado_educacao_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.encarregado_educacao_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.encarregado_educacao_id_seq OWNER TO postgres
;


--
-- TOC entry 5603 (class 0 OID 0)
-- Dependencies: 255
-- Name: encarregado_educacao_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.encarregado_educacao_id_seq OWNED BY public.encarregado_educacao.id
;



--
-- TOC entry 229 (class 1259 OID 24810)
-- Name: estado
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.estado
(
    id          bigint            NOT NULL,
    descricao   character varying NOT NULL,
    tipo_estado bigint            NOT NULL
)
;



ALTER TABLE public.estado
    OWNER TO postgres
;


--
-- TOC entry 228 (class 1259 OID 24809)
-- Name: estado_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.estado_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.estado_id_seq OWNER TO postgres
;


--
-- TOC entry 5604 (class 0 OID 0)
-- Dependencies: 228
-- Name: estado_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.estado_id_seq OWNED BY public.estado.id
;



--
-- TOC entry 312 (class 1259 OID 25620)
-- Name: evento
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.evento
(
    id          bigint                      NOT NULL,
    nome        character varying(200)      NOT NULL,
    descricao   text,
    data_inicio timestamp(6) with time zone NOT NULL,
    data_fim    timestamp(6) with time zone,
    localizacao character varying(255),
    responsavel bigint                      NOT NULL,
    estado      bigint                      NOT NULL
)
;



ALTER TABLE public.evento
    OWNER TO postgres
;


--
-- TOC entry 311 (class 1259 OID 25619)
-- Name: evento_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.evento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.evento_id_seq OWNER TO postgres
;


--
-- TOC entry 5605 (class 0 OID 0)
-- Dependencies: 311
-- Name: evento_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.evento_id_seq OWNED BY public.evento.id
;



--
-- TOC entry 304 (class 1259 OID 25547)
-- Name: folha_pagamento
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.folha_pagamento
(
    id              bigint                                                NOT NULL,
    funcionario     bigint                                                NOT NULL,
    salario_bruto   numeric(10, 2)                                        NOT NULL,
    descontos       numeric(10, 2),
    salario_liquido numeric(10, 2)                                        NOT NULL,
    mes_referencia  date                                                  NOT NULL,
    data_pagamento  timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    estado          bigint                                                NOT NULL
)
;



ALTER TABLE public.folha_pagamento
    OWNER TO postgres
;


--
-- TOC entry 303 (class 1259 OID 25546)
-- Name: folha_pagamento_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.folha_pagamento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.folha_pagamento_id_seq OWNER TO postgres
;


--
-- TOC entry 5606 (class 0 OID 0)
-- Dependencies: 303
-- Name: folha_pagamento_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.folha_pagamento_id_seq OWNED BY public.folha_pagamento.id
;



--
-- TOC entry 320 (class 1259 OID 25693)
-- Name: fornecedor
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.fornecedor
(
    id        bigint                 NOT NULL,
    nome      character varying(200) NOT NULL,
    endereco  character varying(255),
    telefone  character varying(15),
    email     character varying(100),
    descricao text,
    estado    bigint                 NOT NULL
)
;



ALTER TABLE public.fornecedor
    OWNER TO postgres
;


--
-- TOC entry 319 (class 1259 OID 25692)
-- Name: fornecedor_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.fornecedor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.fornecedor_id_seq OWNER TO postgres
;


--
-- TOC entry 5607 (class 0 OID 0)
-- Dependencies: 319
-- Name: fornecedor_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.fornecedor_id_seq OWNED BY public.fornecedor.id
;



--
-- TOC entry 261 (class 1259 OID 25026)
-- Name: funcionario
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.funcionario
(
    id                          bigint                                                NOT NULL,
    nome_completo               character varying(100)                                NOT NULL,
    data_nascimento             date                                                  NOT NULL,
    distrito_nascimento         bigint,
    sexo                        character varying(1)                                  NOT NULL,
    endereco                    character varying(255)                                NOT NULL,
    email                       character varying(75)                                 NOT NULL,
    numero_telefone_principal   bigint                                                NOT NULL,
    numero_telefone_alternativo bigint,
    data_contracto              timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    cargo                       bigint                                                NOT NULL,
    departamento                bigint                                                NOT NULL,
    estado                      bigint                                                NOT NULL,
    estado_civil                character varying(10),
    bilhete_identificacao       character varying(13),
    religiao                    character varying(78),
    grupo_sanguineo             character varying(3),
    area_formacao               bigint                                                NOT NULL,
    tipo_funcionario            bigint                                                NOT NULL
)
;



ALTER TABLE public.funcionario
    OWNER TO postgres
;


--
-- TOC entry 260 (class 1259 OID 25025)
-- Name: funcionario_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.funcionario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.funcionario_id_seq OWNER TO postgres
;


--
-- TOC entry 5608 (class 0 OID 0)
-- Dependencies: 260
-- Name: funcionario_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.funcionario_id_seq OWNED BY public.funcionario.id
;



--
-- TOC entry 278 (class 1259 OID 25228)
-- Name: horario
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.horario
(
    id           bigint                    NOT NULL,
    sala         bigint                    NOT NULL,
    turma        bigint                    NOT NULL,
    disciplina   bigint                    NOT NULL,
    professor    bigint                    NOT NULL,
    dia_semana   character varying(10)     NOT NULL,
    hora_inicio  time(6) without time zone NOT NULL,
    hora_termino time(6) without time zone NOT NULL,
    estado       bigint                    NOT NULL
)
;



ALTER TABLE public.horario
    OWNER TO postgres
;


--
-- TOC entry 277 (class 1259 OID 25227)
-- Name: horario_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.horario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.horario_id_seq OWNER TO postgres
;


--
-- TOC entry 5609 (class 0 OID 0)
-- Dependencies: 277
-- Name: horario_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.horario_id_seq OWNED BY public.horario.id
;



--
-- TOC entry 324 (class 1259 OID 25729)
-- Name: item_ordem_compra
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.item_ordem_compra
(
    id             bigint                 NOT NULL,
    ordem_compra   bigint                 NOT NULL,
    material       bigint                 NOT NULL,
    descricao      character varying(200) NOT NULL,
    quantidade     integer                NOT NULL,
    valor_unitario numeric(10, 2)         NOT NULL,
    estado         bigint                 NOT NULL
)
;



ALTER TABLE public.item_ordem_compra
    OWNER TO postgres
;


--
-- TOC entry 323 (class 1259 OID 25728)
-- Name: item_ordem_compra_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.item_ordem_compra_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.item_ordem_compra_id_seq OWNER TO postgres
;


--
-- TOC entry 5610 (class 0 OID 0)
-- Dependencies: 323
-- Name: item_ordem_compra_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.item_ordem_compra_id_seq OWNED BY public.item_ordem_compra.id
;



--
-- TOC entry 308 (class 1259 OID 25579)
-- Name: manutencao_ativo
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.manutencao_ativo
(
    id              bigint                 NOT NULL,
    ativo           bigint                 NOT NULL,
    descricao       character varying(255) NOT NULL,
    data_manutencao date                   NOT NULL,
    custo           numeric(10, 2),
    responsavel     bigint                 NOT NULL,
    estado          bigint                 NOT NULL
)
;



ALTER TABLE public.manutencao_ativo
    OWNER TO postgres
;


--
-- TOC entry 307 (class 1259 OID 25578)
-- Name: manutencao_ativo_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.manutencao_ativo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.manutencao_ativo_id_seq OWNER TO postgres
;


--
-- TOC entry 5611 (class 0 OID 0)
-- Dependencies: 307
-- Name: manutencao_ativo_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.manutencao_ativo_id_seq OWNED BY public.manutencao_ativo.id
;



--
-- TOC entry 269 (class 1259 OID 25122)
-- Name: material
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.material
(
    id            bigint                 NOT NULL,
    tipo_material bigint                 NOT NULL,
    nome_material character varying(100) NOT NULL,
    quantidade    integer,
    estado        bigint                 NOT NULL
)
;



ALTER TABLE public.material
    OWNER TO postgres
;


--
-- TOC entry 268 (class 1259 OID 25121)
-- Name: material_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.material_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.material_id_seq OWNER TO postgres
;


--
-- TOC entry 5612 (class 0 OID 0)
-- Dependencies: 268
-- Name: material_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.material_id_seq OWNED BY public.material.id
;



--
-- TOC entry 273 (class 1259 OID 25171)
-- Name: matricula
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.matricula
(
    id          bigint  NOT NULL,
    ano_lectivo integer NOT NULL,
    aluno       bigint  NOT NULL,
    estado      bigint  NOT NULL
)
;



ALTER TABLE public.matricula
    OWNER TO postgres
;


--
-- TOC entry 272 (class 1259 OID 25170)
-- Name: matricula_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.matricula_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.matricula_id_seq OWNER TO postgres
;


--
-- TOC entry 5613 (class 0 OID 0)
-- Dependencies: 272
-- Name: matricula_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.matricula_id_seq OWNED BY public.matricula.id
;



--
-- TOC entry 300 (class 1259 OID 25499)
-- Name: orcamento
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.orcamento
(
    id           bigint                                                NOT NULL,
    ano          integer                                               NOT NULL,
    valor_total  numeric(15, 2)                                        NOT NULL,
    data_criacao timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    responsavel  bigint                                                NOT NULL,
    estado       bigint                                                NOT NULL
)
;



ALTER TABLE public.orcamento
    OWNER TO postgres
;


--
-- TOC entry 299 (class 1259 OID 25498)
-- Name: orcamento_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.orcamento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.orcamento_id_seq OWNER TO postgres
;


--
-- TOC entry 5614 (class 0 OID 0)
-- Dependencies: 299
-- Name: orcamento_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.orcamento_id_seq OWNED BY public.orcamento.id
;



--
-- TOC entry 322 (class 1259 OID 25707)
-- Name: ordem_compra
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.ordem_compra
(
    id           bigint         NOT NULL,
    fornecedor   bigint         NOT NULL,
    data_ordem   date           NOT NULL,
    data_entrega date,
    valor_total  numeric(15, 2) NOT NULL,
    responsavel  bigint         NOT NULL,
    estado       bigint         NOT NULL
)
;



ALTER TABLE public.ordem_compra
    OWNER TO postgres
;


--
-- TOC entry 321 (class 1259 OID 25706)
-- Name: ordem_compra_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.ordem_compra_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.ordem_compra_id_seq OWNER TO postgres
;


--
-- TOC entry 5615 (class 0 OID 0)
-- Dependencies: 321
-- Name: ordem_compra_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.ordem_compra_id_seq OWNED BY public.ordem_compra.id
;



--
-- TOC entry 271 (class 1259 OID 25141)
-- Name: pagamento
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.pagamento
(
    id             bigint                                                NOT NULL,
    referencia     character varying(100)                                NOT NULL,
    aluno          bigint                                                NOT NULL,
    valor          numeric(10, 2)                                        NOT NULL,
    tipo_pagamento bigint                                                NOT NULL,
    data_pagamento timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    responsavel    bigint                                                NOT NULL,
    observacao     text,
    estado         bigint                                                NOT NULL
)
;



ALTER TABLE public.pagamento
    OWNER TO postgres
;


--
-- TOC entry 270 (class 1259 OID 25140)
-- Name: pagamento_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.pagamento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.pagamento_id_seq OWNER TO postgres
;


--
-- TOC entry 5616 (class 0 OID 0)
-- Dependencies: 270
-- Name: pagamento_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.pagamento_id_seq OWNED BY public.pagamento.id
;



--
-- TOC entry 314 (class 1259 OID 25639)
-- Name: participante_evento
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.participante_evento
(
    id                bigint                 NOT NULL,
    evento            bigint                 NOT NULL,
    nome_participante character varying(150) NOT NULL,
    tipo_participante bigint                 NOT NULL
)
;



ALTER TABLE public.participante_evento
    OWNER TO postgres
;


--
-- TOC entry 313 (class 1259 OID 25638)
-- Name: participante_evento_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.participante_evento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.participante_evento_id_seq OWNER TO postgres
;


--
-- TOC entry 5617 (class 0 OID 0)
-- Dependencies: 313
-- Name: participante_evento_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.participante_evento_id_seq OWNED BY public.participante_evento.id
;



--
-- TOC entry 288 (class 1259 OID 25363)
-- Name: pauta_final
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.pauta_final
(
    id              bigint                NOT NULL,
    disciplina      bigint                NOT NULL,
    aluno           bigint                NOT NULL,
    ano_lectivo     integer,
    nota_final      integer               NOT NULL,
    professor       bigint                NOT NULL,
    data_publicacao timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP,
    resultado       character varying(30) NOT NULL,
    estado_pauta    bigint                NOT NULL
)
;



ALTER TABLE public.pauta_final
    OWNER TO postgres
;


--
-- TOC entry 287 (class 1259 OID 25362)
-- Name: pauta_final_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.pauta_final_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.pauta_final_id_seq OWNER TO postgres
;


--
-- TOC entry 5618 (class 0 OID 0)
-- Dependencies: 287
-- Name: pauta_final_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.pauta_final_id_seq OWNED BY public.pauta_final.id
;



--
-- TOC entry 286 (class 1259 OID 25333)
-- Name: pauta_trimestral
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.pauta_trimestral
(
    id              bigint  NOT NULL,
    disciplina      bigint  NOT NULL,
    aluno           bigint  NOT NULL,
    trimestre       integer NOT NULL,
    ano_lectivo     integer,
    nota_final      integer NOT NULL,
    professor       bigint  NOT NULL,
    data_publicacao timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP,
    observacao      text,
    estado          bigint  NOT NULL
)
;



ALTER TABLE public.pauta_trimestral
    OWNER TO postgres
;


--
-- TOC entry 285 (class 1259 OID 25332)
-- Name: pauta_trimestral_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.pauta_trimestral_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.pauta_trimestral_id_seq OWNER TO postgres
;


--
-- TOC entry 5619 (class 0 OID 0)
-- Dependencies: 285
-- Name: pauta_trimestral_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.pauta_trimestral_id_seq OWNED BY public.pauta_trimestral.id
;



--
-- TOC entry 284 (class 1259 OID 25305)
-- Name: presencas_aluno
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.presencas_aluno
(
    id         bigint                                                NOT NULL,
    aluno      bigint                                                NOT NULL,
    turma      bigint                                                NOT NULL,
    disciplina bigint                                                NOT NULL,
    data       timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    estado     bigint                                                NOT NULL
)
;



ALTER TABLE public.presencas_aluno
    OWNER TO postgres
;


--
-- TOC entry 283 (class 1259 OID 25304)
-- Name: presencas_aluno_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.presencas_aluno_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.presencas_aluno_id_seq OWNER TO postgres
;


--
-- TOC entry 5620 (class 0 OID 0)
-- Dependencies: 283
-- Name: presencas_aluno_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.presencas_aluno_id_seq OWNED BY public.presencas_aluno.id
;



--
-- TOC entry 259 (class 1259 OID 24996)
-- Name: professor
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.professor
(
    id                          bigint                                                NOT NULL,
    nome_completo               character varying(100)                                NOT NULL,
    data_nascimento             date                                                  NOT NULL,
    distrito_nascimento         bigint,
    sexo                        character varying(1)                                  NOT NULL,
    endereco                    character varying(255)                                NOT NULL,
    email                       character varying(75)                                 NOT NULL,
    numero_telefone_principal   bigint                                                NOT NULL,
    numero_telefone_alternativo bigint,
    data_contracto              timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    estado                      bigint                                                NOT NULL,
    estado_civil                character varying(10),
    bilhete_identificacao       character varying(13),
    religiao                    character varying(78),
    grupo_sanguineo             character varying(3),
    area_formacao               bigint                                                NOT NULL
)
;



ALTER TABLE public.professor
    OWNER TO postgres
;


--
-- TOC entry 266 (class 1259 OID 25091)
-- Name: professor_disciplina
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.professor_disciplina
(
    professor   integer NOT NULL,
    ano_lectivo integer,
    disciplina  integer NOT NULL,
    classe      integer NOT NULL
)
;



ALTER TABLE public.professor_disciplina
    OWNER TO postgres
;


--
-- TOC entry 258 (class 1259 OID 24995)
-- Name: professor_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.professor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.professor_id_seq OWNER TO postgres
;


--
-- TOC entry 5621 (class 0 OID 0)
-- Dependencies: 258
-- Name: professor_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.professor_id_seq OWNED BY public.professor.id
;



--
-- TOC entry 251 (class 1259 OID 24918)
-- Name: provincia
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.provincia
(
    id             bigint NOT NULL,
    nome_provincia character varying(30)
)
;



ALTER TABLE public.provincia
    OWNER TO postgres
;


--
-- TOC entry 250 (class 1259 OID 24917)
-- Name: provincia_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.provincia_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.provincia_id_seq OWNER TO postgres
;


--
-- TOC entry 5622 (class 0 OID 0)
-- Dependencies: 250
-- Name: provincia_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.provincia_id_seq OWNED BY public.provincia.id
;



--
-- TOC entry 296 (class 1259 OID 25453)
-- Name: receita
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.receita
(
    id           bigint                                                NOT NULL,
    descricao    character varying(200)                                NOT NULL,
    valor        numeric(10, 2)                                        NOT NULL,
    data_receita timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    categoria    bigint                                                NOT NULL,
    responsavel  bigint                                                NOT NULL,
    estado       bigint                                                NOT NULL
)
;



ALTER TABLE public.receita
    OWNER TO postgres
;


--
-- TOC entry 295 (class 1259 OID 25452)
-- Name: receita_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.receita_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.receita_id_seq OWNER TO postgres
;


--
-- TOC entry 5623 (class 0 OID 0)
-- Dependencies: 295
-- Name: receita_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.receita_id_seq OWNED BY public.receita.id
;



--
-- TOC entry 316 (class 1259 OID 25656)
-- Name: relatorio_financeiro
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.relatorio_financeiro
(
    id             bigint                                                NOT NULL,
    tipo_relatorio bigint                                                NOT NULL,
    ano            integer                                               NOT NULL,
    mes            integer,
    data_criacao   timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    responsavel    bigint                                                NOT NULL,
    estado         bigint                                                NOT NULL
)
;



ALTER TABLE public.relatorio_financeiro
    OWNER TO postgres
;


--
-- TOC entry 315 (class 1259 OID 25655)
-- Name: relatorio_financeiro_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.relatorio_financeiro_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.relatorio_financeiro_id_seq OWNER TO postgres
;


--
-- TOC entry 5624 (class 0 OID 0)
-- Dependencies: 315
-- Name: relatorio_financeiro_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.relatorio_financeiro_id_seq OWNED BY public.relatorio_financeiro.id
;



--
-- TOC entry 326 (class 1259 OID 25746)
-- Name: requisicao_material
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.requisicao_material
(
    id              bigint                                                NOT NULL,
    data_requisicao timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    material        bigint                                                NOT NULL,
    quantidade      integer                                               NOT NULL,
    requisitor      bigint                                                NOT NULL,
    observacao      text,
    estado          bigint                                                NOT NULL
)
;



ALTER TABLE public.requisicao_material
    OWNER TO postgres
;


--
-- TOC entry 325 (class 1259 OID 25745)
-- Name: requisicao_material_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.requisicao_material_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.requisicao_material_id_seq OWNER TO postgres
;


--
-- TOC entry 5625 (class 0 OID 0)
-- Dependencies: 325
-- Name: requisicao_material_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.requisicao_material_id_seq OWNED BY public.requisicao_material.id
;



--
-- TOC entry 216 (class 1259 OID 24740)
-- Name: role
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.role
(
    id   bigint NOT NULL,
    name character varying(40),
    CONSTRAINT role_name_check CHECK (((name)::text = ANY
                                       (ARRAY [('ROLE_USER'::character varying)::text, ('ROLE_MODERATOR'::character varying)::text, ('ROLE_ESTUDANTE'::character varying)::text, ('ROLE_PROFESSOR'::character varying)::text, ('ROLE_DIRECTOR'::character varying)::text, ('ROLE_PEDAGOGICO'::character varying)::text, ('ROLE_FINANCEIRO'::character varying)::text, ('ROLE_BIBLIOTECARIO'::character varying)::text, ('ROLE_AQUISICOES'::character varying)::text])))
)
;



ALTER TABLE public.role
    OWNER TO postgres
;


--
-- TOC entry 215 (class 1259 OID 24739)
-- Name: role_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.role_id_seq OWNER TO postgres
;


--
-- TOC entry 5626 (class 0 OID 0)
-- Dependencies: 215
-- Name: role_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id
;



--
-- TOC entry 233 (class 1259 OID 24833)
-- Name: sala
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.sala
(
    id         bigint                 NOT NULL,
    nome_sala  character varying(100) NOT NULL,
    capacidade integer                NOT NULL
)
;



ALTER TABLE public.sala
    OWNER TO postgres
;


--
-- TOC entry 232 (class 1259 OID 24832)
-- Name: sala_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.sala_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.sala_id_seq OWNER TO postgres
;


--
-- TOC entry 5627 (class 0 OID 0)
-- Dependencies: 232
-- Name: sala_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.sala_id_seq OWNED BY public.sala.id
;



--
-- TOC entry 245 (class 1259 OID 24891)
-- Name: sector_trabalho
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.sector_trabalho
(
    id        bigint                 NOT NULL,
    descricao character varying(150) NOT NULL
)
;



ALTER TABLE public.sector_trabalho
    OWNER TO postgres
;


--
-- TOC entry 244 (class 1259 OID 24890)
-- Name: sector_trabalho_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.sector_trabalho_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.sector_trabalho_id_seq OWNER TO postgres
;


--
-- TOC entry 5628 (class 0 OID 0)
-- Dependencies: 244
-- Name: sector_trabalho_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.sector_trabalho_id_seq OWNED BY public.sector_trabalho.id
;



--
-- TOC entry 237 (class 1259 OID 24851)
-- Name: tipo_avaliacao
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.tipo_avaliacao
(
    id        bigint            NOT NULL,
    descricao character varying NOT NULL
)
;



ALTER TABLE public.tipo_avaliacao
    OWNER TO postgres
;


--
-- TOC entry 236 (class 1259 OID 24850)
-- Name: tipo_avaliacao_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.tipo_avaliacao_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.tipo_avaliacao_id_seq OWNER TO postgres
;


--
-- TOC entry 5629 (class 0 OID 0)
-- Dependencies: 236
-- Name: tipo_avaliacao_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.tipo_avaliacao_id_seq OWNED BY public.tipo_avaliacao.id
;



--
-- TOC entry 235 (class 1259 OID 24842)
-- Name: tipo_documento
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.tipo_documento
(
    id        bigint                NOT NULL,
    descricao character varying(70) NOT NULL
)
;



ALTER TABLE public.tipo_documento
    OWNER TO postgres
;


--
-- TOC entry 234 (class 1259 OID 24841)
-- Name: tipo_documento_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.tipo_documento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.tipo_documento_id_seq OWNER TO postgres
;


--
-- TOC entry 5630 (class 0 OID 0)
-- Dependencies: 234
-- Name: tipo_documento_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.tipo_documento_id_seq OWNED BY public.tipo_documento.id
;



--
-- TOC entry 227 (class 1259 OID 24801)
-- Name: tipo_estado
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.tipo_estado
(
    id        bigint            NOT NULL,
    descricao character varying NOT NULL
)
;



ALTER TABLE public.tipo_estado
    OWNER TO postgres
;


--
-- TOC entry 226 (class 1259 OID 24800)
-- Name: tipo_estado_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.tipo_estado_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.tipo_estado_id_seq OWNER TO postgres
;


--
-- TOC entry 5631 (class 0 OID 0)
-- Dependencies: 226
-- Name: tipo_estado_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.tipo_estado_id_seq OWNED BY public.tipo_estado.id
;



--
-- TOC entry 328 (class 1259 OID 34661)
-- Name: tipo_funcionario
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.tipo_funcionario
(
    id        bigint                NOT NULL,
    descricao character varying(70) NOT NULL
)
;



ALTER TABLE public.tipo_funcionario
    OWNER TO postgres
;


--
-- TOC entry 327 (class 1259 OID 34660)
-- Name: tipo_funcionario_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.tipo_funcionario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.tipo_funcionario_id_seq OWNER TO postgres
;


--
-- TOC entry 5632 (class 0 OID 0)
-- Dependencies: 327
-- Name: tipo_funcionario_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.tipo_funcionario_id_seq OWNED BY public.tipo_funcionario.id
;



--
-- TOC entry 247 (class 1259 OID 24900)
-- Name: tipo_material
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.tipo_material
(
    id        bigint                 NOT NULL,
    descricao character varying(100) NOT NULL
)
;



ALTER TABLE public.tipo_material
    OWNER TO postgres
;


--
-- TOC entry 246 (class 1259 OID 24899)
-- Name: tipo_material_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.tipo_material_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.tipo_material_id_seq OWNER TO postgres
;


--
-- TOC entry 5633 (class 0 OID 0)
-- Dependencies: 246
-- Name: tipo_material_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.tipo_material_id_seq OWNED BY public.tipo_material.id
;



--
-- TOC entry 249 (class 1259 OID 24909)
-- Name: tipo_pagamento
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.tipo_pagamento
(
    id        bigint NOT NULL,
    descricao character varying(80)
)
;



ALTER TABLE public.tipo_pagamento
    OWNER TO postgres
;


--
-- TOC entry 248 (class 1259 OID 24908)
-- Name: tipo_pagamento_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.tipo_pagamento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.tipo_pagamento_id_seq OWNER TO postgres
;


--
-- TOC entry 5634 (class 0 OID 0)
-- Dependencies: 248
-- Name: tipo_pagamento_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.tipo_pagamento_id_seq OWNED BY public.tipo_pagamento.id
;



--
-- TOC entry 225 (class 1259 OID 24792)
-- Name: tipo_pessoa
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.tipo_pessoa
(
    id        bigint                NOT NULL,
    descricao character varying(70) NOT NULL
)
;



ALTER TABLE public.tipo_pessoa
    OWNER TO postgres
;


--
-- TOC entry 224 (class 1259 OID 24791)
-- Name: tipo_pessoa_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.tipo_pessoa_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.tipo_pessoa_id_seq OWNER TO postgres
;


--
-- TOC entry 5635 (class 0 OID 0)
-- Dependencies: 224
-- Name: tipo_pessoa_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.tipo_pessoa_id_seq OWNED BY public.tipo_pessoa.id
;



--
-- TOC entry 221 (class 1259 OID 24774)
-- Name: tipo_relatorio
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.tipo_relatorio
(
    id        bigint                 NOT NULL,
    descricao character varying(100) NOT NULL
)
;



ALTER TABLE public.tipo_relatorio
    OWNER TO postgres
;


--
-- TOC entry 220 (class 1259 OID 24773)
-- Name: tipo_relatorio_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.tipo_relatorio_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.tipo_relatorio_id_seq OWNER TO postgres
;


--
-- TOC entry 5636 (class 0 OID 0)
-- Dependencies: 220
-- Name: tipo_relatorio_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.tipo_relatorio_id_seq OWNED BY public.tipo_relatorio.id
;



--
-- TOC entry 239 (class 1259 OID 24862)
-- Name: tipo_transacao
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.tipo_transacao
(
    id        bigint                 NOT NULL,
    descricao character varying(100) NOT NULL
)
;



ALTER TABLE public.tipo_transacao
    OWNER TO postgres
;


--
-- TOC entry 238 (class 1259 OID 24861)
-- Name: tipo_transacao_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.tipo_transacao_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.tipo_transacao_id_seq OWNER TO postgres
;


--
-- TOC entry 5637 (class 0 OID 0)
-- Dependencies: 238
-- Name: tipo_transacao_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.tipo_transacao_id_seq OWNED BY public.tipo_transacao.id
;



--
-- TOC entry 302 (class 1259 OID 25517)
-- Name: transacao
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.transacao
(
    id             bigint                                                NOT NULL,
    tipo_transacao bigint                                                NOT NULL,
    valor          numeric(10, 2)                                        NOT NULL,
    data_transacao timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    categoria      bigint                                                NOT NULL,
    responsavel    bigint                                                NOT NULL,
    descricao      text                                                  NOT NULL,
    estado         bigint                                                NOT NULL
)
;



ALTER TABLE public.transacao
    OWNER TO postgres
;


--
-- TOC entry 301 (class 1259 OID 25516)
-- Name: transacao_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.transacao_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.transacao_id_seq OWNER TO postgres
;


--
-- TOC entry 5638 (class 0 OID 0)
-- Dependencies: 301
-- Name: transacao_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.transacao_id_seq OWNED BY public.transacao.id
;



--
-- TOC entry 263 (class 1259 OID 25066)
-- Name: turma
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.turma
(
    id                    bigint                 NOT NULL,
    nome_turma            character varying(100) NOT NULL,
    ano_lectivo           integer                NOT NULL,
    professor_responsavel bigint                 NOT NULL,
    estado                bigint                 NOT NULL
)
;



ALTER TABLE public.turma
    OWNER TO postgres
;


--
-- TOC entry 276 (class 1259 OID 25212)
-- Name: turma_aluno
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.turma_aluno
(
    aluno       integer NOT NULL,
    ano_lectivo integer NOT NULL,
    turma       integer NOT NULL
)
;



ALTER TABLE public.turma_aluno
    OWNER TO postgres
;


--
-- TOC entry 262 (class 1259 OID 25065)
-- Name: turma_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.turma_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.turma_id_seq OWNER TO postgres
;


--
-- TOC entry 5639 (class 0 OID 0)
-- Dependencies: 262
-- Name: turma_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.turma_id_seq OWNED BY public.turma.id
;



--
-- TOC entry 218 (class 1259 OID 24748)
-- Name: utilizador
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.utilizador
(
    id       bigint                 NOT NULL,
    email    character varying(50),
    password character varying(120),
    username character varying(20),
    nome     character varying(150) NOT NULL
)
;



ALTER TABLE public.utilizador
    OWNER TO postgres
;


--
-- TOC entry 217 (class 1259 OID 24747)
-- Name: utilizador_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.utilizador_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.utilizador_id_seq OWNER TO postgres
;


--
-- TOC entry 5640 (class 0 OID 0)
-- Dependencies: 217
-- Name: utilizador_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.utilizador_id_seq OWNED BY public.utilizador.id
;



--
-- TOC entry 219 (class 1259 OID 24754)
-- Name: utilizador_role
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.utilizador_role
(
    utilizador bigint NOT NULL,
    role       bigint NOT NULL
)
;



ALTER TABLE public.utilizador_role
    OWNER TO postgres
;


--
-- TOC entry 292 (class 1259 OID 25411)
-- Name: veiculo
;
 Type: TABLE
;
 Schema: public
;
 Owner: postgres
--

CREATE TABLE public.veiculo
(
    id             bigint NOT NULL,
    codigo_veiculo character varying(19),
    marca_veiculo  character varying(30),
    motorista      bigint NOT NULL,
    estado         bigint NOT NULL
)
;



ALTER TABLE public.veiculo
    OWNER TO postgres
;


--
-- TOC entry 291 (class 1259 OID 25410)
-- Name: veiculo_id_seq
;
 Type: SEQUENCE
;
 Schema: public
;
 Owner: postgres
--

CREATE SEQUENCE public.veiculo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;



ALTER SEQUENCE public.veiculo_id_seq OWNER TO postgres
;


--
-- TOC entry 5641 (class 0 OID 0)
-- Dependencies: 291
-- Name: veiculo_id_seq
;
 Type: SEQUENCE OWNED BY
;
 Schema: public
;
 Owner: postgres
--

ALTER SEQUENCE public.veiculo_id_seq OWNED BY public.veiculo.id
;



--
-- TOC entry 4980 (class 2604 OID 36512)
-- Name: area_cientifica id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.area_cientifica
    ALTER COLUMN id SET DEFAULT nextval('public.area_cientifica_id_seq'::regclass)
;



--
-- TOC entry 5036 (class 2604 OID 36530)
-- Name: ativo id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.ativo
    ALTER COLUMN id SET DEFAULT nextval('public.ativo_id_seq'::regclass)
;



--
-- TOC entry 5021 (class 2604 OID 36555)
-- Name: aula id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.aula
    ALTER COLUMN id SET DEFAULT nextval('public.aula_id_seq'::regclass)
;



--
-- TOC entry 5011 (class 2604 OID 36587)
-- Name: avaliacao id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.avaliacao
    ALTER COLUMN id SET DEFAULT nextval('public.avaliacao_id_seq'::regclass)
;



--
-- TOC entry 5012 (class 2604 OID 36634)
-- Name: avaliacao_aluno id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.avaliacao_aluno
    ALTER COLUMN id SET DEFAULT nextval('public.avaliacao_aluno_id_seq'::regclass)
;



--
-- TOC entry 4984 (class 2604 OID 36656)
-- Name: cargo id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.cargo
    ALTER COLUMN id SET DEFAULT nextval('public.cargo_id_seq'::regclass)
;



--
-- TOC entry 4990 (class 2604 OID 36669)
-- Name: categoria_financeira id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.categoria_financeira
    ALTER COLUMN id SET DEFAULT nextval('public.categoria_financeira_id_seq'::regclass)
;



--
-- TOC entry 5008 (class 2604 OID 36692)
-- Name: comunicados id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.comunicados
    ALTER COLUMN id SET DEFAULT nextval('public.comunicados_id_seq'::regclass)
;



--
-- TOC entry 5038 (class 2604 OID 36724)
-- Name: contrato id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.contrato
    ALTER COLUMN id SET DEFAULT nextval('public.contrato_id_seq'::regclass)
;



--
-- TOC entry 4989 (class 2604 OID 36744)
-- Name: departamento id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.departamento
    ALTER COLUMN id SET DEFAULT nextval('public.departamento_id_seq'::regclass)
;



--
-- TOC entry 5028 (class 2604 OID 36758)
-- Name: despesa id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.despesa
    ALTER COLUMN id SET DEFAULT nextval('public.despesa_id_seq'::regclass)
;



--
-- TOC entry 5043 (class 2604 OID 36784)
-- Name: detalhe_relatorio_financeiro id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.detalhe_relatorio_financeiro
    ALTER COLUMN id SET DEFAULT nextval('public.detalhe_relatorio_financeiro_id_seq'::regclass)
;



--
-- TOC entry 5003 (class 2604 OID 36804)
-- Name: disciplina id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.disciplina
    ALTER COLUMN id SET DEFAULT nextval('public.disciplina_id_seq'::regclass)
;



--
-- TOC entry 4995 (class 2604 OID 36852)
-- Name: distrito id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.distrito
    ALTER COLUMN id SET DEFAULT nextval('public.distrito_id_seq'::regclass)
;



--
-- TOC entry 5024 (class 2604 OID 36888)
-- Name: documento id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.documento
    ALTER COLUMN id SET DEFAULT nextval('public.documento_id_seq'::regclass)
;



--
-- TOC entry 4997 (class 2604 OID 33443)
-- Name: encarregado_educacao id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.encarregado_educacao
    ALTER COLUMN id SET DEFAULT nextval('public.encarregado_educacao_id_seq'::regclass)
;



--
-- TOC entry 4983 (class 2604 OID 36942)
-- Name: estado id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.estado
    ALTER COLUMN id SET DEFAULT nextval('public.estado_id_seq'::regclass)
;



--
-- TOC entry 5039 (class 2604 OID 37122)
-- Name: evento id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.evento
    ALTER COLUMN id SET DEFAULT nextval('public.evento_id_seq'::regclass)
;



--
-- TOC entry 5034 (class 2604 OID 37147)
-- Name: folha_pagamento id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.folha_pagamento
    ALTER COLUMN id SET DEFAULT nextval('public.folha_pagamento_id_seq'::regclass)
;



--
-- TOC entry 5044 (class 2604 OID 37164)
-- Name: fornecedor id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.fornecedor
    ALTER COLUMN id SET DEFAULT nextval('public.fornecedor_id_seq'::regclass)
;



--
-- TOC entry 5000 (class 2604 OID 33747)
-- Name: funcionario id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ALTER COLUMN id SET DEFAULT nextval('public.funcionario_id_seq'::regclass)
;



--
-- TOC entry 5010 (class 2604 OID 37245)
-- Name: horario id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.horario
    ALTER COLUMN id SET DEFAULT nextval('public.horario_id_seq'::regclass)
;



--
-- TOC entry 5046 (class 2604 OID 37279)
-- Name: item_ordem_compra id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.item_ordem_compra
    ALTER COLUMN id SET DEFAULT nextval('public.item_ordem_compra_id_seq'::regclass)
;



--
-- TOC entry 5037 (class 2604 OID 37304)
-- Name: manutencao_ativo id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.manutencao_ativo
    ALTER COLUMN id SET DEFAULT nextval('public.manutencao_ativo_id_seq'::regclass)
;



--
-- TOC entry 5004 (class 2604 OID 37329)
-- Name: material id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.material
    ALTER COLUMN id SET DEFAULT nextval('public.material_id_seq'::regclass)
;



--
-- TOC entry 5007 (class 2604 OID 37367)
-- Name: matricula id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.matricula
    ALTER COLUMN id SET DEFAULT nextval('public.matricula_id_seq'::regclass)
;



--
-- TOC entry 5030 (class 2604 OID 37383)
-- Name: orcamento id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.orcamento
    ALTER COLUMN id SET DEFAULT nextval('public.orcamento_id_seq'::regclass)
;



--
-- TOC entry 5045 (class 2604 OID 37400)
-- Name: ordem_compra id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.ordem_compra
    ALTER COLUMN id SET DEFAULT nextval('public.ordem_compra_id_seq'::regclass)
;



--
-- TOC entry 5005 (class 2604 OID 37430)
-- Name: pagamento id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ALTER COLUMN id SET DEFAULT nextval('public.pagamento_id_seq'::regclass)
;



--
-- TOC entry 5040 (class 2604 OID 37462)
-- Name: participante_evento id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.participante_evento
    ALTER COLUMN id SET DEFAULT nextval('public.participante_evento_id_seq'::regclass)
;



--
-- TOC entry 5019 (class 2604 OID 37487)
-- Name: pauta_final id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pauta_final
    ALTER COLUMN id SET DEFAULT nextval('public.pauta_final_id_seq'::regclass)
;



--
-- TOC entry 5017 (class 2604 OID 37513)
-- Name: pauta_trimestral id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pauta_trimestral
    ALTER COLUMN id SET DEFAULT nextval('public.pauta_trimestral_id_seq'::regclass)
;



--
-- TOC entry 5015 (class 2604 OID 37545)
-- Name: presencas_aluno id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.presencas_aluno
    ALTER COLUMN id SET DEFAULT nextval('public.presencas_aluno_id_seq'::regclass)
;



--
-- TOC entry 4998 (class 2604 OID 34373)
-- Name: professor id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.professor
    ALTER COLUMN id SET DEFAULT nextval('public.professor_id_seq'::regclass)
;



--
-- TOC entry 4994 (class 2604 OID 37614)
-- Name: provincia id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.provincia
    ALTER COLUMN id SET DEFAULT nextval('public.provincia_id_seq'::regclass)
;



--
-- TOC entry 5026 (class 2604 OID 37627)
-- Name: receita id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.receita
    ALTER COLUMN id SET DEFAULT nextval('public.receita_id_seq'::regclass)
;



--
-- TOC entry 5041 (class 2604 OID 37653)
-- Name: relatorio_financeiro id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.relatorio_financeiro
    ALTER COLUMN id SET DEFAULT nextval('public.relatorio_financeiro_id_seq'::regclass)
;



--
-- TOC entry 5047 (class 2604 OID 37684)
-- Name: requisicao_material id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.requisicao_material
    ALTER COLUMN id SET DEFAULT nextval('public.requisicao_material_id_seq'::regclass)
;



--
-- TOC entry 4977 (class 2604 OID 37944)
-- Name: role id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.role
    ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass)
;



--
-- TOC entry 4985 (class 2604 OID 37716)
-- Name: sala id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.sala
    ALTER COLUMN id SET DEFAULT nextval('public.sala_id_seq'::regclass)
;



--
-- TOC entry 4991 (class 2604 OID 37729)
-- Name: sector_trabalho id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.sector_trabalho
    ALTER COLUMN id SET DEFAULT nextval('public.sector_trabalho_id_seq'::regclass)
;



--
-- TOC entry 4987 (class 2604 OID 37742)
-- Name: tipo_avaliacao id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_avaliacao
    ALTER COLUMN id SET DEFAULT nextval('public.tipo_avaliacao_id_seq'::regclass)
;



--
-- TOC entry 4986 (class 2604 OID 37757)
-- Name: tipo_documento id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_documento
    ALTER COLUMN id SET DEFAULT nextval('public.tipo_documento_id_seq'::regclass)
;



--
-- TOC entry 4982 (class 2604 OID 37770)
-- Name: tipo_estado id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_estado
    ALTER COLUMN id SET DEFAULT nextval('public.tipo_estado_id_seq'::regclass)
;



--
-- TOC entry 5049 (class 2604 OID 34664)
-- Name: tipo_funcionario id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_funcionario
    ALTER COLUMN id SET DEFAULT nextval('public.tipo_funcionario_id_seq'::regclass)
;



--
-- TOC entry 4992 (class 2604 OID 37784)
-- Name: tipo_material id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_material
    ALTER COLUMN id SET DEFAULT nextval('public.tipo_material_id_seq'::regclass)
;



--
-- TOC entry 4993 (class 2604 OID 37797)
-- Name: tipo_pagamento id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_pagamento
    ALTER COLUMN id SET DEFAULT nextval('public.tipo_pagamento_id_seq'::regclass)
;



--
-- TOC entry 4981 (class 2604 OID 37810)
-- Name: tipo_pessoa id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_pessoa
    ALTER COLUMN id SET DEFAULT nextval('public.tipo_pessoa_id_seq'::regclass)
;



--
-- TOC entry 4979 (class 2604 OID 37828)
-- Name: tipo_relatorio id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_relatorio
    ALTER COLUMN id SET DEFAULT nextval('public.tipo_relatorio_id_seq'::regclass)
;



--
-- TOC entry 4988 (class 2604 OID 37841)
-- Name: tipo_transacao id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_transacao
    ALTER COLUMN id SET DEFAULT nextval('public.tipo_transacao_id_seq'::regclass)
;



--
-- TOC entry 5032 (class 2604 OID 37854)
-- Name: transacao id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ALTER COLUMN id SET DEFAULT nextval('public.transacao_id_seq'::regclass)
;



--
-- TOC entry 5002 (class 2604 OID 37897)
-- Name: turma id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.turma
    ALTER COLUMN id SET DEFAULT nextval('public.turma_id_seq'::regclass)
;



--
-- TOC entry 4978 (class 2604 OID 24751)
-- Name: utilizador id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.utilizador
    ALTER COLUMN id SET DEFAULT nextval('public.utilizador_id_seq'::regclass)
;



--
-- TOC entry 5023 (class 2604 OID 37928)
-- Name: veiculo id
;
 Type: DEFAULT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.veiculo
    ALTER COLUMN id SET DEFAULT nextval('public.veiculo_id_seq'::regclass)
;



--
-- TOC entry 5506 (class 0 OID 24938)
-- Dependencies: 254
-- Data for Name: aluno
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5475 (class 0 OID 24783)
-- Dependencies: 223
-- Data for Name: area_cientifica
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5558 (class 0 OID 25565)
-- Dependencies: 306
-- Data for Name: ativo
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5542 (class 0 OID 25391)
-- Dependencies: 290
-- Data for Name: aula
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5532 (class 0 OID 25255)
-- Dependencies: 280
-- Data for Name: avaliacao
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5534 (class 0 OID 25284)
-- Dependencies: 282
-- Data for Name: avaliacao_aluno
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5483 (class 0 OID 24824)
-- Dependencies: 231
-- Data for Name: cargo
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.cargo (id, descricao)
VALUES (1, 'Diretor(a)')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (2, 'Vice-Diretor(a)')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (3, 'Coordenador(a) Pedagógico(a)')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (4, 'Secretário(a) Escolar')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (5, 'Assistente Administrativo')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (6, 'Gestor(a) Financeiro(a)')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (7, 'Contador(a)')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (8, 'Bibliotecário(a)')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (9, 'Recepcionista')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (10, 'Inspetor(a) de Alunos')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (11, 'Professor(a)')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (12, 'Orientador(a) Educacional')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (13, 'Supervisor(a) Educacional')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (14, 'Auxiliar de Sala de Aula')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (15, 'Monitor(a) de Laboratório')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (16, 'Zelador(a)')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (17, 'Servente')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (18, 'Cozinheiro(a)')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (19, 'Auxiliar de Cozinha')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (20, 'Segurança')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (21, 'Motorista')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (22, 'Técnico(a) de Informática')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (23, 'Manutencionista')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (24, 'Psicólogo(a) Escolar')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (25, 'Assistente Social')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (26, 'Mediador(a) Escolar')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (27, 'Nutricionista')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (28, 'Enfermeiro(a) Escolar')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (29, 'Gestor(a) de Tecnologia da Informação')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (30, 'Gestor(a) de Manutenção')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (31, 'Gestor(a) de Recursos Humanos')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (32, 'Gestor(a) de Logística')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (33, 'Gestor(a) de Compras')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (34, 'Responsável pelo Marketing Escolar')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (35, 'Responsável pela Comunicação Institucional')
ON CONFLICT DO NOTHING
;

INSERT INTO public.cargo (id, descricao)
VALUES (36, 'Designer Gráfico')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5495 (class 0 OID 24882)
-- Dependencies: 243
-- Data for Name: categoria_financeira
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5527 (class 0 OID 25188)
-- Dependencies: 275
-- Data for Name: comunicados
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5562 (class 0 OID 25601)
-- Dependencies: 310
-- Data for Name: contrato
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5493 (class 0 OID 24871)
-- Dependencies: 241
-- Data for Name: departamento
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.departamento (id, descricao, sigla)
VALUES (1, 'Departamento Academico', 'DEA')
ON CONFLICT DO NOTHING
;

INSERT INTO public.departamento (id, descricao, sigla)
VALUES (3, 'Departamento Finaceiro', 'DEF')
ON CONFLICT DO NOTHING
;

INSERT INTO public.departamento (id, descricao, sigla)
VALUES (4, 'Departamento de Recursos Humanos', 'DRH')
ON CONFLICT DO NOTHING
;

INSERT INTO public.departamento (id, descricao, sigla)
VALUES (5, 'Departamento Aquisicoes', 'DAQ')
ON CONFLICT DO NOTHING
;

INSERT INTO public.departamento (id, descricao, sigla)
VALUES (2, 'Departamento Pedagogico', 'DEP')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5550 (class 0 OID 25476)
-- Dependencies: 298
-- Data for Name: despesa
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5570 (class 0 OID 25679)
-- Dependencies: 318
-- Data for Name: detalhe_relatorio_financeiro
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5517 (class 0 OID 25083)
-- Dependencies: 265
-- Data for Name: disciplina
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.disciplina (id, nome_disciplina)
VALUES (1, 'Portugues')
ON CONFLICT DO NOTHING
;

INSERT INTO public.disciplina (id, nome_disciplina)
VALUES (2, 'Matematica')
ON CONFLICT DO NOTHING
;

INSERT INTO public.disciplina (id, nome_disciplina)
VALUES (3, 'Quimica')
ON CONFLICT DO NOTHING
;

INSERT INTO public.disciplina (id, nome_disciplina)
VALUES (4, 'Fisica')
ON CONFLICT DO NOTHING
;

INSERT INTO public.disciplina (id, nome_disciplina)
VALUES (5, 'Historia')
ON CONFLICT DO NOTHING
;

INSERT INTO public.disciplina (id, nome_disciplina)
VALUES (6, 'Geografia')
ON CONFLICT DO NOTHING
;

INSERT INTO public.disciplina (id, nome_disciplina)
VALUES (7, 'Ingles')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5519 (class 0 OID 25106)
-- Dependencies: 267
-- Data for Name: disciplina_aluno
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5505 (class 0 OID 24927)
-- Dependencies: 253
-- Data for Name: distrito
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (1, 'Boane', 1)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (2, 'Magude', 1)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (3, 'Manhiça', 1)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (4, 'Marracuene', 1)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (5, 'Matutuíne', 1)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (6, 'Moamba', 1)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (7, 'Namaacha', 1)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (8, 'Matola', 1)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (9, 'KaMpfumo', 2)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (10, 'KaMubukwana', 2)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (11, 'KaTembe', 2)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (12, 'Machava', 2)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (13, 'Mahanje', 2)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (14, 'Malhangalene', 2)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (15, 'Mavalane', 2)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (16, 'Nlhamankulu', 2)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (17, 'Zimpeto', 2)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (18, 'Bilene', 3)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (19, 'Chibuto', 3)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (20, 'Chicualacuala', 3)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (21, 'Chigubo', 3)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (22, 'Chókwè', 3)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (23, 'Guijá', 3)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (24, 'Limpopo', 3)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (25, 'Mabalane', 3)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (26, 'Manjacaze', 3)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (27, 'Massangena', 3)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (28, 'Massingir', 3)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (29, 'Xai-Xai', 3)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (30, 'Funhalouro', 4)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (31, 'Govuro', 4)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (32, 'Homoíne', 4)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (33, 'Inharrime', 4)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (34, 'Inhassoro', 4)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (35, 'Jangamo', 4)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (36, 'Mabote', 4)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (37, 'Massinga', 4)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (38, 'Morrumbene', 4)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (39, 'Panda', 4)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (40, 'Vilankulo', 4)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (41, 'Zavala', 4)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (42, 'Beira', 5)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (43, 'Búzi', 5)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (44, 'Caia', 5)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (45, 'Chemba', 5)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (46, 'Cheringoma', 5)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (47, 'Chibabava', 5)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (48, 'Dondo', 5)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (49, 'Gorongosa', 5)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (50, 'Machanga', 5)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (51, 'Marínguè', 5)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (52, 'Marromeu', 5)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (53, 'Muanza', 5)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (54, 'Nhamatanda', 5)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (55, 'Bárue', 6)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (56, 'Chimoio', 6)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (57, 'Gondola', 6)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (58, 'Guro', 6)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (59, 'Macate', 6)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (60, 'Machaze', 6)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (61, 'Macossa', 6)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (62, 'Manica', 6)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (63, 'Mossurize', 6)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (64, 'Sussundenga', 6)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (65, 'Tambara', 6)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (66, 'Vanduzi', 6)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (67, 'Angónia', 7)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (68, 'Cahora-Bassa', 7)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (69, 'Changara', 7)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (70, 'Chifunde', 7)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (71, 'Chiuta', 7)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (72, 'Marávia', 7)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (73, 'Mágoè', 7)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (74, 'Moatize', 7)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (75, 'Mutarara', 7)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (76, 'Tete', 7)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (77, 'Tsangano', 7)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (78, 'Zumbo', 7)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (79, 'Alto Molócuè', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (80, 'Chinde', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (81, 'Gilé', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (82, 'Gurué', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (83, 'Ile', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (84, 'Inhassunge', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (85, 'Lugela', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (86, 'Maganja da Costa', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (87, 'Milange', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (88, 'Mocuba', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (89, 'Mopeia', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (90, 'Morrumbala', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (91, 'Namacurra', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (92, 'Namarroi', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (93, 'Nicoadala', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (94, 'Pebane', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (95, 'Quelimane', 8)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (96, 'Angoche', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (97, 'Eráti', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (98, 'Ilha de Moçambique', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (99, 'Lalaua', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (100, 'Larde', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (101, 'Liúpo', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (102, 'Malema', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (103, 'Meconta', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (104, 'Mecubúri', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (105, 'Memba', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (106, 'Mogincual', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (107, 'Mogovolas', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (108, 'Moma', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (109, 'Monapo', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (110, 'Mossuril', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (111, 'Muecate', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (112, 'Murrupula', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (113, 'Nacala-a-Velha', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (114, 'Nacala Porto', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (115, 'Nacaroa', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (116, 'Nampula', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (117, 'Rapale', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (118, 'Ribaué', 9)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (119, 'Ancuabe', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (120, 'Balama', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (121, 'Chiúre', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (122, 'Ibo', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (123, 'Macomia', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (124, 'Mecúfi', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (125, 'Meluco', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (126, 'Mocímboa da Praia', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (127, 'Montepuez', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (128, 'Mueda', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (129, 'Muidumbe', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (130, 'Namuno', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (131, 'Nangade', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (132, 'Palma', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (133, 'Pemba-Metuge', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (134, 'Quissanga', 10)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (135, 'Chimbonila', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (136, 'Lago', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (137, 'Lichinga', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (138, 'Majune', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (139, 'Mandimba', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (140, 'Marrupa', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (141, 'Maúa', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (142, 'Mavago', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (143, 'Mecanhelas', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (144, 'Mecula', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (145, 'Metarica', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (146, 'Muembe', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (147, 'Ngauma', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (148, 'Nipepe', 11)
ON CONFLICT DO NOTHING
;

INSERT INTO public.distrito (id, nome_distrito, provincia)
VALUES (149, 'Sanga', 11)
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5546 (class 0 OID 25428)
-- Dependencies: 294
-- Data for Name: documento
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5509 (class 0 OID 24980)
-- Dependencies: 257
-- Data for Name: encarregado_aluno
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5508 (class 0 OID 24962)
-- Dependencies: 256
-- Data for Name: encarregado_educacao
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5481 (class 0 OID 24810)
-- Dependencies: 229
-- Data for Name: estado
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5564 (class 0 OID 25620)
-- Dependencies: 312
-- Data for Name: evento
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5556 (class 0 OID 25547)
-- Dependencies: 304
-- Data for Name: folha_pagamento
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5572 (class 0 OID 25693)
-- Dependencies: 320
-- Data for Name: fornecedor
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5513 (class 0 OID 25026)
-- Dependencies: 261
-- Data for Name: funcionario
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5530 (class 0 OID 25228)
-- Dependencies: 278
-- Data for Name: horario
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5576 (class 0 OID 25729)
-- Dependencies: 324
-- Data for Name: item_ordem_compra
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5560 (class 0 OID 25579)
-- Dependencies: 308
-- Data for Name: manutencao_ativo
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5521 (class 0 OID 25122)
-- Dependencies: 269
-- Data for Name: material
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5525 (class 0 OID 25171)
-- Dependencies: 273
-- Data for Name: matricula
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5552 (class 0 OID 25499)
-- Dependencies: 300
-- Data for Name: orcamento
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5574 (class 0 OID 25707)
-- Dependencies: 322
-- Data for Name: ordem_compra
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5523 (class 0 OID 25141)
-- Dependencies: 271
-- Data for Name: pagamento
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5566 (class 0 OID 25639)
-- Dependencies: 314
-- Data for Name: participante_evento
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5540 (class 0 OID 25363)
-- Dependencies: 288
-- Data for Name: pauta_final
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5538 (class 0 OID 25333)
-- Dependencies: 286
-- Data for Name: pauta_trimestral
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5536 (class 0 OID 25305)
-- Dependencies: 284
-- Data for Name: presencas_aluno
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5511 (class 0 OID 24996)
-- Dependencies: 259
-- Data for Name: professor
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5518 (class 0 OID 25091)
-- Dependencies: 266
-- Data for Name: professor_disciplina
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5503 (class 0 OID 24918)
-- Dependencies: 251
-- Data for Name: provincia
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.provincia (id, nome_provincia)
VALUES (1, 'Maputo provincia')
ON CONFLICT DO NOTHING
;

INSERT INTO public.provincia (id, nome_provincia)
VALUES (2, 'Maputo Cidade')
ON CONFLICT DO NOTHING
;

INSERT INTO public.provincia (id, nome_provincia)
VALUES (3, 'Gaza')
ON CONFLICT DO NOTHING
;

INSERT INTO public.provincia (id, nome_provincia)
VALUES (4, 'Inhambane')
ON CONFLICT DO NOTHING
;

INSERT INTO public.provincia (id, nome_provincia)
VALUES (5, 'Sofala')
ON CONFLICT DO NOTHING
;

INSERT INTO public.provincia (id, nome_provincia)
VALUES (6, 'Manica')
ON CONFLICT DO NOTHING
;

INSERT INTO public.provincia (id, nome_provincia)
VALUES (7, 'Tete')
ON CONFLICT DO NOTHING
;

INSERT INTO public.provincia (id, nome_provincia)
VALUES (8, 'Zambézia')
ON CONFLICT DO NOTHING
;

INSERT INTO public.provincia (id, nome_provincia)
VALUES (9, 'Nampula')
ON CONFLICT DO NOTHING
;

INSERT INTO public.provincia (id, nome_provincia)
VALUES (10, 'Cabo Delgado')
ON CONFLICT DO NOTHING
;

INSERT INTO public.provincia (id, nome_provincia)
VALUES (11, 'Niassa')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5548 (class 0 OID 25453)
-- Dependencies: 296
-- Data for Name: receita
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5568 (class 0 OID 25656)
-- Dependencies: 316
-- Data for Name: relatorio_financeiro
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5578 (class 0 OID 25746)
-- Dependencies: 326
-- Data for Name: requisicao_material
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5468 (class 0 OID 24740)
-- Dependencies: 216
-- Data for Name: role
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.role (id, name)
VALUES (12, 'ROLE_USER')
ON CONFLICT DO NOTHING
;

INSERT INTO public.role (id, name)
VALUES (13, 'ROLE_MODERATOR')
ON CONFLICT DO NOTHING
;

INSERT INTO public.role (id, name)
VALUES (14, 'ROLE_ESTUDANTE')
ON CONFLICT DO NOTHING
;

INSERT INTO public.role (id, name)
VALUES (15, 'ROLE_PROFESSOR')
ON CONFLICT DO NOTHING
;

INSERT INTO public.role (id, name)
VALUES (16, 'ROLE_DIRECTOR')
ON CONFLICT DO NOTHING
;

INSERT INTO public.role (id, name)
VALUES (17, 'ROLE_PEDAGOGICO')
ON CONFLICT DO NOTHING
;

INSERT INTO public.role (id, name)
VALUES (18, 'ROLE_FINANCEIRO')
ON CONFLICT DO NOTHING
;

INSERT INTO public.role (id, name)
VALUES (19, 'ROLE_BIBLIOTECARIO')
ON CONFLICT DO NOTHING
;

INSERT INTO public.role (id, name)
VALUES (20, 'ROLE_AQUISICOES')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5485 (class 0 OID 24833)
-- Dependencies: 233
-- Data for Name: sala
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5497 (class 0 OID 24891)
-- Dependencies: 245
-- Data for Name: sector_trabalho
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (1, 'Saúde')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (2, 'Educação')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (3, 'Comércio')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (4, 'Serviços')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (5, 'Indústria')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (6, 'Agricultura')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (7, 'Construção Civil')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (8, 'Tecnologia da Informação')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (9, 'Finanças')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (10, 'Transporte e Logística')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (11, 'Administração Pública')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (12, 'Forças Armadas')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (13, 'Segurança Privada')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (14, 'Turismo e Hotelaria')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (15, 'Artes e Entretenimento')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (16, 'Comunicação e Marketing')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (17, 'Engenharia')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (18, 'Recursos Humanos')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (19, 'Consultoria')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (20, 'Pesquisa e Desenvolvimento')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (21, 'Energia e Meio Ambiente')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (22, 'Jurídico')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (23, 'Imobiliário')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (24, 'Telecomunicações')
ON CONFLICT DO NOTHING
;

INSERT INTO public.sector_trabalho (id, descricao)
VALUES (25, 'Bancário')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5489 (class 0 OID 24851)
-- Dependencies: 237
-- Data for Name: tipo_avaliacao
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.tipo_avaliacao (id, descricao)
VALUES (1, 'Exame Final')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5487 (class 0 OID 24842)
-- Dependencies: 235
-- Data for Name: tipo_documento
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.tipo_documento (id, descricao)
VALUES (1, 'Boletim Escolar')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (2, 'Histórico Escolar')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (3, 'Certificado de Conclusão')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (4, 'Declaração de Matrícula')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (5, 'Plano de Aula')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (6, 'Calendário Escolar')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (7, 'Horário das Aulas')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (8, 'Lista de Presença')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (9, 'Relatórios de Desempenho')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (10, 'Pauta de Notas')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (11, 'Ficha de Inscrição')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (12, 'Contrato de Prestação de Serviços Educacionais')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (13, 'Regulamento Interno')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (14, 'Relatórios de Gestão')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (15, 'Atas de Reuniões')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (16, 'Plano de Desenvolvimento Escolar')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (17, 'Relatórios de Inspeção Escolar')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (18, 'Registros de Funcionários')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (19, 'Planilhas de Controle Financeiro')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (20, 'Recibos de Pagamento')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (21, 'Notas Fiscais')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (22, 'Relatórios de Orçamento')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (23, 'Balancetes')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (24, 'Demonstrativos Financeiros')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (25, 'Planilhas de Custos')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (26, 'Plano de Orçamento Anual')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (27, 'Relatórios de Auditoria')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (28, 'Contratos de Trabalho')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (29, 'Folha de Pagamento')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (30, 'Relatórios de Frequência')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (31, 'Avaliações de Desempenho')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (32, 'Planos de Carreira')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (33, 'Relatórios de Treinamento e Desenvolvimento')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (34, 'Registros de Férias')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (35, 'Circulares Internas')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (36, 'Avisos e Comunicados')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (37, 'Boletins Informativos')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (38, 'Cartas e Memorandos')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (39, 'E-mails Institucionais')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (40, 'Licenças de Funcionamento')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (41, 'Regulamentos do Ministério da Educação')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (42, 'Acordos e Convênios')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (43, 'Termos de Ajuste de Conduta')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (44, 'Registros de Processos Judiciais')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (45, 'Projetos Pedagógicos')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (46, 'Plano de Curso')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (47, 'Material Didático')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (48, 'Provas e Avaliações')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (49, 'Trabalhos de Alunos')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (50, 'Projetos de Pesquisa')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (51, 'Registros de Atividades Extracurriculares')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (52, 'Plantas e Projetos Arquitetônicos')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (53, 'Relatórios de Manutenção')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (54, 'Registros de Inventário de Equipamentos')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (55, 'Contratos de Serviço de Manutenção')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (56, 'Fichas Médicas dos Alunos')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (57, 'Registros de Vacinação')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (58, 'Relatórios de Acidentes')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (59, 'Planos de Evacuação')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_documento (id, descricao)
VALUES (60, 'Relatórios de Inspeção de Segurança')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5479 (class 0 OID 24801)
-- Dependencies: 227
-- Data for Name: tipo_estado
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.tipo_estado (id, descricao)
VALUES (22, 'Estado Geral')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_estado (id, descricao)
VALUES (23, 'Estado de Pessoas')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_estado (id, descricao)
VALUES (24, 'Estado de Estudante')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_estado (id, descricao)
VALUES (25, 'Estado de Professor')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_estado (id, descricao)
VALUES (26, 'Estado de Avaliação')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_estado (id, descricao)
VALUES (27, 'Estado de Equipamento')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_estado (id, descricao)
VALUES (28, 'Estado de Evento')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5580 (class 0 OID 34661)
-- Dependencies: 328
-- Data for Name: tipo_funcionario
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5499 (class 0 OID 24900)
-- Dependencies: 247
-- Data for Name: tipo_material
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.tipo_material (id, descricao)
VALUES (1, 'Equipamento informatico')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_material (id, descricao)
VALUES (2, 'Carteiras')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_material (id, descricao)
VALUES (3, 'Material didactico')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_material (id, descricao)
VALUES (4, 'Material de Escritorio')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5501 (class 0 OID 24909)
-- Dependencies: 249
-- Data for Name: tipo_pagamento
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.tipo_pagamento (id, descricao)
VALUES (1, 'Pagamento de Mensalidade Escolar')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pagamento (id, descricao)
VALUES (2, 'Pagamento de Matrícula')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pagamento (id, descricao)
VALUES (3, 'Pagamento de Material Didático')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pagamento (id, descricao)
VALUES (4, 'Pagamento de Refeições')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pagamento (id, descricao)
VALUES (5, 'Pagamento de Transporte Escolar')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pagamento (id, descricao)
VALUES (6, 'Pagamento de Atividades Extracurriculares')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pagamento (id, descricao)
VALUES (7, 'Pagamento de Eventos e Excursões')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pagamento (id, descricao)
VALUES (8, 'Pagamento de Taxas Administrativas')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pagamento (id, descricao)
VALUES (9, 'Pagamento de Seguro Escolar')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pagamento (id, descricao)
VALUES (10, 'Pagamento de Doações')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pagamento (id, descricao)
VALUES (11, 'Outros')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5477 (class 0 OID 24792)
-- Dependencies: 225
-- Data for Name: tipo_pessoa
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.tipo_pessoa (id, descricao)
VALUES (1, 'Professor')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pessoa (id, descricao)
VALUES (2, 'Aluno')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pessoa (id, descricao)
VALUES (3, 'Funcionario')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pessoa (id, descricao)
VALUES (4, 'Fornecedor')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pessoa (id, descricao)
VALUES (5, 'Encarregado de Educação')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_pessoa (id, descricao)
VALUES (6, 'Director')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5473 (class 0 OID 24774)
-- Dependencies: 221
-- Data for Name: tipo_relatorio
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5491 (class 0 OID 24862)
-- Dependencies: 239
-- Data for Name: tipo_transacao
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.tipo_transacao (id, descricao)
VALUES (1, 'Receita')
ON CONFLICT DO NOTHING
;

INSERT INTO public.tipo_transacao (id, descricao)
VALUES (2, 'Despesa')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5554 (class 0 OID 25517)
-- Dependencies: 302
-- Data for Name: transacao
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5515 (class 0 OID 25066)
-- Dependencies: 263
-- Data for Name: turma
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5528 (class 0 OID 25212)
-- Dependencies: 276
-- Data for Name: turma_aluno
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5470 (class 0 OID 24748)
-- Dependencies: 218
-- Data for Name: utilizador
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.utilizador (id, email, password, username, nome)
VALUES (23, 'mlmaunze@gmail.com', '$2a$10$kuT/TLRdpmuzl2i4NP6NJO2XZFyf3sRYyRoo41InwSiyKE/8YsTC.', 'mmaunze',
        'Meldo Leonardo Maunze')
ON CONFLICT DO NOTHING
;

INSERT INTO public.utilizador (id, email, password, username, nome)
VALUES (24, 'jagostinho@bci.co.mz', '$2a$10$a/97gUgeQ3oaEwR98YakB.UXhhtZE7tq/F7oG/W3wkx6o.NR4TbjS', 'jagostinho',
        'Joana Joao Agostinho')
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5471 (class 0 OID 24754)
-- Dependencies: 219
-- Data for Name: utilizador_role
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--

INSERT INTO public.utilizador_role (utilizador, role)
VALUES (23, 12)
ON CONFLICT DO NOTHING
;

INSERT INTO public.utilizador_role (utilizador, role)
VALUES (23, 14)
ON CONFLICT DO NOTHING
;

INSERT INTO public.utilizador_role (utilizador, role)
VALUES (24, 12)
ON CONFLICT DO NOTHING
;



--
-- TOC entry 5544 (class 0 OID 25411)
-- Dependencies: 292
-- Data for Name: veiculo
;
 Type: TABLE DATA
;
 Schema: public
;
 Owner: postgres
--


--
-- TOC entry 5642 (class 0 OID 0)
-- Dependencies: 222
-- Name: area_cientifica_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.area_cientifica_id_seq', 1, false)
;



--
-- TOC entry 5643 (class 0 OID 0)
-- Dependencies: 305
-- Name: ativo_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.ativo_id_seq', 1, false)
;



--
-- TOC entry 5644 (class 0 OID 0)
-- Dependencies: 289
-- Name: aula_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.aula_id_seq', 1, false)
;



--
-- TOC entry 5645 (class 0 OID 0)
-- Dependencies: 281
-- Name: avaliacao_aluno_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.avaliacao_aluno_id_seq', 1, false)
;



--
-- TOC entry 5646 (class 0 OID 0)
-- Dependencies: 279
-- Name: avaliacao_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.avaliacao_id_seq', 1, false)
;



--
-- TOC entry 5647 (class 0 OID 0)
-- Dependencies: 230
-- Name: cargo_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.cargo_id_seq', 36, true)
;



--
-- TOC entry 5648 (class 0 OID 0)
-- Dependencies: 242
-- Name: categoria_financeira_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.categoria_financeira_id_seq', 1, false)
;



--
-- TOC entry 5649 (class 0 OID 0)
-- Dependencies: 274
-- Name: comunicados_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.comunicados_id_seq', 1, false)
;



--
-- TOC entry 5650 (class 0 OID 0)
-- Dependencies: 309
-- Name: contrato_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.contrato_id_seq', 1, false)
;



--
-- TOC entry 5651 (class 0 OID 0)
-- Dependencies: 240
-- Name: departamento_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.departamento_id_seq', 13, true)
;



--
-- TOC entry 5652 (class 0 OID 0)
-- Dependencies: 297
-- Name: despesa_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.despesa_id_seq', 1, false)
;



--
-- TOC entry 5653 (class 0 OID 0)
-- Dependencies: 317
-- Name: detalhe_relatorio_financeiro_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.detalhe_relatorio_financeiro_id_seq', 1, false)
;



--
-- TOC entry 5654 (class 0 OID 0)
-- Dependencies: 264
-- Name: disciplina_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.disciplina_id_seq', 7, true)
;



--
-- TOC entry 5655 (class 0 OID 0)
-- Dependencies: 252
-- Name: distrito_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.distrito_id_seq', 149, true)
;



--
-- TOC entry 5656 (class 0 OID 0)
-- Dependencies: 293
-- Name: documento_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.documento_id_seq', 1, false)
;



--
-- TOC entry 5657 (class 0 OID 0)
-- Dependencies: 255
-- Name: encarregado_educacao_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.encarregado_educacao_id_seq', 1, false)
;



--
-- TOC entry 5658 (class 0 OID 0)
-- Dependencies: 228
-- Name: estado_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.estado_id_seq', 1, false)
;



--
-- TOC entry 5659 (class 0 OID 0)
-- Dependencies: 311
-- Name: evento_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.evento_id_seq', 1, false)
;



--
-- TOC entry 5660 (class 0 OID 0)
-- Dependencies: 303
-- Name: folha_pagamento_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.folha_pagamento_id_seq', 1, false)
;



--
-- TOC entry 5661 (class 0 OID 0)
-- Dependencies: 319
-- Name: fornecedor_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.fornecedor_id_seq', 1, false)
;



--
-- TOC entry 5662 (class 0 OID 0)
-- Dependencies: 260
-- Name: funcionario_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.funcionario_id_seq', 1, false)
;



--
-- TOC entry 5663 (class 0 OID 0)
-- Dependencies: 277
-- Name: horario_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.horario_id_seq', 1, false)
;



--
-- TOC entry 5664 (class 0 OID 0)
-- Dependencies: 323
-- Name: item_ordem_compra_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.item_ordem_compra_id_seq', 1, false)
;



--
-- TOC entry 5665 (class 0 OID 0)
-- Dependencies: 307
-- Name: manutencao_ativo_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.manutencao_ativo_id_seq', 1, false)
;



--
-- TOC entry 5666 (class 0 OID 0)
-- Dependencies: 268
-- Name: material_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.material_id_seq', 1, false)
;



--
-- TOC entry 5667 (class 0 OID 0)
-- Dependencies: 272
-- Name: matricula_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.matricula_id_seq', 1, false)
;



--
-- TOC entry 5668 (class 0 OID 0)
-- Dependencies: 299
-- Name: orcamento_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.orcamento_id_seq', 1, false)
;



--
-- TOC entry 5669 (class 0 OID 0)
-- Dependencies: 321
-- Name: ordem_compra_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.ordem_compra_id_seq', 1, false)
;



--
-- TOC entry 5670 (class 0 OID 0)
-- Dependencies: 270
-- Name: pagamento_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.pagamento_id_seq', 1, false)
;



--
-- TOC entry 5671 (class 0 OID 0)
-- Dependencies: 313
-- Name: participante_evento_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.participante_evento_id_seq', 1, false)
;



--
-- TOC entry 5672 (class 0 OID 0)
-- Dependencies: 287
-- Name: pauta_final_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.pauta_final_id_seq', 1, false)
;



--
-- TOC entry 5673 (class 0 OID 0)
-- Dependencies: 285
-- Name: pauta_trimestral_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.pauta_trimestral_id_seq', 1, false)
;



--
-- TOC entry 5674 (class 0 OID 0)
-- Dependencies: 283
-- Name: presencas_aluno_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.presencas_aluno_id_seq', 1, false)
;



--
-- TOC entry 5675 (class 0 OID 0)
-- Dependencies: 258
-- Name: professor_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.professor_id_seq', 1, false)
;



--
-- TOC entry 5676 (class 0 OID 0)
-- Dependencies: 250
-- Name: provincia_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.provincia_id_seq', 11, true)
;



--
-- TOC entry 5677 (class 0 OID 0)
-- Dependencies: 295
-- Name: receita_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.receita_id_seq', 1, false)
;



--
-- TOC entry 5678 (class 0 OID 0)
-- Dependencies: 315
-- Name: relatorio_financeiro_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.relatorio_financeiro_id_seq', 1, false)
;



--
-- TOC entry 5679 (class 0 OID 0)
-- Dependencies: 325
-- Name: requisicao_material_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.requisicao_material_id_seq', 1, false)
;



--
-- TOC entry 5680 (class 0 OID 0)
-- Dependencies: 215
-- Name: role_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.role_id_seq', 20, true)
;



--
-- TOC entry 5681 (class 0 OID 0)
-- Dependencies: 232
-- Name: sala_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.sala_id_seq', 1, false)
;



--
-- TOC entry 5682 (class 0 OID 0)
-- Dependencies: 244
-- Name: sector_trabalho_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.sector_trabalho_id_seq', 25, true)
;



--
-- TOC entry 5683 (class 0 OID 0)
-- Dependencies: 236
-- Name: tipo_avaliacao_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_avaliacao_id_seq', 1, true)
;



--
-- TOC entry 5684 (class 0 OID 0)
-- Dependencies: 234
-- Name: tipo_documento_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_documento_id_seq', 60, true)
;



--
-- TOC entry 5685 (class 0 OID 0)
-- Dependencies: 226
-- Name: tipo_estado_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_estado_id_seq', 28, true)
;



--
-- TOC entry 5686 (class 0 OID 0)
-- Dependencies: 327
-- Name: tipo_funcionario_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_funcionario_id_seq', 1, false)
;



--
-- TOC entry 5687 (class 0 OID 0)
-- Dependencies: 246
-- Name: tipo_material_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_material_id_seq', 4, true)
;



--
-- TOC entry 5688 (class 0 OID 0)
-- Dependencies: 248
-- Name: tipo_pagamento_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_pagamento_id_seq', 11, true)
;



--
-- TOC entry 5689 (class 0 OID 0)
-- Dependencies: 224
-- Name: tipo_pessoa_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_pessoa_id_seq', 6, true)
;



--
-- TOC entry 5690 (class 0 OID 0)
-- Dependencies: 220
-- Name: tipo_relatorio_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_relatorio_id_seq', 1, false)
;



--
-- TOC entry 5691 (class 0 OID 0)
-- Dependencies: 238
-- Name: tipo_transacao_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_transacao_id_seq', 2, true)
;



--
-- TOC entry 5692 (class 0 OID 0)
-- Dependencies: 301
-- Name: transacao_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.transacao_id_seq', 1, false)
;



--
-- TOC entry 5693 (class 0 OID 0)
-- Dependencies: 262
-- Name: turma_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.turma_id_seq', 1, false)
;



--
-- TOC entry 5694 (class 0 OID 0)
-- Dependencies: 217
-- Name: utilizador_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.utilizador_id_seq', 24, true)
;



--
-- TOC entry 5695 (class 0 OID 0)
-- Dependencies: 291
-- Name: veiculo_id_seq
;
 Type: SEQUENCE SET
;
 Schema: public
;
 Owner: postgres
--

SELECT pg_catalog.setval('public.veiculo_id_seq', 1, false)
;



--
-- TOC entry 5126 (class 2606 OID 24945)
-- Name: aluno aluno_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.aluno
    ADD CONSTRAINT aluno_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5066 (class 2606 OID 24790)
-- Name: area_cientifica area_cientifica_descricao_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.area_cientifica
    ADD CONSTRAINT area_cientifica_descricao_key UNIQUE (descricao)
;



--
-- TOC entry 5068 (class 2606 OID 36514)
-- Name: area_cientifica area_cientifica_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.area_cientifica
    ADD CONSTRAINT area_cientifica_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5186 (class 2606 OID 36532)
-- Name: ativo ativo_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.ativo
    ADD CONSTRAINT ativo_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5170 (class 2606 OID 36557)
-- Name: aula aula_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.aula
    ADD CONSTRAINT aula_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5162 (class 2606 OID 36636)
-- Name: avaliacao_aluno avaliacao_aluno_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.avaliacao_aluno
    ADD CONSTRAINT avaliacao_aluno_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5160 (class 2606 OID 36589)
-- Name: avaliacao avaliacao_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.avaliacao
    ADD CONSTRAINT avaliacao_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5078 (class 2606 OID 24831)
-- Name: cargo cargo_descricao_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.cargo
    ADD CONSTRAINT cargo_descricao_key UNIQUE (descricao)
;



--
-- TOC entry 5080 (class 2606 OID 36658)
-- Name: cargo cargo_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.cargo
    ADD CONSTRAINT cargo_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5104 (class 2606 OID 24889)
-- Name: categoria_financeira categoria_financeira_descricao_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.categoria_financeira
    ADD CONSTRAINT categoria_financeira_descricao_key UNIQUE (descricao)
;



--
-- TOC entry 5106 (class 2606 OID 36671)
-- Name: categoria_financeira categoria_financeira_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.categoria_financeira
    ADD CONSTRAINT categoria_financeira_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5154 (class 2606 OID 36694)
-- Name: comunicados comunicados_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.comunicados
    ADD CONSTRAINT comunicados_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5190 (class 2606 OID 36726)
-- Name: contrato contrato_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.contrato
    ADD CONSTRAINT contrato_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5098 (class 2606 OID 24878)
-- Name: departamento departamento_descricao_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.departamento
    ADD CONSTRAINT departamento_descricao_key UNIQUE (descricao)
;



--
-- TOC entry 5100 (class 2606 OID 36746)
-- Name: departamento departamento_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.departamento
    ADD CONSTRAINT departamento_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5102 (class 2606 OID 24880)
-- Name: departamento departamento_sigla_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.departamento
    ADD CONSTRAINT departamento_sigla_key UNIQUE (sigla)
;



--
-- TOC entry 5178 (class 2606 OID 36760)
-- Name: despesa despesa_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT despesa_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5198 (class 2606 OID 36786)
-- Name: detalhe_relatorio_financeiro detalhe_relatorio_financeiro_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.detalhe_relatorio_financeiro
    ADD CONSTRAINT detalhe_relatorio_financeiro_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5144 (class 2606 OID 25110)
-- Name: disciplina_aluno disciplina_aluno_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.disciplina_aluno
    ADD CONSTRAINT disciplina_aluno_pkey PRIMARY KEY (disciplina, aluno)
;



--
-- TOC entry 5138 (class 2606 OID 25090)
-- Name: disciplina disciplina_nome_disciplina_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.disciplina
    ADD CONSTRAINT disciplina_nome_disciplina_key UNIQUE (nome_disciplina)
;



--
-- TOC entry 5140 (class 2606 OID 36806)
-- Name: disciplina disciplina_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.disciplina
    ADD CONSTRAINT disciplina_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5124 (class 2606 OID 36854)
-- Name: distrito distrito_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.distrito
    ADD CONSTRAINT distrito_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5174 (class 2606 OID 36890)
-- Name: documento documento_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.documento
    ADD CONSTRAINT documento_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5130 (class 2606 OID 24984)
-- Name: encarregado_aluno encarregado_aluno_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.encarregado_aluno
    ADD CONSTRAINT encarregado_aluno_pkey PRIMARY KEY (encarregado, aluno)
;



--
-- TOC entry 5128 (class 2606 OID 33445)
-- Name: encarregado_educacao encarregado_educacao_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.encarregado_educacao
    ADD CONSTRAINT encarregado_educacao_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5076 (class 2606 OID 36944)
-- Name: estado estado_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5192 (class 2606 OID 37124)
-- Name: evento evento_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT evento_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5184 (class 2606 OID 37149)
-- Name: folha_pagamento folha_pagamento_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.folha_pagamento
    ADD CONSTRAINT folha_pagamento_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5200 (class 2606 OID 37166)
-- Name: fornecedor fornecedor_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.fornecedor
    ADD CONSTRAINT fornecedor_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5134 (class 2606 OID 33749)
-- Name: funcionario funcionario_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5158 (class 2606 OID 37247)
-- Name: horario horario_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.horario
    ADD CONSTRAINT horario_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5204 (class 2606 OID 37281)
-- Name: item_ordem_compra item_ordem_compra_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.item_ordem_compra
    ADD CONSTRAINT item_ordem_compra_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5188 (class 2606 OID 37306)
-- Name: manutencao_ativo manutencao_ativo_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.manutencao_ativo
    ADD CONSTRAINT manutencao_ativo_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5146 (class 2606 OID 25129)
-- Name: material material_nome_material_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.material
    ADD CONSTRAINT material_nome_material_key UNIQUE (nome_material)
;



--
-- TOC entry 5148 (class 2606 OID 37331)
-- Name: material material_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.material
    ADD CONSTRAINT material_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5152 (class 2606 OID 37369)
-- Name: matricula matricula_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.matricula
    ADD CONSTRAINT matricula_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5180 (class 2606 OID 37385)
-- Name: orcamento orcamento_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.orcamento
    ADD CONSTRAINT orcamento_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5202 (class 2606 OID 37402)
-- Name: ordem_compra ordem_compra_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.ordem_compra
    ADD CONSTRAINT ordem_compra_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5150 (class 2606 OID 37432)
-- Name: pagamento pagamento_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT pagamento_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5194 (class 2606 OID 37464)
-- Name: participante_evento participante_evento_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.participante_evento
    ADD CONSTRAINT participante_evento_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5168 (class 2606 OID 37489)
-- Name: pauta_final pauta_final_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pauta_final
    ADD CONSTRAINT pauta_final_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5166 (class 2606 OID 37515)
-- Name: pauta_trimestral pauta_trimestral_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pauta_trimestral
    ADD CONSTRAINT pauta_trimestral_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5164 (class 2606 OID 37547)
-- Name: presencas_aluno presencas_aluno_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.presencas_aluno
    ADD CONSTRAINT presencas_aluno_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5142 (class 2606 OID 25095)
-- Name: professor_disciplina professor_disciplina_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.professor_disciplina
    ADD CONSTRAINT professor_disciplina_pkey PRIMARY KEY (professor, disciplina, classe)
;



--
-- TOC entry 5132 (class 2606 OID 34375)
-- Name: professor professor_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.professor
    ADD CONSTRAINT professor_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5120 (class 2606 OID 24925)
-- Name: provincia provincia_nome_provincia_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.provincia
    ADD CONSTRAINT provincia_nome_provincia_key UNIQUE (nome_provincia)
;



--
-- TOC entry 5122 (class 2606 OID 37616)
-- Name: provincia provincia_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.provincia
    ADD CONSTRAINT provincia_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5176 (class 2606 OID 37629)
-- Name: receita receita_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.receita
    ADD CONSTRAINT receita_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5196 (class 2606 OID 37655)
-- Name: relatorio_financeiro relatorio_financeiro_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.relatorio_financeiro
    ADD CONSTRAINT relatorio_financeiro_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5206 (class 2606 OID 37686)
-- Name: requisicao_material requisicao_material_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.requisicao_material
    ADD CONSTRAINT requisicao_material_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5052 (class 2606 OID 37946)
-- Name: role role_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5082 (class 2606 OID 24840)
-- Name: sala sala_nome_sala_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.sala
    ADD CONSTRAINT sala_nome_sala_key UNIQUE (nome_sala)
;



--
-- TOC entry 5084 (class 2606 OID 37718)
-- Name: sala sala_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.sala
    ADD CONSTRAINT sala_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5108 (class 2606 OID 24898)
-- Name: sector_trabalho sector_trabalho_descricao_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.sector_trabalho
    ADD CONSTRAINT sector_trabalho_descricao_key UNIQUE (descricao)
;



--
-- TOC entry 5110 (class 2606 OID 37731)
-- Name: sector_trabalho sector_trabalho_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.sector_trabalho
    ADD CONSTRAINT sector_trabalho_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5090 (class 2606 OID 24860)
-- Name: tipo_avaliacao tipo_avaliacao_descricao_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_avaliacao
    ADD CONSTRAINT tipo_avaliacao_descricao_key UNIQUE (descricao)
;



--
-- TOC entry 5092 (class 2606 OID 37744)
-- Name: tipo_avaliacao tipo_avaliacao_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_avaliacao
    ADD CONSTRAINT tipo_avaliacao_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5086 (class 2606 OID 24849)
-- Name: tipo_documento tipo_documento_descricao_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_documento
    ADD CONSTRAINT tipo_documento_descricao_key UNIQUE (descricao)
;



--
-- TOC entry 5088 (class 2606 OID 37759)
-- Name: tipo_documento tipo_documento_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_documento
    ADD CONSTRAINT tipo_documento_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5074 (class 2606 OID 37772)
-- Name: tipo_estado tipo_estado_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_estado
    ADD CONSTRAINT tipo_estado_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5208 (class 2606 OID 34863)
-- Name: tipo_funcionario tipo_funcionario_descricao_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_funcionario
    ADD CONSTRAINT tipo_funcionario_descricao_key UNIQUE (descricao)
;



--
-- TOC entry 5210 (class 2606 OID 34666)
-- Name: tipo_funcionario tipo_funcionario_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_funcionario
    ADD CONSTRAINT tipo_funcionario_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5112 (class 2606 OID 24907)
-- Name: tipo_material tipo_material_descricao_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_material
    ADD CONSTRAINT tipo_material_descricao_key UNIQUE (descricao)
;



--
-- TOC entry 5114 (class 2606 OID 37786)
-- Name: tipo_material tipo_material_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_material
    ADD CONSTRAINT tipo_material_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5116 (class 2606 OID 24916)
-- Name: tipo_pagamento tipo_pagamento_descricao_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_pagamento
    ADD CONSTRAINT tipo_pagamento_descricao_key UNIQUE (descricao)
;



--
-- TOC entry 5118 (class 2606 OID 37799)
-- Name: tipo_pagamento tipo_pagamento_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_pagamento
    ADD CONSTRAINT tipo_pagamento_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5070 (class 2606 OID 24799)
-- Name: tipo_pessoa tipo_pessoa_descricao_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_pessoa
    ADD CONSTRAINT tipo_pessoa_descricao_key UNIQUE (descricao)
;



--
-- TOC entry 5072 (class 2606 OID 37812)
-- Name: tipo_pessoa tipo_pessoa_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_pessoa
    ADD CONSTRAINT tipo_pessoa_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5062 (class 2606 OID 24781)
-- Name: tipo_relatorio tipo_relatorio_descricao_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_relatorio
    ADD CONSTRAINT tipo_relatorio_descricao_key UNIQUE (descricao)
;



--
-- TOC entry 5064 (class 2606 OID 37830)
-- Name: tipo_relatorio tipo_relatorio_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_relatorio
    ADD CONSTRAINT tipo_relatorio_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5094 (class 2606 OID 24869)
-- Name: tipo_transacao tipo_transacao_descricao_key
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_transacao
    ADD CONSTRAINT tipo_transacao_descricao_key UNIQUE (descricao)
;



--
-- TOC entry 5096 (class 2606 OID 37843)
-- Name: tipo_transacao tipo_transacao_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.tipo_transacao
    ADD CONSTRAINT tipo_transacao_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5182 (class 2606 OID 37856)
-- Name: transacao transacao_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5156 (class 2606 OID 25216)
-- Name: turma_aluno turma_aluno_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.turma_aluno
    ADD CONSTRAINT turma_aluno_pkey PRIMARY KEY (aluno, turma)
;



--
-- TOC entry 5136 (class 2606 OID 37899)
-- Name: turma turma_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.turma
    ADD CONSTRAINT turma_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5054 (class 2606 OID 24760)
-- Name: utilizador uk95oprqrxvxhsfbyuagaq71ipg
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.utilizador
    ADD CONSTRAINT uk95oprqrxvxhsfbyuagaq71ipg UNIQUE (username)
;



--
-- TOC entry 5056 (class 2606 OID 24762)
-- Name: utilizador ukeougu510uft70icifeafv6cll
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.utilizador
    ADD CONSTRAINT ukeougu510uft70icifeafv6cll UNIQUE (email)
;



--
-- TOC entry 5058 (class 2606 OID 24753)
-- Name: utilizador utilizador_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.utilizador
    ADD CONSTRAINT utilizador_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5060 (class 2606 OID 37957)
-- Name: utilizador_role utilizador_role_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.utilizador_role
    ADD CONSTRAINT utilizador_role_pkey PRIMARY KEY (utilizador, role)
;



--
-- TOC entry 5172 (class 2606 OID 37930)
-- Name: veiculo veiculo_pkey
;
 Type: CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.veiculo
    ADD CONSTRAINT veiculo_pkey PRIMARY KEY (id)
;



--
-- TOC entry 5215 (class 2606 OID 36870)
-- Name: aluno aluno_distrito_nascimento_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.aluno
    ADD CONSTRAINT aluno_distrito_nascimento_fkey FOREIGN KEY (distrito_nascimento) REFERENCES public.distrito (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5216 (class 2606 OID 37060)
-- Name: aluno aluno_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.aluno
    ADD CONSTRAINT aluno_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5217 (class 2606 OID 24946)
-- Name: aluno aluno_id_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.aluno
    ADD CONSTRAINT aluno_id_fkey FOREIGN KEY (id) REFERENCES public.utilizador (id)
;



--
-- TOC entry 5300 (class 2606 OID 37065)
-- Name: ativo ativo_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.ativo
    ADD CONSTRAINT ativo_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5279 (class 2606 OID 36837)
-- Name: aula aula_disciplina_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.aula
    ADD CONSTRAINT aula_disciplina_fkey FOREIGN KEY (disciplina) REFERENCES public.disciplina (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5280 (class 2606 OID 37070)
-- Name: aula aula_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.aula
    ADD CONSTRAINT aula_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5264 (class 2606 OID 33099)
-- Name: avaliacao_aluno avaliacao_aluno_aluno_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.avaliacao_aluno
    ADD CONSTRAINT avaliacao_aluno_aluno_fkey FOREIGN KEY (aluno) REFERENCES public.aluno (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5265 (class 2606 OID 37080)
-- Name: avaliacao_aluno avaliacao_aluno_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.avaliacao_aluno
    ADD CONSTRAINT avaliacao_aluno_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5260 (class 2606 OID 35106)
-- Name: avaliacao avaliacao_aluno_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.avaliacao
    ADD CONSTRAINT avaliacao_aluno_fkey FOREIGN KEY (aluno) REFERENCES public.aluno (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5261 (class 2606 OID 36842)
-- Name: avaliacao avaliacao_disciplina_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.avaliacao
    ADD CONSTRAINT avaliacao_disciplina_fkey FOREIGN KEY (disciplina) REFERENCES public.disciplina (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5262 (class 2606 OID 37075)
-- Name: avaliacao avaliacao_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.avaliacao
    ADD CONSTRAINT avaliacao_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5263 (class 2606 OID 37745)
-- Name: avaliacao avaliacao_tipo_avaliacao_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.avaliacao
    ADD CONSTRAINT avaliacao_tipo_avaliacao_fkey FOREIGN KEY (tipo_avaliacao) REFERENCES public.tipo_avaliacao (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5250 (class 2606 OID 37813)
-- Name: comunicados comunicados_destinatario_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.comunicados
    ADD CONSTRAINT comunicados_destinatario_fkey FOREIGN KEY (destinatario) REFERENCES public.tipo_pessoa (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5251 (class 2606 OID 37085)
-- Name: comunicados comunicados_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.comunicados
    ADD CONSTRAINT comunicados_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5252 (class 2606 OID 33800)
-- Name: comunicados comunicados_responsavel_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.comunicados
    ADD CONSTRAINT comunicados_responsavel_fkey FOREIGN KEY (responsavel) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5304 (class 2606 OID 37090)
-- Name: contrato contrato_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.contrato
    ADD CONSTRAINT contrato_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5305 (class 2606 OID 33805)
-- Name: contrato contrato_responsavel_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.contrato
    ADD CONSTRAINT contrato_responsavel_fkey FOREIGN KEY (responsavel) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5289 (class 2606 OID 36766)
-- Name: despesa despesa_categoria_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT despesa_categoria_fkey FOREIGN KEY (categoria) REFERENCES public.categoria_financeira (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5290 (class 2606 OID 37095)
-- Name: despesa despesa_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT despesa_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5291 (class 2606 OID 33810)
-- Name: despesa despesa_responsavel_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT despesa_responsavel_fkey FOREIGN KEY (responsavel) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5313 (class 2606 OID 37656)
-- Name: detalhe_relatorio_financeiro detalhe_relatorio_financeiro_relatorio_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.detalhe_relatorio_financeiro
    ADD CONSTRAINT detalhe_relatorio_financeiro_relatorio_fkey FOREIGN KEY (relatorio) REFERENCES public.relatorio_financeiro (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5240 (class 2606 OID 25116)
-- Name: disciplina_aluno disciplina_aluno_aluno_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.disciplina_aluno
    ADD CONSTRAINT disciplina_aluno_aluno_fkey FOREIGN KEY (aluno) REFERENCES public.aluno (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5241 (class 2606 OID 36812)
-- Name: disciplina_aluno disciplina_aluno_disciplina_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.disciplina_aluno
    ADD CONSTRAINT disciplina_aluno_disciplina_fkey FOREIGN KEY (disciplina) REFERENCES public.disciplina (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5214 (class 2606 OID 37617)
-- Name: distrito distrito_provincia_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.distrito
    ADD CONSTRAINT distrito_provincia_fkey FOREIGN KEY (provincia) REFERENCES public.provincia (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5283 (class 2606 OID 33815)
-- Name: documento documento_autor_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.documento
    ADD CONSTRAINT documento_autor_fkey FOREIGN KEY (autor) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5284 (class 2606 OID 37100)
-- Name: documento documento_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.documento
    ADD CONSTRAINT documento_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5285 (class 2606 OID 37760)
-- Name: documento documento_tipo_documento_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.documento
    ADD CONSTRAINT documento_tipo_documento_fkey FOREIGN KEY (tipo_documento) REFERENCES public.tipo_documento (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5223 (class 2606 OID 24990)
-- Name: encarregado_aluno encarregado_aluno_aluno_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.encarregado_aluno
    ADD CONSTRAINT encarregado_aluno_aluno_fkey FOREIGN KEY (aluno) REFERENCES public.aluno (id)
;



--
-- TOC entry 5224 (class 2606 OID 33451)
-- Name: encarregado_aluno encarregado_aluno_encarregado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.encarregado_aluno
    ADD CONSTRAINT encarregado_aluno_encarregado_fkey FOREIGN KEY (encarregado) REFERENCES public.encarregado_educacao (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5219 (class 2606 OID 36920)
-- Name: encarregado_educacao encarregado_educacao_distrito_nascimento_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.encarregado_educacao
    ADD CONSTRAINT encarregado_educacao_distrito_nascimento_fkey FOREIGN KEY (distrito_nascimento) REFERENCES public.distrito (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5220 (class 2606 OID 33446)
-- Name: encarregado_educacao encarregado_educacao_id_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.encarregado_educacao
    ADD CONSTRAINT encarregado_educacao_id_fkey FOREIGN KEY (id) REFERENCES public.utilizador (id)
;



--
-- TOC entry 5213 (class 2606 OID 37773)
-- Name: estado estado_tipo_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.estado
    ADD CONSTRAINT estado_tipo_estado_fkey FOREIGN KEY (tipo_estado) REFERENCES public.tipo_estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5306 (class 2606 OID 37136)
-- Name: evento evento_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT evento_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5307 (class 2606 OID 33820)
-- Name: evento evento_responsavel_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT evento_responsavel_fkey FOREIGN KEY (responsavel) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5266 (class 2606 OID 36590)
-- Name: avaliacao_aluno fk37xrlgvf8p0kl8tajttbt9mh8
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.avaliacao_aluno
    ADD CONSTRAINT fk37xrlgvf8p0kl8tajttbt9mh8 FOREIGN KEY (avaliacao) REFERENCES public.avaliacao (id) ON DELETE CASCADE
;



--
-- TOC entry 5211 (class 2606 OID 24768)
-- Name: utilizador_role fk47b6nduvkj7lmjinhlv8s6bto
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.utilizador_role
    ADD CONSTRAINT fk47b6nduvkj7lmjinhlv8s6bto FOREIGN KEY (utilizador) REFERENCES public.utilizador (id)
;



--
-- TOC entry 5318 (class 2606 OID 36955)
-- Name: item_ordem_compra fk7woghxkgm4nqiq1kw1o1avecf
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.item_ordem_compra
    ADD CONSTRAINT fk7woghxkgm4nqiq1kw1o1avecf FOREIGN KEY (estado) REFERENCES public.estado (id) ON DELETE CASCADE
;



--
-- TOC entry 5218 (class 2606 OID 41631)
-- Name: aluno fkbq5nt66xcxxrb7xbujb2ouqjc
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.aluno
    ADD CONSTRAINT fkbq5nt66xcxxrb7xbujb2ouqjc FOREIGN KEY (username) REFERENCES public.utilizador (id)
;



--
-- TOC entry 5221 (class 2606 OID 37732)
-- Name: encarregado_educacao fknhfbxdjf0455kxn9j95r8h4k1
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.encarregado_educacao
    ADD CONSTRAINT fknhfbxdjf0455kxn9j95r8h4k1 FOREIGN KEY (sector_trabalho) REFERENCES public.sector_trabalho (id) ON DELETE CASCADE
;



--
-- TOC entry 5222 (class 2606 OID 36945)
-- Name: encarregado_educacao fko6s10ywoe774dkkjwv4v8hrye
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.encarregado_educacao
    ADD CONSTRAINT fko6s10ywoe774dkkjwv4v8hrye FOREIGN KEY (estado) REFERENCES public.estado (id) ON DELETE CASCADE
;



--
-- TOC entry 5212 (class 2606 OID 37958)
-- Name: utilizador_role fkphtwi61sw0744d9fswd2ehnat
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.utilizador_role
    ADD CONSTRAINT fkphtwi61sw0744d9fswd2ehnat FOREIGN KEY (role) REFERENCES public.role (id)
;



--
-- TOC entry 5229 (class 2606 OID 34902)
-- Name: funcionario fkpsyut9lh7x4v2m2ssd9ypyna
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT fkpsyut9lh7x4v2m2ssd9ypyna FOREIGN KEY (tipo_funcionario) REFERENCES public.tipo_funcionario (id) ON DELETE CASCADE
;



--
-- TOC entry 5255 (class 2606 OID 36950)
-- Name: horario fks5plgjbovrcju2vmdt2ptb0fn
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.horario
    ADD CONSTRAINT fks5plgjbovrcju2vmdt2ptb0fn FOREIGN KEY (estado) REFERENCES public.estado (id) ON DELETE CASCADE
;



--
-- TOC entry 5298 (class 2606 OID 37155)
-- Name: folha_pagamento folha_pagamento_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.folha_pagamento
    ADD CONSTRAINT folha_pagamento_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5299 (class 2606 OID 33825)
-- Name: folha_pagamento folha_pagamento_funcionario_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.folha_pagamento
    ADD CONSTRAINT folha_pagamento_funcionario_fkey FOREIGN KEY (funcionario) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5314 (class 2606 OID 37178)
-- Name: fornecedor fornecedor_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.fornecedor
    ADD CONSTRAINT fornecedor_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5230 (class 2606 OID 37190)
-- Name: funcionario funcionario_area_formacao_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_area_formacao_fkey FOREIGN KEY (area_formacao) REFERENCES public.area_cientifica (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5231 (class 2606 OID 37201)
-- Name: funcionario funcionario_cargo_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_cargo_fkey FOREIGN KEY (cargo) REFERENCES public.cargo (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5232 (class 2606 OID 37212)
-- Name: funcionario funcionario_departamento_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_departamento_fkey FOREIGN KEY (departamento) REFERENCES public.departamento (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5233 (class 2606 OID 37223)
-- Name: funcionario funcionario_distrito_nascimento_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_distrito_nascimento_fkey FOREIGN KEY (distrito_nascimento) REFERENCES public.distrito (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5234 (class 2606 OID 37234)
-- Name: funcionario funcionario_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5235 (class 2606 OID 33750)
-- Name: funcionario funcionario_id_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_id_fkey FOREIGN KEY (id) REFERENCES public.utilizador (id)
;



--
-- TOC entry 5256 (class 2606 OID 37252)
-- Name: horario horario_disciplina_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.horario
    ADD CONSTRAINT horario_disciplina_fkey FOREIGN KEY (disciplina) REFERENCES public.disciplina (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5257 (class 2606 OID 34391)
-- Name: horario horario_professor_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.horario
    ADD CONSTRAINT horario_professor_fkey FOREIGN KEY (professor) REFERENCES public.professor (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5258 (class 2606 OID 37719)
-- Name: horario horario_sala_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.horario
    ADD CONSTRAINT horario_sala_fkey FOREIGN KEY (sala) REFERENCES public.sala (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5259 (class 2606 OID 37900)
-- Name: horario horario_turma_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.horario
    ADD CONSTRAINT horario_turma_fkey FOREIGN KEY (turma) REFERENCES public.turma (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5319 (class 2606 OID 37332)
-- Name: item_ordem_compra item_ordem_compra_material_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.item_ordem_compra
    ADD CONSTRAINT item_ordem_compra_material_fkey FOREIGN KEY (material) REFERENCES public.material (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5320 (class 2606 OID 37403)
-- Name: item_ordem_compra item_ordem_compra_ordem_compra_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.item_ordem_compra
    ADD CONSTRAINT item_ordem_compra_ordem_compra_fkey FOREIGN KEY (ordem_compra) REFERENCES public.ordem_compra (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5301 (class 2606 OID 37311)
-- Name: manutencao_ativo manutencao_ativo_ativo_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.manutencao_ativo
    ADD CONSTRAINT manutencao_ativo_ativo_fkey FOREIGN KEY (ativo) REFERENCES public.ativo (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5302 (class 2606 OID 37320)
-- Name: manutencao_ativo manutencao_ativo_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.manutencao_ativo
    ADD CONSTRAINT manutencao_ativo_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5303 (class 2606 OID 33991)
-- Name: manutencao_ativo manutencao_ativo_responsavel_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.manutencao_ativo
    ADD CONSTRAINT manutencao_ativo_responsavel_fkey FOREIGN KEY (responsavel) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5242 (class 2606 OID 37347)
-- Name: material material_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.material
    ADD CONSTRAINT material_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5243 (class 2606 OID 37787)
-- Name: material material_tipo_material_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.material
    ADD CONSTRAINT material_tipo_material_fkey FOREIGN KEY (tipo_material) REFERENCES public.tipo_material (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5248 (class 2606 OID 34045)
-- Name: matricula matricula_aluno_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.matricula
    ADD CONSTRAINT matricula_aluno_fkey FOREIGN KEY (aluno) REFERENCES public.aluno (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5249 (class 2606 OID 37374)
-- Name: matricula matricula_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.matricula
    ADD CONSTRAINT matricula_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5292 (class 2606 OID 37391)
-- Name: orcamento orcamento_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.orcamento
    ADD CONSTRAINT orcamento_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5293 (class 2606 OID 34084)
-- Name: orcamento orcamento_responsavel_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.orcamento
    ADD CONSTRAINT orcamento_responsavel_fkey FOREIGN KEY (responsavel) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5315 (class 2606 OID 37412)
-- Name: ordem_compra ordem_compra_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.ordem_compra
    ADD CONSTRAINT ordem_compra_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5316 (class 2606 OID 37421)
-- Name: ordem_compra ordem_compra_fornecedor_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.ordem_compra
    ADD CONSTRAINT ordem_compra_fornecedor_fkey FOREIGN KEY (fornecedor) REFERENCES public.fornecedor (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5317 (class 2606 OID 34123)
-- Name: ordem_compra ordem_compra_responsavel_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.ordem_compra
    ADD CONSTRAINT ordem_compra_responsavel_fkey FOREIGN KEY (responsavel) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5244 (class 2606 OID 34148)
-- Name: pagamento pagamento_aluno_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT pagamento_aluno_fkey FOREIGN KEY (aluno) REFERENCES public.aluno (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5245 (class 2606 OID 37440)
-- Name: pagamento pagamento_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT pagamento_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5246 (class 2606 OID 34170)
-- Name: pagamento pagamento_responsavel_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT pagamento_responsavel_fkey FOREIGN KEY (responsavel) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5247 (class 2606 OID 37800)
-- Name: pagamento pagamento_tipo_pagamento_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT pagamento_tipo_pagamento_fkey FOREIGN KEY (tipo_pagamento) REFERENCES public.tipo_pagamento (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5308 (class 2606 OID 37469)
-- Name: participante_evento participante_evento_evento_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.participante_evento
    ADD CONSTRAINT participante_evento_evento_fkey FOREIGN KEY (evento) REFERENCES public.evento (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5309 (class 2606 OID 37818)
-- Name: participante_evento participante_evento_tipo_participante_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.participante_evento
    ADD CONSTRAINT participante_evento_tipo_participante_fkey FOREIGN KEY (tipo_participante) REFERENCES public.tipo_pessoa (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5275 (class 2606 OID 34229)
-- Name: pauta_final pauta_final_aluno_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pauta_final
    ADD CONSTRAINT pauta_final_aluno_fkey FOREIGN KEY (aluno) REFERENCES public.aluno (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5276 (class 2606 OID 37495)
-- Name: pauta_final pauta_final_disciplina_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pauta_final
    ADD CONSTRAINT pauta_final_disciplina_fkey FOREIGN KEY (disciplina) REFERENCES public.disciplina (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5277 (class 2606 OID 37504)
-- Name: pauta_final pauta_final_estado_pauta_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pauta_final
    ADD CONSTRAINT pauta_final_estado_pauta_fkey FOREIGN KEY (estado_pauta) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5278 (class 2606 OID 34396)
-- Name: pauta_final pauta_final_professor_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pauta_final
    ADD CONSTRAINT pauta_final_professor_fkey FOREIGN KEY (professor) REFERENCES public.professor (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5271 (class 2606 OID 34281)
-- Name: pauta_trimestral pauta_trimestral_aluno_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pauta_trimestral
    ADD CONSTRAINT pauta_trimestral_aluno_fkey FOREIGN KEY (aluno) REFERENCES public.aluno (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5272 (class 2606 OID 37523)
-- Name: pauta_trimestral pauta_trimestral_disciplina_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pauta_trimestral
    ADD CONSTRAINT pauta_trimestral_disciplina_fkey FOREIGN KEY (disciplina) REFERENCES public.disciplina (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5273 (class 2606 OID 37534)
-- Name: pauta_trimestral pauta_trimestral_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pauta_trimestral
    ADD CONSTRAINT pauta_trimestral_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5274 (class 2606 OID 34401)
-- Name: pauta_trimestral pauta_trimestral_professor_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.pauta_trimestral
    ADD CONSTRAINT pauta_trimestral_professor_fkey FOREIGN KEY (professor) REFERENCES public.professor (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5267 (class 2606 OID 34337)
-- Name: presencas_aluno presencas_aluno_aluno_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.presencas_aluno
    ADD CONSTRAINT presencas_aluno_aluno_fkey FOREIGN KEY (aluno) REFERENCES public.aluno (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5268 (class 2606 OID 37553)
-- Name: presencas_aluno presencas_aluno_disciplina_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.presencas_aluno
    ADD CONSTRAINT presencas_aluno_disciplina_fkey FOREIGN KEY (disciplina) REFERENCES public.disciplina (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5269 (class 2606 OID 37562)
-- Name: presencas_aluno presencas_aluno_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.presencas_aluno
    ADD CONSTRAINT presencas_aluno_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5270 (class 2606 OID 37910)
-- Name: presencas_aluno presencas_aluno_turma_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.presencas_aluno
    ADD CONSTRAINT presencas_aluno_turma_fkey FOREIGN KEY (turma) REFERENCES public.turma (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5225 (class 2606 OID 37581)
-- Name: professor professor_area_formacao_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.professor
    ADD CONSTRAINT professor_area_formacao_fkey FOREIGN KEY (area_formacao) REFERENCES public.area_cientifica (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5238 (class 2606 OID 36807)
-- Name: professor_disciplina professor_disciplina_disciplina_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.professor_disciplina
    ADD CONSTRAINT professor_disciplina_disciplina_fkey FOREIGN KEY (disciplina) REFERENCES public.disciplina (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5239 (class 2606 OID 34386)
-- Name: professor_disciplina professor_disciplina_professor_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.professor_disciplina
    ADD CONSTRAINT professor_disciplina_professor_fkey FOREIGN KEY (professor) REFERENCES public.professor (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5226 (class 2606 OID 37592)
-- Name: professor professor_distrito_nascimento_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.professor
    ADD CONSTRAINT professor_distrito_nascimento_fkey FOREIGN KEY (distrito_nascimento) REFERENCES public.distrito (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5227 (class 2606 OID 37603)
-- Name: professor professor_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.professor
    ADD CONSTRAINT professor_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5228 (class 2606 OID 34376)
-- Name: professor professor_id_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.professor
    ADD CONSTRAINT professor_id_fkey FOREIGN KEY (id) REFERENCES public.utilizador (id)
;



--
-- TOC entry 5286 (class 2606 OID 37635)
-- Name: receita receita_categoria_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.receita
    ADD CONSTRAINT receita_categoria_fkey FOREIGN KEY (categoria) REFERENCES public.categoria_financeira (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5287 (class 2606 OID 37644)
-- Name: receita receita_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.receita
    ADD CONSTRAINT receita_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5288 (class 2606 OID 34495)
-- Name: receita receita_responsavel_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.receita
    ADD CONSTRAINT receita_responsavel_fkey FOREIGN KEY (responsavel) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5310 (class 2606 OID 37666)
-- Name: relatorio_financeiro relatorio_financeiro_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.relatorio_financeiro
    ADD CONSTRAINT relatorio_financeiro_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5311 (class 2606 OID 34530)
-- Name: relatorio_financeiro relatorio_financeiro_responsavel_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.relatorio_financeiro
    ADD CONSTRAINT relatorio_financeiro_responsavel_fkey FOREIGN KEY (responsavel) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5312 (class 2606 OID 37831)
-- Name: relatorio_financeiro relatorio_financeiro_tipo_relatorio_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.relatorio_financeiro
    ADD CONSTRAINT relatorio_financeiro_tipo_relatorio_fkey FOREIGN KEY (tipo_relatorio) REFERENCES public.tipo_relatorio (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5321 (class 2606 OID 37694)
-- Name: requisicao_material requisicao_material_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.requisicao_material
    ADD CONSTRAINT requisicao_material_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5322 (class 2606 OID 37705)
-- Name: requisicao_material requisicao_material_material_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.requisicao_material
    ADD CONSTRAINT requisicao_material_material_fkey FOREIGN KEY (material) REFERENCES public.material (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5323 (class 2606 OID 34586)
-- Name: requisicao_material requisicao_material_requisitor_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.requisicao_material
    ADD CONSTRAINT requisicao_material_requisitor_fkey FOREIGN KEY (requisitor) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5294 (class 2606 OID 37864)
-- Name: transacao transacao_categoria_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_categoria_fkey FOREIGN KEY (categoria) REFERENCES public.categoria_financeira (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5295 (class 2606 OID 37875)
-- Name: transacao transacao_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5296 (class 2606 OID 34775)
-- Name: transacao transacao_responsavel_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_responsavel_fkey FOREIGN KEY (responsavel) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5297 (class 2606 OID 37886)
-- Name: transacao transacao_tipo_transacao_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_tipo_transacao_fkey FOREIGN KEY (tipo_transacao) REFERENCES public.tipo_transacao (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5253 (class 2606 OID 25217)
-- Name: turma_aluno turma_aluno_aluno_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.turma_aluno
    ADD CONSTRAINT turma_aluno_aluno_fkey FOREIGN KEY (aluno) REFERENCES public.aluno (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5254 (class 2606 OID 37905)
-- Name: turma_aluno turma_aluno_turma_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.turma_aluno
    ADD CONSTRAINT turma_aluno_turma_fkey FOREIGN KEY (turma) REFERENCES public.turma (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5236 (class 2606 OID 37919)
-- Name: turma turma_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.turma
    ADD CONSTRAINT turma_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5237 (class 2606 OID 34828)
-- Name: turma turma_professor_responsavel_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.turma
    ADD CONSTRAINT turma_professor_responsavel_fkey FOREIGN KEY (professor_responsavel) REFERENCES public.professor (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5281 (class 2606 OID 37935)
-- Name: veiculo veiculo_estado_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.veiculo
    ADD CONSTRAINT veiculo_estado_fkey FOREIGN KEY (estado) REFERENCES public.estado (id) ON UPDATE CASCADE ON DELETE CASCADE
;



--
-- TOC entry 5282 (class 2606 OID 34853)
-- Name: veiculo veiculo_motorista_fkey
;
 Type: FK CONSTRAINT
;
 Schema: public
;
 Owner: postgres
--

ALTER TABLE ONLY public.veiculo
    ADD CONSTRAINT veiculo_motorista_fkey FOREIGN KEY (motorista) REFERENCES public.funcionario (id) ON UPDATE CASCADE ON DELETE CASCADE
;



-- Completed on 2024-10-05 07:34:38

--
-- PostgreSQL database dump complete
--

