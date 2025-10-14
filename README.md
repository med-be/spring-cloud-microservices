# Microservices Architecture Project

A Spring Boot microservices project demonstrating service discovery, API gateway, and inter-service communication using Netflix Eureka and Spring Cloud.

## 🏗️ Architecture Overview

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│             │    │             │    │             │
│ API Gateway │    │   Primary   │    │   Unknown   │
│   (9090)    │◄──►│  Service    │◄──►│  Service    │
│             │    │   (8082)    │    │   (8081)    │
└─────────────┘    └─────────────┘    └─────────────┘
        │                   │                   │
        │                   │                   │
        └───────────────────▼───────────────────┘
                    ┌─────────────┐
                    │             │
                    │   Eureka    │
                    │   Server    │
                    │   (8761)    │
                    └─────────────┘
```

## 🚀 Services

### 1. **Eureka Server** (Port: 8761)
- Service discovery and registration server
- Dashboard available at: http://localhost:8761

### 2. **API Gateway** (Port: 9090)
- Central entry point for all microservices
- Routes requests to appropriate services
- Load balancing and service discovery integration

### 3. **Primary Service** (Port: 8082)
- Main business service
- Communicates with Unknown Service via Feign Client
- Endpoint: `/api/primary`

### 4. **Unknown Service** (Port: 8081)
- Secondary business service
- Provides data to Primary Service
- Endpoint: `/api/unknown`

## 🛠️ Technology Stack

- **Spring Boot**: 2.7.18
- **Spring Cloud**: 2021.0.8
- **Java**: 8+
- **Maven**: Build and dependency management
- **Netflix Eureka**: Service discovery
- **Spring Cloud Gateway**: API Gateway
- **OpenFeign**: Service-to-service communication
- **Spring Boot Actuator**: Health checks and monitoring
- **Docker**: Containerization
- **Docker Compose**: Container orchestration

## 📋 Prerequisites

- Java 8 or higher
- Maven 3.6+
- Git

## 🚀 Getting Started

### Option 1: Run with Docker (Recommended)

#### Prerequisites
- Docker Desktop installed and running
- Docker Compose V2

#### Quick Start
```bash
# Clone the repository
git clone <repository-url>
cd MICROSERVICES

# Build and start all services
docker-compose up --build

# Or run in detached mode
docker-compose up --build -d
```

#### Individual Docker Commands
```bash
# Build all images
docker-compose build

# Start services
docker-compose up

# Stop services
docker-compose down

# View logs
docker-compose logs -f [service-name]

# Scale a service (example: 3 instances of primary-service)
docker-compose up --scale primary-service=3
```

### Option 2: Run Locally (Traditional)

#### Prerequisites
- Java 8 or higher
- Maven 3.6+

#### 1. Clone the Repository
```bash
git clone <repository-url>
cd MICROSERVICES
```

#### 2. Build All Services
```bash
# Build Eureka Server
cd eureka-server
mvn clean install

# Build API Gateway
cd ../api-gateway
mvn clean install

# Build Primary Service
cd ../primary-service
mvn clean install

# Build Unknown Service
cd ../unknown-service
mvn clean install
```

#### 3. Start Services (In Order)

##### Start Eureka Server First:
```bash
cd eureka-server
mvn spring-boot:run
```
Wait for Eureka to fully start, then open: http://localhost:8761

##### Start Unknown Service:
```bash
cd unknown-service
mvn spring-boot:run
```

##### Start Primary Service:
```bash
cd primary-service
mvn spring-boot:run
```

##### Start API Gateway:
```bash
cd api-gateway
mvn spring-boot:run
```

## 📡 API Endpoints

### Direct Service Access
- **Unknown Service**: `GET http://localhost:8081/api/unknown`
- **Primary Service**: `GET http://localhost:8082/api/primary`

### Via API Gateway
- **Unknown Service**: `GET http://localhost:9090/unknown-service/api/unknown`
- **Primary Service**: `GET http://localhost:9090/primary-service/api/primary`

### Service Discovery
- **Eureka Dashboard**: `http://localhost:8761`

## 🔧 Configuration

### Service Registration
All services register with Eureka using localhost to avoid network configuration issues:

```properties
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
eureka.instance.hostname=localhost
eureka.instance.prefer-ip-address=true
```

### Gateway Configuration
The API Gateway uses service discovery for automatic routing:

