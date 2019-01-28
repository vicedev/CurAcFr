package com.vice.curacfrlib;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.vice.curacfrlib.floatwindow.constant.Screen;
import com.vice.curacfrlib.floatwindow.interfaces.ViewStateListener;
import com.vice.curacfrlib.floatwindow.view.FloatWindow;

/**
 * author:vice
 * email:vicedev1001@gmail.com
 * date:2019/1/24 11:23
 */
public class FloatViewManager {

    private TextView mTvCurrentActivity;
    private TextView mTvCurrentFragment;
    private View mFloatView;

    private static class SingletonHolder {

        private static final FloatViewManager INSTANCE = new FloatViewManager();

    }

    private FloatViewManager (){}

    public static FloatViewManager getInstance() {

        return SingletonHolder.INSTANCE;

    }

    public void showFloat(final Context context, final ViewStateListener viewStateListener, final View.OnClickListener onCloseClickListener) {

        if (mFloatView == null) {
            mFloatView = View.inflate(context, R.layout.float_view_layout, null);

            mTvCurrentActivity = mFloatView.findViewById(R.id.tv_current_activity);
            mTvCurrentFragment = mFloatView.findViewById(R.id.tv_current_fragment);

            mFloatView.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCloseClickListener != null) {
                        onCloseClickListener.onClick(v);
                    }
                }
            });
        }

        FloatWindow
                .with(context)
                .setView(mFloatView)
                .setWidth(Screen.width, 1.0f)             //设置控件宽高
//                .setHeight(Screen.width, 0.2f)
//                .setX(100)                                   //设置控件初始位置
                .setY(Screen.height, 0.1f)
                .setDesktopShow(true)                        //桌面显示
                .setViewStateListener(viewStateListener)    //监听悬浮控件状态改变
//                .setPermissionListener(new PermissionListener() {
//                    @Override
//                    public void onSuccess() {
//                        Log.d("CurAcFrTag","onSuccess");
//                    }
//
//                    @Override
//                    public void onFail() {
//                        Log.d("CurAcFrTag","onFail");
//                        Toast.makeText(context, "请到设置中手动打开悬浮窗权限~", Toast.LENGTH_LONG).show();
//                    }
//                })  //监听权限申请结果
                .build();
    }

    public void hideFloat() {
        FloatWindow.destroy();
    }

    public void setCurrentActivityInfo(String currentActivityName) {
        if (mTvCurrentActivity != null) {
            mTvCurrentActivity.setText("当前Activity：\n" + currentActivityName);
        }
    }

    public void setCurrentFragmentInfo(String currentFragmentName) {
        if (mTvCurrentFragment != null) {
            mTvCurrentFragment.setText("当前Fragment：\n" + currentFragmentName);
        }
    }
}
