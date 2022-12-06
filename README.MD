## Running locally

    ./gradlew bootRun

## Calling API
### Send message
    curl 'localhost:8080/chat/send/{message}’
### Subscribe to messages (SSE)
    curl 'localhost:8080/chat/read?sender={CONSUMER_GROUP}'

