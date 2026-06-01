
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
   
   Check the health and port mapping of all running containers:
   docker-compose ps

   Verify that the Confluent Schema Registry is live and accepting external traffic from your Windows host:
   curl http://localhost:8081/subjects

---

## Step 2: Compile & Run the Spring Boot App

1. Clean your workspace and compile generated code mappings:
   ```bash
   mvn clean compile

2. Launch your local Spring Boot application instance:
3. ```bash
   mvn spring-boot:run

## Step 3: End-to-End Verification Pipeline

1. Fire an Employee Creation (POST Payload)
   ```bash
   curl -X POST http://localhost:8082/api/v1/employees \
     -H "Content-Type: application/json" \
     -d "{\"firstName\":\"Alex\",\"lastName\":\"Mercer\",\"age\":34,\"dob\":\"1992-11-04\",\"email\":\"alex.mercer@company.com\"}"

   Expected REST API Response (HTTP 202 Accepted):
   {
    "id": "c1a123bc-8b43-41e3-baee-bb0b018b34f0",
    "firstName": "Alex",
    "lastName": "Mercer",
    "age": 34,
    "dob": "1992-11-04",
    "email": "alex.mercer@company.com"
  }

## Step 4: Check Persistence (MySQL Verification)

  ```bash
  docker exec -it mysql-db mysql -u appuser -papppassword -e "USE employee_db; SELECT * FROM employees;"
