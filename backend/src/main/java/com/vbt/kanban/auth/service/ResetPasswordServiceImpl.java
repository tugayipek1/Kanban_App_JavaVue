package com.vbt.kanban.auth.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vbt.kanban.auth.repository.ResetPasswordRepository;
import com.vbt.kanban.dto.EmailContentDto;
import com.vbt.kanban.entity.Auth;
import com.vbt.kanban.entity.ResetPasswordToken;
import com.vbt.kanban.service.EmailService;
import com.vbt.kanban.service.PropertyAccessorService;

import javax.mail.MessagingException;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {

    @Autowired
    ResetPasswordRepository resetPasswordRepository;

    @Autowired
    AuthService authService;

    @Autowired
    EmailService emailService;

    @Autowired
    PropertyAccessorService properties;

    @Override
    public Optional<ResetPasswordToken> findByToken(String token) {
        return resetPasswordRepository.findByToken(token);
    }

    @Override
    public void createResetPasswordToken(Auth auth) throws MessagingException {
        ResetPasswordToken resetPasswordToken = resetPasswordRepository.save(new ResetPasswordToken(auth));
        emailService.sendHtmlEmail(createResetPasswordMailContent(auth, resetPasswordToken));
    }

    public EmailContentDto createResetPasswordMailContent(Auth auth, ResetPasswordToken resetPasswordToken) {

        String link = properties.getFrontendHost() + "/reset-password?token="
                + resetPasswordToken.getToken();
        EmailContentDto content = new EmailContentDto();
        content.setTemplateName("ResetPasswordMailTemplate");
        content.setTo(auth.getEmail());
        content.setSubject("Şifre Sıfırlama talebi");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("link", link);
        content.setProps(model);
        return content;

    }

    @Override
    public void update(ResetPasswordToken resetPasswordToken) {
        resetPasswordRepository.save(resetPasswordToken);
    }

}
