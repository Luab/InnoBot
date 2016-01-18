package com.company;

import com.company.Services.Message;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import sun.misc.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Lua_b on 11.01.2016.
 */
public class Tes {
  public static void main(String[] args){
        System.out.print(Message.sendPhoto("1.gif",-43487263));

  }
}
