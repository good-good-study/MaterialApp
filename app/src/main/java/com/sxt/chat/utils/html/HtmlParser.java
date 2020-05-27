package com.sxt.chat.utils.html;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;

import androidx.core.text.HtmlCompat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by xt.sun
 * 2020/5/15
 */
public class HtmlParser {
    public static Spanned parseHtml(Context context, String htmlPath) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(htmlPath);
            byte[] b = new byte[1024];
            while (is.read(b) != -1) {
                String content = new String(b, StandardCharsets.UTF_8);
                builder.append(content);
            }
            is.close();
            return HtmlCompat.fromHtml(builder.toString(), HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS);
        } catch (IOException e) {
            e.printStackTrace();
            return new SpannableString("parseHtmlText");
        }
    }
}