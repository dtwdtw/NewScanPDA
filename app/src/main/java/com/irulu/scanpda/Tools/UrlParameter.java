package com.irulu.scanpda.Tools;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by dtw on 16/10/19.
 */

public class UrlParameter {
    public static String get(Map<String,String> loginfo){
        StringBuilder stringBuilder=new StringBuilder();
        Iterator<Map.Entry<String,String>> iterator=loginfo.entrySet().iterator();
        Boolean firstParameter=true;
        while (iterator.hasNext()){
            Map.Entry<String,String> entry=iterator.next();
            if(firstParameter) {
                firstParameter=false;
                stringBuilder.append("?" + entry.getKey());
                stringBuilder.append("=" + entry.getValue());
            }else{
                stringBuilder.append("&" + entry.getKey());
                stringBuilder.append("=" + entry.getValue());
            }
        }
        return stringBuilder.toString();
    }
}
