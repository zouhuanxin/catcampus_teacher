package com.jvtc.catcampus_teacher.util;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jvtc.catcampus_teacher.data.model.LoggedInUser;

public class AppRepository {

    private static final String NAME = "jiuxiaoshi";
    private static final String USER = "user";

    public static void setUser(Context c, LoggedInUser u) {
        c.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit().putString(USER, JSONObject.toJSON(u).toString()).commit();
    }

    public static void clearUser(Context c) {
        c.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit().remove(USER).commit();
    }

    public static LoggedInUser getUser(Context c) {
        String s = c.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(USER, "");
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        LoggedInUser user = JSON.parseObject(s, LoggedInUser.class);
        return user;
    }

}
