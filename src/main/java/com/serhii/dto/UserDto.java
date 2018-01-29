package com.serhii.dto;

import javax.validation.constraints.NotNull;

public class UserDto {
    @NotNull
    private int id;
    @NotNull
    private String nickname;
    @NotNull
    private String password;

    public UserDto() {
    }

    public UserDto(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
