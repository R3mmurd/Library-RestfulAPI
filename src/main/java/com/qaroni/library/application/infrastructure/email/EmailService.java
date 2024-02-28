package com.qaroni.library.application.infrastructure.email;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
