# Microservices Docker Compose Setup
This Docker Compose configuration helps you set up a collection of microservices using Docker containers. It includes services like foos-resources, uiapplication, eureka-server, eureka-client, and eureka-feign.

## Prerequisites
* Docker: Install Docker on your machine.

## Usage
### Clone the Repository:

```bash
git clone <https://github.com/josevicenteayala/MicroservicesTasks.git>
cd <MicroservicesTasks>
```

### Directory Structure:

Make sure your project directory structure matches the configuration provided in the Docker Compose file. This includes having subdirectories for each service (foos-resources, uiapplication, eureka-server, eureka-client, eureka-feign) with their respective Dockerfile and source code.

### Build and Run:

Use the following command to build and run the microservices using Docker Compose:

```cypher
docker-compose up -d
```
This command will start containers for each microservice, creating a network to connect them.

### Access Services:

Once the containers are up and running, you can access the services:

* foos-resources: http://localhost:8083
* uiapplication: http://localhost:8084
* eureka-server: http://localhost:8761
* eureka-client: http://localhost:8081
* eureka-feign: http://localhost:8082

### Clean Up:

To stop and remove the containers, use the following command:
```cypher
docker-compose down
```

## Configuration
* `compose.yaml`: Defines services and their configurations.
* `Dockerfile` (in each service directory): Contains instructions for building Docker images for each microservice.

## Notes
Services depend on the eureka-server service. Ensure eureka-server starts first before the dependent services.
Adjust service ports and other configurations according to your project needs.