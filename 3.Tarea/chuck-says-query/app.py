from flask import Flask, jsonify, request
from pymongo import MongoClient
from bson.objectid import ObjectId
import configparser

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
  fact = db.facts.find_one({'_id': ObjectId(id)})
  fact['_id'] = str(fact['_id'])
  print(fact)
  return jsonify(fact)

if  __ame__ == '__main__':
  app.run(host='0.0.0.0', port=int(config.get('ServerSection', 'server.port')))
