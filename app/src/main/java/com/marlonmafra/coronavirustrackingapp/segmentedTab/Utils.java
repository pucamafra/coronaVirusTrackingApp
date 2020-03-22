package com.marlonmafra.coronavirustrackingapp.segmentedTab;

import android.content.Context;

public class Utils {

    public static float getValueByDensity(Context context, int value) {
        return (context.getResources().getDisplayMetrics().density * value);
    }

    public static float getValueByDensity(Context context, double value) {
        return (float) (context.getResources().getDisplayMetrics().density * value);
    }
}