package com.company.Services;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Lua_b on 12.01.2016.
 */
public class Photo extends File {
    private Integer width;
    private Integer height;

    public Photo(String fileId, Integer width, Integer height) {
        super(fileId);
        this.width = width;
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
    public static Photo getLargestPhotoFromMessage(JSONObject message){
       JSONArray photos = message.getJSONArray("photo");
       JSONObject photo = photos.getJSONObject(photos.length());
       return new Photo(photo.getString("field_id"), photo.getInt("width"), photo.getInt("height"));
    }
}
