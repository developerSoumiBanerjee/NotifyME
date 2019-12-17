package com.notify.service;

import java.util.List;

import com.notify.api.dto.Message;

public interface NotifyServcie {
	
	void notifyTo(List<Message> message);
}
