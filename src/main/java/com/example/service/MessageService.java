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
            return null;
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

    public Message getMessageById(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public int deleteMessageById(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    public int updateMessageText(int messageId, String newMessageText) {
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        
        if (existingMessage.isPresent()) {
            Message message = existingMessage.get();
            message.setMessageText(newMessageText);
            messageRepository.save(message);
            return 1;
        }
        
        return 0;
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

}
