package com.astorprotect.cloudbasedvideosurveillance.Service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
    void sendSimpleMessaeUsingTemplate(String to, String subject,
                                       SimpleMailMessage template,
                                       String ... templateArgs);
    void sendMessageWithAttachement(String to, String subject,
                                    String text, String pathToAttachement);

}
