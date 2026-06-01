
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


# Employee Management Service (Spring Boot 3.x + Kafka Avro)

An enterprise-grade Employee Management system utilizing Spring Boot 3.x, Java 21, and Apache Kafka serialized with Apache Avro format backed by Confluent Schema Registry.

## System Pre-requisites
* Windows 10/11 with WSL2 enabled
* Docker Desktop installed and running
* Apache Maven 3.9+
* Java Development Kit (JDK) 21

---

## Step 1: Bootstrap Infrastructure (Docker Setup)

1. Open a terminal in the root directory containing your `docker-compose.yml`.
2. Launch all components in detached mode:
   ```bash
   docker-compose up -d
