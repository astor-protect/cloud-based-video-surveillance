package com.astorprotect.cloudbasedvideosurveillance.Service.imp;

import com.astorprotect.cloudbasedvideosurveillance.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
@Component
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender mailSender;


    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(text);

            mailSender.send(mailMessage);

        }catch (MailException e){
            e.printStackTrace();
        }

    }

    @Override
    public void sendSimpleMessaeUsingTemplate(String to, String subject, SimpleMailMessage template, String... templateArgs) {
        String text = String.format(template.getText(),templateArgs);
        sendSimpleMessage(to,subject,text);
    }

    @Override
    public void sendMessageWithAttachement(String to, String subject, String text, String pathToAttachement) {
        try {
            MimeMessage message  = mailSender.createMimeMessage();
            //mettre 'true' au constructeur pour creer un mmessage Multipart
            MimeMessageHelper helper = new MimeMessageHelper(message,true);

            helper.setTo(to);
            helper.setSubject(subject);
                    helper.setText(text);

            FileSystemResource fileSystemResource = new FileSystemResource(new File(pathToAttachement));
            helper.addAttachment("Invoice",fileSystemResource);

            mailSender.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }
}
