# Tarea de Aplicaciones Basadas en Contenderores

## Tips para la ejecuccion de la tarea:

1. Para el diseño de la arquitectura pedida:
   a. Resolver y justificar los patrones de comunicación entre los distintos componentes implicados (REST vs Mensajería y el porque de cada elección)
   b. Elegir y justificar el/los mejores patrones de arquitectura para los problemas planteados (consistencia, sincronía, transaccionalidad, que asunciones hacemos y como el patrón elegido ayuda a resolver el problema)
2. Documentación de las APIS: Usar [Swagger Editor](https://editor.swagger.io/) para documentar tanto las APIs Rest como las posibles APIs de eventos.
3. Si la opción elegida es la de crear el docker file sobre el servicio `chuck-says-query` implementado en `Python` en esta carpeta:

   a. Adjuntar el Dockerfile implementado para la aplicación. Este [ejemplo](https://docs.docker.com/language/python/containerize/) puede ser de utilidad

   b. Subir la imagen a DockerHub  

   c. Crear los recursos Kubernetes (deployment, configmap, service) necesarios para exponer el servicio por `LoadBalancer` 

## Formato de Entrega

Se pide como entrega:

1. Documento de arquitectura (debe incluir un digrama grafico)
2. Fichero de definicion Swagger en **formato YAML**
3. Dockerfile del servicio `chuck-says-query` y url de la imagen en Docker Hub
4. Ficheros YAML de los recursos K8s
