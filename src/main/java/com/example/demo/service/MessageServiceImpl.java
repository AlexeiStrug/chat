package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.reference.errors.NoUserWithSuchUsernameCustomException;
import com.example.demo.reference.errors.UserCouldNotGetMessagesException;
import com.example.demo.reference.errors.UserCouldNotWriteHereException;
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
import java.util.List;

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
    public void handleNewMessage(Integer userFromId, Integer userToId, MessageDetails messageDetails) {
        List<Room> room = roomRepository.findRoomByUserFromAndUserTo(userFromId, userToId);
//        .orElseThrow(NoUserWithSuchUsernameCustomException::new);
        if (room.size() != 0) {
            String username = securityUtils.getCurrentUserLogin();
            messageDetails.setUsername(username);
            messageDetails.setDate(new Date());

            Message message = new Message();
            User userFrom = userRepository.findById(userFromId).orElseThrow(NoUserWithSuchUsernameCustomException::new);
//            User userFrom = findOneOrThrowNotFound(userRepository, userFromId, User.class);
            User userTo = userRepository.findById(userToId).orElseThrow(NoUserWithSuchUsernameCustomException::new);
//            User userTo = findOneOrThrowNotFound(userRepository, userToId, User.class);

            message.setUserFrom(userFrom);
            message.setUserTo(userTo);
            message.setContent(messageDetails.getContent());
            message.setDate(messageDetails.getDate());

//        Room room = findOneOrThrowNotFound(roomRepository, roomId, Room.class);
//        message.setRoom(room);
            messageRepository.saveAndFlush(message);
        } else throw new UserCouldNotWriteHereException();
    }

    @Override
    public Page<MessageDetails> getMessages(Integer userFromId, Integer userToId, Pageable pageable) {
        List<Room> room = roomRepository.findRoomByUserFromAndUserTo(userFromId, userToId);
//                .orElseThrow(NoUserWithSuchUsernameCustomException::new);
        if (room.size() != 0) {
            Page<MessageDetails> mes = messageRepository.findMessageByUserFromAndUserTo(userFromId, userToId, pageable).map(this::mapToMessageDetail);
            return mes;
        } else throw new UserCouldNotGetMessagesException();
    }

    private MessageDetails mapToMessageDetail(Message message) {
        MessageDetails details = new MessageDetails();
        details.setUsername(message.getUserFrom().getUsername());
        details.setContent(message.getContent());
        details.setDate(message.getDate());
        return details;
    }
}
