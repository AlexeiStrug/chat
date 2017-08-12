package com.example.demo.repositories;

import com.example.demo.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Igor Rybak
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    Page<Message> findByRoomId(Integer roomId, Pageable pageable);
}
