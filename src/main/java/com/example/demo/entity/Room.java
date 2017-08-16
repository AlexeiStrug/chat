package com.example.demo.entity;

import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "channel", schema = "freelance")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "first_user_id", referencedColumnName = "id")
    private User firstUser;
    @ManyToOne
    @JoinColumn(name = "second_user_id", referencedColumnName = "id")
    private User secondUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) &&
                Objects.equals(firstUser, room.firstUser) &&
                Objects.equals(secondUser, room.secondUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstUser, secondUser);
    }
}
