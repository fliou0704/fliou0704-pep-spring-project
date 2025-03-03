package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    // 1: Our API should be able to process new User registrations.
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account){
        Account newAccount = accountService.registerAccount(account);

        if (newAccount == null) {
            return ResponseEntity.status(400).body("Invalid input: username or password is missing/too short");
        }

        if (newAccount.getAccountId() == null) {
            return ResponseEntity.status(409).body("Username already taken");
        }

        return ResponseEntity.status(200).body(newAccount);
    }

    // 2: Our API should be able to process User logins.
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody Account account) {
        Account loggedInAccount = accountService.login(account.getUsername(), account.getPassword());

        if (loggedInAccount == null) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        return ResponseEntity.status(200).body(loggedInAccount);
    }

    // 3: Our API should be able to process the creation of new messages.
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        Message newMessage = messageService.createMessage(message);

        if (newMessage == null) {
            return ResponseEntity.status(400).body("Invalid message data");
        }

        return ResponseEntity.ok(newMessage);
    }

    // 4: Our API should be able to retrieve all messages.
    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    // 5: Our API should be able to retrieve a message by its ID.
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.status(200).body(message);
    }

    // 6: Our API should be able to delete a message identified by a message ID.
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        int rowsDeleted = messageService.deleteMessageById(messageId);
        if (rowsDeleted > 0) {
            return ResponseEntity.status(200).body(1);
        } else {
            return ResponseEntity.status(200).body(null);
        }
    }

    // 7: Our API should be able to update a message text identified by a message ID.
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message) {

        if (message.getMessageText() == null || message.getMessageText().length() == 0 || message.getMessageText().length() > 255) {
            return ResponseEntity.status(400).body(null);
        }

        int rowsUpdated = messageService.updateMessageText(messageId, message.getMessageText());

        if (rowsUpdated > 0) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.status(400).body(null);
        }
    }

    // 8: Our API should be able to retrieve all messages written by a particular user.
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable int accountId) {
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.ok(messages); // Always returns 200, even if the list is empty
    }

}
