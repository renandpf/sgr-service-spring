# sgr-service

GERAR BUILD
docker build -t renandpf/sgr-service-spring:1.0.0 .
docker tag renandpf/sgr-service-spring:1.0.0 renandpf/sgr-service-spring:1.0.0
docker push renandpf/sgr-service-spring:1.0.0

SUBIR MINIKUBE
minikube start

SUBIR APP (PS: recomendado que o banco de dados deve estar up. Deve rodar nesta ordem)
kubectl apply -f sgr-service-spring-cm.yaml
kubectl apply -f sgr-service-spring-secrets.yaml
kubectl apply -f sgr-service-spring-svc.yaml
kubectl apply -f sgr-service-spring-deploy.yaml
kubectl apply -f sgr-service-spring-metrics.yaml
kubectl apply -f sgr-service-spring-hpa.yaml