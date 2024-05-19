import configparser

from flask import Flask, jsonify
from pymongo import MongoClient

app = Flask(__name__)

config = configparser.ConfigParser()
config.read('config.properties')
mongo_uri = config.get('DatabaseSection', 'mongo.uri')
client = MongoClient(mongo_uri)
db = client.test


@app.route('/facts', methods=['GET'])
def get_facts():
  facts = list(db.facts.find())
  for fact in facts:
    fact['_id'] = str(fact['_id'])
  print(facts)
  return jsonify(facts)


@app.route('/facts/<id>', methods=['GET'])
def get_fact(id):
  fact = db.facts.find_one({'id': int(id)})
  if fact:
    fact['_id'] = str(fact['_id'])
    print(fact)
    return jsonify(fact)
  else:
    return jsonify({"error": "Fact not found"}), 404

if __name__ == '__main__':
  app.run(host='0.0.0.0', port=int(config.get('ServerSection', 'server.port')))
