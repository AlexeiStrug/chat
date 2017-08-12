package com.example.demo.service;


import com.example.demo.entity.Room;
import com.example.demo.transfer.CreatedResourceDto;
import com.example.demo.transfer.RoomDetail;
import com.example.demo.transfer.RoomForm;

import java.util.List;

/**
 * @author Igor Rybak
 */
public interface RoomService {
    CreatedResourceDto createRoom(Room room);

    List<RoomDetail> getCurrentUserRooms();

    RoomDetail getRoom(Integer roomId);

    void deleteRoom(Integer roomId);

    void updateRoom(Integer roomId, RoomForm roomForm);
}
