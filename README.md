**Alert-Hub (Microservices Architecture)**<br>
**Overview**

Alert-Hub is a distributed microservices-based system that provides real-time notifications to project managers when tasks are updated across external platforms such as Jira, ClickUp, and GitHub.
The system is built using an event-driven architecture, leveraging Kafka for message handling and Spring Boot for backend services. It is designed for scalability, modularity, and high availability.

<i>**Features :**</i>

1. **Real-Time Notifications**
Instantly alerts users when task status changes occur in external project management tools.

2. **Microservices Architecture**
Built from ten independent services that can be deployed and scaled individually.

3. **Event-Driven Messaging Layer**
Uses Apache Kafka to support asynchronous, reliable communication between services.

4. **API Gateway**
Centralized entry point responsible for routing, request validation, and authentication.

5. **Load Balancing**
Ensures efficient distribution of traffic across services to maintain responsiveness under load.

6. **Secure Access Control**
Implements JWT and Spring Security for user authentication and role-based authorization.

<i>**System Services Overview :**</i>

1. **Notification Service**
Sends email and SMS notifications by consuming messages from Kafka topics.

2. **Event Listener Service**
Listens to Jira, GitHub, and ClickUp updates and publishes events to Kafka.

3. **User Service**
Manages user accounts, roles, and permissions stored in MySQL.

4. **Security Service**
Handles authentication (JWT), authorization, and permission validation.

5. **Loader Services**
Scan platform data files hourly (or manually), transform them, and load results into the platformInformation table.

6. **Metric Service**
Stores and manages metric definitions such as thresholds, labels, and time frames.

7. **Action Service**
Defines actions with conditions, schedules, messages, and delivery types (SMS/Email).

8. **Job Scheduler Service**
Runs every 30 minutes to identify and queue actions that match schedule constraints.

9. **Processor Service**
Evaluates action conditions and produces Kafka messages when thresholds are met.

10. **Evaluation Service**
Provides analytics on developer workload and performance based on stored platform data.

9. **Logger Service**
Stores logs from all microservices in MongoDB for monitoring and tracing.

10. **Gateway Service**
Handles routing, authentication, and service communication .

<i>**Technology Stack**</i>

   **Java** — Core programming language.
  
   **Spring Boot** — Framework for building the microservices.
  
   **Spring Security** — Authentication and authorization.
  
   **Spring Cloud Gateway** — API routing and filtering.
  
   **OpenFeign** — Declarative HTTP client for inter-service communication.
  
   **Apache Kafka** — Message broker for asynchronous events.
  
   **SQL** — Relational database.
  
   **MongoDB** — NoSQL database .
  
   **Microservices Architecture** — Independent, scalable componen
