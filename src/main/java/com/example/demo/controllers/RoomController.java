//package com.example.demo.controllers;
//
//import com.example.demo.entity.Room;
//import com.example.demo.service.RoomService;
//import com.example.demo.transfer.CreatedResourceDto;
//import com.example.demo.transfer.RoomDetail;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class RoomController {
//
//    private RoomService roomService;
//
//    @Autowired
//    public void setRoomService(RoomService roomService) {
//        this.roomService = roomService;
//    }

////    @PreAuthorize("hasRole('ROLE_USER')")
//    @PostMapping("/users/me/rooms")
//    @ResponseStatus(HttpStatus.CREATED)
//    public CreatedResourceDto createRoom(@RequestBody Room room) {
//        return roomService.createRoom(room);
//    }

//    @GetMapping("/users/me/rooms")
//    @ResponseStatus(HttpStatus.OK)
//    public List<RoomDetail> getCurrentUserRooms() {
//        return roomService.getCurrentUserRooms();
//    }
//
//    @GetMapping("/users/me/rooms/{roomId}")
//    @ResponseStatus(HttpStatus.OK)
//    public RoomDetail getRoom(@PathVariable Integer roomId) {
//        return roomService.getRoom(roomId);
//    }
//
//    @DeleteMapping("/users/me/rooms/{roomId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteRoom(@PathVariable Integer roomId) {
//        roomService.deleteRoom(roomId);
//    }

    //    @PutMapping(Constants.URI_USERS + "/me" + Constants.URI_ROOMS + "/{roomId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void updateRoom(@PathVariable Integer roomId, @RequestBody RoomForm roomForm) {
//        roomService.updateRoom(roomId, roomForm);
//    }
//}
