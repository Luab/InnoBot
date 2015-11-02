package com.company;

import com.company.Services.Update;
import org.json.HTTP;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) {
        String response = "{\"update_id\":310821015,\n" +
                "\"message\":{\"message_id\":31,\"from\":{\"id\":47289384,\"first_name\":\"Bulat\",\"last_name\":\"Maksudov\",\"username\":\"Lua_b\"},\"chat\":{\"id\":-12703239,\"title\":\"300 \\u0438\\u043d\\u043d\\u043e\\u043f\\u043e\\u043b\\u044f\\u0442\\u043e\\u0432\",\"type\":\"group\"},\"date\":1446451163,\"new_chat_title\":\"300 \\u0438\\u043d\\u043d\\u043e\\u043f\\u043e\\u043b\\u044f\\u0442\\u043e\\u0432\"}}";
        JSONObject update = new JSONObject(response);
        Ht
        System.out.print(Update.getUpdate(update).getMessage().getText());

    }
}
