package com.sxt.mvvm.model.material;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/22
 */
public class Material {
    private int configurationVersion;
    private List<Category> categories;

    public int getConfigurationVersion() {
        return configurationVersion;
    }

    public List<Category> getCategories() {
        return categories;
    }
}