package com.qaroni.library.application.infrastructure.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfiguration {

    @Autowired
    private EmailProperties emailProperties;
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailProperties.getHost());
        mailSender.setPort(emailProperties.getPort());

        mailSender.setUsername(emailProperties.getEmailFrom());
        mailSender.setPassword(emailProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", emailProperties.getTransportProtocol());
        props.put("mail.smtp.auth", emailProperties.isSmtpAuth());
        props.put("mail.smtp.starttls.enable", emailProperties.isSmtpStarttlsEnable());
        props.put("mail.debug", emailProperties.isDebug());

        return mailSender;
    }
}
