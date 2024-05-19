package main

import (
	"bufio"
	"context"
	"encoding/json"
	"github.com/gorilla/mux"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"log"
	"net/http"
	"os"
	"strconv"
	"strings"
)

type Fact struct {
	ID        int64  `json:"id"`
	Timestamp int64  `json:"timestamp"`
	Fact      string `json:"fact"`
}

var client *mongo.Client

func main() {
	log.Println("main")
	config := readConfig("config.properties")
	clientOptions := options.Client().ApplyURI(config["mongoURI"])
	client, _ = mongo.Connect(context.Background(), clientOptions)

	log.Println("mongo")

	router := mux.NewRouter()
	router.HandleFunc("/facts", getFacts).Methods("GET")
	router.HandleFunc("/facts/{id}", getFact).Methods("GET")
	log.Println("http sirviendo")
	log.Fatal(http.ListenAndServe(":"+config["port"], router))
}

func getFacts(w http.ResponseWriter, r *http.Request) {
	collection := client.Database("test").Collection("facts")
	log.Println("coleccion1")
	cursor, _ := collection.Find(context.Background(), bson.D{{}})
	log.Println("coleccion2")
	var facts []Fact
	if err := cursor.All(context.Background(), &facts); err != nil {
		log.Fatal(err)
	}
	json.NewEncoder(w).Encode(facts)
	log.Println(facts)
}

func getFact(w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)
	id, _ := strconv.ParseInt(params["id"], 10, 64)
	var fact Fact
	collection := client.Database("test").Collection("facts")
	collection.FindOne(context.Background(), bson.D{{"id", id}}).Decode(&fact)
	json.NewEncoder(w).Encode(fact)
	log.Println(fact)
}

func readConfig(file string) map[string]string {
	config := make(map[string]string)
	f, _ := os.Open(file)
	scanner := bufio.NewScanner(f)
	for scanner.Scan() {
		parts := strings.Split(scanner.Text(), "=")
		config[parts[0]] = parts[1]
	}
	return config
}
