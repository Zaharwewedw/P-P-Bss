package com.bank.authorization.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Bank",
                description = "Loyalty System", version = "1.0.0",
                contact = @Contact(
                        name = "Zahar Tolstikov"
                )
        )
)
public class ConfigOpenApi {
}
