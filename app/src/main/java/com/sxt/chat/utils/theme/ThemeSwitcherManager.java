/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sxt.chat.utils.theme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseIntArray;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatDelegate;

import com.sxt.chat.app.App;
import com.sxt.chat.R;

/**
 * 主题切换
 * Material Design 官方提供的主题切换方式
 */
@SuppressLint("StaticFieldLeak")
public class ThemeSwitcherManager {

    private static final String PREFERENCES_NAME = "night_mode_preferences";
    private static final String KEY_NIGHT_MODE = "night_mode";
    private static final SparseIntArray THEME_NIGHT_MODE_MAP = new SparseIntArray();

    static {
        THEME_NIGHT_MODE_MAP.append(R.id.theme_light, AppCompatDelegate.MODE_NIGHT_NO);
        THEME_NIGHT_MODE_MAP.append(R.id.theme_dark, AppCompatDelegate.MODE_NIGHT_YES);
        THEME_NIGHT_MODE_MAP.append(R.id.theme_default, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    private final Context context;
    private static ThemeSwitcherManager instance = new ThemeSwitcherManager(App.getCtx());

    private ThemeSwitcherManager(Context context) {
        this.context = context;
    }

    public static ThemeSwitcherManager getInstance() {
        return instance;
    }

    public void saveAndApplyTheme(@IdRes int id) {
        int nightMode = convertToNightMode(id);
        saveNightMode(nightMode);
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }

    public void applyTheme() {
        AppCompatDelegate.setDefaultNightMode(getNightMode());
    }

    @IdRes
    public int getCurrentThemeId() {
        return convertToThemeId(getNightMode());
    }

    private int getNightMode() {
        return getSharedPreferences()
                .getInt(KEY_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    private void saveNightMode(int nightMode) {
        getSharedPreferences().edit().putInt(KEY_NIGHT_MODE, nightMode).apply();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private int convertToNightMode(@IdRes int id) {
        return THEME_NIGHT_MODE_MAP.get(id, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    @IdRes
    private int convertToThemeId(int nightMode) {
        return THEME_NIGHT_MODE_MAP.keyAt(THEME_NIGHT_MODE_MAP.indexOfValue(nightMode));
    }
}