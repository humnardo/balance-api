# Balance API


Este é uma API de gerenciamento de saldo. A API permite criar contas, verificar saldos, fazer depósitos, saques e transferências entre contas.

## Funcionalidades

- Resetar o estado do sistema.
- Obter saldo de uma conta.
- Criar uma conta com saldo inicial.
- Depositar em uma conta existente.
- Sacar de uma conta existente.
- Transferir saldo entre contas.

## Tecnologias Utilizadas

- Java 11
- Spring Boot 2.5
- Maven

## Configuração do Ambiente

### Pré-requisitos

- JDK 11 ou superior
- Maven
- IntelliJ IDEA (ou outra IDE)

### Passos para Configuração

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd seu-repositorio

2. Importe o projeto no IntelliJ IDEA como um projeto Maven.
3. Construa o projeto:
    ```bash
   mvn clean install

4. Execute a aplicação:

  •No IntelliJ, execute a classe BalanceApiApplication.


# Uso da API
## Endpoints
#### Resetar
  - URL /reset
  - mETODO `POST`
  - Resposta `200 OK`

#### Obter Saldo
  - URL `/balance?account_id={id}`
  - Metodo `GET`
  - Resposta (conta existente):` 200 {balance}`
  - Resposta (conta invexistente):` 404 0`

#### Criar Conta com Saldo Inicial
  - URL `/event`
  - Metodo `POST`
  - Body da requisicao
 ```json
{
  "type": "deposit",
  "destination": "100",
  "amount": 10
}
```
- Resposta: 201 {"destination": {"id": "100", "balance": 10}}

#### Depositar em conta existente
  - URL `/event`
  - Metodo `POST`
  - Body da requisicao
 ```json
{
  "type": "deposit",
  "destination": "100",
  "amount": 10
}
```
- Resposta: 201 {"destination": {"id": "100", "balance": 20}}

#### Sacar de conta existente
  - URL `/event`
  - Metodo `POST`
  - Body da requisicao
 ```json
{
  "type": "withdraw",
  "origin": "100",
  "amount": 5
}
```
- Resposta: 201 {"origin": {"id": "100", "balance": 15}}


#### Transferir entre contas
  - URL `/event`
  - Metodo `POST`
  - Body da requisicao
 ```json
{
  "type": "transfer",
  "origin": "100",
  "amount": 15,
  "destination": "300"
}
```
- Resposta: 201 {"origin": {"id": "100", "balance": 0}, "destination": {"id": "300", "balance": 15}}

# Para testar com postman:
## 1. Reset 

- Método: `POST`
- URL: http://localhost:8080/reset

## 2. Obter saldo 

- Método: `GET`
- URL: http://localhost:8080/balance?account_id=1234 (deixando claro, que essa conta nao existe, apenas para seguir o passo a passo da API, depois que for criado a conta, precisa somente trocar o id, ex: http://localhost:8080/balance?account_id=100)

## 3. Criar Conta com Saldo Inicial

- Método: `POST`
- URL: http://localhost:8080/event
- Body:
 ```json
{
  "type": "deposit",
  "destination": "100",
  "amount": 10
}
```

## 4. Depositar em Conta Existente

- Método: `POST`
- URL: http://localhost:8080/event
- Body:
 ```json
{
  "type": "deposit",
  "destination": "100",
  "amount": 10
}
```


## 5. Sacar de Conta Existente

- Método: `POST`
- URL: http://localhost:8080/event
- Body:
 ```json
{
  "type": "withdraw",
  "origin": "100",
  "amount": 5
}
```

## 6. Sacar de Conta Existente

- Método: `POST`
- URL: http://localhost:8080/event
- Body:
 ```json
{
  "type": "transfer",
  "origin": "100",
  "amount": 15,
  "destination": "300"
}
```

