package com.rest.core.service.v1.implementation;

import com.rest.core.constant.Constant;
import com.rest.core.dto.mailing.EmailData;
import com.rest.core.service.v1.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;



    @Override
    public void sendEmail(EmailData emailData) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        message.setSubject(emailData.getSubject());
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);
        helper.setFrom(Constant.SMTP_USERNAME);
        helper.setTo(emailData.getTo());
        helper.setText(emailData.getBody().toString()) ;
        emailSender.send(message);
        }

}
