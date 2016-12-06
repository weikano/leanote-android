package com.wkswind.leanote.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wkswind.leanote.BuildConfig;
import com.wkswind.leanote.database.Note;
import com.wkswind.leanote.gson.NoteTypeAdapter;

import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;

import se.simbio.encryption.Encryption;
import timber.log.Timber;

public class Utils {

    private static Encryption encryption;

    public static String generateKeyPrefix(@NonNull Class<?> clazz) {
        return clazz.getName()+".";
    }

    public static void hideInputMethod(@NonNull Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    private static Encryption defaultEncryption(){
        if(encryption == null) {
            synchronized (Utils.class){
                if(encryption == null){
                    try {
                        encryption = new Encryption.Builder().setKeyLength(128)
                                .setKeyAlgorithm("AES")
                                .setCharsetName("UTF8")
                                .setIterationCount(1)
                                .setKey(BuildConfig.ACCOUNT_TYPE)
                                .setDigestAlgorithm("SHA1")
                                .setSalt(BuildConfig.APPLICATION_ID)
                                .setBase64Mode(Base64.DEFAULT)
                                .setAlgorithm("AES/CBC/PKCS5Padding")
                                .setSecureRandomAlgorithm("SHA1PRNG")
                                .setSecretKeyType("PBKDF2WithHmacSHA1")
                                .setIv(new byte[] { 29, 88, -79, -101, -108, -38, -126, 90, 52, 101, -35, 114, 12, -48, -66, -30 }) .build();
                    } catch (NoSuchAlgorithmException e) {
                        Timber.e(e);
//                        e.printStackTrace();
                    }
                }
            }
        }
        return encryption;
    }

    @Nullable
    public static String encrypt(@NonNull String plain){
        try {
            return defaultEncryption().encrypt(plain);
        } catch (Exception e){
            Timber.e(e);
        }
        return null;
    }

    @Nullable
    public static String decrypt(@NonNull String cipher){
        try {
            return defaultEncryption().decrypt(cipher);
        } catch (Exception e) {
            Timber.e(e);
        }
        return null;
    }

    public static String getHost(){
        return "https://leanote.com/api/";
    }

    public static Gson defaultGson(){
        return new GsonBuilder().registerTypeAdapter(Note.class, new NoteTypeAdapter()).excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE).serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
    }
}
