package com.vice.curacfrlib.floatwindow.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import android.widget.Toast;
import com.vice.curacfrlib.floatwindow.permission.FloatActivity;
import com.vice.curacfrlib.floatwindow.interfaces.FloatView;
import com.vice.curacfrlib.floatwindow.interfaces.PermissionListener;
import com.vice.curacfrlib.floatwindow.util.LogUtil;
import com.vice.curacfrlib.floatwindow.adaptation.Miui;

/**
 * Created by yhao on 17-11-14.
 * https://github.com/yhaolpz
 */

class FloatPhone implements FloatView {

    private final Context mContext;

    private final WindowManager mWindowManager;
    private final WindowManager.LayoutParams mLayoutParams;
    private View mView;
    private int mX, mY;
    private boolean isRemove = false;
    private PermissionListener mPermissionListener;

    FloatPhone(Context applicationContext, PermissionListener permissionListener, Boolean childViewTouchable) {
        mContext = applicationContext;
        mPermissionListener = permissionListener;
        mWindowManager = (WindowManager) applicationContext.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        if (!childViewTouchable) {
            mLayoutParams.flags += WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }
        mLayoutParams.windowAnimations = 0;
    }

    @Override
    public void setSize(int width, int height) {
        mLayoutParams.width = width;
        mLayoutParams.height = height;
    }

    @Override
    public void setView(View view) {
        mView = view;
    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public void setGravity(int gravity, int xOffset, int yOffset) {
        mLayoutParams.gravity = gravity;
        mLayoutParams.x = mX = xOffset;
        mLayoutParams.y = mY = yOffset;
    }


    @Override
    public void init() {
        try {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            mWindowManager.addView(mView, mLayoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(mContext, "若未开启悬浮窗，请到设置中手动打开悬浮窗权限~", Toast.LENGTH_LONG).show();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
//            req();
//        } else if (Miui.rom()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                req();
//            } else {
//                mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
//                Miui.req(mContext, new PermissionListener() {
//                    @Override
//                    public void onSuccess() {
//                        mWindowManager.addView(mView, mLayoutParams);
//                        if (mPermissionListener != null) {
//                            mPermissionListener.onSuccess();
//                        }
//                    }
//
//                    @Override
//                    public void onFail() {
//                        if (mPermissionListener != null) {
//                            mPermissionListener.onFail();
//                        }
//                    }
//                });
//            }
//        } else {
//            try {
//                mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
//                mWindowManager.addView(mView, mLayoutParams);
//            } catch (Exception e) {
//                mWindowManager.removeView(mView);
//                LogUtil.e("TYPE_TOAST 失败");
//                req();
//            }
//        }
    }

    private void req() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        FloatActivity.request(mContext, new PermissionListener() {
            @Override
            public void onSuccess() {
                mWindowManager.addView(mView, mLayoutParams);
                if (mPermissionListener != null) {
                    mPermissionListener.onSuccess();
                }
            }

            @Override
            public void onFail() {
                if (mPermissionListener != null) {
                    mPermissionListener.onFail();
                }
            }
        });
    }

    @Override
    public void dismiss() {
        isRemove = true;
        try {
            mWindowManager.removeView(mView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateXY(int x, int y) {
        if (isRemove) {
            return;
        }
        mLayoutParams.x = mX = x;
        mLayoutParams.y = mY = y;
        mWindowManager.updateViewLayout(mView, mLayoutParams);
    }

    @Override
    public void updateX(int x) {
        if (isRemove) {
            return;
        }
        mLayoutParams.x = mX = x;
        mWindowManager.updateViewLayout(mView, mLayoutParams);
    }

    @Override
    public void updateY(int y) {
        if (isRemove) {
            return;
        }
        mLayoutParams.y = mY = y;
        mWindowManager.updateViewLayout(mView, mLayoutParams);
    }

    @Override
    public int getX() {
        return mX;
    }

    @Override
    public int getY() {
        return mY;
    }
}
