package com.vbt.kanban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class PropertyAccessorService {

	@Autowired
	private Environment env;

	public String getStringProperty(String key) {
		return env.getProperty(key);

	}

	public Integer integerProperty(String key) {
		Integer property = null;
		if (env.getProperty(key) != null) {
			try {
				property = Integer.parseInt(key);
			} catch (Exception e) {
				System.err.println("Can not cast to int for " + key + " property");
				e.printStackTrace();
			}
		}
		return property;
	}

	public String getBackendHost() {
		return env.getProperty("backend.host");
	}

	public String getFrontendHost() {
		return env.getProperty("frontend.host");
	}

	public String getDefaultMailSender() {
		return env.getProperty("spring.mail.from.email");
	}

}
