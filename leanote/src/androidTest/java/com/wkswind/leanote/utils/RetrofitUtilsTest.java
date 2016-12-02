package com.wkswind.leanote.utils;

import com.wkswind.leanote.database.User;

import org.junit.Test;

import io.reactivex.functions.Consumer;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016-12-2.
 */
public class RetrofitUtilsTest {
    @Test
    public void login() throws Exception {
        final String email = "wkswind@gmail.com";
        final String pwd = "palm?fish";
        RetrofitUtils.login(email, pwd).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                assertEquals(user.getEmail(), email);
                assertEquals(user.getUsername(), email);
                assertEquals(user.getToken(), "58411fe7ab64417032005dfa");
            }
        });
    }

}