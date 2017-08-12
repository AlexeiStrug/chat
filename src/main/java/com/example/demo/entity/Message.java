package com.example.demo.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alex on 10.08.2017.
 */
@Entity
@Table(name = "message", schema = "public")
public class Message {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "content")
    private String content;
    @Column(name = "date")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "channel_id", referencedColumnName = "id", nullable = false)
    private Room room;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
