package com.example.demo.repositories;

import com.example.demo.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "SELECT * FROM message m WHERE (m.user_from_id = ?1 AND m.user_to_id = ?2)" +
            " OR (m.user_from_id = ?2 AND m.user_to_id = ?1) order by ?#{#pageable}",
            countQuery = "SELECT COUNT (*) FROM message m WHERE (m.user_from_id = ?1 AND m.user_to_id = ?2)" +
                    " OR (m.user_from_id = ?2 AND m.user_to_id = ?1) order by ?#{#pageable}", nativeQuery = true)
    Page<Message> findMessageByUserFromAndUserTo(Integer userFromId, Integer userToId, Pageable pageable);
}
