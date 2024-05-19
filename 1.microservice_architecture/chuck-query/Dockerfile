FROM golang:1.21.5-alpine

WORKDIR /app

COPY go.mod .
COPY go.sum .

RUN go mod download

COPY . .

RUN go build -o main .

EXPOSE 38080

CMD ["./main"]