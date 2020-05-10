package com.sxt.chat.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/10
 */
public class Strings {
    public static List<String> generatedTabs() {
        return Arrays.asList("探索", "动态", "通勤", "贡献");
    }

    public static List<String> generatedChips() {
        return Arrays.asList("单位", "外卖", "送餐", "茶馆", "超市", "药店", "咖啡", "酒店", "更多");
    }

    public static List<String> generatedWords() {
        List<String> list = new ArrayList<>();
        for (char word = 'A'; word <= 'Z'; word++) {
            list.add(String.valueOf(word));
        }
        return list;
    }
}
