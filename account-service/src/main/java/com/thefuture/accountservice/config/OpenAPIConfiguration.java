package com.thefuture.accountservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "https://localhost:8081"),
        },
        info = @Info(
                contact = @Contact(
                        name = "Jeremy",
                        email = "jeremywib7@gmail.com"
                ),
                title = "Account Service APIs",
                description = "This lists all the Account Service API Calls",
                version = "v1.0")
)
public class OpenAPIConfiguration {
}


