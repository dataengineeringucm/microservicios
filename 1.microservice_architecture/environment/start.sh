echo "Levantando entorno"

set -u -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

docker-compose up -d

echo "Esperando 120 sg a que connect levante"

sleep 30

docker-compose exec connect confluent-hub install --no-prompt confluentinc/kafka-connect-jdbc:10.7.4

docker-compose exec connect confluent-hub install --no-prompt  mongodb/kafka-connect-mongodb:1.11.2

docker cp $DIR/mysql/mysql-connector-java-5.1.45.jar connect:/usr/share/confluent-hub-components/confluentinc-kafka-connect-jdbc/lib/mysql-connector-java-5.1.45.jar

docker-compose restart connect
docker-compose restart chuck-command
docker-compose restart chuck-query

echo "Esperando 120 sg a que connect reinicie"

sleep 60

### Creamos un primer fact para que la BBDD se cree correctamente.
curl -X POST http://localhost:28080/chuck-says

$DIR/connect/create-connectors.sh