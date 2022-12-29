package com.example.smartcityc.ServerHot;

import android.os.Message;

public class Tools {
    public static Message getErrorMessage(){
        Message msg = new Message();
        msg.what = -1;
        return msg;
    }
    public static String removeHtml(String content){
        return content.replace("</p>","")
                .replace("&lt;", "")
                .replace("<p>","")
                .replace("&nbsp","");
    }
}
