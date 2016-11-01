package com.irulu.scanpda.Data.LocalDateIO;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dtw on 16/10/15.
 */

public class SharedPreferencesIO {
    private SharedPreferences sharedPreferences=null;
    private String sharedPreferencesKey="irulu_scan_PDA";
    private String userNameKey="username";

    public SharedPreferencesIO(Context context){
        sharedPreferences=context.getSharedPreferences(sharedPreferencesKey,Context.MODE_NO_LOCALIZED_COLLATORS);
    }
    public void addUser(String userName){
        Set<String> setUserName = new HashSet<>(sharedPreferences.getStringSet(userNameKey,new HashSet<String>()));
        setUserName.add(userName);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putStringSet(userNameKey,setUserName);
        editor.commit();
    }
    public Set<String> getUserName(){
        return sharedPreferences.getStringSet(userNameKey, new HashSet<String>());
    }
}
