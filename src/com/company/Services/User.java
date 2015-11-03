package com.company.Services;

import org.json.JSONObject;

/**
 * Created by Lua_b on 01.11.2015.
 */
public class User {
    public User(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String first_name) {
        this.username = first_name;
    }

    private Integer id;
    private String username;
    public static User getUserSender(JSONObject from){
        Integer id = from.getInt("id");
        String username = from.getString("username");
        return new User(id,username);
    }
    public static User getUserForwardFrom(JSONObject forwardFrom){
        Integer id = forwardFrom.getInt("id");
        String username = forwardFrom.getString("username");
        return new User(id,username);
    }
}
