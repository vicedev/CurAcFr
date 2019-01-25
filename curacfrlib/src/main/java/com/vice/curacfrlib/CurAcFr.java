package com.vice.curacfrlib;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import com.vice.curacfrlib.floatwindow.interfaces.ViewStateListener;

/**
 * author:vice
 * email:vicedev1001@gmail.com
 * date:2019/1/23 18:51
 */
public class CurAcFr {
    private static String TAG = "CurAcFrTag";

    private static FragmentManager.FragmentLifecycleCallbacks supportFragmentLifecycleCallbacks;
    private static android.app.FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks;

    private static OnChangeListener sOnChangeListener;

    private static FloatViewManager sFloatViewManager;

    private static boolean sShowDefaultFloatView;

    private static Application sApplication;

    private static String sCurrentActivityName;

    private static String sCurrentFragmentName;


    public static void init(Application app) {

        if (app == null) {
            throw new NullPointerException("Application is null");
        }

        sApplication = app;

        Application.ActivityLifecycleCallbacks sActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activity instanceof FragmentActivity) {
                    FragmentActivity fragmentActivity = (FragmentActivity) activity;
                    fragmentActivity.getSupportFragmentManager().registerFragmentLifecycleCallbacks(supportFragmentLifecycleCallbacks, true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        fragmentActivity.getFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        activity.getFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true);
                    }
                }

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                sCurrentActivityName = activity.getComponentName().getClassName();
                sCurrentFragmentName = "";
                Log.d(TAG, sCurrentActivityName);
                if (sOnChangeListener != null) {
                    sOnChangeListener.onCurrentActivityChange(sCurrentActivityName);
                    sOnChangeListener.onCurrentFragmentChange(sCurrentFragmentName);
                }
                if (sShowDefaultFloatView) {
                    sFloatViewManager.setCurrentActivityInfo(sCurrentActivityName);
                    sFloatViewManager.setCurrentFragmentInfo(sCurrentFragmentName);
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (activity instanceof FragmentActivity) {
                    FragmentActivity fragmentActivity = (FragmentActivity) activity;
                    fragmentActivity.getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(supportFragmentLifecycleCallbacks);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        fragmentActivity.getFragmentManager().unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        activity.getFragmentManager().unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks);
                    }
                }
            }
        };

        supportFragmentLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment f) {
                sCurrentFragmentName = f.getClass().getName();
                Log.d(TAG, sCurrentFragmentName);
                if (sOnChangeListener != null) {
                    sOnChangeListener.onCurrentFragmentChange(sCurrentFragmentName);
                }
                if (sShowDefaultFloatView) {
                    sFloatViewManager.setCurrentFragmentInfo(sCurrentFragmentName);
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fragmentLifecycleCallbacks = new android.app.FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentResumed(android.app.FragmentManager fm, android.app.Fragment f) {
                    sCurrentFragmentName = f.getClass().getName();
                    Log.d(TAG, sCurrentFragmentName);
                    if (sOnChangeListener != null) {
                        sOnChangeListener.onCurrentFragmentChange(sCurrentFragmentName);
                    }
                    if (sShowDefaultFloatView) {
                        sFloatViewManager.setCurrentFragmentInfo(sCurrentFragmentName);
                    }
                }
            };
        }

        sApplication.registerActivityLifecycleCallbacks(sActivityLifecycleCallbacks);
    }

    public static void show() {
        show(null, true);
    }

    public static void show(OnChangeListener listener, boolean showDefaultFloatView) {

        if (sApplication == null) {
            throw new NullPointerException("Application is null , please call init first! ");
        }

        sShowDefaultFloatView = showDefaultFloatView;

        sOnChangeListener = listener;

        if (showDefaultFloatView) {
            if (sFloatViewManager == null) {
                sFloatViewManager = FloatViewManager.getInstance();
            }
            //显示悬浮窗
            sFloatViewManager.showFloat(sApplication.getApplicationContext(), new ViewStateListener() {
                @Override
                public void onPositionUpdate(int x, int y) {
                    Log.d(TAG, "onPositionUpdate");
                }

                @Override
                public void onShow() {
                    Log.d(TAG, "onShow");
                }

                @Override
                public void onHide() {
                    Log.d(TAG, "onHide");
                }

                @Override
                public void onShowByUesr() {
                    Log.d(TAG, "onShowByUesr");

                }

                @Override
                public void onHideByUser() {
                    Log.d(TAG, "onHideByUser");
                }

                @Override
                public void onDismiss() {
                    Log.d(TAG, "onDismiss");
                }

                @Override
                public void onMoveAnimStart() {
                    Log.d(TAG, "onMoveAnimStart");
                }

                @Override
                public void onMoveAnimEnd() {
                    Log.d(TAG, "onMoveAnimEnd");
                }

                @Override
                public void onBackToDesktop() {
                    Log.d(TAG, "onBackToDesktop");
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hide();
                }
            });

            sFloatViewManager.setCurrentActivityInfo(sCurrentActivityName == null ? "" : sCurrentActivityName);
            sFloatViewManager.setCurrentFragmentInfo(sCurrentFragmentName == null ? "" : sCurrentFragmentName);
        }
    }

    public static void hide() {
        sShowDefaultFloatView = false;

        if (sFloatViewManager != null) {
            sFloatViewManager.hideFloat();
        }
    }


    public interface OnChangeListener {
        void onCurrentActivityChange(String currentActivityName);

        void onCurrentFragmentChange(String currentFragmentName);
    }
}
