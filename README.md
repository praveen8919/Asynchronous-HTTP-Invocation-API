Asynchronous HTTP Invocation API
📄 Overview
This project implements a non-blocking HTTP invocation API that supports asynchronous, concurrent requests to various HTTP endpoints. It accepts a dynamic payload containing target URL, headers, HTTP method (GET, POST, PUT, DELETE), optional request body, and a request timeout. It is designed to scale efficiently under heavy load (1000+ concurrent users) and is implemented with Java 17, Spring Boot 3, and Spring WebFlux.

🛠 Tools & Technologies
Java 17: Programming language
Spring Boot 3: Framework for building the application
Spring WebFlux: For asynchronous and non-blocking HTTP invocations
WebClient (Spring WebFlux): Non-blocking HTTP client
Project Reactor: For reactive stream handling (Mono, Flux)
Maven: Dependency and build management
Lombok: Reduces boilerplate code (e.g., getters/setters)
SLF4J / Logback: Logging framework
JUnit (optional): For unit testing
k6: For load testing

⚙️ Project Setup
Clone the repository:
git clone <repository-url>
cd <project-directory>

Build the project using Maven:
mvn clean install

Run the application:
mvn spring-boot:run
The application will be available on http://localhost:8080.

📌 API Endpoints
1. POST /invoke
This endpoint accepts a dynamic JSON payload for making asynchronous HTTP invocations.

Request Payload:
{
  "targetUrl": "https://example.com/api",
  "httpMethod": "GET",       // Options: GET, POST, PUT, DELETE
  "headers": {
    "Authorization": "Bearer <token>"
  },
  "requestBody": {           // Optional: JSON body for POST/PUT requests
    "key": "value"
  },
  "timeout": 5000            // Timeout in milliseconds (optional)
}


Response:
{
  "status": "success",
  "response": "Response data from target API"
}


Error Response:
{
  "status": "error",
  "message": "Timeout or error occurred"
}


2. Load Testing
For testing with a load of 1000+ concurrent users, you can use k6. To run the test:

Install k6 (if you haven't already): https://k6.io/docs/getting-started/

Create a script load-test.js for k6:

import http from 'k6/http';
import { check } from 'k6';

export default function () {
  let response = http.post('http://localhost:8080/invoke', JSON.stringify({
    targetUrl: 'https://jsonplaceholder.typicode.com/todos/1',
    httpMethod: 'GET',
    timeout: 3000
  }), { headers: { 'Content-Type': 'application/json' } });

  check(response, {
    'is status 200': (r) => r.status === 200,
  });
}


Run the load test:

k6 run load-test.js
🧩 Project Structure

src/main/java
 └── com.example.invoker
      ├── controller
      │    └── InvokeController.java
      ├── dto
      │    └── RequestDTO.java
      ├── service
      │    └── ApiService.java
      ├── model
      │    └── ApiMethod.java
      └── config
           └── WebClientConfig.java


🧠 Key Design Decisions
Non-blocking I/O: The application uses Spring WebFlux's WebClient to make asynchronous HTTP calls. This minimizes thread usage and ensures scalability.

Reactive Programming: Service methods return Mono<String>, allowing for deferred execution and event-driven responses.

Dynamic Request Handling: All input parameters (HTTP method, headers, body, etc.) are handled dynamically via a flexible JSON payload.

✅ Testing and Validation
Functional Testing: The API was manually tested using Postman and curl.

Load Testing: The system was stress-tested with k6 to simulate high concurrent requests.

📈 Results
The application can handle multiple concurrent requests without blocking threads.

The system scales efficiently under high load.

The final implementation is fully asynchronous, providing excellent performance even with 1000+ concurrent requests.

📚 Future Enhancements
Caching: Add caching for frequently requested URLs or header configurations to improve performance.

Retry Logic: Implement retry logic with exponential backoff for handling transient errors.

Observability: Integrate Prometheus for metrics collection and monitoring.

Swagger/OpenAPI Documentation: Provide API documentation for ease of use.

Error Handling: Improve error handling with structured error responses and validation.

