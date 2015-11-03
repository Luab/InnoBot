package com.company;

import com.company.Services.Message;
import com.company.Services.QueryString;
import com.company.Services.Rule;
import com.company.Services.Update;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static URL requestUpdates() throws MalformedURLException {
        return requestUpdates(null);
    }

    static URL requestUpdates(Integer offset) throws MalformedURLException {
        String s = "https://api.telegram.org/bot104981927:AAE2q-Wsvj2aT2jmMu-ta97RdazhQQ4w0AM/getUpdates";
        if (offset != null) {
            s += "?offset=" + offset;
        }
        return new URL(s);
    }

    /**
     * @param text
     * @param replyTo may be <code>null</code>
     * @param chatId
     */
    static void sendMessage(String text, Integer replyTo, int chatId) throws IOException {
        String s = "https://api.telegram.org/bot104981927:AAE2q-Wsvj2aT2jmMu-ta97RdazhQQ4w0AM/sendMessage?";
        QueryString q = new QueryString();
        // chat_id=47289384&text=Test&reply_to_message_id=52
        q.add("chat_id", String.valueOf(chatId));
        q.add("text", text);
        if (replyTo != null) {
            q.add("reply_to_message_id", replyTo.toString());
        }
        URL url = new URL(s + q.getQuery());
        url.openStream();
    }


    static int lastUpdateId = 0;
    static List<Rule> rules = new ArrayList<>();

    static void makeRules() {
        // ROOM 319
        rules.add(new Rule() {

            final String[] dict = {"хуй", "аккредитация", "армия", "повестка", "пизда", "мейер", "эйфель", "стипендия",
                    "12к", "кальян", "шокобарон"};

            @Override
            public boolean check(String txt, Message msg) throws IOException {
                for (String w : dict) {
                    if (txt.contains(w)) {
                        sendMessage(makeText(msg),
                                msg.getMessageId(), msg.getChat().getId());
                        return true;
                    }
                }
                return false;
            }

            String makeText(Message msg) {
                String[] q = new String[]{"@" + msg.getFrom().getUsername() + ", пройдите в 319",
                "Друзья мои, нам необходимо со всеми вами пообщаться, похоже назрела такая необходимость. О времени встречи будет сообщено позднее",
                        "@" + msg.getFrom().getUsername()

                };
                Random random = new Random();
                return q[random.nextInt(q.length)];

            }


        });

        // AIFFEL
        rules.add(new Rule() {

            @Override
            public boolean check(String txt, Message msg) throws IOException {
                if (txt.contains("айфель") || txt.contains("афель")) {
                    sendMessage("у меня в Иннаполисе за АФЕЛЬ убивали нахрен",
                            msg.getMessageId(), msg.getChat().getId());
                    return true;
                }
                return false;
            }
        });
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner l = new Scanner(new File("last_update_id.txt"));


        if (l.hasNextInt()) {
            lastUpdateId = l.nextInt();
        }
        l.close();
        while (true) {
            try {
                URL url = requestUpdates(lastUpdateId);
                System.err.println(url);
                Scanner in = new Scanner(new InputStreamReader(url.openStream()));
                String json = "";
                while (in.hasNextLine()) {
                    json += in.nextLine();
                }
                System.err.println(json);
                JSONObject response = new JSONObject(json);
                JSONArray result = response.getJSONArray("result");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject update = result.getJSONObject(i);
                    Update up = Update.getUpdate(update);
                    lastUpdateId = up.getUpdate_id() + 1;

                    PrintWriter o = new PrintWriter("last_update_id.txt");
                    o.println(lastUpdateId);
                    o.close();

                    Message msg = up.getMessage();
                    if (msg.getChat().getType().equals("private")) {
                        String txt = msg.getText().toLowerCase();
                        shouldComeTo319(txt, msg);
                        aifel(txt, msg);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.err.println("$$ WAITING...");
            Thread.sleep(4000);
        }
    }
}
