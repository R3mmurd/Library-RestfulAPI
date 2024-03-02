package com.qaroni.library.application.infrastructure.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.mail")
@Data
public class EmailProperties {
    private String host;
    private int port;
    private String emailFrom;
    private String password;
    private String transportProtocol;
    private boolean smtpAuth;
    private boolean smtpStarttlsEnable;
    private boolean debug;
}
