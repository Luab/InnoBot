package com.company.Services;

import jdk.nashorn.api.scripting.JSObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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

    public Integer getMessageId() {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    private String text;

    /**
     *
     * @param message JSON object to parse from
     * @return parsed message
     */
    public static Message getMessage(JSONObject message){
        System.out.print(message);
        Integer message_id = message.getInt("message_id");
        Integer date = message.getInt("date");
        User from = User.getUserSender(message.getJSONObject("from"));
        Chat chat = Chat.getChat(message.getJSONObject("chat"));
        if ( message.has("text")){
            String text = message.getString("text");
            return new Message(chat,message_id,date,text,from);
        }
        else{
            return new Message(chat,message_id,date,from);
        }
    }

    public static Message getMessageFromResult(JSONObject result){
        return getMessage(result.getJSONObject("result"));
    }
    /**
     * @param text
     * @param replyTo may be <code>null</code>
     * @param chatId
     */
   public static Message sendMessage(String text, Integer replyTo, int chatId) throws IOException {
        String s = "https://api.telegram.org/bot"+ BuildVars.BOT_TOKEN+"/sendMessage?";
        QueryString q = new QueryString();
        // chat_id=47289384&text=Test&reply_to_message_id=52
        q.add("chat_id", String.valueOf(chatId));
        q.add("text", text);
        if (replyTo != null) {
            q.add("reply_to_message_id", replyTo.toString());
        }
        URL url = new URL(s + q.getQuery());
        java.util.Scanner scanner = null;
        try {
            scanner = new java.util.Scanner(url.openStream()).useDelimiter("\\A");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println(url);
       String response = "";
       while(scanner.hasNext()){
           response+=scanner.next();
       }
       System.out.print(response);
        return getMessageFromResult(new JSONObject(response));
    }

    /**
     * Utill function used to send everything
     * @param what type (should corresspond with Telegram API) ex photo
     * @param chatId chat to send to
     * @param file file to send
     * @return
     */
    private static Message sender(String what, int chatId,File file){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost("https://api.telegram.org/bot"+ BuildVars.BOT_TOKEN+"/send"+what);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("chat_id", String.valueOf(chatId), ContentType.TEXT_PLAIN);
        builder.addBinaryBody(what, file, ContentType.APPLICATION_OCTET_STREAM, file.getName());
        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity responseEntity = response.getEntity();

        java.util.Scanner s = null;
        try {
            s = new java.util.Scanner(responseEntity.getContent()).useDelimiter("\\A");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getMessageFromResult(new JSONObject(s.hasNext() ? s.next() : ""));
    }
    /**
     * Utill function used to send everything
     * @param what type (should corresspond with Telegram API) ex photo
     * @param chatId chat to send to
     * @param file file to send
     * @return
     */
    private static Message sender(String what, int chatId,byte[] file){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost("https://api.telegram.org/bot"+ BuildVars.BOT_TOKEN+"/send"+what);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("chat_id", String.valueOf(chatId), ContentType.TEXT_PLAIN);
        builder.addBinaryBody(what, file, ContentType.APPLICATION_OCTET_STREAM, "new");
        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity responseEntity = response.getEntity();

        java.util.Scanner s = null;
        try {
            s = new java.util.Scanner(responseEntity.getContent()).useDelimiter("\\A");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getMessageFromResult(new JSONObject(s.hasNext() ? s.next() : ""));
    }
    /**
     * Sends a photo
     * @param photo photo to send (file)
     * @param chatId ID of chat to reply
     * @return send message
     */
    public static Message sendPhoto(File photo,int chatId){
        return sender("photo",chatId,photo);
    }
    /**
     * Sends a photo
     * @param photo photo to send (filepath)
     * @param chatId ID of chat to reply
     * @return send message
     */
    public static Message sendPhoto(byte[] photo,Integer chatId){
        return sender("photo",chatId, photo);

    }

    /**
     * Sends a photo
     * @param photo photo to send (filepath)
     * @param chatId ID of chat to reply
     * @return send message
     */
    public static Message sendPhoto(String photo,Integer chatId){
        return sender("photo",chatId,new File(photo));
    }

    /**
     * Sends a Document
     * @param document photo to send (filepath)
     * @param chatId ID of chat to reply
     * @return send message
     */
    public static Message sendDocument(String document,Integer chatId){
        return sender("document",chatId,new File(document));
    }
}
