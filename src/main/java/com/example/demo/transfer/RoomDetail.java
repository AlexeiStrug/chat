package com.example.demo.transfer;

/**
 * @author Igor Rybak
 */
public class RoomDetail {
    private Integer id;

    private String name;

    private String connectedUsers;

    private String owner;

    private boolean currentUserIsOwner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(String connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isCurrentUserIsOwner() {
        return currentUserIsOwner;
    }

    public void setCurrentUserIsOwner(boolean currentUserIsOwner) {
        this.currentUserIsOwner = currentUserIsOwner;
    }
}
