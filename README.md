# sgr-service-spring

Este projeto faz parte da entrega do curso de arquitetura da FIAP

**SISTEMA DE GESTÃO DE REFEIÇÃO**

Trata-se de um sistema de gerenciamento de pedidos. O sistema gere produtos, cliente e pedidos.
Este sistema é o backend e expõe serviços a serem consumidos por um front-end (ou outro serviço)

**TECNOLOGIAS**

Neste sistema foram utlizadas as tecnologias:

* Java 18
* Spring Boot
* Maven
* Docker
* Kubernect

**MÓDULOS**

O sistema oi dividido em 3 módulos: Gerencial, Pagamento e Pedido. A intergação entre esses sistemas ocorre via HTTP. Foi seguido essa estratêgia pensando em futuramente separar esses serviços.

**ARQUITETURA**

O projeto foi desenvolvido usando Arquitetura Limpa.

Cada módulo do sistema seguem a mesma estrutura. Essa estrutura visa separa as responsabilidades de processamento de um fluxo de execução. São elas:

* adapter
   - **external** *(contêm as implementações (adptadores) para acesso a sistemas exteriores)*
   - **repository** *(contêm as implementações (adptadores) para acesso a repositório de dados (database, etc))*
   - **web** *(API: contêm as implementações (adptadores) para receber dados de sistemas exteriores e assim realizar processamentos)*

* core
   - **controller** *(contêm serviços de entrada de dados para realização de processamento (por outras camadas) )*
   - **domain** *(também chamada de "entity" ou entidades, contêm toda a regra de negócio do sistema. Cada classe Representa as entidades de negócio, qual contêm dados essências e ações (métodos) essências ao negócio)*
   - **dto** *(contêm classes responsáveis para agrupar dados a serem trafegados entre as camadas. Não devem ter regras de negócio)*
   - **exception** *(contêm classes que representam erros e exceções de negócio do sistema)*
   - **gateway** *(contêm as interfaces que determinam o 'contrato' a serem implementados pelos adaptadores (pacote/diretório adapter))*
   - **usecase** *(contêm as classes que representam o processamento, jornada ou fluxo a ser executada por uma entrada (ex: controller). Um usecase utiliza-se de dados contidos nos DTOs para montar objetos de negócio (domain/entidade) e assim realizar processamentos até salvar dados no repositorio e/ou retornar o processamento para o serviço cliente)*

**TESTES AUTOMATIZADOS**

Foram criados diversos testes unitários e testes de componente. Tais testes encontram-se no diretório 'test'.

* JUnit
* Mockito
* Wiremock
* Spring Test

**BANCO DE DADOS**

O banco de dados do sistema está no respositório git: https://github.com/renandpf/sgr-database-mysql

**SISTEMA DE PAGAMENTOS**

O sistema está preparado para trabalhar com o mercado pago (somente com PIX).

No entanto, o sistema está preparado para operar com mais de uma plataforma de pagamento (mercado pago, etc). Para tal foi utilizado SOLID e Factory. No caso de querer adicionar uma nova plataforma, poucas alterações serão necessárias.

**MAVEN: Build do projeto**

Executar o comando:

```sh
mvn clean install
```

**DOCKER: GERAR BUILD**

```sh
docker build -t renandpf/sgr-service-spring:2.0.0 .
```

```sh
docker tag renandpf/sgr-service-spring:2.0.0 renandpf/sgr-service-spring:2.0.0
```

```sh
docker push renandpf/sgr-service-spring:2.0.0
```

**MINIKUBE**

Foi utilizado o minikube para prover serviços K8S

Para subir o minikube:

*minikube start*

**KUBERNETES**

Para execução do projeto usando K8S, deve seguir os passos abaixo.

NOTA: foi utilizado o minikube para testes

NOTA: recomendado que o banco de dados deve estar up (vide readme do projeto do banco de dado). Deve executar nesta ordem

```sh
kubectl apply -f sgr-service-spring-cm.yaml
```

```sh
kubectl apply -f sgr-service-spring-secrets.yaml
```

```sh
kubectl apply -f sgr-service-spring-svc.yaml
```

