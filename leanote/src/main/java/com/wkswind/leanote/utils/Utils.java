package com.wkswind.leanote.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2016-12-3.
 */

public class Utils {
    public static String generateKeyPrefix(Class<?> clazz) {
        return clazz.getName()+".";
    }

    public static void hideInputMethod(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
}
