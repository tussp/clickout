package com.clickout.clickout;

import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtil {
    public static int getScreenHeight(WindowManager manager) {
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getScreenWidth(WindowManager manager) {
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
