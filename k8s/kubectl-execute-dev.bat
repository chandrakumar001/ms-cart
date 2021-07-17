kubectl apply -f 01-k8s-deployment.yaml
kubectl apply -f 02-k8s-service.yaml
kubectl apply -f 03-k8s-ingress.yaml
kubectl apply -f 04-k8s-network-policy.yaml
kubectl apply -f 05-k8s-hpa.yaml
kubectl delete configmap  ms-cart-configmap
kubectl create configmap  ms-cart-configmap  --from-env-file=06-env-config-dev.properties
pause
