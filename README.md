# API Transacao

API REST desenvolvida em Java com Spring Boot para registrar transacoes em memoria e consultar estatisticas das transacoes realizadas em uma janela de tempo.

O projeto foi criado como parte de um desafio tecnico e expoe endpoints para:

- cadastrar transacoes;
- remover todas as transacoes registradas;
- calcular estatisticas de transacoes recentes;
- consultar documentacao interativa via Swagger UI;
- expor informacoes de saude e metricas via Spring Actuator.

## Tecnologias

- Java 21
- Spring Boot 3.5.16
- Spring Web
- Spring Boot Actuator
- Springdoc OpenAPI/Swagger UI
- Lombok
- Maven
- Docker e Docker Compose
- JUnit 5, Mockito e Spring Boot Test

## Requisitos

Para executar localmente:

- JDK 21 ou superior
- Maven Wrapper incluido no projeto

Para executar com Docker:

- Docker
- Docker Compose

## Como executar localmente

No terminal, na raiz do projeto, execute:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
.\mvnw.cmd spring-boot:run
```

A aplicacao sobe na porta configurada em `src/main/resources/application.yaml`:

```text
http://localhost:8082
```

## Como executar com Docker

Gere o pacote da aplicacao:

```bash
./mvnw clean package
```

No Windows:

```bash
.\mvnw.cmd clean package
```

Em seguida, suba os containers:

```bash
docker compose up --build
```

O `Dockerfile` expoe a porta `8080` e o `docker-compose.yaml` publica `8080:8080`.

> Observacao: a configuracao atual da aplicacao define `server.port: 8082`. Caso execute via Docker sem sobrescrever essa porta, ajuste o mapeamento do compose para `8080:8082` ou altere `server.port` para `8080`.

## Documentacao da API

Com a aplicacao em execucao, acesse:

```text
http://localhost:8082/
```

A especificacao OpenAPI fica disponivel em:

```text
http://localhost:8082/v3/api-docs
```

## Endpoints

### Criar transacao

```http
POST /transacao
Content-Type: application/json
```

Corpo da requisicao:

```json
{
  "valor": 123.45,
  "dataHora": "2026-06-29T13:49:00Z"
}
```

Regras de validacao:

- `valor` nao pode ser negativo;
- `dataHora` nao pode estar no futuro.

Respostas:

- `201 Created`: transacao registrada com sucesso;
- `422 Unprocessable Entity`: transacao com valor negativo ou data/hora futura;
- `400 Bad Request`: requisicao invalida;
- `500 Internal Server Error`: erro interno.

### Remover transacoes

```http
DELETE /transacao
```

Remove todas as transacoes armazenadas em memoria.

Respostas:

- `200 OK`: transacoes removidas com sucesso;
- `400 Bad Request`: requisicao invalida;
- `500 Internal Server Error`: erro interno.

### Consultar estatisticas

```http
GET /estatistica
```

Por padrao, calcula as estatisticas das transacoes dos ultimos 60 segundos.

Tambem e possivel informar a janela de busca, em segundos:

```http
GET /estatistica?intervalorDeBusca=120
```

> Observacao: o parametro implementado no controller se chama `intervalorDeBusca`.

Resposta:

```json
{
  "count": 1,
  "sum": 123.45,
  "avg": 123.45,
  "min": 123.45,
  "max": 123.45
}
```

Campos retornados:

- `count`: quantidade de transacoes consideradas;
- `sum`: soma dos valores;
- `avg`: media dos valores;
- `min`: menor valor;
- `max`: maior valor.

Quando nao existem transacoes no periodo informado, todos os valores retornam zerados.

## Actuator

Os endpoints do Actuator expostos pela configuracao sao:

```text
http://localhost:8082/actuator/health
http://localhost:8082/actuator/info
http://localhost:8082/actuator/metrics
```

## Exemplos com cURL

Criar uma transacao:

```bash
curl -X POST http://localhost:8082/transacao \
  -H "Content-Type: application/json" \
  -d '{"valor": 100.50, "dataHora": "2026-06-29T13:49:00Z"}'
```

Consultar estatisticas dos ultimos 60 segundos:

```bash
curl http://localhost:8082/estatistica
```

Consultar estatisticas dos ultimos 120 segundos:

```bash
curl "http://localhost:8082/estatistica?intervalorDeBusca=120"
```

Remover todas as transacoes:

```bash
curl -X DELETE http://localhost:8082/transacao
```

## Testes

Execute a suite de testes com:

```bash
./mvnw test
```

No Windows:

```bash
.\mvnw.cmd test
```

Os testes cobrem controllers e services, incluindo:

- cadastro de transacao;
- rejeicao de valor negativo;
- rejeicao de data/hora futura;
- limpeza das transacoes;
- calculo de estatisticas;
- retorno zerado quando nao ha transacoes.

## Estrutura do projeto

```text
src/main/java/br/com/itau/api_transacao
|-- ApiTransacaoApplication.java
|-- business/services
|   |-- EstatisticasService.java
|   `-- TransacaoService.java
|-- controllers
|   |-- EstatisticasController.java
|   |-- TransacaoController.java
|   `-- dtos
|       |-- EstatisticasResponseDTO.java
|       `-- TransacaoRequestDTO.java
`-- infraestructure
    |-- ResponseTimeFilter.java
    `-- exceptions
```

## Observacoes

- As transacoes sao armazenadas em memoria por uma lista mantida pelo `TransacaoService`.
- Ao reiniciar a aplicacao, as transacoes registradas sao perdidas.
- O filtro `ResponseTimeFilter` imprime no console metodo HTTP, URL, status e tempo de resposta de cada requisicao.
