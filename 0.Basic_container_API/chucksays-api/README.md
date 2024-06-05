# Basic API Example

En este ejemplo básico implementamos un API REST que expone un recurso `/chuk-says` por POST devolviendo una linea de texto con un fact de Chuck Norris Aleatorio.

Además del código de la propia aplicación encontrareis:

## Dockerfile

el `Dockerfile` de la misma que se encuentra subido a mi repositorio dockerhub ` ogomezso/chuck-says-api:0.0.1`

## Recursos Kubernetes

### Publicación de la aplicación

En la carpeta `k8s_resources` encontrareis los ficheros de deployment necesarios para publicar este servicio en la distribución de K8s de vuestra elección

1. **Deployment**: Publicación *stateless* de la aplicación en si misma basándonos en la imagen del servicio, la manera de escalado del mismo seria a través del `replicaset` correspondiente a su deployment.
2. **ConfigMap**: Recurso de tipo default en la que crearemos un fichero `config.properties` que montaremos en un volumen en cada POD.

### Exposición de la aplicación

Para exponer el API al exterior del cluster K8s tenemos dos opciones:

1. Servicio tipo `LoadBalancer` descrito en la documentación del módulo, expone una ip externa para el servicio accesible desde redes externas al cluster k8s (`chuck-says-api-service-lb.yaml`)
2. Exposición tipo `Ingress`: En este tipo de servicio debemos tener un `IngressController` a nivel cluster K8s que será el encargado de, a modo de gateway, exponer una única IP y puerto al exterior de manera que:

   a. El servicio se expondrá via `ClusterIP` es decir exponiendo un puerto interno a la red K8s (solo accesible desde dentro) (`chuck-says-api-service-cip.yaml`)

   b. Creamos un recurso tipo `Ingress` donde haremos el mapeo de recursos REST (endpoints) al servicio y puerto adecuado (el expuesto por el servicio ClusterIP) (`chuck-says-api-ingress.yaml)

## Instrucciones para levantar el entorno

Situados en la carpeta `k8s_resources` vamos a aplicar **todos** los recursos descritos anteriormente:

`kubectl apply -f .`

de modo que los tendremos creados dentro del namespace `default`

### Accediendo al servicio en K3d

#### A través de Ingress externo

K3D se despliega por defecto con un servicio de Ingress Controller `traeffik` pero no es capaz de exponer la red docker asociada a el en el sistema host por lo que al crear el cluster haremos un mapeo de puertos:

`k3d cluster create mycluster --api-port 6550 -p "8081:80@loadbalancer" --agents 2`

en este caso exponemos el loadbalancer (traeffik) de K3d por el puerto 8081 en la red del sistema host.

De este modo, una vez creados los recursos, k8s podremos acceder al servicio:

en sistemas basados en UNIX (MAC y Linux):

`curl -X POST http://localhost:8081/chuck-says`

en PowerShell:

`curl -Uri http://172.19.0.2:8080/chuck-says -Method POST`

#### Desde dentro de la red K8s

En la carpeta `k8s_resources` hay un recurso adicional llamado  `busybox` que levanta un POD con herramientas útiles para el diagnostico de aplicaciones entre ellas `curl` por tanto podemos usarlo como manera de comprobar si nuestro servicio esta funcionando correctamente.

Por tanto una vez creados todos los servicios K8s de la carpeta `k8s_resources` podemos llamar al servicio de la siguiente manera:

por el servicio clusterIP, usando la DNS interna del mismo:

`kubectl exec -it busybox -- curl -X POST http://chuck-says-api-cip:80/chuck-says`

por el servicio LoadBalancer:

`kubectl exec -it busybox -- curl -X POST http://chuck-says-api-lb:8080/chuck-says`


> Notese la diferencia de puertos, el servicio cluster IP está exponiendo hacer fuera del contenedor el puerto 80 mientras que el loadbalancer el 8080. 
> El servicio ingress y el mapeo hacia el puerto que expone la aplicación siguen funcionando gracias al portmapping de los recursos de red asociados.
