package com.wkswind.leanote.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;

import com.facebook.android.crypto.keychain.AndroidConceal;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.CryptoConfig;
import com.facebook.crypto.Entity;
import com.facebook.crypto.keychain.KeyChain;
import com.wkswind.leanote.BuildConfig;

import timber.log.Timber;

public class Utils {
    public static String generateKeyPrefix(@NonNull Class<?> clazz) {
        return clazz.getName()+".";
    }

    public static void hideInputMethod(@NonNull Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    private static Crypto crypto;
    private static Entity entity;

    private static Entity defaultEntity(){
        if(entity == null){
            synchronized (Utils.class){
                if(entity == null){
                    entity = Entity.create(BuildConfig.ACCOUNT_TYPE);
                }
            }
        }
        return entity;
    }
    private static Crypto defaultCrypto(Context context){
        if(crypto == null) {
            synchronized (Utils.class){
                if(crypto == null){
                    KeyChain keyChain = new SharedPrefsBackedKeyChain(context, CryptoConfig.KEY_256);
                    crypto = AndroidConceal.get().createDefaultCrypto(keyChain);
                }
            }
        }
        return crypto;
    }

    public static String encrypt(@NonNull Context context, @NonNull String plain){
        try {
            return new String(defaultCrypto(context).encrypt(plain.getBytes(), defaultEntity()));
        } catch (Exception e){
            Timber.e(e);
        }
        return null;
    }

    public static String decrypt(@NonNull Context context,@NonNull String cipher){
        try {
            return new String(defaultCrypto(context).decrypt(cipher.getBytes(), defaultEntity()));
        } catch (Exception e) {
            Timber.e(e);
        }
        return null;
    }
}
