package com.example.demo.transfer;

import com.example.demo.entity.User;

public class RoomDetail {
    private Integer id;

    private User owner;

    private User connectedUsers;

//    private boolean currentUserIsOwner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(User connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

//    public boolean isCurrentUserIsOwner() {
//        return currentUserIsOwner;
//    }
//
//    public void setCurrentUserIsOwner(boolean currentUserIsOwner) {
//        this.currentUserIsOwner = currentUserIsOwner;
//    }
}
