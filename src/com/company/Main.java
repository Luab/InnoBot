package com.company;

import com.company.Services.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
        String s = "https://api.telegram.org/bot"+ BuildVars.BOT_TOKEN+"/getUpdates";
        if (offset != null) {
            s += "?offset=" + offset;
        }
        return new URL(s);
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
                        Message.sendMessage(makeText(msg), msg.getMessageId(), msg.getChat().getId());
                        return true;
                    }
                }
                return false;
            }

            String makeText(Message msg) {
                String[] q = new String[]{"@" + msg.getFrom().getUsername() + ", пройдите в 319"};
                Random random = new Random();
                return q[random.nextInt(q.length)];

            }


        });

        // AIFFEL
        rules.add(new Rule() {

            @Override
            public boolean check(String txt, Message msg) throws IOException {
                if (txt.contains("айфель") || txt.contains("афель")) {
                    Message.sendMessage("у меня в Иннаполисе за АФЕЛЬ убивали нахрен", msg.getMessageId(), msg.getChat().getId());
                    return true;
                }
                return false;
            }
        });

        // Кукла
        rules.add(new Rule() {

            @Override
            public boolean check(String txt, Message msg) throws IOException {
                if (txt.matches("(^|\\s)кукла(\\s|$)")) {
                    Message.sendMessage("не нужна", msg.getMessageId(), msg.getChat().getId());
                            return true;
                        }
                        return false;
                    }
                });
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner l = new Scanner(new File("last_update_id.txt"));

        makeRules();
        if (l.hasNextInt()) {
       //     lastUpdateId = l.nextInt();
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
                    String txt = msg.getText().toLowerCase();
                    for (Rule rule : rules) {
                       Boolean x = rule.check(txt, msg);
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
