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
4 - curl для тестирования:
```bash
curl --request POST \
  --url http://arch.homework/user \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "qwerty"
}'
```
```bash
curl --request PUT \
  --url http://arch.homework/user1/3 \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "qwerty1"
}'
```
```bash
curl --request DELETE \
  --url http://arch.homework/user/1
```
```bash
curl --request GET \
  --url http://arch.homework/user/1
```