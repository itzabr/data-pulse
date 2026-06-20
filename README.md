<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/AWS-Cloud-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white" />
  <img src="https://img.shields.io/badge/Apache_Spark-3.x-E25A1C?style=for-the-badge&logo=apachespark&logoColor=white" />
  <img src="https://img.shields.io/badge/Apache_Kafka-Streaming-231F20?style=for-the-badge&logo=apachekafka&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-Containerized-2496ED?style=for-the-badge&logo=docker&logoColor=white" />
</p>

# 🔬 DataPulse — Real-Time E-Commerce Analytics Pipeline

**DataPulse** is a production-grade, end-to-end data engineering and analytics platform that simulates a high-throughput e-commerce event stream, processes it through a multi-layered AWS data pipeline, and exposes real-time analytics via RESTful APIs.

Built to demonstrate mastery over modern backend engineering, cloud-native data processing, and distributed systems architecture.

---

## 📐 System Architecture

```
┌─────────────────────┐
│   Event Producer    │   Spring Boot service generating realistic
│   (Spring Boot)     │   e-commerce events (orders, views, carts, reviews)
└────────┬────────────┘
         │  HTTP / JSON
         ▼
┌─────────────────────┐
│   AWS Lambda        │   Serverless function that receives events and
│   (datapulse-hello) │   fans out to dual storage backends
└────────┬────────────┘
         │
    ┌────┴────┐
    ▼         ▼
┌────────┐ ┌──────────────┐
│DynamoDB│ │  S3 (Raw     │   DynamoDB: low-latency seller metrics
│        │ │   JSON)      │   S3: immutable raw event archive
└────────┘ └──────┬───────┘
                  │
                  ▼
         ┌────────────────┐
         │  Apache Spark  │   PySpark ETL job:
         │  (PySpark)     │   JSON → cleaned → partitioned Parquet
         └────────┬───────┘
                  │
                  ▼
         ┌────────────────┐
         │  S3 (Parquet)  │   Columnar storage optimized for
         │  Data Lake     │   analytical queries
         └────────┬───────┘
                  │
                  ▼
         ┌────────────────┐
         │  AWS Athena    │   Serverless SQL engine querying
         │  (Serverless)  │   Parquet directly from S3
         └────────┬───────┘
                  │
                  ▼
         ┌────────────────┐
         │  Analytics     │   Spring Boot service exposing
         │  Service       │   real-time REST APIs backed by
         │  (Spring Boot) │   DynamoDB + Athena
         └────────┬───────┘
                  │
                  ▼
         ┌────────────────┐
         │  REST APIs     │   JSON endpoints for dashboards,
         │                │   BI tools, and frontend clients
         └────────────────┘
```

---

## 🧩 Project Structure

