## Установка istio с помощью istioctl
Установка описана здесь:
https://github.com/izhigalko/otus-demo-apigw#%D0%A3%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%BA%D0%B0-Istio-Gateway


## Установка profile-service
0 - перейти в каталог `profile-service`
```bash
cd profile-service
```
1 - добавить helm репо bitnami
```bash
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
```
2 - установить БД с помощью helm
```bash
helm install postgresql bitnami/postgresql
```
3 - применить манифесты
```bash
kubectl apply -f . 
```
