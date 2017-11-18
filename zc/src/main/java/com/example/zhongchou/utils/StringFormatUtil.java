package com.example.zhongchou.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

/**
 * <请描述这个类是干什么的>
 *
 * @author Caiyk
 * @date: 2016/12/26
 * @version: v1.0
 */

public class StringFormatUtil {

    /**
     * @param context
     * @param wholeStr     全部文字
     * @param highlightStr 改变颜色的文字
     * @param color        颜色
     */
    public static SpannableStringBuilder fillColor(Context context, String wholeStr, String highlightStr, int color) {
        SpannableStringBuilder spBuilder;
        int start, end;
        if (!TextUtils.isEmpty(wholeStr) && !TextUtils.isEmpty(highlightStr)) {
            if (wholeStr.contains(highlightStr)) {
                /*
                 *  返回highlightStr字符串wholeStr字符串中第一次出现处的索引。
                 */
                start = wholeStr.indexOf(highlightStr);
                end = start + highlightStr.length();
            } else {
                return null;
            }
        } else {
            return null;
        }
        spBuilder = new SpannableStringBuilder(wholeStr);
        color = context.getResources().getColor(color);
        CharacterStyle charaStyle = new ForegroundColorSpan(color);
        spBuilder.setSpan(charaStyle, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spBuilder;
    }

}
