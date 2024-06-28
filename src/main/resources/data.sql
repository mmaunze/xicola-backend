/********************
Areas Cientificas
******************/

INSERT INTO area_cientifica (descricao) VALUES
('Administração'),
('Pedagogia'),
('Ciências Biológicas'),
('Ciências Exatas'),
('Matemática'),
('Letras'),
('História'),
('Geografia'),
('Filosofia'),
('Sociologia'),
('Física'),
('Química'),
('Educação Física'),
('Artes Plásticas'),
('Música'),
('Teatro'),
('Tecnologia da Informação'),
('Engenharia'),
('Psicologia'),
('Serviço Social'),
('Direito'),
('Comunicação e Marketing'),
('Línguas Estrangeiras'),
('Biblioteconomia'),
('Gestão Escolar'),
('Recursos Humanos'),
('Nutrição'),
('Medicina'),
('Enfermagem'),
('Contabilidade'),
('Economia'),
('Arquitetura'),
('Agronomia'),
('Veterinária'),
('Farmácia'),
('Odontologia'),
('Biomedicina'),
('Ciências Ambientais'),
('Engenharia Civil'),
('Engenharia Elétrica'),
('Engenharia Mecânica'),
('Engenharia de Produção'),
('Engenharia Química'),
('Engenharia de Alimentos'),
('Engenharia de Materiais'),
('Engenharia de Minas'),
('Engenharia de Computação'),
('Design Gráfico'),
('Design de Interiores'),
('Design de Produto'),
('Moda'),
('Relações Internacionais'),
('Turismo'),
('Hotelaria'),
('Gastronomia'),
('Educação Especial'),
('Fonoaudiologia'),
('Fisioterapia'),
('Terapia Ocupacional'),
('Relações Públicas'),
('Jornalismo'),
('Publicidade e Propaganda'),
('Ciências Políticas'),
('Antropologia'),
('Arqueologia'),
('Zootecnia'),
('Meteorologia'),
('Oceanografia'),
('Geologia'),
('Estatística'),
('Ciência da Computação'),
('Sistemas de Informação'),
('Redes de Computadores'),
('Inteligência Artificial'),
('Big Data e Análise de Dados');


/********************
Provincias
******************/

INSERT INTO provincia (nome_provincia) VALUES('Maputo provincia');
INSERT INTO provincia (nome_provincia) VALUES('Maputo Cidade');
INSERT INTO provincia (nome_provincia) VALUES('Gaza');
INSERT INTO provincia (nome_provincia) VALUES('Inhambane');
INSERT INTO provincia (nome_provincia) VALUES('Sofala');
INSERT INTO provincia (nome_provincia) VALUES('Manica');
INSERT INTO provincia (nome_provincia) VALUES('Tete');
INSERT INTO provincia (nome_provincia) VALUES('Zambézia');
INSERT INTO provincia (nome_provincia) VALUES('Nampula');
INSERT INTO provincia (nome_provincia) VALUES('Cabo Delgado');
INSERT INTO provincia (nome_provincia) VALUES('Niassa');

/********************
distritos
******************/


/*-------------------------------------- Distritos de Maputo ----------------------------------------*/