```properties
spring.cloud.gateway.discovery.locator.enabled=true
spring.cloud.gateway.discovery.locator.lower-case-service-id=true
```

## 🧪 Testing

### Docker Environment Testing
```bash
# Health Checks for Docker containers
curl http://localhost:8761/actuator/health  # Eureka Server
curl http://localhost:8081/actuator/health  # Unknown Service  
curl http://localhost:8082/actuator/health  # Primary Service
curl http://localhost:9090/actuator/health  # API Gateway

# Service Registry Check
curl http://localhost:8761/eureka/apps

# API Endpoints (Docker)
curl http://localhost:8081/api/unknown  # Direct Unknown Service
curl http://localhost:8082/api/primary  # Direct Primary Service 
curl http://localhost:9090/unknown-service/api/unknown  # Via Gateway
curl http://localhost:9090/primary-service/api/primary  # Via Gateway

# Check Docker containers status
docker-compose ps
```

### Local Environment Testing
```bash
# Check if all services are running
curl http://localhost:8761/eureka/apps  # Eureka registry
curl http://localhost:8081/api/unknown  # Unknown Service
curl http://localhost:8082/api/primary  # Primary Service (calls Unknown Service)
curl http://localhost:9090/primary-service/api/primary  # Via Gateway
```

### Expected Responses
- **Unknown Service**: Returns `"Unknown Service"`
- **Primary Service**: Returns `"Unknown Service"` (proxied from Unknown Service)

## 🐛 Troubleshooting

### Docker Environment Issues

1. **Services not starting**
   ```bash
   # Check container logs
   docker-compose logs [service-name]
   
   # Check container status
   docker-compose ps
   
   # Restart specific service
   docker-compose restart [service-name]
   ```

2. **Services not registering with Eureka**
   ```bash
   # Check Eureka Server logs
   docker-compose logs eureka-server
   
   # Verify network connectivity
   docker network ls
   docker network inspect microservices-network
   ```

3. **Port conflicts**
   ```bash
   # Check what's using the ports
   netstat -tulpn | grep :8761
   
   # Stop conflicting services
   docker-compose down
   ```

4. **Build failures**
   ```bash
   # Clean rebuild
   docker-compose down
   docker-compose build --no-cache
   docker-compose up
   ```

### Local Environment Issues

1. **Services not registering with Eureka**
   - Ensure Eureka Server is running first
   - Check network configuration
   - Verify service URLs in application.properties

2. **Gateway returning 500 errors**
   - Check if target services are registered in Eureka
   - Verify service names match between Gateway and Eureka registration
   - Check logs for connection issues

3. **Connection refused errors**
   - Ensure all services are running on correct ports
   - Check firewall settings
   - Verify service startup order

### Logs Location
- **Docker**: Use `docker-compose logs [service-name]`
- **Local**: Service logs displayed in console, check target/ directories

## � Docker Architecture

### Container Network
All services run in a custom Docker network (`microservices-network`) for service-to-service communication using container hostnames.

### Health Checks
Each service includes health checks with:
- **Interval**: 30 seconds
- **Timeout**: 10 seconds  
- **Start Period**: 60 seconds
- **Retries**: 5

### Service Dependencies
- **eureka-server**: Starts first (no dependencies)
- **unknown-service**: Depends on eureka-server
- **primary-service**: Depends on eureka-server + unknown-service
- **api-gateway**: Depends on all other services

### Profiles
Services use `SPRING_PROFILES_ACTIVE=docker` to load Docker-specific configurations.

## �📁 Project Structure

```
MICROSERVICES/
├── eureka-server/          # Service Discovery Server
│   ├── src/main/java/
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── application-docker.properties
│   ├── Dockerfile
│   └── pom.xml
├── api-gateway/            # API Gateway Service  
│   ├── src/main/java/
│   ├── src/main/resources/
│   │   ├── application.properties  
│   │   └── application-docker.properties
│   ├── Dockerfile
│   └── pom.xml
├── primary-service/        # Primary Business Service
│   ├── src/main/java/
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── application-docker.properties
│   ├── Dockerfile
│   └── pom.xml
├── unknown-service/        # Secondary Business Service
│   ├── src/main/java/
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── application-docker.properties
│   ├── Dockerfile
│   └── pom.xml
├── docker-compose.yml      # Container orchestration
├── .gitignore
└── README.md
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

If you encounter any issues or have questions, please create an issue in the repository.