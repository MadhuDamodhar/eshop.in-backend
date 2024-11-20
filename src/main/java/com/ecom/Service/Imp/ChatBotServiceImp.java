package com.ecom.Service.Imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.Exception.ResourceNotFoundException;
import com.ecom.Model.ChatBotModel;
import com.ecom.Model.User;
import com.ecom.Repository.ChatBotRepository;
import com.ecom.Repository.UserRepository;
import com.ecom.Service.ChatBotService;
import com.ecom.payload.ChatBotResponse;
@Service
public class ChatBotServiceImp implements ChatBotService {

    @Autowired
    private ChatBotRepository chatBotRepo;

    @Autowired
    private UserRepository userRepo;

    // Method to create a chat based on the message and user name
    public ChatBotModel createChat(ChatBotResponse response, String name) {
        // Find the user by name (email or username)
        User user = this.userRepo.findByEmail(name).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Create the chat model with the response based on the message
        ChatBotModel chatBotModel = responseFactory(response.getMessage(), user);

        // Save the chat history to the database
        return chatBotRepo.save(chatBotModel);
    }

    // Fetching chat history for a specific user
    public List<ChatBotModel> fetchChatHistoryByUser(String userName) {
        User user = this.userRepo.findByEmail(userName).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return this.chatBotRepo.findByUser(user);  // Assuming you have a method to find chats by user
    }

    // Factory method to generate a response based on the message
    private ChatBotModel responseFactory(String message, User user) {
        String reply;
        // Your predefined responses based on the message
        // Example:
     // Respond based on predefined message
        if ("hi".equalsIgnoreCase(message)) {
            reply = "Hi there, How can I help you?";
        } else if (message.toLowerCase().contains("order not received")) {
            reply = "I'm sorry to hear that you haven't received your order. Can you provide me with the order ID so I can look into it?";
        } else if (message.toLowerCase().contains("order delayed")) {
            reply = "We apologize for the delay. Let me check the latest status of your shipment. Could you provide your order ID?";
        } else if (message.toLowerCase().contains("payment failed")) {
            reply = "It seems there was an issue with your payment. Please check if the amount was deducted. If so, provide us with transaction details for verification.";
        } else if (message.toLowerCase().contains("double billing")) {
            reply = "We apologize for the inconvenience of a duplicate charge. Could you share the transaction details to help us resolve this quickly?";
        } else if (message.toLowerCase().contains("refund not received")) {
            reply = "Refunds usually take a few days to process. Could you share the order ID and payment details to expedite the process?";
        } else if (message.toLowerCase().contains("login issue")) {
            reply = "If you're having trouble logging in, please try resetting your password. If the issue persists, let us know.";
        } else if (message.toLowerCase().contains("product not as described")) {
            reply = "We strive to provide accurate descriptions. Could you share the product ID and details of the discrepancy?";
        } else if (message.toLowerCase().contains("return request")) {
            reply = "To initiate a return, please provide your order ID and the reason for return. We will guide you through the process.";
        } else if (message.toLowerCase().contains("discount code not applied")) {
            reply = "We're sorry for the issue with the discount code. Could you provide the code and your cart details?";
        } else if (message.toLowerCase().contains("website issue")) {
            reply = "We're sorry for any technical difficulties. Could you provide details of the issue you're facing?";
        } else {
            reply = "Sorry, I didn't understand that. Could you please clarify?";
        }

        // Create and return a new ChatBotModel with the message, response, and user
        ChatBotModel chatBotModel = new ChatBotModel();
        chatBotModel.setMessage(message);
        chatBotModel.setResponse(reply);
        chatBotModel.setUserName(user.getUsername());
        chatBotModel.setUser(user); // Associate the chat with the user
        return chatBotModel;
    }

    // Deleting all chat history
    public String deleteChatHistory() {
        this.chatBotRepo.deleteAll(); // Delete all chat history
        return "All chats deleted successfully"; // Return success message
    }

	@Override
	public ChatBotModel createChat(String message, String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
