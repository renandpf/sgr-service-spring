# sgr-service
sudo docker build -t renandpf/sgr-service-spring:1.0.0 .
docker tag renandpf/sgr-service-spring:1.0.0 renandpf/sgr-service-spring:1.0.0
docker push renandpf/sgr-service-spring:1.0.0

Para criar pod a partir do arquivo yaml
kubectl apply -f sgr-service-spring-pod.yaml