```
DataPulse/
│
├── pom.xml                          # Root Maven POM (multi-module)
├── docker-compose.yml               # Kafka, PostgreSQL, Prometheus
├── prometheus.yml                   # Prometheus scrape configuration
│
├── services/
│   ├── event-producer/              # Event generation microservice
│   │   ├── pom.xml
│   │   ├── Dockerfile
│   │   └── src/main/java/com/datapulse/producer/
│   │       ├── DataPulseProducerApplication.java
│   │       ├── controller/
│   │       │   ├── HelloController.java        # Simulation endpoints
│   │       │   └── LambdaController.java       # Lambda trigger endpoint
│   │       ├── generator/
│   │       │   ├── EventFactory.java           # Weighted event factory
│   │       │   ├── OrderEventGenerator.java    # ORDER_PLACED events
│   │       │   ├── ProductViewGenerator.java   # PRODUCT_VIEWED events
│   │       │   ├── CartAbandonedGenerator.java # CART_ABANDONED events
│   │       │   └── ReviewGenerator.java        # REVIEW_SUBMITTED events
│   │       ├── model/event/
│   │       │   ├── BaseEvent.java              # Abstract event (polymorphic JSON)
│   │       │   ├── EventType.java              # Enum: event types
│   │       │   ├── OrderPlacedEvent.java
│   │       │   ├── ProductViewedEvent.java
│   │       │   ├── CartAbandonedEvent.java
│   │       │   ├── ReviewSubmittedEvent.java
│   │       │   └── OrderLineItem.java
│   │       ├── lambda/
│   │       │   └── LambdaEventPublisher.java   # AWS Lambda SDK invocation
│   │       ├── service/
│   │       │   └── SimulationService.java      # Batch, parallel, async gen
│   │       └── publisher/                      # Event publishing abstraction
│   │
│   ├── analytics-api/               # Kafka consumer + PostgreSQL + Prometheus
│   │   ├── pom.xml
│   │   └── src/main/java/com/datapulse/analytics/
│   │       ├── AnalyticsApiApplication.java
│   │       ├── consumer/            # Kafka consumers
│   │       ├── entity/              # JPA entities
│   │       ├── repository/          # Spring Data JPA repositories
│   │       ├── service/             # Business logic
│   │       ├── controller/          # REST endpoints
│   │       ├── config/              # App configuration
│   │       └── metrics/             # Prometheus/Micrometer metrics
│   │
│   └── analytics-service/           # AWS-native analytics microservice
│       ├── pom.xml
│       └── src/main/java/com/datapulse/analyticsservice/
│           ├── AnalyticsServiceApplication.java
│           ├── athena/
│           │   └── AthenaQueryService.java     # Athena SQL execution
│           ├── config/
│           │   └── AthenaConfig.java           # AthenaClient bean
│           ├── controller/
│           │   ├── AnalyticsController.java    # Analytics REST endpoints
│           │   └── AthenaController.java       # Athena test endpoint
│           ├── dto/
│           │   ├── EventCountResponse.java
│           │   ├── TopSellerResponse.java
│           │   ├── SellerMetricsResponse.java
│           │   └── RevenueLeaderboardResponse.java
│           ├── repository/
│           │   └── SellerMetricsRepository.java  # DynamoDB data access
│           └── service/
│               └── AnalyticsService.java         # Core analytics orchestration
│
└── scripts/
    └── spark/
        ├── transform_events.py      # PySpark ETL pipeline
        ├── data/                    # Raw JSON event files
        └── output/                  # Generated Parquet files
```

---

## ⚙️ Tech Stack

| Layer | Technology | Purpose |
|:------|:-----------|:--------|
| **Language** | Java 17 | Primary backend language |
| **Framework** | Spring Boot 3.x | Microservice framework |
| **Build Tool** | Apache Maven | Multi-module dependency management |
| **Event Streaming** | Apache Kafka | Real-time event ingestion |
| **Serverless Compute** | AWS Lambda | Event processing & fan-out |
| **NoSQL Database** | AWS DynamoDB | Low-latency seller metric lookups |
| **Object Storage** | AWS S3 | Raw JSON archive + Parquet data lake |
| **ETL Engine** | Apache Spark (PySpark) | JSON → Parquet transformation |
| **Query Engine** | AWS Athena | Serverless SQL over Parquet |
| **Relational DB** | PostgreSQL 17 | Transactional analytics storage |
| **Monitoring** | Prometheus + Micrometer | Application metrics & alerting |
| **Containerization** | Docker + Docker Compose | Local infrastructure orchestration |
| **Serialization** | Jackson (Polymorphic JSON) | Type-safe event serialization |

---

## 🚀 Getting Started

### Prerequisites

| Requirement | Version |
|:------------|:--------|
| Java JDK | 17+ |
| Apache Maven | 3.8+ |
| Docker & Docker Compose | Latest |
| Python | 3.8+ (for PySpark) |
| Apache Spark | 3.x |
| AWS CLI | v2 (configured with valid credentials) |
| AWS Account | With Lambda, S3, DynamoDB, Athena access |

### 1. Clone the Repository

```bash
git clone https://github.com/<your-username>/DataPulse.git
cd DataPulse
```

### 2. Start Infrastructure Services

```bash
docker-compose up -d
```

This starts:
- **Kafka** (KRaft mode, no Zookeeper) on `localhost:9092`
- **PostgreSQL 17** on `localhost:5432`
- **Prometheus** on `localhost:9090`

