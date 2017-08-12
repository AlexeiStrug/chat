package com.example.demo.transfer;

/**
 * Transfer object, contains id to send in response of POST request.
 * @author Igor Rybak
 */
public class CreatedResourceDto {
    private Integer id;

    public CreatedResourceDto() {
    }

    public CreatedResourceDto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
