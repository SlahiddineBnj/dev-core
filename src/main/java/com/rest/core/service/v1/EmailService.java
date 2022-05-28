package com.rest.core.service.v1;

import com.rest.core.dto.mailing.EmailData;

import javax.mail.MessagingException;

public interface EmailService {

    void sendEmail(EmailData emailData,Boolean isHtml) throws MessagingException;
}
