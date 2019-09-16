package com.astorprotect.cloudbasedvideosurveillance.EmailConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new  JavaMailSenderImpl();
        //TODO
        return  mailSender;

    }

}
