package com.sxt.chat.ui.material.cards;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sxt.mvvm.data.card.CardComment;
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
public class CardCategoryViewModel extends BaseViewModel {
    public CardCategoryViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<CardComment>> comments = new MutableLiveData<>();

    @Override
    public void onCreate() {
        super.onCreate();
        buildComments();
    }

    private void buildComments() {
        try {
            InputStream inputStream = getApplication().getResources().getAssets().open("json/card_comment.json");
            Type type = new TypeToken<List<CardComment>>() {
            }.getType();
            InputStreamReader reader = new InputStreamReader(inputStream);
            JsonReader jsonReader = new JsonReader(reader);
            List<CardComment> comments = new Gson().fromJson(jsonReader, type);
            this.comments.setValue(comments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}