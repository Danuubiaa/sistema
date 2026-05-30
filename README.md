# Sistema de Gerenciamento de Tarefas

Sistema desenvolvido em Java via console para gerenciamento de tarefas.

## Funcionalidades
- Login
- Cadastro de usuários
- Cadastro de tarefas
- Alteração de status
- Relatórios simples

## Plano de Testes

| ID   | Cenário                  | Resultado Esperado         |
|------|--------------------------|----------------------------|
| CT01 | Login válido             | Login realizado com sucesso|
| CT02 | Login inválido           | Exibir erro                |
| CT03 | Login vazio              | Exibir erro                |
| CT04 | Cadastrar tarefa         | Tarefa cadastrada          |
| CT05 | Alterar status da tarefa | Status alterado            |

## Registro de Bugs

| ID     | Problema                        | Severidade |
|--------|---------------------------------|------------|
| BUG01  | Classes não acessíveis no teste | Alta       |
| BUG02  | pom.xml sem tag de fechamento   | Alta       |

## Tecnologias
- Java 21
- Maven 3.9.16
- JUnit 5.10.2