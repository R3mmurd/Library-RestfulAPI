package com.qaroni.library.application.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Autowired
    private OpenAPIProperties openAPIProperties;
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl(openAPIProperties.getServer().getUrl());
        server.description(openAPIProperties.getServer().getDescription());

        Contact contact = new Contact();
        contact.setName(openAPIProperties.getContact().getName());
        contact.setEmail(openAPIProperties.getContact().getEmail());

        Info information = new Info()
                .title(openAPIProperties.getInfo().getTitle())
                .version(openAPIProperties.getInfo().getVersion())
                .description(openAPIProperties.getInfo().getDescription())
                .contact(contact);

        return new OpenAPI().info(information).servers(List.of(server));
    }
}
