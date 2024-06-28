# Sistema de Gestão Escolar - API

Este projeto é uma API desenvolvida em Spring Boot para um sistema de gestão escolar completo. A API fornece todas as funcionalidades necessárias para gerenciar uma escola, atuando como um ERP específico para instituições de ensino. O frontend deste sistema é desenvolvido em Vue.js e está localizado em outro repositório.

## Funcionalidades

### Gestão de Usuários

- **Cadastro e autenticação de usuários**: administração, professores, alunos e responsáveis.
- **Gerenciamento de permissões e funções**: controle detalhado das permissões de acesso com base nas funções dos usuários.
- **Perfis de usuários**: gerenciamento de informações pessoais, fotos de perfil, dados de contato e preferências.

### Gestão Acadêmica

- **Matrículas e transferências**:
  - Cadastro de novos alunos.
  - Processamento de transferências entre escolas.
  - Histórico acadêmico dos alunos.

- **Gestão de turmas e cursos**:
  - Criação e gerenciamento de turmas.
  - Alocação de alunos e professores às turmas.
  - Configuração de horários de aula.

- **Calendário acadêmico e eventos escolares**:
  - Planificação e visualização do calendário escolar.
  - Gestão de eventos escolares como reuniões de pais, feiras científicas e outros eventos.
  - Notificações automáticas de eventos.

### Gestão de Notas e Frequência

- **Lançamento e consulta de notas**:
  - Inserção de notas por professores.
  - Consulta de notas por alunos e responsáveis.
  - Emissão de boletins e relatórios de desempenho.

- **Controle de frequência**:
  - Registo de presença/ausência de alunos.
  - Relatórios de frequência para alunos e turmas.
  - Notificações para responsáveis sobre faltas.

### Gestão de Disciplinas e Currículo

- **Cadastro e gerenciamento de disciplinas**:
  - Criação e edição de disciplinas.
  - Atribuição de disciplinas aos cursos e turmas.
  - Definição de pré-requisitos e equivalências.

- **Definição de currículo escolar**:
  - Estruturação de currículos para diferentes níveis de ensino.
  - Acompanhamento do progresso do currículo.

- **Planificação e organização de horários de aulas**:
  - Criação de horários de aula.
  - Ajuste e reprogramação de horários conforme necessário.
  - Visualização de horários por alunos, professores e turmas.

### Comunicação

- **Envio de mensagens e notificações**:
  - Mensagens internas entre usuários.
  - Notificações automáticas e manuais para eventos, notas, faltas, etc.
  - Integração com e-mail e SMS para notificações externas.

- **Mural de avisos e comunicados**:
  - Publicação de avisos gerais para toda a escola.
  - Comunicados específicos para turmas ou grupos de usuários.

### Gestão Financeira

- **Controle de mensalidades e pagamentos**:
  - Cadastro de planos de pagamento e mensalidades.
  - Emissão e acompanhamento de faturas.
  - Registo de pagamentos e débitos.

- **Emissão de boletos e faturas**:
  - Geração automática de boletos bancários.
  - Envio de faturas por e-mail.

- **Relatórios financeiros**:
  - Relatórios detalhados de receitas e despesas.
  - Análise financeira por período, turma, etc.

### Biblioteca

- **Gerenciamento de acervo bibliográfico**:
  - Cadastro e catalogação de livros e outros materiais.
  - Consulta ao acervo disponível.

- **Empréstimo e devolução de livros**:
  - Registo de empréstimos e devoluções.
  - Controle de prazos e multas por atraso.

- **Consulta de disponibilidade de títulos**:
  - Sistema de busca e reserva de livros.

### Infraestrutura e Manutenção

- **Gestão de patrimônio escolar**:
  - Cadastro e controle de bens patrimoniais.
  - Registo de localização e estado dos bens.

- **Solicitações de manutenção e reparos**:
  - Registo e acompanhamento de pedidos de manutenção.
  - Prioritização e histórico de reparos.

- **Controle de inventário**:
  - Gestão de estoque de materiais e equipamentos.
  - Relatórios de consumo e necessidade de reposição.


## Tecnologias Utilizadas

- **Spring Boot**: Framework principal para o desenvolvimento da API.
- **JPA/Hibernate**: Para gerenciamento de base de dados.
- **Spring Security**: Para autenticação e autorização.
- **JWT**: Para gerenciamento de tokens de segurança.
- **MySQL**: Como base de dados relacional.
- **Maven**: Para gerenciamento de dependências.
- **Swagger**: Para documentação da API.

## Pré-requisitos

- **Java 17** ou superior.
- **Maven 3.6.3** ou superior.
- **PostgreSQL 16**.

## Configuração e Instalação

### Clone o Repositório

```bash
git clone https://github.com/mmaunze/xicola.git
cd xicola
```

### Configuração da Base de Dados

Crie um base de dados no MySQL ou PostgreSQL. Configure o arquivo `application.properties` com as informações de conexão da base de dados:

```
#PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/xixola
spring.datasource.username=usuario
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

```

### Executando a Aplicação

Com o Maven configurado, você pode executar a aplicação usando o seguinte comando:

```
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

### Documentação da API

A documentação da API pode ser acessada através do Swagger em `http://localhost:8080/swagger-ui.html`.

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/xicola/xicola/
│   │   ├── controller/   # Controladores REST
│   │   ├── model/        # Modelos de dados
│   │   ├── repository/   # Repositórios JPA
│   │   ├── service/      # Serviços de negócio
│   │   ├── config/       # Configurações de segurança e outros
│   │   └── XicolaApplication.java  # Classe principal
│   └── resources/
│       ├── application.properties  # Configurações da aplicação
│       ├── data.sql                # Dados iniciais (opcional)
│       └── schema.sql              # Esquema da base de dados (opcional)
└── test/
    └── java/com/xicola/xicola  # Testes unitários e de integração
```

## Contribuição

Contribuições são bem-vindas! Siga os passos abaixo para contribuir com o projeto:

1. Fork o repositório.
2. Crie uma nova branch (`git checkout -b minha-branch`).
3. Faça suas alterações e comente (`git commit -m 'Minha contribuição'`).
4. Envie para o repositório remoto (`git push origin minha-branch`).
5. Abra um Pull Request.

## Licença

Este projeto está licenciado por Mefema Systems. Consulte o arquivo [LICENSE](LICENSE.md) para mais informações.

---
