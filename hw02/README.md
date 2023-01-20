url: http://arch.homework/actuator/health
ответ:
```json
{"status":"UP","groups":["liveness","readiness"]}
```

Как сделать сервис доступным на хосте:
1 - включить аддон ingress-nginx
```bash
minikube addons enable ingress
```
2 - применить манифесты
```bash
kubectl apply -f . 
```
3 - добавить в /etc/hosts, minikube ip - получить ip
```bash
minikube ip
```    



