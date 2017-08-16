package com.example.demo.service;

import com.example.demo.transfer.MessageDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    void handleNewMessage(Integer userFromId, Integer userToId, MessageDetails message);

    Page<MessageDetails> getMessages(Integer userFromId, Integer userToId, Pageable pageable);
}
