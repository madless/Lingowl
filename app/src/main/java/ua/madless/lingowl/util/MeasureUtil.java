package ua.madless.lingowl.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by User on 21.02.2016.
 */
public class MeasureUtil {
    public static float getDp(Context context, int value) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, r.getDisplayMetrics());
    }
}
