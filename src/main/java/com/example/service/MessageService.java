package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getMessagesByUser(int userId) {
        List<Message> m = new LinkedList<>();
        for ( Message curr : getAllMessages()){
            if(curr.getPostedBy().equals(userId)) m.add(curr);
        }
        return m;
    }


    public Message createMessage(Message message, com.example.service.AccountService a) {
        if(message.getMessageText().length() == 0 || message.getMessageText().length() > 255) return null;
        if(!a.getAccountById(message.getPostedBy()).isPresent()) return null;
        return messageRepository.save(message);
    }


    public Message findMessageById(Integer messageId) {
        Optional<Message> a = messageRepository.findById(messageId);
        return a.orElse(null);
    }

    public Message updateMessage(int messageId, Message updatedMessage) {
        if(updatedMessage.getMessageText().length() == 0 || updatedMessage.getMessageText().length() > 255) return null;
        return messageRepository.findById(messageId).map(existingMessage -> {
                    existingMessage.setMessageText(updatedMessage.getMessageText());
                    return messageRepository.save(existingMessage);
            }).orElse(null);
    }

    public Message deleteMessage(int messageId) {
        Optional<Message> m = messageRepository.findById(messageId);
        if(m.isPresent()){
            messageRepository.deleteById(messageId);
        }
        return m.orElse(null);
    }
}

