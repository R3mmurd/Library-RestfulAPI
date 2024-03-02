package com.qaroni.library.application.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "springdoc.openapi")
@Data
public class OpenAPIProperties {
    @Data
    public static class Server {
        private String url;
        private String description;
    }

    @Data
    public static class Contact {
        private String name;
        private String email;
    }

    @Data
    public static class Info {
        private String title;
        private String version;
        private String description;
    }

    private Server server;
    private Contact contact;
    private Info info;
}
