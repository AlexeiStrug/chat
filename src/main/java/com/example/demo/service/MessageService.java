package com.example.demo.service;

import com.example.demo.transfer.MessageDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Igor Rybak
 */
public interface MessageService {
    void handleNewMessage(Integer roomId, MessageDetails message);

    Page<MessageDetails> getMessages(Integer roomId, Pageable pageable);
}
