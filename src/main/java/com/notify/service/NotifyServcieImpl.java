package com.notify.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.notify.api.dto.Message;
import com.notify.controllers.NotifyController;
import com.notify.repository.MessageRepository;


@Service
public class NotifyServcieImpl implements NotifyServcie {
	 static final String URL="http://localhost:8080/emailMock";
	 static final String URL_SLACK="http://localhost:8080/slackMock";
	 private static final Logger logger = LogManager.getLogger(NotifyController.class);

	@Autowired
	private MessageRepository messageRepository;

	@Override
	public void notifyTo(List<Message> messages) {

		for(Message message:messages) {
			if (save(message)) {
				notifyCarriers(message);
			}
		}
	}

	/**
	 * 
	 * To decide upon the notifying medium
	 * @param message
	 */
	private void notifyCarriers(Message message) {
		String[] carriers = message.getNotifyby().split(" ");
		for (String carrier : carriers) {
			switch (carrier) {
			case "email":
				mockingEmail(message);
				logger.info("Email notify");
				break;
			case "slack":
				mockingSlack(message);
				logger.info("Slack notify");
				break;
			default:
				mockingEmail(message);
				mockingSlack(message);
				logger.info("All notify");
			}
		}

	}

	/**
	 * Email Carrier invocation
	 * @param message
	 * @return
	 */
	private boolean mockingEmail(Message message) {
		return mockingTemplate(message,NotifyServcieImpl.URL);
	}

	/**
	 * Slack Carrier invocation
	 * @param message
	 * @return
	 */
	private boolean mockingSlack(Message message) {
		return mockingTemplate(message,NotifyServcieImpl.URL_SLACK);
	}

	/**
	 * Saving notifications
	 * @param message
	 * @return
	 */
	private boolean save(Message message) {
		try {
			messageRepository.save(message);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Template to Mock
	 * @param message
	 * @param URL
	 * @return
	 */
	private boolean mockingTemplate(Message message,String URL) {
		RestTemplate restTemplate = new RestTemplate();
		String emailMockUrl = URL;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		try {

			ResponseEntity<String> response  = restTemplate.postForEntity(emailMockUrl, message, String.class);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

}
