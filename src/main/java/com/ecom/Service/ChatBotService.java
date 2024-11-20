package com.ecom.Service;

import com.ecom.Model.ChatBotModel;

public interface ChatBotService {

	ChatBotModel createChat(String message, String name);
	
	
}
