package com.wkswind.leanote.base;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

/**
 * Created by Administrator on 2016-12-22.
 */

public class BaseEntity {
    @Id(autoincrement = true)
    @Expose(deserialize = false, serialize = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
