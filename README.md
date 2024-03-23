# Four Worktime App

Aplicação MVP para demonstração no Hackathon da FIAP Post Tech - SOAT1.

## TODOs
- [x] Add login endpoint and routes protection
- [x] Add register endpoint (extra)
- [x] Add clock in/clock out endpoint for work time registration endpoint
- [ ] Add work time summary endpoint
- [ ] Add monthly report generation endpoint

## DOC

### 1. Tecnologias
* Java 17
* Spring Boot
* Postgres SQL
* Docker
* [Swagger](http://localhost:8080/swagger-ui/index.html)

### 2. Pre-requisitos para rodar a aplicação
* [Java Version: 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Gradle](https://gradle.org/install/)
* [IDE IntelliJ](https://www.jetbrains.com/idea/)
* [Docker](https://www.docker.com/)

### 3. Para executar localmente com Docker
#### 3.1. Necessário um arquivo .env na raiz do projeto com a seguinte conteúdo:

Para executar localmente com o docker é necessário fazer uma cópia do arquivo .env.example, renomear a
cópia para .env e configurar as variavéis de acordo com o ambiente.

```
DATABASE_USERNAME=username_do_banco  
DATABASE_PASSWORD=password_do_banco
```

#### 3.2. Para buildar, executar as migration e rodar o app no docker pela primeira vez

`docker compose up --build -d`

#### 3.3. Para executar o app com docker após a primeira vez
Após rodar a primeira o comando acima, execute o seguinte comando abaixo para que apenas execute
os containers sem a etapa de build.

`docker compose up -d`

### 4. Testando

#### 4.1 Com insomnia
Para executar as requisições com o insomnia, basta importar o arquivo `fourwoktimeapp.json`
no seu insomnia que a collection estará pronta para ser usada. Detalhe ao usar a porta configurada na aplicação, que por
padrão é a 8080, mas se entrar em conflito será necessário troca-la.

### 4.2 Com HAR
Para executar as requisições com o insomnia, basta importar o arquivo `fourwoktimeapp.har`
no seu insomnia que a collection estará pronta para ser usada. Detalhe ao usar a porta configurada na aplicação, que por
padrão é a 8080, mas se entrar em conflito será necessário troca-la.

### 5. Links Adicionais