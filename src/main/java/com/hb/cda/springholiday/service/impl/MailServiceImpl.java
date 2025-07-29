package com.hb.cda.springholiday.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hb.cda.springholiday.entity.User;
import com.hb.cda.springholiday.service.MailService;

import lombok.AllArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService{
    private JavaMailSender mailSender;

    @Override
    public void sendEmailValidation(User user, String token) {
        String serverUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(user.getEmail());
            helper.setFrom("springholiday@human-booster.fr");
            helper.setSubject("SpringHoliday Email Validation");

            helper.setText("""
                    To validate your account click on <a href="%s">this link</a>
                    """
                    .formatted(serverUrl+"/api/account/validate/"+token),
                    true);
            mailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            throw new RuntimeException("Unable to send mail", e);
        }

    }

    @Override
    public void sendResetPassword(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendResetPassword'");
    }

}
