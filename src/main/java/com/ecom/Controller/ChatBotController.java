package com.ecom.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.Exception.ResourceNotFoundException;
import com.ecom.Model.ChatBotModel;
import com.ecom.Service.Imp.ChatBotServiceImp;
import com.ecom.payload.ApiResponse;
import com.ecom.payload.ChatBotResponse;

@RestController
@RequestMapping("/helpDesk")
public class ChatBotController {

    @Autowired
    private ChatBotServiceImp chatBotServiceImp;

    // Creating a chat with the message and user principal (authenticated user)
    @PostMapping("/createChat")
    public ChatBotModel createChat(@RequestBody ChatBotResponse response, Principal principal) {
        // If principal is null, handle the unauthenticated user case appropriately.
        if (principal == null) {
            throw new ResourceNotFoundException("User is not authenticated.");
        }
        return this.chatBotServiceImp.createChat(response, principal.getName()); // Using email or username as identifier
    }

    // Fetching chat history for the authenticated user
    @GetMapping("/fetchChatHistory")
    public List<ChatBotModel> fetchChatHistory(Principal principal) {
        if (principal == null) {
            throw new ResourceNotFoundException("User is not authenticated.");
        }
        return this.chatBotServiceImp.fetchChatHistoryByUser(principal.getName()); // Fetch chats for the specific user
    }

    @DeleteMapping("/clearChat")
    public ResponseEntity<ApiResponse> deleteChatHistory() {
        String responseMessage = chatBotServiceImp.deleteChatHistory();
        return new ResponseEntity<>(new ApiResponse(responseMessage, true), HttpStatus.OK);
    }
}
