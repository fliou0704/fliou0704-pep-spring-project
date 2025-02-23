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

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody Account account) {
        Account loggedInAccount = accountService.login(account.getUsername(), account.getPassword());

        if (loggedInAccount == null) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        return ResponseEntity.status(200).body(loggedInAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        Message newMessage = messageService.createMessage(message);

        if (newMessage == null) {
            return ResponseEntity.status(400).body("Invalid message data");
        }

        return ResponseEntity.ok(newMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

}
