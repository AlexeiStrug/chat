package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.reference.errors.NoUserWithSuchUsernameCustomException;
import com.example.demo.repositories.MessageRepository;
import com.example.demo.repositories.RoomRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.transfer.MessageDetails;
import com.example.demo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.demo.utils.EntityUtil.findOneOrThrowNotFound;


/**
 * @author Igor Rybak
 */
@Service
public class MessageServiceImpl implements MessageService {
    private SecurityUtils securityUtils;

    private MessageRepository messageRepository;

    private UserRepository userRepository;

    private RoomRepository roomRepository;


    @Autowired
    public MessageServiceImpl(
            SecurityUtils securityUtils,
            MessageRepository messageRepository,
            UserRepository userRepository,
            RoomRepository roomRepository
    ) {
        this.securityUtils = securityUtils;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void handleNewMessage(Integer roomId, MessageDetails messageDetails) {
        String username = securityUtils.getCurrentUserLogin();
        messageDetails.setUsername(username);
        messageDetails.setDate(new Date());
        Message message = new Message();
        User user = userRepository.findByUsername(username)
                .orElseThrow(NoUserWithSuchUsernameCustomException::new);

        message.setUser(user);
        message.setContent(messageDetails.getContent());
        message.setDate(messageDetails.getDate());

        Room room = findOneOrThrowNotFound(roomRepository, roomId, Room.class);
        message.setRoom(room);
        messageRepository.save(message);
    }

    @Override
    public Page<MessageDetails> getMessages(Integer roomId, Pageable pageable) {
        return messageRepository.findByRoomId(roomId, pageable).map(this::mapToMessageDetail);
    }

    private MessageDetails mapToMessageDetail(Message message) {
        MessageDetails details = new MessageDetails();
        details.setUsername(message.getUser().getUsername());
        details.setContent(message.getContent());
        details.setDate(message.getDate());
        return details;
    }
}
