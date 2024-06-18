package com.project.restful.suppliers;


import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EmailService {

    private JavaMailSender mailSender;
    private Environment environment;


    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            message.setFrom(environment.getProperty("config.email",String.class));
            mailSender.send(message);
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
