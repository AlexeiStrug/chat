package com.example.demo.service;


import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.transfer.CreatedResourceDto;
import com.example.demo.transfer.RoomDetail;

import java.util.List;

public interface RoomService {

    Room createRoom(User userFrom, User userTO);

    void deleteRoom(Integer roomId);

//    List<RoomDetail> getCurrentUserRooms();
//
//    RoomDetail getRoom(Integer roomId);


//    void updateRoom(Integer roomId, RoomForm roomForm);
}
