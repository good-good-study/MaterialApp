package com.sxt.chat.utils;

import android.app.Activity;

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
}