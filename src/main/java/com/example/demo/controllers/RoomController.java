package com.example.demo.controllers;

import com.example.demo.entity.Room;
import com.example.demo.reference.Constants;
import com.example.demo.service.RoomService;
import com.example.demo.transfer.CreatedResourceDto;
import com.example.demo.transfer.RoomDetail;
import com.example.demo.transfer.RoomForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Igor Rybak
 */
@RestController
@RequestMapping(Constants.URI_API)
public class RoomController {

    private RoomService roomService;

    @Autowired
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping(value = Constants.URI_USERS + "/me" + Constants.URI_ROOMS)
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResourceDto createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @GetMapping(Constants.URI_USERS + "/me" + Constants.URI_ROOMS)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<RoomDetail> getCurrentUserRooms() {
        return roomService.getCurrentUserRooms();
    }

    @GetMapping(Constants.URI_USERS + "/me" + Constants.URI_ROOMS + "/{roomId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public RoomDetail getRoom(@PathVariable Integer roomId) {
        return roomService.getRoom(roomId);
    }

    @PutMapping(Constants.URI_USERS + "/me" + Constants.URI_ROOMS + "/{roomId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoom(@PathVariable Integer roomId, @RequestBody RoomForm roomForm) {
        roomService.updateRoom(roomId, roomForm);
    }

    @DeleteMapping(Constants.URI_USERS + "/me" + Constants.URI_ROOMS + "/{roomId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable Integer roomId) {
        roomService.deleteRoom(roomId);
    }
}
