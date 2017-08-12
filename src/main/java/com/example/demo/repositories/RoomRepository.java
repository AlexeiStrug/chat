package com.example.demo.repositories;


import com.example.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Igor Rybak
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByUserId(Integer id);
}
