package com.vbt.kanban.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailContentDto {
	@Setter(value = AccessLevel.NONE)
	@Value("${spring.mail.from.email}")
	private String from;

	@NotBlank
	@NotNull
	private String subject;

	@NotBlank
	@NotNull
	private String message;

	@Email
	@NotBlank
	@NotNull
	private String to;


	@NotBlank
	@NotNull
	private String templateName;

	private Map<String, Object> props;
}
