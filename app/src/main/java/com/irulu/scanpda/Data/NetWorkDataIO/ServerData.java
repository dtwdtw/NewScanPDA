package com.irulu.scanpda.Data.NetWorkDataIO;

import android.util.Log;

import com.google.gson.Gson;
import com.irulu.scanpda.Model.JsonModel.JsonModel;

import java.io.IOException;

/**
 * Created by dtw on 16/10/17.
 */

public class ServerData {
    public static <T extends JsonModel> void getJsonModel(final String url, final Class<T> jsonType, final CallBackScan callBackScan) {
        NetWorkIO.request(url, new NetWorkIO.CallBackScanJson() {
            @Override
            public void onFailure(IOException e) {
                callBackScan.onFailure(e);
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                JsonModel jsonModel = gson.fromJson(response, jsonType);
                callBackScan.onResponse(jsonModel);
                Log.v("dtw","callbackJson   "+response);
            }
        });
    }

    public interface CallBackScan<E extends JsonModel> {
        void onFailure(IOException e);
        void onResponse(E model);
    }
}
