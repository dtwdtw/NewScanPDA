package com.irulu.scanpda.Tools;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by dtw on 16/10/18.
 */

public class LoginKey {
    public static String get(String password){
        String key = "Gua(%&hj7x89H$yu";  //BI2013FtmaT5&fvHUFCy55*h%(GilJ$lhj!y6&(*jkP00jH7
        SecretKeySpec secretKeySpec=new SecretKeySpec(key.getBytes(),"AES");

        Cipher cipher=null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] bytes=null;

        try {
            bytes=cipher.doFinal(password.getBytes());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return new String( Base64.encode(bytes,Base64.DEFAULT));
    }
}
