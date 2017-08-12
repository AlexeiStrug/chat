package com.example.demo.service;

import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.reference.errors.NoUserWithSuchUsernameCustomException;
import com.example.demo.reference.errors.core.ForbiddenCustomException;
import com.example.demo.repositories.RoomRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.transfer.CreatedResourceDto;
import com.example.demo.transfer.RoomDetail;
import com.example.demo.transfer.RoomForm;
import com.example.demo.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.utils.EntityUtil.findOneOrThrowNotFound;


/**
 * @author Igor Rybak
 */
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    public RoomServiceImpl(
            RoomRepository roomRepository,
            UserRepository userRepository,
            SecurityUtils securityUtils
    ) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public CreatedResourceDto createRoom(Room room) {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin())
                .orElseThrow(NoUserWithSuchUsernameCustomException::new);
        room.setUser(user);
        roomRepository.save(room);
        return new CreatedResourceDto(room.getId());
    }

    private RoomDetail mapToRoomDetail(Room room) {
        RoomDetail roomDetail = new RoomDetail();
        roomDetail.setId(room.getId());
        roomDetail.setName(room.getName());
        roomDetail.setOwner(room.getUser().getUsername());
        String currentUserLogin = securityUtils.getCurrentUserLogin();
        roomDetail.setCurrentUserIsOwner(room.getUser().getUsername().equals(currentUserLogin));
        return roomDetail;
    }

    @Override
    public List<RoomDetail> getCurrentUserRooms() {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin())
                .orElseThrow(NoUserWithSuchUsernameCustomException::new);

        return roomRepository.findByUserId(user.getId()).stream()
                .map(this::mapToRoomDetail)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDetail getRoom(Integer roomId) {
        Room room = findOneOrThrowNotFound(roomRepository, roomId, Room.class);
        return mapToRoomDetail(room);
    }

    @Override
    public void updateRoom(Integer roomId, RoomForm roomForm) {
        Room room = findOneOrThrowNotFound(roomRepository, roomId, Room.class);
        authorizeRoom(room);
        room.setName(roomForm.getName());
        roomRepository.save(room);
    }

    @Override
    public void deleteRoom(Integer roomId) {
        Room room = findOneOrThrowNotFound(roomRepository, roomId, Room.class);
        authorizeRoom(room);
        roomRepository.delete(room);
    }

    private void authorizeRoom(Room room) {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin())
                .orElseThrow(NoUserWithSuchUsernameCustomException::new);

        if (!room.getUser().getId().equals(user.getId()))
            throw new ForbiddenCustomException();
    }
}
