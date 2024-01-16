# Simple Bank Microservices

This repository contains a simple implementation of a banking system using microservices architecture in Java. The project is designed to showcase key concepts related to microservices, including service isolation, scalability, and independent deployability.

## Features

- **Account Service:** Manages user accounts, allowing for the creation, retrieval, and modification of account details.

- **Transaction Service:** Handles financial transactions, ensuring the integrity and consistency of funds movement between accounts.

- **Notification Service:** Sends notifications to users for important account events, such as transactions and balance updates.

## Technologies Used

- **Spring Boot:** Utilized for building and deploying microservices, providing a robust and scalable foundation.

- **Spring Cloud:** Integrated to manage common microservices concerns such as service discovery, configuration management, and fault tolerance.

- **Spring IoC (Inversion of Control):** Leveraged for managing the dependency injection and promoting loose coupling between components.

- **Java 11:** The programming language used for developing the microservices.

- **Java Stream:** Applied to process collections of data in a functional programming style, enhancing code readability and conciseness.

- **Advance Native SQL Queries:** Utilized for optimizing database interactions and addressing specific data retrieval needs.

- **Containerization & Microservices:** Docker employed for containerization, allowing each microservice to be deployed independently, promoting scalability and resource efficiency.

- **Kafka & Stream-Based Application:** Incorporated Kafka for building a stream-based application, enabling real-time event-driven communication between microservices.

- **Redis, Caching Strategy & Data Grid:** Redis used as an in-memory data store for caching, implementing an effective caching strategy, and supporting a distributed data grid.

- **Elastic & Other Non-Relational DB:** ElasticSearch and other non-relational databases integrated to demonstrate the flexibility of the microservices architecture in supporting diverse data storage requirements.

## Getting Started

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/simple-bank-microservices.git
    ```

2. Navigate to the project directory:

    ```bash
    cd simple-bank-microservices
    ```

3. Build and run microservices using Docker:

    ```bash
    docker-compose up
    ```

4. Access the microservices at their respective endpoints.

## Usage

- Detailed documentation on how to use each microservice and their endpoints can be found in the `docs` directory.

- Explore the codebase to understand the implementation details of each microservice.

## Contributing

Feel free to contribute by opening issues, providing feedback, or submitting pull requests. Contributions are highly encouraged!

## License

This project is licensed under the [MIT License](LICENSE).
