package com.example.demo.repositories;


import com.example.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query(value = "SELECT * FROM channel c WHERE c.first_user_id = ?1 OR c.second_user_id = ?1", nativeQuery = true)
    List<Room> findRoomByUserFromOrUserTo(Integer id);

    @Query(value = "SELECT * FROM channel c WHERE c.first_user_id = ?1 AND c.second_user_id = ?2 OR c.first_user_id = ?2 AND c.second_user_id = ?1", nativeQuery = true)
    List<Room> findRoomByUserFromAndUserTo(Integer userFromTo, Integer userToId);

}
