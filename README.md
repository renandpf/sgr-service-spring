# sgr-service

GERAR BUILD
docker build -t renandpf/sgr-service-spring:1.0.0 .
docker tag renandpf/sgr-service-spring:1.0.0 renandpf/sgr-service-spring:1.0.0
docker push renandpf/sgr-service-spring:1.0.0

SUBIR MINIKUBE
minikube start

SUBIR APP (PS: banco de dados deve estar up)
kubectl apply -f sgr-service-spring-cm.yaml
kubectl apply -f sgr-service-spring-secrets.yaml
kubectl apply -f sgr-service-spring-svc.yaml
kubectl apply -f sgr-service-spring-deploy.yaml