### 3. Configure AWS Credentials

Ensure your AWS CLI is configured with an IAM user that has the following policies attached:

| Policy | Service |
|:-------|:--------|
| `AWSLambda_FullAccess` | Lambda invocation |
| `AmazonDynamoDBFullAccess` | Seller metrics read/write |
| `AmazonS3FullAccess` | Raw JSON + Parquet storage |
| `AmazonAthenaFullAccess` | SQL queries over data lake |

```bash
aws configure
# AWS Access Key ID: <your-key>
# AWS Secret Access Key: <your-secret>
# Default region: ap-south-1
# Output format: json
```

### 4. Build the Project

```bash
# From the project root
mvn clean install
```

### 5. Run the Event Producer

```bash
cd services/event-producer
mvn spring-boot:run
```

The producer starts on `http://localhost:8080`.

### 6. Run the Analytics Service

```bash
cd services/analytics-service
mvn spring-boot:run
```

The analytics service starts on `http://localhost:8080` (use a different port if the producer is running).

### 7. Run the Spark ETL Pipeline

```bash
cd scripts/spark
python transform_events.py
```

This reads raw JSON events, performs aggregations, and writes partitioned Parquet files.

---

## 📡 API Reference

### Event Producer APIs

| Method | Endpoint | Description |
|:-------|:---------|:------------|
| `GET` | `/simulation/start` | Generate a single random event |
| `GET` | `/simulation/batch?count={n}` | Generate `n` events sequentially |
| `GET` | `/simulation/performance?count={n}` | Benchmark: sequential generation |
| `GET` | `/simulation/performance-parallel?count={n}` | Benchmark: parallel generation |
| `GET` | `/simulation/async?count={n}` | Fire-and-forget async generation |
| `GET` | `/lambda/test` | Generate event → invoke Lambda → S3 + DynamoDB |

### Analytics Service APIs

| Method | Endpoint | Data Source | Description |
|:-------|:---------|:------------|:------------|
| `GET` | `/health` | — | Service health check |
| `GET` | `/analytics/event-counts` | **Athena → S3 Parquet** | Aggregated event counts by type |
| `GET` | `/analytics/top-sellers` | Service | Top sellers by revenue |
| `GET` | `/analytics/seller/{sellerId}` | **DynamoDB** | Real-time seller metrics |
| `GET` | `/analytics/revenue-leaderboard` | Service | Revenue leaderboard |
| `GET` | `/athena/test` | **Athena** | Returns raw Athena query execution ID |

### Sample Responses

<details>
<summary><strong>GET /analytics/event-counts</strong> (Athena-backed)</summary>

```json
[
  {
    "eventType": "ORDER_PLACED",
    "count": 4
  },
  {
    "eventType": "PRODUCT_VIEWED",
    "count": 2
  },
  {
    "eventType": "CART_ABANDONED",
    "count": 4
  },
  {
    "eventType": "REVIEW_SUBMITTED",
    "count": 4
  }
]
```

</details>

<details>
<summary><strong>GET /analytics/seller/SELLER-001</strong> (DynamoDB-backed)</summary>

```json
{
  "sellerId": "SELLER-001",
  "totalOrders": 11,
  "totalRevenue": 275000,
  "productViews": 42,
  "abandonedCarts": 5,
  "reviewCount": 7
}
```

</details>

<details>
<summary><strong>GET /simulation/start</strong></summary>

```json
{
  "eventId": "a3f1c9e2-7d4b-4e8a-b6f3-9c2d1e5f8a7b",
  "eventType": "ORDER_PLACED",
  "customerId": "CUSTOMER-472",
  "timestamp": "2026-06-20T18:30:00Z",
  "orderId": "ORDER-8341",
  "sellerId": "SELLER-57",
  "items": [
    {
      "productId": "PRODUCT-128",
      "quantity": 3,
      "unitPrice": 2499
    }
  ],
  "totalAmount": 7497,
  "currency": "INR"
}
```

</details>

---

## 🔄 Data Flow — End to End

