package com.sxt.chat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.sxt.chat.App;
import com.sxt.chat.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Prefs extends BasePrefs {
    private static Prefs instance;
    private Context context;

    private Prefs(Context context) {
        super(context);
        this.context = context.getApplicationContext();
    }

    public static synchronized Prefs getInstance(Context context) {
        if (instance == null) {
            instance = new Prefs(context);
        }
        return instance;
    }

    public String getUserName() {
        return super.getString(Key.USER_NAME, null);
    }

    public int getUserId() {
        return getInt(Key.USER_ID, 0);
    }

    public void clearUserPrefs() {
        SharedPreferences sp = context.getSharedPreferences(getUserName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear(); // reset user data, eg: csn
        editor.apply();
    }

    public List<String> getAll() {
        List<String> list = new ArrayList<>();
        String user = getUserName();
        if (user != null) {
            SharedPreferences sp = context.getSharedPreferences(user, Context.MODE_PRIVATE);
            Map<String, ?> allContent = sp.getAll();
            //注意遍历map的方法
            for (Map.Entry<String, ?> entry : allContent.entrySet()) {
                //content+=(entry.getKey()+entry.getValue());
                list.add(entry.getKey());
            }
        }
        return list;
    }

    public void putObject(String key, Object obj) {
        String user = getUserName();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(obj);
            String value = new String(Base64.encode(out.toByteArray(), Base64.DEFAULT));
            SharedPreferences sp = context.getSharedPreferences(user, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, value);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getObject(String key) {
        String user = getUserName();
        SharedPreferences sp = context.getSharedPreferences(user, Context.MODE_PRIVATE);
        String value = sp.getString(key, null);
        if (value != null) {
            byte[] valueBytes = Base64.decode(value, Base64.DEFAULT);
            ByteArrayInputStream bin = new ByteArrayInputStream(valueBytes);
            try {
                ObjectInputStream oin = new ObjectInputStream(bin);
                return oin.readObject();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
