package com.thefuture.transactionservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "https://localhost:8085"),
        },
        info = @Info(
                contact = @Contact(
                        name = "Jeremy",
                        email = "jeremywib7@gmail.com"
                ),
                title = "Transaction Service APIs",
                description = "This lists all the Transaction Service API Calls",
                version = "v1.0")
)
public class OpenAPIConfiguration {
}


