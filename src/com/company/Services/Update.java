package com.company.Services;

/**
 * Created by Lua_b on 01.11.2015.
 */
public class Update {
    public Integer getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(Integer update_id) {
        this.update_id = update_id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    private Integer update_id;
    private Message message;
    public Update(Integer id,Message mess){
        update_id =id;
        message = mess;
    }
    public static Update getUpdate(){

    }
}
