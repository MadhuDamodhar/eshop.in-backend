package com.ecom.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.Model.ChatBotModel;
import com.ecom.Model.User;
@Repository
public interface ChatBotRepository extends JpaRepository<ChatBotModel, Integer> {
	 List<ChatBotModel> findByUser(User user);
}
