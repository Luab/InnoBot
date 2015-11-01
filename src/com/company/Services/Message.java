package com.company.Services;

import jdk.nashorn.api.scripting.JSObject;
import org.json.JSONObject;

/**
 * Created by Lua_b on 01.11.2015.
 */
public class Message {
    public Message(Chat chat, Integer message_id, Integer date) {
        this.chat = chat;
        this.message_id = message_id;
        this.date = date;
    }

    public Message(Chat chat, Integer message_id, Integer date, String text, User from) {
        this.chat = chat;
        this.message_id = message_id;
        this.date = date;
        this.text = text;
        this.from = from;
    }

    public Message(Chat chat, Integer message_id, Integer date, User from) {
        this.chat = chat;
        this.message_id = message_id;
        this.date = date;
        this.from = from;
    }

    public Integer getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    /**
     * All we need inside message. Required
     */
    private Chat chat;
    private Integer message_id;
    private Integer date;

    /**
     * Everything optional
     */
    private User from;
    private String text;

    public static Message getMessage(JSONObject message){
        Integer message_id = message.getInt("message_id");
        Integer date = message.getInt("date");
        User from = User.getUserSender(message.getJSONObject("from"));
        Chat chat = Chat.getChat(message.getJSONObject("chat"));
        String text = message.getString("text");
        if (text != null){
            return new Message(chat,message_id,date,text,from);
        }
        else{
            return new Message(chat,message_id,date,from);
        }
    }

}
