package com.sxt.chat.ui.material;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sxt.mvvm.model.material.Category;
import com.sxt.mvvm.model.material.Material;
import com.sxt.mvvm.viewmodel.BaseViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/21
 */
public class MaterialViewModel extends BaseViewModel {
    public MaterialViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Category>> categories = new MutableLiveData<>();

    @Override
    public void onCreate() {
        super.onCreate();
        buildCategories();
    }

    private void buildCategories() {
        try {
            InputStream inputStream = getApplication().getResources().getAssets().open("json/material.json");
            Type type = new TypeToken<Material>() {
            }.getType();
            InputStreamReader reader = new InputStreamReader(inputStream);
            JsonReader jsonReader = new JsonReader(reader);
            Material material = new Gson().fromJson(jsonReader, type);
            categories.setValue(material.getCategories());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
