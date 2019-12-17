package com.notify.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notify.api.dto.Message;
import com.notify.service.NotifyServcie;

@RestController
@RequestMapping(value="/" , consumes = "application/json" )
public class NotifyController {
	 private static final Logger logger = LogManager.getLogger(NotifyController.class);
	
	@Autowired
	NotifyServcie notifyService;
	
	@PostMapping(value="/notify" )
	public void notify(@RequestBody List<Message> message) {
		 notifyService.notifyTo(message);
	}
	
	@PostMapping(value="/emailMock")
	public void email(@RequestBody Message message) {
		logger.info("Email Message received");
	}
	
	@PostMapping(value="/slackMock")
	public void slack(@RequestBody Message message) {
		logger.info("Slack Message received");
	}
	

}
