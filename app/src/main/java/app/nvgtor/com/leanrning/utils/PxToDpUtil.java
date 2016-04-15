package app.nvgtor.com.leanrning.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by ydz on 16/3/24.
 */
public class PxToDpUtil {

    static int WIDTH;
    static int HEIGHT;

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getWindowHeightPx(Context context) {
        if (HEIGHT > 0)
            return HEIGHT;
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(metric);
        HEIGHT = metric.heightPixels; // 屏幕宽度（像素）
        return HEIGHT;
    }

    public static int getWindowPx(Context context) {
        if (WIDTH > 0)
            return WIDTH;

        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(metric);
        WIDTH = metric.widthPixels; // 屏幕宽度（像素）
        return WIDTH;
    }

    public static int sp2px(Context context, float spValue) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        int pixelValue = (int) (spValue * scaledDensity + 0.5f);
        return pixelValue;
    }

}