```sh
kubectl apply -f sgr-service-spring-deploy.yaml
```

```sh
kubectl apply -f sgr-service-spring-metrics.yaml
```

```sh
kubectl apply -f sgr-service-spring-hpa.yaml
```

# DETALHAMENTO DA API

ATENÇÃO: se tiver usando minikube, talvez seja necessário mudar a base da url para uso do ip do minikube.

A porta configurada para uso é a 30880

Os testes realizados funcionaram com a url base: http://192.168.49.2:30880

**PRODUTO - API - ENDPOINTS**

Obter produto pelo id

```sh
curl --location 'http://localhost:8080/sgr/gerencial/produtos/10'
```

Criar produto

```sh
curl --location 'http://localhost:8080/sgr/gerencial/produtos' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "any name",
    "valor": 500,
    "categoria": "LANCHE"
}'
```

Alterar produto

```sh
curl --location --request PUT 'http://localhost:8080/sgr/gerencial/produtos/10' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "any name - ALTERADOaaaaaaaa",
    "valor": 1,
    "categoria": "LANCHE"
}'
```

Deletar produto

```sh
curl --location --request DELETE 'http://localhost:8080/sgr/gerencial/produtos/8'
```

Obter produto por categoria

```sh
curl --location 'http://localhost:8080/sgr/gerencial/categorias/LANCHE/produtos'
```

**CLIENTE - API - ENDPOINTS**

Obter cliente por id

```sh
curl --location 'http://localhost:8080/sgr/gerencial/clientes/2'
```

Cadastrar Cliente

```sh
curl --location 'http://localhost:8080/sgr/gerencial/clientes' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "any name",
    "cpf": "123123",
    "email": "anymail.com"
}'
```

Obter cliente por CPF

```sh
curl --location 'http://localhost:8080/sgr/gerencial/clientes/cpf/123123'
```

Obter Cliente por email

```sh
curl --location 'http://localhost:8080/sgr/gerencial/clientes/email/anymail.com'
```

Alterar cliente

```sh
curl --location --request PUT 'http://localhost:8080/sgr/gerencial/clientes/2' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "any nameaaaa",
    "cpf": "123123",
    "email": "anymail.com"
}'
```

**PEDIDO - API - ENDPOINTS**

Obter pedido por ID

```sh
curl --location 'http://localhost:8080/sgr/pedidos/4'
```

Criar Pedido

```json
curl --location 'http://localhost:8080/sgr/pedidos' \
--header 'Content-Type: application/json' \
--data '{
  "observacao": "Sem cebolaaaa",
  "clienteId": 2,
  "itens": [
    {
      "quantidade": 2,
      "produtoId": 8
    },
    {
      "quantidade": 1,
      "produtoId": 10
    }
  ]
}'
```

Obter pedido em andamento

```sh
curl --location 'http://localhost:8080/sgr/pedidos/andamento'
```

Obter pagameto pelo identificador pagameno

```csv
curl --location 'http://localhost:8080/sgr/pedidos/pagamentos/identificiadorPagamentoMock?identificadorPagamento=any'
```

Alterar status do pedido

```sh
curl --location --request PATCH 'http://localhost:8080/sgr/pedidos/4/status' \
--header 'Content-Type: application/json' \
--data '{
  "status": "PAGAMENTO_INVALIDO"
}'
```

**PAGAMENTO - API - ENDPOINTS**

Efetual pagamento

```sh
curl --location 'http://localhost:8080/sgr/pagamentos/efetuar' \
--header 'Content-Type: application/json' \
--data '{
  "pedidoId": 4,
  "forma": "PIX"
}'
```

Notificar pagamento (chamado pela plataforma de pagamento)

```sh
curl --location 'http://localhost:8080/sgr/pagamentos/notificacoes/mercado-pago' \
--header 'Content-Type: application/json' \
--data '{
    "identificador": "1317607491"
}'
```

Obter pagamento pelo identificador de pagamento externo (plataforma de pagamento)

```sh
curl --location 'http://localhost:8080/sgr/pagamentos/identificador-pagamento-externo/identificiadorPagamentoMock'
```