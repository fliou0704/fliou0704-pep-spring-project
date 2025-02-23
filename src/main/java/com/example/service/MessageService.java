package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) {
        // Check that message text is not blank
        if (message.getMessageText() == null || message.getMessageText().length() == 0) {
            return null; // Blank message text
        }
        
        // Check that message text is not too long
        if (message.getMessageText().length() > 255) {
            return null;
        }
    
        // Check that the user who is posting the message exists
        Optional<Account> user = accountRepository.findById(message.getPostedBy());
        if (user.isEmpty()) {
            return null;
        }
    
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

}
