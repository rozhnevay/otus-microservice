## Установка istio с помощью istioctl
```bash
istioctl install 
istioctl operator init
kubectl apply -f .\routes.yaml
```
Установить IstioOperator - перейти в каталог `gateway` и выполнить
```bash
kubectl apply -f istio.yaml
```
Настроить проксирование порта 80 с хостовой машины:
```bash
kubectl edit -n istio-system deployment istio-ingressgateway
```
Добавить:
```
	- containerPort: 80
      protocol: TCP
	  hostPort: 80
```
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

## Установка gateway
