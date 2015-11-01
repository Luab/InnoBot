package com.company.Services;
import org.json.*;

import java.util.ArrayList;

/**
 * Created by Lua_b on 01.11.2015.
 */
public class Parse {
    public static ArrayList<Update> parseUpdate(String response) {
        ArrayList<Update> answer = new ArrayList<Update>(50);
        JSONObject update = new JSONObject(response);
        JSONArray res = update.getJSONArray("result");
        for(int i=0;i<res.length();i++){
            answer.add(i,);
        }
        return answer;
    }
}
