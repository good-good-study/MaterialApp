package com.sxt.chat.ui.material.chips;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.sxt.chat.R;
import com.sxt.chat.data.ChipData;
import com.sxt.mvvm.base.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/10
 */
public class ChipsViewModel extends BaseViewModel {
    public ChipsViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<ChipData>> chips = new MutableLiveData<>();
    public MutableLiveData<List<ChipData>> chipsAction = new MutableLiveData<>();
    public MutableLiveData<List<ChipData>> chipsEntry = new MutableLiveData<>();

    public void buildChips() {
        List<ChipData> chips = new ArrayList<>();
        chips.add(new ChipData(R.drawable.ic_chip_hotel, R.color.point_yellow_light, "Ads"));
        chips.add(new ChipData(R.drawable.ic_chip_coffee, R.color.point_green_light, "IOT"));
        chips.add(new ChipData(R.drawable.ic_chip_hospital, R.color.point_orange, "ML / AI"));
        chips.add(new ChipData(R.drawable.ic_chip_shopping, R.color.point_pink, "Misc"));
        chips.add(new ChipData(R.drawable.ic_chip_home, R.color.point_yellow, "Firebase"));
        chips.add(new ChipData(R.drawable.ic_chip_ev_station, R.color.point_green, "Gaming"));
        chips.add(new ChipData(R.drawable.ic_chip_food, R.color.point_purple, "Flutter"));
        this.chips.setValue(chips);
    }

    public void buildChipsAction() {
        List<ChipData> chips = new ArrayList<>();
        chips.add(new ChipData(R.drawable.ic_chip_home, R.color.point_blue, "Keynote"));
        chips.add(new ChipData(R.drawable.ic_chip_food, R.color.point_green, "Location / Maps"));
        chips.add(new ChipData(R.drawable.ic_chip_ev_station, R.color.point_yellow, "Open Source"));
        chips.add(new ChipData(R.drawable.ic_chip_shopping, R.color.point_pink, "Payment"));
        chips.add(new ChipData(R.drawable.ic_chip_hospital, R.color.point_orange, "Search"));
        chips.add(new ChipData(R.drawable.ic_chip_coffee, R.color.point_purple, "Web"));
        chips.add(new ChipData(R.drawable.ic_chip_hotel, R.color.point_green_light, "Workshop"));
        chips.add(new ChipData(R.drawable.ic_chip_more, R.color.point_yellow_light, "Gaming"));
        this.chipsAction.setValue(chips);
    }

    public void buildChipsEntry() {
        List<ChipData> chips = new ArrayList<>();
        chips.add(new ChipData(R.drawable.ic_point_blue, R.color.point_orange, "Design"));
        chips.add(new ChipData(R.drawable.ic_point_green, R.color.point_green, "Android / Play"));
        chips.add(new ChipData(R.drawable.ic_point_yellow, R.color.point_yellow, "Assistant"));
        chips.add(new ChipData(R.drawable.ic_point_pink, R.color.point_pink, "Augmented"));
        chips.add(new ChipData(R.drawable.ic_point_orange, R.color.point_blue, "Chrome OS"));
        chips.add(new ChipData(R.drawable.ic_point_purple, R.color.point_yellow_light, "Cloud"));
        chips.add(new ChipData(R.drawable.ic_point_green_light, R.color.point_green_light, "Open Source"));
        chips.add(new ChipData(R.drawable.ic_point_yellow_ligtht, R.color.point_purple, "Reality"));
        chips.add(new ChipData(R.drawable.ic_point_blue_light, R.color.point_blue_light, "Accessibility"));
        this.chipsEntry.setValue(chips);
    }
}