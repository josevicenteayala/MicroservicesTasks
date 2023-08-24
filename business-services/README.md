# Microservices Docker Compose Setup
This Docker Compose configuration helps you set up multiple microservices using Docker containers. It includes services like common-service, one-service, and two-service.

## Prerequisites
* Docker: Install Docker on your machine.
Usage
### Clone the Repository:

```cypher
git clone <https://github.com/josevicenteayala/MicroservicesTasks.git>
cd <MicroservicesTasks>
```

### Directory Structure:

Ensure your project directory structure matches the configuration provided in the Docker Compose file. This includes subdirectories for each service (common, one, two) with their respective Dockerfile and source code.

### Build and Run:

Use the following command to build and run the microservices using Docker Compose:

```docker-compose up -d
```

This command will start containers for each microservice, creating a network to connect them.

### Access Services:

Once the containers are up and running, you can access the services:

* common-service: http://localhost:8085
* one-service: http://localhost:8086
* two-service: http://localhost:8087

### Clean Up:

To stop and remove the containers, use the following command:

```docker-compose down
```

## Configuration
* `compose.yaml`: Defines services and their configurations.
* `Dockerfile` (in each service directory): Contains instructions for building Docker images for each microservice.

## Notes
* Services are configured to listen on specific ports as defined in the docker-compose.yml file.
* The one-service container has additional environment variables set (SPRINGPROFILES) for its operation.
* Adjust service ports and other configurations according to your project needs.