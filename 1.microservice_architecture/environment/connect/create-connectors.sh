#/bin/bash

curl -d @"connect/facts-outbox.json" -H "Content-Type: application/json" -X POST http://localhost:8083/connectors
curl -d @"connect/facts-query.json" -H "Content-Type: application/json" -X POST http://localhost:8083/connectors