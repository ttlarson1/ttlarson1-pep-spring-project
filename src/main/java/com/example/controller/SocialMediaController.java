package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.http.HttpStatus;
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

    private final AccountService accountService;

    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    //message stuff
    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }
    

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message savedMessage = messageService.createMessage(message, accountService);
        if(savedMessage == null) return ResponseEntity.status(400).build();
        return ResponseEntity.status(200).body(savedMessage);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> findMessageById(@PathVariable Integer messageId) {
        Message message = messageService.findMessageById(messageId);
        return ResponseEntity.ok(message); 
    }


    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message) {
        Message m = messageService.updateMessage(messageId, message);
        if(m != null) return ResponseEntity.status(200).body(1);
        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        Message m = messageService.deleteMessage(messageId);
        if(m != null) return ResponseEntity.status(200).body(1); 
        return ResponseEntity.status(200).build();
    }


    @PostMapping("/register")
    public ResponseEntity<Void> createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginToAccount(@RequestBody Account account) {
        Account a = accountService.loginAccount(account);
        return (a != null) ? ResponseEntity.status(200).body(a) : ResponseEntity.status(401).build();
    }

    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getMessageByUser(@PathVariable int accountId) {
        return messageService.getMessagesByUser(accountId);
    }
}