package com.wkswind.leanote.base;

import org.greenrobot.greendao.annotation.Transient;

/**
 * Leanote 接口返回的状态判断，设计的真恶心
 */
public class LeanoteResponseBody {
    @Transient
    private boolean Ok = true;
    @Transient
    private String Msg;

    public boolean isOk() {
        return Ok;
    }

    public void setOk(boolean ok) {
        Ok = ok;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
