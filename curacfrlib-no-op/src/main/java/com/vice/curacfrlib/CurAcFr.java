package com.vice.curacfrlib;

import android.app.Application;

/**
 * author:vice
 * email:vicedev1001@gmail.com
 * date:2019/1/23 18:51
 */
public class CurAcFr {

    public static void init(Application app) {

    }

    public static void show() {

    }

    public static void show(OnChangeListener listener, boolean showDefaultFloatView) {

    }

    public static void hide() {

    }


    public interface OnChangeListener {
        void onCurrentActivityChange(String currentActivityName);

        void onCurrentFragmentChange(String currentFragmentName);
    }
}