```
Step 1:  Producer generates realistic e-commerce event
            │
Step 2:  Event sent via HTTP to AWS Lambda
            │
Step 3:  Lambda fans out to two storage backends:
            ├── DynamoDB (seller-metrics table — low-latency reads)
            └── S3 Raw JSON (s3://datapulse-raw-events/)
                    │
Step 4:  PySpark ETL reads raw JSON from S3
            │
Step 5:  Spark transforms, cleans, and writes partitioned Parquet
            │
Step 6:  Parquet files land in S3 data lake
            │
Step 7:  AWS Athena external table maps to Parquet location
            │
Step 8:  Analytics Service executes SQL via Athena SDK
            │
Step 9:  Results converted to DTOs and served via REST API
```

---

## 🏗️ AWS Infrastructure

### Lambda Function

| Property | Value |
|:---------|:------|
| Function Name | `datapulse-hello` |
| Runtime | Python / Node.js |
| Trigger | HTTP (via SDK invocation) |
| Fan-out | DynamoDB + S3 |

### DynamoDB Table

| Property | Value |
|:---------|:------|
| Table Name | `seller-metrics` |
| Partition Key | `sellerId` (String) |
| Attributes | `totalOrders`, `totalRevenue`, `productViews`, `abandonedCarts`, `reviewCount` |
| Capacity | On-Demand |

### S3 Buckets

| Bucket | Purpose |
|:-------|:--------|
| `datapulse-raw-events` | Raw JSON event archive |
| `datapulse-parquet` | Transformed Parquet data lake |
| `datapulse-athena-results` | Athena query result staging |

### Athena Configuration

| Property | Value |
|:---------|:------|
| Database | `datapulse` |
| Table | `datapulse_events_parquet` |
| Format | Apache Parquet |
| Query Result Location | `s3://datapulse-athena-results/` |

---

## 🧪 Event Types

DataPulse simulates four realistic e-commerce event types with weighted probability distribution:

| Event Type | Probability | Key Fields |
|:-----------|:------------|:-----------|
| `PRODUCT_VIEWED` | 70% | `productId`, `category`, `sellerId` |
| `ORDER_PLACED` | 15% | `orderId`, `sellerId`, `items[]`, `totalAmount`, `currency` |
| `CART_ABANDONED` | 10% | `cartId`, `sellerId`, `cartTotal` |
| `REVIEW_SUBMITTED` | 5% | `reviewId`, `productId`, `rating`, `comment` |

Events use **Jackson polymorphic deserialization** (`@JsonTypeInfo` + `@JsonSubTypes`) for type-safe serialization across service boundaries.

---

## 🐳 Docker Services

```yaml
services:
  kafka:       # Apache Kafka (KRaft mode) — localhost:9092
  postgres:    # PostgreSQL 17              — localhost:5432
  prometheus:  # Prometheus                 — localhost:9090
```

Start all:

```bash
docker-compose up -d
```

Tear down:

```bash
docker-compose down
```

---

## 📊 Monitoring

- **Prometheus** scrapes the `analytics-api` service at `/actuator/prometheus`
- **Micrometer** provides JVM, HTTP, and custom application metrics
- Access the Prometheus UI at `http://localhost:9090`

---

## 🧰 Key Design Decisions

| Decision | Rationale |
|:---------|:----------|
| **Dual-write (DynamoDB + S3)** | DynamoDB for sub-millisecond seller lookups; S3 for immutable, cost-effective historical archive |
| **Parquet over CSV/JSON** | Columnar format offers 10x compression and predicate pushdown for analytical queries |
| **Athena over Redshift** | Serverless, pay-per-query model — ideal for intermittent analytical workloads |
| **Lambda fan-out** | Decouples producer from storage backends; enables independent scaling |
| **Event polymorphism** | Single ingestion pipeline handles all event types via Jackson type discrimination |
| **Multi-module Maven** | Shared dependencies, consistent versioning, single-command builds |

---

## 📝 License

This project is open-source and available under the [MIT License](LICENSE).

---

## 👤 Author

Built with ☕ and ☁️ by **Aditya Bhushan Rai**

---

<p align="center">
  <sub>If you found this project useful, consider giving it a ⭐ on GitHub!</sub>
</p>
