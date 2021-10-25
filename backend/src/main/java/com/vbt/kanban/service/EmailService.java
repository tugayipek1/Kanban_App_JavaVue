package com.vbt.kanban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.vbt.kanban.dto.EmailContentDto;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@EnableAsync
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Autowired
	private PropertyAccessorService propertyAccessor;


	@Async
	public void send(EmailContentDto emailContent) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(emailContent.getTo());
		mailMessage.setSubject(emailContent.getSubject());
		mailMessage.setFrom(emailContent.getFrom());
		mailMessage.setText(emailContent.getMessage());

		javaMailSender.send(mailMessage);
	}
	@Async
	public void sendHtmlEmail(EmailContentDto mail) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		Context context = new Context();
		context.setVariables(mail.getProps());
		String html = templateEngine.process(mail.getTemplateName(), context);
		helper.setTo(mail.getTo());
		helper.setText(html, true);
		helper.setSubject(mail.getSubject());
		helper.setFrom(propertyAccessor.getStringProperty("spring.mail.from.email"));
		javaMailSender.send(message);
	}
}
