package com.escapenest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendMail(String toEmail, String subject, String content) {
        log.info("[MAIL] Bắt đầu gửi mail tới: {}", toEmail);
        log.info("[MAIL] Tiêu đề: {}", subject);
        log.info("[MAIL] Nội dung:\n{}", content);

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(content);

            javaMailSender.send(simpleMailMessage);
            log.info("[MAIL] Gửi mail thành công tới {}", toEmail);
        } catch (Exception e) {
            log.error("[MAIL ERROR] Gửi mail thất bại tới {}: {}", toEmail, e.getMessage(), e);
        }
    }
}
