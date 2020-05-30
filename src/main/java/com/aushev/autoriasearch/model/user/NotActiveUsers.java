package com.aushev.autoriasearch.model.user;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotActiveUsers {

    private List<User> notActiveUsers;

    public List<User> getNotActiveUsers() {
        return notActiveUsers;
    }

    public void setNotActiveUsers(List<User> notActiveUsers) {
        this.notActiveUsers = notActiveUsers;
    }
}
