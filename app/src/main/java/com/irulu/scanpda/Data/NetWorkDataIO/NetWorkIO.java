package com.irulu.scanpda.Data.NetWorkDataIO;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dtw on 16/10/17.
 */
public class NetWorkIO{
   public static void request(final String urlStr, final CallBackScanJson callBackScanJson){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL(urlStr);
                    HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder stringBuilder=new StringBuilder();
                    String line;
                    while((line=bufferedReader.readLine())!=null){
                        stringBuilder.append(line);
                    }
                    callBackScanJson.onResponse(stringBuilder.toString());


                    Log.v("dtw", "connectTo:  "+url.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    callBackScanJson.onFailure(e);
                } catch (IOException e) {
                    e.printStackTrace();
                    callBackScanJson.onFailure(e);
                }
            }
        }).start();
    }
    public interface CallBackScanJson{
        void onFailure(IOException e);
        void onResponse(String response);
    }
}
