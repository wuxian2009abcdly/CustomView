package com.genvict.customview.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by longy on 2018/12/4.
 * Description ${CLASS}
 */

public class ViewUtils {

    public static float sp2px(Context context, int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}
