package com.example.demo.service;

import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.reference.errors.NoUserWithSuchUsernameCustomException;
import com.example.demo.reference.errors.core.ForbiddenCustomException;
import com.example.demo.repositories.RoomRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.transfer.CreatedResourceDto;
import com.example.demo.transfer.RoomDetail;
import com.example.demo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.utils.EntityUtil.findOneOrThrowNotFound;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    @Autowired
    public RoomServiceImpl(
            RoomRepository roomRepository,
            UserService userService,
            UserRepository userRepository,
            SecurityUtils securityUtils
    ) {
        this.roomRepository = roomRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public Room createRoom(User userFrom, User userTO) {
        Room room = new Room();
        room.setFirstUser(userFrom);
        room.setSecondUser(userTO);
        roomRepository.save(room);
        return room;
    }

    @Override
    public void deleteRoom(Integer roomId) {
        Room room = findOneOrThrowNotFound(roomRepository, roomId, Room.class);
        authorizeRoom(room);
        roomRepository.delete(room);
    }

    private void authorizeRoom(Room room) {
        User user = userService.getCurrentUser();

        if (!room.getFirstUser().getId().equals(user.getId()) || !room.getSecondUser().getId().equals(user.getId()))
            throw new ForbiddenCustomException();
    }

//    @Override
//    public List<RoomDetail> getCurrentUserRooms() {
//        User user = userService.getCurrentUser();
//
//        return roomRepository.findRoomByUserFromOrUserTo(user.getId()).stream()
//                .map(this::mapToRoomDetail)
//                .collect(Collectors.toList());
//    }
//
//    private RoomDetail mapToRoomDetail(Room room) {
//        RoomDetail roomDetail = new RoomDetail();
//        roomDetail.setId(room.getId());
//        roomDetail.setOwner(room.getFirstUser());
//        roomDetail.setConnectedUsers(room.getSecondUser());
////      String currentUserLogin = securityUtils.getCurrentUserLogin();
////      roomDetail.setCurrentUserIsOwner(room.getFirstUser().getUsername().equals(currentUserLogin));
//        return roomDetail;
//    }

//    @Override
//    public RoomDetail getRoom(Integer roomId) {
//        Room room = findOneOrThrowNotFound(roomRepository, roomId, Room.class);
//        return mapToRoomDetail(room);
//    }

//    @Override
//    public void updateRoom(Integer roomId, RoomForm roomForm) {
//        Room room = findOneOrThrowNotFound(roomRepository, roomId, Room.class);
//        authorizeRoom(room);
//        room.setName(roomForm.getName());
//        roomRepository.save(room);
//    }

}
