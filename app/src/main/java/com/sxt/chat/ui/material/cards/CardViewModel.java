package com.sxt.chat.ui.material.cards;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sxt.mvvm.model.card.CardMessage;
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
public class CardViewModel extends BaseViewModel {
    public CardViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<CardMessage>> cards = new MutableLiveData<>();

    @Override
    public void onCreate() {
        super.onCreate();
        buildCardMessage();
    }

    private void buildCardMessage() {
        try {
            InputStream inputStream = getApplication().getResources().getAssets().open("json/card.json");
            Type type = new TypeToken<List<CardMessage>>() {
            }.getType();
            InputStreamReader reader = new InputStreamReader(inputStream);
            JsonReader jsonReader = new JsonReader(reader);
            List<CardMessage> list = new Gson().fromJson(jsonReader, type);
            cards.setValue(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
