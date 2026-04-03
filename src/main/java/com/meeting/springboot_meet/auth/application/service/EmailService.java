package com.meeting.springboot_meet.auth.application.service;

public interface EmailService {
    void sendVerificationEmail(String toEmail, String token);
}
