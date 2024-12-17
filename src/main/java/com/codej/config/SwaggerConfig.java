package com.codej.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "API COLLEGE",
                description = "API REST para la gestión de estudiantes y profesores",
                termsOfService = "www.codej.vercel.app/terminos_y_condiciones",
                version = "1.0.0",
                contact = @Contact(
                        name = "Jhordan Briceno",
                        url = "https://codej.vercel.app/",
                        email = "jhordanbriceocaipo@mail.com"
                ),
                license = @License(
                        name = "Standard Software Use License for codej.com",
                        url = "https://codej.vercel.app/licence"
                )
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD SERVER",
                        url = "https://codej.vercel.app:8080"
                )
        }
)
public class SwaggerConfig {
}
