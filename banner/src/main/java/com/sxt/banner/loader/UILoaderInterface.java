package com.sxt.banner.loader;

import android.content.Context;
import android.view.View;

import java.io.Serializable;


public interface UILoaderInterface<T extends View> extends Serializable {

    void displayView(Context context, Object path, T displayView);

    T createView(Context context);
}
