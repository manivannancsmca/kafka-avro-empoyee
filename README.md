
[ Windows Host Machine ]                 [ Docker Containers Bridge Network ]
┌──────────────────────────┐             ┌──────────────────────────────────┐
│  Spring Boot App         │             │  Apache Kafka Broker             │
│  (Port 8082)             │             │  (Port 29092 internal)           │
│                          │             │  (Port 9092 external to host)    │
│  - Producer (REST API) ──┼───Produce───┼─►                                │
│  - Consumer (Async)    ◄─┼───Consume───┼─┤  - Topic:                      │
└────────────┬─────────────┘             │    employee-mutations-topic      │
             │                           └─────────────────▲────────────────┘
         HTTP Post                                         │
        (Register)                                    Sync Schemas
             │                                             │
             ▼                                             ▼
┌──────────────────────────┐             ┌──────────────────────────────────┐
│  Confluent Registry      │             │  MySQL Database                  │
│  (Port 8081)             │             │  (Port 3306)                     │
│                          │             │                                  │
│  - Stores employee.avsc  │             │  - Table: employees              │
└──────────────────────────┘             └──────────────────────────────────┘


