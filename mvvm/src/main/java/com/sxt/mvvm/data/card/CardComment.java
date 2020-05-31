package com.sxt.mvvm.data.card;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/23
 */
public class CardComment {
    private String title;
    private String image;
    private float rating;
    private String members;
    private List<Comment> comments;

    public float getRating() {
        return rating;
    }

    public String getMembers() {
        return members;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public List<Comment> getComments() {
        return comments;
    }
}