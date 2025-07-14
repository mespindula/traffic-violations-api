# Crédito API

API RESTful para Cadastro e Consulta de Infrações de Trânsito.

---

## Tecnologias Utilizadas

- Java 17, Spring Boot, Spring Data JPA, Hibernate
- PostgreSQL
- Flyway (migração de banco)
- Docker e Docker Compose
- JUnit, Mockito (testes automatizados)
- Swagger (documentação da API)

---

## Configuração do Banco de Dados

Antes de executar a aplicação ou rodar as migrações com Flyway, é necessário criar manualmente o banco de dados utilizado pela aplicação.

### Criando o banco `traffic_violations_db` no PostgreSQL

Acessar o terminal do PostgreSQL
```
sudo -u postgres psql
```

Execute o comando SQL:

```sql
CREATE DATABASE traffic_violations_db;
```

## Para realizar Build Maven

Verifique e altere, se necessário, as credenciais da base de dados nos arquivos
 - myFlywayConfig.conf
 - docker-compose.yml (SPRING_DATASOURCE e postgres.environment)
 - application.yml (spring.datasource)

### Gerando o artefato e criando tabelas (previamante populadas)

Execute o comando mvn:

```
mvn clean flyway:migrate package -Dflyway.configFiles=./myFlywayConfig.conf
```

## Criar uma rede Docker externa compartilhada
    sudo docker network create traffic-violations-network

## Para realizar Deploy Docker
    sudo docker-compose down
	sudo docker-compose up --build

## Swagger
	http://localhost:8080/swagger-ui/index.html

## Docker
	https://hub.docker.com/r/mespindula/traffic-violations-api

## Para obter Token
```
	POST auth/login Content-Type: application/json
    {
     username = admin
     password = 123456
    }
```