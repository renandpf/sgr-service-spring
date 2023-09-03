# sgr-service-spring

Este projeto faz parte da entrega do curso de arquitetura da FIAP

SISTEMA DE GESTÃO DE REFEIÇÃO

Trata-se de um sistema de gerenciamento de pedidos. O sistema gere produtos, cliente e pedidos.
Este sistema é o backend e expõe serviços a serem consumidos por um front-end (ou outro serviço) 

TECNOLOGIAS

Neste sistema foram utlizadas as tecnologias:
* Java 18
* Spring Boot

GERAR BUILD

docker build -t renandpf/sgr-service-spring:1.0.0 .
docker tag renandpf/sgr-service-spring:1.0.0 renandpf/sgr-service-spring:1.0.0
docker push renandpf/sgr-service-spring:1.0.0

MÓDULOS

O sistema oi dividido em 3 módulos: Gerencial, Pagamento e Pedido. A intergação entre esses sistemas ocorre via HTTP. Foi seguido essa estratêgia pensando em futuramente separar esses serviços.

ARQUITETURA

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


SUBIR MINIKUBE
minikube start

SUBIR APP (PS: recomendado que o banco de dados deve estar up. Deve rodar nesta ordem)
kubectl apply -f sgr-service-spring-cm.yaml
kubectl apply -f sgr-service-spring-secrets.yaml
kubectl apply -f sgr-service-spring-svc.yaml
kubectl apply -f sgr-service-spring-deploy.yaml
kubectl apply -f sgr-service-spring-metrics.yaml
kubectl apply -f sgr-service-spring-hpa.yaml