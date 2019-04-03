# Arquitetura

Este documento propõe, de forma breve, detalhes acerca da arquitetura proposta para a implementação do sistema Chernobyl Filmes.

## Contexto

### Restrições

Restrições arquiteturais inegociáveis impostas ao sistema por fatores externos. Devem ser contempladas pela arquitetura.

| Nº | Nome | Descrição
| --- | --- | --- 
| RT1 | Web | O sistema deve ser web
| RT2 | Maven |  A aplicação deve utilizar o [Maven](https://maven.apache.org/)
| RT3 | Spring Boot |  A aplicação deve utilizar o framework [Spring Boot](https://spring.io/projects/spring-boot)
| RT4 | Persistência relacional |  A aplicação deve utilizar um mecanismo de persistência relacional


### Necessidades

Necessidades arquiteturais causadas pelos requisitos do sistema. Devem ser contempladas pela arquitetura.

| Nº | Categoria | Nome | Descrição
| --- | --- | --- | --- 
| NE1 | Segurança | Autenticação | Usuários devem ser autenticados (com usuário/email e senha) de forma segura.
| NE2 | Segurança | Controle de acesso | Usuários possuem diferentes papéis que possuem níveis de acesso diferente às funcionalidades do sistema.
| NE3 | Funcionalidade | Páginas web | Usuários devem poder acessar as funcionalidades do sistema através de páginas web.
| NE4 | Funcionalidade | Persistência de dados | Os dados do sistema devem ser persistidos.

---

## Arquitetura proposta

### Tecnologias

Propõe-se a utilização das seguintes tecnologias:

| Nº | Nome | Motivo | Descrição
| --- | --- | --- | ---
| T1 | JDK 1.8+ | - | A plataforma Java é necessária para desenvolvimento do sistema.
| T2 | Maven 3.2+ | *RT2* | Permite automatizar a compilação da aplicação, bem como gerenciar dependências e outras coisas.
| T3 | Spring Boot | *RT3* | Facilita a criação de aplicações Spring com pouca configuração.
| T4 | Spring MVC | *RT1, NE3* | Permite a criação de *servlet web applications* utilizando o padrão arquitetural MVC.
| T5 | JPA | *RT4, NE4* | Simplifica a persistência de dados através do mapeamento objeto-relacional.
| T6 | Hibernate | *RT4, NE4* | Implementação do padrão JPA.
| T7 | MySQL | *RT4, NE4* | Permite persistir dados utilizando SQL com alto desempenho e estabilidade.
| T8 | Spring Data JPA | *RT4, NE4* | Facilita a utilização do JPA, reduzindo a quantidade de boilerplate necessário.
| T9 | Spring Security | *NE1, NE2* | Provê mecanismos de autenticação e controle de acesso.
| T10 | Thymeleaf | *RT1, NE3* | Engine de templates para a "View" do Spring MVC.
| T11 | Tomcat | *RT1* | Container de servlets para a execução do servidor de aplicação Java.

### Componentes

- **Client** – Máquina do usuário responsável por rodar o navegador web.
  - **Navegador** – Responsável por exibir e interagir com as páginas web do sistema.
- **Server** - Máquina responsável por executar o servidor de aplicação. 
  - **Presentation Layer** - Contém os componentes responsáveis por receber as requisições web, acessar a camada de negócio para realizar as operações necessárias e exibir uma página web adequada como resposta.
    - **Controller** - Responsável por receber as requisições HTTP, montar o Model a partir de operações na camada de negócio e retornar a View gerada com tal Model.
    - **Model** - Guarda os dados relevantes à requisição atual que são utilizados para montar a View a ser exibida.
    - **View** - Páginas web (HTML) geradas pelo sistema para exibir o resultado da requisição do usuário.
  - **Business Layer** - Contém os componentes responsáveis por implementar os requisitos de negócio.
  - **Data Layer** - Contém os componentes responsáveis por obter e manipular os dados persistidos.
   - **Banco de dados (SGBD)** - Aplicação responsável por persistir dados e permitir a consulta dos dados persistidos.
