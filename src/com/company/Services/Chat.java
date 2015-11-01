package com.company.Services;

import org.json.JSONObject;

/**
 * Created by Lua_b on 01.11.2015.
 */
public class Chat {
    private Integer id;
    private String type;
    private String title;
    private String username;

    public Chat(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Chat(String title, String type, Integer id) {
        this.title = title;
        this.type = type;
        this.id = id;
    }

    public Chat(String first_name, String username, String type, Integer id) {
        this.first_name = first_name;
        this.username = username;
        this.type = type;
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String first_name;
    public static Chat getChat(JSONObject chat){
        Integer id = chat.getInt("id");
        String type = chat.getString("type");
        if (type !="private") {
            String title = chat.getString("title");
            return new Chat(title,type,id);
        }
        else{
            String username = chat.getString("username");
            String first_name = chat.getString("first_name");
            return new Chat(first_name,username,type,id);
        }
    }
}
