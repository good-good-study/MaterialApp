package com.sxt.chat.data;


/**
 * Created by xt.sun
 * 2020/5/10
 */
public class ChipData {
    private int icon;
    private int color;
    private String title;

    public ChipData(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public ChipData(int icon, int color, String title) {
        this.icon = icon;
        this.color = color;
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }
}