INSERT INTO distrito (nome_distrito, provincia) VALUES('Boane', (SELECT id FROM provincia WHERE nome_provincia='Maputo provincia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Magude', (SELECT id FROM provincia WHERE nome_provincia='Maputo provincia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Manhiça', (SELECT id FROM provincia WHERE nome_provincia='Maputo provincia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Marracuene', (SELECT id FROM provincia WHERE nome_provincia='Maputo provincia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Matutuíne', (SELECT id FROM provincia WHERE nome_provincia='Maputo provincia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Moamba', (SELECT id FROM provincia WHERE nome_provincia='Maputo provincia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Namaacha', (SELECT id FROM provincia WHERE nome_provincia='Maputo provincia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Matola', (SELECT id FROM provincia WHERE nome_provincia='Maputo provincia'));

/*-------------------------------------- Distritos de Maputo Cidade ----------------------------------------*/
INSERT INTO distrito (nome_distrito, provincia) VALUES('KaMpfumo', (SELECT id FROM provincia WHERE nome_provincia='Maputo Cidade'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('KaMubukwana', (SELECT id FROM provincia WHERE nome_provincia='Maputo Cidade'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('KaTembe', (SELECT id FROM provincia WHERE nome_provincia='Maputo Cidade'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Machava', (SELECT id FROM provincia WHERE nome_provincia='Maputo Cidade'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mahanje', (SELECT id FROM provincia WHERE nome_provincia='Maputo Cidade'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Malhangalene', (SELECT id FROM provincia WHERE nome_provincia='Maputo Cidade'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mavalane', (SELECT id FROM provincia WHERE nome_provincia='Maputo Cidade'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Nlhamankulu', (SELECT id FROM provincia WHERE nome_provincia='Maputo Cidade'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Zimpeto', (SELECT id FROM provincia WHERE nome_provincia='Maputo Cidade'));

/*-------------------------------------- Distritos de Gaza ----------------------------------------*/
INSERT INTO distrito (nome_distrito, provincia) VALUES('Bilene', (SELECT id FROM provincia WHERE nome_provincia='Gaza'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Chibuto', (SELECT id FROM provincia WHERE nome_provincia='Gaza'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Chicualacuala', (SELECT id FROM provincia WHERE nome_provincia='Gaza'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Chigubo', (SELECT id FROM provincia WHERE nome_provincia='Gaza'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Chókwè', (SELECT id FROM provincia WHERE nome_provincia='Gaza'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Guijá', (SELECT id FROM provincia WHERE nome_provincia='Gaza'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Limpopo', (SELECT id FROM provincia WHERE nome_provincia='Gaza'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mabalane', (SELECT id FROM provincia WHERE nome_provincia='Gaza'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Manjacaze', (SELECT id FROM provincia WHERE nome_provincia='Gaza'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Massangena', (SELECT id FROM provincia WHERE nome_provincia='Gaza'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Massingir', (SELECT id FROM provincia WHERE nome_provincia='Gaza'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Xai-Xai', (SELECT id FROM provincia WHERE nome_provincia='Gaza'));

/*-------------------------------------- Distritos de Inhambane ----------------------------------------*/
INSERT INTO distrito (nome_distrito, provincia) VALUES('Funhalouro', (SELECT id FROM provincia WHERE nome_provincia='Inhambane'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Govuro', (SELECT id FROM provincia WHERE nome_provincia='Inhambane'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Homoíne', (SELECT id FROM provincia WHERE nome_provincia='Inhambane'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Inharrime', (SELECT id FROM provincia WHERE nome_provincia='Inhambane'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Inhassoro', (SELECT id FROM provincia WHERE nome_provincia='Inhambane'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Jangamo', (SELECT id FROM provincia WHERE nome_provincia='Inhambane'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mabote', (SELECT id FROM provincia WHERE nome_provincia='Inhambane'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Massinga', (SELECT id FROM provincia WHERE nome_provincia='Inhambane'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Morrumbene', (SELECT id FROM provincia WHERE nome_provincia='Inhambane'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Panda', (SELECT id FROM provincia WHERE nome_provincia='Inhambane'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Vilankulo', (SELECT id FROM provincia WHERE nome_provincia='Inhambane'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Zavala', (SELECT id FROM provincia WHERE nome_provincia='Inhambane'));

/*-------------------------------------- Distritos de Sofala ----------------------------------------*/
INSERT INTO distrito (nome_distrito, provincia) VALUES('Beira', (SELECT id FROM provincia WHERE nome_provincia='Sofala'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Búzi', (SELECT id FROM provincia WHERE nome_provincia='Sofala'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Caia', (SELECT id FROM provincia WHERE nome_provincia='Sofala'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Chemba', (SELECT id FROM provincia WHERE nome_provincia='Sofala'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Cheringoma', (SELECT id FROM provincia WHERE nome_provincia='Sofala'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Chibabava', (SELECT id FROM provincia WHERE nome_provincia='Sofala'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Dondo', (SELECT id FROM provincia WHERE nome_provincia='Sofala'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Gorongosa', (SELECT id FROM provincia WHERE nome_provincia='Sofala'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Machanga', (SELECT id FROM provincia WHERE nome_provincia='Sofala'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Marínguè', (SELECT id FROM provincia WHERE nome_provincia='Sofala'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Marromeu', (SELECT id FROM provincia WHERE nome_provincia='Sofala'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Muanza', (SELECT id FROM provincia WHERE nome_provincia='Sofala'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Nhamatanda', (SELECT id FROM provincia WHERE nome_provincia='Sofala'));

/*-------------------------------------- Distritos de Manica ----------------------------------------*/

INSERT INTO distrito (nome_distrito, provincia) VALUES('Bárue', (SELECT id FROM provincia WHERE nome_provincia='Manica'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Chimoio', (SELECT id FROM provincia WHERE nome_provincia='Manica'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Gondola', (SELECT id FROM provincia WHERE nome_provincia='Manica'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Guro', (SELECT id FROM provincia WHERE nome_provincia='Manica'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Macate', (SELECT id FROM provincia WHERE nome_provincia='Manica'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Machaze', (SELECT id FROM provincia WHERE nome_provincia='Manica'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Macossa', (SELECT id FROM provincia WHERE nome_provincia='Manica'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Manica', (SELECT id FROM provincia WHERE nome_provincia='Manica'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mossurize', (SELECT id FROM provincia WHERE nome_provincia='Manica'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Sussundenga', (SELECT id FROM provincia WHERE nome_provincia='Manica'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Tambara', (SELECT id FROM provincia WHERE nome_provincia='Manica'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Vanduzi', (SELECT id FROM provincia WHERE nome_provincia='Manica'));

/*-------------------------------------- Distritos de Tete ----------------------------------------*/

INSERT INTO distrito (nome_distrito, provincia) VALUES('Angónia', (SELECT id FROM provincia WHERE nome_provincia='Tete'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Cahora-Bassa', (SELECT id FROM provincia WHERE nome_provincia='Tete'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Changara', (SELECT id FROM provincia WHERE nome_provincia='Tete'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Chifunde', (SELECT id FROM provincia WHERE nome_provincia='Tete'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Chiuta', (SELECT id FROM provincia WHERE nome_provincia='Tete'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Marávia', (SELECT id FROM provincia WHERE nome_provincia='Tete'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mágoè', (SELECT id FROM provincia WHERE nome_provincia='Tete'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Moatize', (SELECT id FROM provincia WHERE nome_provincia='Tete'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mutarara', (SELECT id FROM provincia WHERE nome_provincia='Tete'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Tete', (SELECT id FROM provincia WHERE nome_provincia='Tete'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Tsangano', (SELECT id FROM provincia WHERE nome_provincia='Tete'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Zumbo', (SELECT id FROM provincia WHERE nome_provincia='Tete'));

/*-------------------------------------- Distritos de Zambézia ----------------------------------------*/

INSERT INTO distrito (nome_distrito, provincia) VALUES('Alto Molócuè', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Chinde', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Gilé', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Gurué', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Ile', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Inhassunge', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Lugela', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Maganja da Costa', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Milange', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mocuba', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mopeia', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Morrumbala', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Namacurra', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Namarroi', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Nicoadala', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Pebane', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Quelimane', (SELECT id FROM provincia WHERE nome_provincia='Zambézia'));

/* -------------------------------------- Distritos de Nampula ---------------------------------------- */

INSERT INTO distrito (nome_distrito, provincia) VALUES('Angoche', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Eráti', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Ilha de Moçambique', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Lalaua', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Larde', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Liúpo', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Malema', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Meconta', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mecubúri', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Memba', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mogincual', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mogovolas', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Moma', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Monapo', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mossuril', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Muecate', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Murrupula', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Nacala-a-Velha', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Nacala Porto', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Nacaroa', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Nampula', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Rapale', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Ribaué', (SELECT id FROM provincia WHERE nome_provincia='Nampula'));

/* -------------------------------------- Distritos de Cabo Delgado ---------------------------------------- */

INSERT INTO distrito (nome_distrito, provincia) VALUES('Ancuabe', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Balama', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Chiúre', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Ibo', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Macomia', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mecúfi', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Meluco', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mocímboa da Praia', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Montepuez', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mueda', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Muidumbe', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Namuno', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Nangade', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Palma', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Pemba-Metuge', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Quissanga', (SELECT id FROM provincia WHERE nome_provincia='Cabo Delgado'));

/*-------------------------------------- Distritos de Niassa ----------------------------------------*/

INSERT INTO distrito (nome_distrito, provincia) VALUES('Chimbonila', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Lago', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Lichinga', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Majune', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mandimba', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Marrupa', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Maúa', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mavago', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mecanhelas', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Mecula', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Metarica', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Muembe', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Ngauma', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Nipepe', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));
INSERT INTO distrito (nome_distrito, provincia) VALUES('Sanga', (SELECT id FROM provincia WHERE nome_provincia='Niassa'));


/********************
tipos de estado
******************/
INSERT INTO tipo_estado ( descricao ) VALUES( 'Estado Geral');
INSERT INTO tipo_estado ( descricao ) VALUES( 'Estado de Pessoas');
INSERT INTO tipo_estado ( descricao ) VALUES( 'Estado de Estudante');
INSERT INTO tipo_estado ( descricao ) VALUES( 'Estado de Professor');
INSERT INTO tipo_estado ( descricao ) VALUES( 'Estado de Avaliação');
INSERT INTO tipo_estado ( descricao ) VALUES( 'Estado de Equipamento');
INSERT INTO tipo_estado ( descricao ) VALUES( 'Estado de Evento');

/********************
estados 
******************/



/********************
Tipos de Pessoas
******************/
INSERT INTO tipo_pessoa (descricao ) VALUES('Professor');
INSERT INTO tipo_pessoa (descricao ) VALUES('Aluno');
INSERT INTO tipo_pessoa (descricao ) VALUES('Funcionario');
INSERT INTO tipo_pessoa (descricao ) VALUES('Fornecedor');
INSERT INTO tipo_pessoa (descricao ) VALUES('Encarregado de Educação');
INSERT INTO tipo_pessoa (descricao ) VALUES('Director');



/********************
Tipos de Documentos
******************/

INSERT INTO tipo_documento (descricao) VALUES ('Boletim Escolar');
INSERT INTO tipo_documento (descricao) VALUES ('Histórico Escolar');
INSERT INTO tipo_documento (descricao) VALUES ('Certificado de Conclusão');
INSERT INTO tipo_documento (descricao) VALUES ('Declaração de Matrícula');
INSERT INTO tipo_documento (descricao) VALUES ('Plano de Aula');
INSERT INTO tipo_documento (descricao) VALUES ('Calendário Escolar');
INSERT INTO tipo_documento (descricao) VALUES ('Horário das Aulas');
INSERT INTO tipo_documento (descricao) VALUES ('Lista de Presença');
INSERT INTO tipo_documento (descricao) VALUES ('Relatórios de Desempenho');
INSERT INTO tipo_documento (descricao) VALUES ('Pauta de Notas');
INSERT INTO tipo_documento (descricao) VALUES ('Ficha de Inscrição');
INSERT INTO tipo_documento (descricao) VALUES ('Contrato de Prestação de Serviços Educacionais');
INSERT INTO tipo_documento (descricao) VALUES ('Regulamento Interno');
INSERT INTO tipo_documento (descricao) VALUES ('Relatórios de Gestão');
INSERT INTO tipo_documento (descricao) VALUES ('Atas de Reuniões');
INSERT INTO tipo_documento (descricao) VALUES ('Plano de Desenvolvimento Escolar');
INSERT INTO tipo_documento (descricao) VALUES ('Relatórios de Inspeção Escolar');
INSERT INTO tipo_documento (descricao) VALUES ('Registros de Funcionários');
INSERT INTO tipo_documento (descricao) VALUES ('Planilhas de Controle Financeiro');
INSERT INTO tipo_documento (descricao) VALUES ('Recibos de Pagamento');
INSERT INTO tipo_documento (descricao) VALUES ('Notas Fiscais');
INSERT INTO tipo_documento (descricao) VALUES ('Relatórios de Orçamento');
INSERT INTO tipo_documento (descricao) VALUES ('Balancetes');
INSERT INTO tipo_documento (descricao) VALUES ('Demonstrativos Financeiros');
INSERT INTO tipo_documento (descricao) VALUES ('Planilhas de Custos');
INSERT INTO tipo_documento (descricao) VALUES ('Plano de Orçamento Anual');
INSERT INTO tipo_documento (descricao) VALUES ('Relatórios de Auditoria');
INSERT INTO tipo_documento (descricao) VALUES ('Contratos de Trabalho');
INSERT INTO tipo_documento (descricao) VALUES ('Folha de Pagamento');
INSERT INTO tipo_documento (descricao) VALUES ('Relatórios de Frequência');
INSERT INTO tipo_documento (descricao) VALUES ('Avaliações de Desempenho');
INSERT INTO tipo_documento (descricao) VALUES ('Planos de Carreira');
INSERT INTO tipo_documento (descricao) VALUES ('Relatórios de Treinamento e Desenvolvimento');
INSERT INTO tipo_documento (descricao) VALUES ('Registros de Férias');
INSERT INTO tipo_documento (descricao) VALUES ('Circulares Internas');
INSERT INTO tipo_documento (descricao) VALUES ('Avisos e Comunicados');
INSERT INTO tipo_documento (descricao) VALUES ('Boletins Informativos');
INSERT INTO tipo_documento (descricao) VALUES ('Cartas e Memorandos');
INSERT INTO tipo_documento (descricao) VALUES ('E-mails Institucionais');
INSERT INTO tipo_documento (descricao) VALUES ('Licenças de Funcionamento');
INSERT INTO tipo_documento (descricao) VALUES ('Regulamentos do Ministério da Educação');
INSERT INTO tipo_documento (descricao) VALUES ('Acordos e Convênios');
INSERT INTO tipo_documento (descricao) VALUES ('Termos de Ajuste de Conduta');
INSERT INTO tipo_documento (descricao) VALUES ('Registros de Processos Judiciais');
INSERT INTO tipo_documento (descricao) VALUES ('Projetos Pedagógicos');
INSERT INTO tipo_documento (descricao) VALUES ('Plano de Curso');
INSERT INTO tipo_documento (descricao) VALUES ('Material Didático');
INSERT INTO tipo_documento (descricao) VALUES ('Provas e Avaliações');
INSERT INTO tipo_documento (descricao) VALUES ('Trabalhos de Alunos');
INSERT INTO tipo_documento (descricao) VALUES ('Projetos de Pesquisa');
INSERT INTO tipo_documento (descricao) VALUES ('Registros de Atividades Extracurriculares');
INSERT INTO tipo_documento (descricao) VALUES ('Plantas e Projetos Arquitetônicos');
INSERT INTO tipo_documento (descricao) VALUES ('Relatórios de Manutenção');
INSERT INTO tipo_documento (descricao) VALUES ('Registros de Inventário de Equipamentos');
INSERT INTO tipo_documento (descricao) VALUES ('Contratos de Serviço de Manutenção');
INSERT INTO tipo_documento (descricao) VALUES ('Fichas Médicas dos Alunos');
INSERT INTO tipo_documento (descricao) VALUES ('Registros de Vacinação');
INSERT INTO tipo_documento (descricao) VALUES ('Relatórios de Acidentes');
INSERT INTO tipo_documento (descricao) VALUES ('Planos de Evacuação');
INSERT INTO tipo_documento (descricao) VALUES ('Relatórios de Inspeção de Segurança');




/********************
cargos
******************/


INSERT INTO cargo (descricao) VALUES ('Diretor(a)');
INSERT INTO cargo (descricao) VALUES ('Vice-Diretor(a)');
INSERT INTO cargo (descricao) VALUES ('Coordenador(a) Pedagógico(a)');
INSERT INTO cargo (descricao) VALUES ('Secretário(a) Escolar');
INSERT INTO cargo (descricao) VALUES ('Assistente Administrativo');
INSERT INTO cargo (descricao) VALUES ('Gestor(a) Financeiro(a)');
INSERT INTO cargo (descricao) VALUES ('Contador(a)');
INSERT INTO cargo (descricao) VALUES ('Bibliotecário(a)');
INSERT INTO cargo (descricao) VALUES ('Recepcionista');
INSERT INTO cargo (descricao) VALUES ('Inspetor(a) de Alunos');
INSERT INTO cargo (descricao) VALUES ('Professor(a)');
INSERT INTO cargo (descricao) VALUES ('Orientador(a) Educacional');
INSERT INTO cargo (descricao) VALUES ('Supervisor(a) Educacional');
INSERT INTO cargo (descricao) VALUES ('Auxiliar de Sala de Aula');
INSERT INTO cargo (descricao) VALUES ('Monitor(a) de Laboratório');
INSERT INTO cargo (descricao) VALUES ('Zelador(a)');
INSERT INTO cargo (descricao) VALUES ('Servente');
INSERT INTO cargo (descricao) VALUES ('Cozinheiro(a)');
INSERT INTO cargo (descricao) VALUES ('Auxiliar de Cozinha');
INSERT INTO cargo (descricao) VALUES ('Segurança');
INSERT INTO cargo (descricao) VALUES ('Motorista');
INSERT INTO cargo (descricao) VALUES ('Técnico(a) de Informática');
INSERT INTO cargo (descricao) VALUES ('Manutencionista');
INSERT INTO cargo (descricao) VALUES ('Psicólogo(a) Escolar');
INSERT INTO cargo (descricao) VALUES ('Assistente Social');
INSERT INTO cargo (descricao) VALUES ('Mediador(a) Escolar');
INSERT INTO cargo (descricao) VALUES ('Nutricionista');
INSERT INTO cargo (descricao) VALUES ('Enfermeiro(a) Escolar');
INSERT INTO cargo (descricao) VALUES ('Gestor(a) de Tecnologia da Informação');
INSERT INTO cargo (descricao) VALUES ('Gestor(a) de Manutenção');
INSERT INTO cargo (descricao) VALUES ('Gestor(a) de Recursos Humanos');
INSERT INTO cargo (descricao) VALUES ('Gestor(a) de Logística');
INSERT INTO cargo (descricao) VALUES ('Gestor(a) de Compras');
INSERT INTO cargo (descricao) VALUES ('Responsável pelo Marketing Escolar');
INSERT INTO cargo (descricao) VALUES ('Responsável pela Comunicação Institucional');
INSERT INTO cargo (descricao) VALUES ('Designer Gráfico');



/********************
tipos de avaliacao
******************/
INSERT INTO tipo_avaliacao (descricao) VALUES('Exame Final');



/********************
tipo de receitas
******************/
INSERT INTO tipo_transacao (descricao) VALUES('Receita');
INSERT INTO tipo_transacao (descricao) VALUES('Despesa');






/********************
departamentos
******************/

INSERT INTO departamento (descricao,sigla) values ('Departamento Pegagogico','DAP');
INSERT INTO departamento (descricao,sigla) values ('Departamento Finaceiro','DAF');
INSERT INTO departamento (descricao,sigla) values ('Departamento de Recursos Humanos','DRH');
INSERT INTO departamento (descricao,sigla) values ('Departamento Desportivos','DDP');






/********************
sectores de trabalho
******************/

INSERT INTO sector_trabalho (descricao) VALUES
('Saúde'),
('Educação'),
('Comércio'),
('Serviços'),
('Indústria'),
('Agricultura'),
('Construção Civil'),
('Tecnologia da Informação'),
('Finanças'),
('Transporte e Logística'),
('Administração Pública'),
('Forças Armadas'),
('Segurança Privada'),
('Turismo e Hotelaria'),
('Artes e Entretenimento'),
('Comunicação e Marketing'),
('Engenharia'),
('Recursos Humanos'),
('Consultoria'),
('Pesquisa e Desenvolvimento'),
('Energia e Meio Ambiente'),
('Jurídico'),
('Imobiliário'),
('Telecomunicações'),
('Bancário');






/********************
tipos de material
******************/
insert into tipo_material (descricao) VALUES ('Equipamento informatico');
insert into tipo_material (descricao) VALUES ('Carteiras');
insert into tipo_material (descricao) VALUES ('Material didactico');
insert into tipo_material (descricao) VALUES ('Material de Escritorio');


/********************
Tipos de Pagamentos
******************/
INSERT INTO tipo_pagamento (descricao) VALUES ('Pagamento de Mensalidade Escolar');
INSERT INTO tipo_pagamento (descricao) VALUES ('Pagamento de Matrícula');
INSERT INTO tipo_pagamento (descricao) VALUES ('Pagamento de Material Didático');
INSERT INTO tipo_pagamento (descricao) VALUES ('Pagamento de Refeições');
INSERT INTO tipo_pagamento (descricao) VALUES ('Pagamento de Transporte Escolar');
INSERT INTO tipo_pagamento (descricao) VALUES ('Pagamento de Atividades Extracurriculares');
INSERT INTO tipo_pagamento (descricao) VALUES ('Pagamento de Eventos e Excursões');
INSERT INTO tipo_pagamento (descricao) VALUES ('Pagamento de Taxas Administrativas');
INSERT INTO tipo_pagamento (descricao) VALUES ('Pagamento de Seguro Escolar');
INSERT INTO tipo_pagamento (descricao) VALUES ('Pagamento de Doações');
INSERT INTO tipo_pagamento (descricao) VALUES ('Outros');





/********************
disciplinas
******************/
insert into disciplina (nome_disciplina) values ('Portugues');
insert into disciplina (nome_disciplina) values ('Matematica');
insert into disciplina (nome_disciplina) values ('Quimica');
insert into disciplina (nome_disciplina) values ('Fisica');
insert into disciplina (nome_disciplina) values ('Historia');
insert into disciplina (nome_disciplina) values ('Geografia');
insert into disciplina (nome_disciplina) values ('Ingles');






/********************

******************/





/********************

******************/







/********************

******************/



