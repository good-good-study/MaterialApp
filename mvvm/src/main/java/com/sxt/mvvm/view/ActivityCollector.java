package com.sxt.mvvm.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 关闭所有的Activity
     *
     * @param exceptClassName 如果不想关闭某一个activity, 将activity的className传入
     */
    public static void finishAll(String... exceptClassName) {
        List<String> names = Arrays.asList(exceptClassName == null ? new String[]{} : exceptClassName);
        for (Activity activity : activities) {
            if (!activity.isFinishing() && !names.contains(activity.getClass().getName())) {
                activity.finish();
            }
        }
    }

    /**
     * 关闭某一个Activity
     *
     * @param exceptClassName 将activity的className传入
     */
    public static void finish(String... exceptClassName) {
        List<String> names = Arrays.asList(exceptClassName == null ? new String[]{} : exceptClassName);
        for (Activity activity : activities) {
            if (!activity.isFinishing() && names.contains(activity.getClass().getName())) {
                activity.finish();
            }
        }
    }

    public static void startActivity(Context context, Class<? extends Activity> clazz) {
        if (clazz == null) return;
        Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}