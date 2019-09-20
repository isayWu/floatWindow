package com.isay.cywu.floatwindow;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import com.isay.cywu.floatwindow.floatwindow.FloatWindow;
import com.isay.cywu.floatwindow.floatwindow.MoveType;
import com.isay.cywu.floatwindow.floatwindow.PermissionListener;
import com.isay.cywu.floatwindow.floatwindow.Screen;
import com.isay.cywu.floatwindow.floatwindow.ViewStateListener;
import com.isay.cywu.floatwindow.permission.FloatWindowManager;
import com.isay.cywu.floatwindow.service.FloatWindowService;

/**
 * @author cywu4
 * @contact_qq 727643332
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_apply_permission).setOnClickListener(this);
        findViewById(R.id.btn_open_float_window).setOnClickListener(this);
        findViewById(R.id.btn_hide_float_window).setOnClickListener(this);
        findViewById(R.id.btn_show_float_window).setOnClickListener(this);
        findViewById(R.id.btn_close_float_window).setOnClickListener(this);
        findViewById(R.id.btn_open_service).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_apply_permission:
                applyPermission();
                break;
            case R.id.btn_open_float_window:
                openFloatWindow();
                break;
            case R.id.btn_hide_float_window:
                hideFloatWindow();
                break;
            case R.id.btn_show_float_window:
                showFloatWindow();
                break;
            case R.id.btn_close_float_window:
                closeFloatWindow();
                break;
            case R.id.btn_open_service:
                FloatWindowService.start(getApplication());
                break;
            default:
                break;
        }
    }


    //申请权限
    private void applyPermission() {
        if (FloatWindowManager.getInstance().checkPermission(getApplicationContext())) {
            //已开启权限
            Toast.makeText(this, "已开启权限", Toast.LENGTH_SHORT).show();
        } else {
            //去申请权限,里面有弹窗，不要传ApplicationContext
            FloatWindowManager.getInstance().applyPermission(this);
        }
    }


    //隐藏悬浮窗
    private void hideFloatWindow() {
        if (FloatWindow.get() != null) {
            FloatWindow.get().hide();
        }
    }

    //显示悬浮窗
    private void showFloatWindow() {
        if (FloatWindow.get() != null) {
            FloatWindow.get().show();
        }
    }

    //关闭悬浮窗
    private void closeFloatWindow() {
        if (FloatWindow.get() != null) {
            FloatWindow.destroy();
        }
    }


    //开启悬浮窗
    private void openFloatWindow() {
        if (FloatWindowManager.getInstance().checkPermission(getApplicationContext())) {
            if (FloatWindow.get() == null) {
                FloatView floatView = new FloatView(this);
                FloatWindow
                        .with(getApplication())
                        .setView(floatView)
                        .setX(Screen.width, 0.5f)
                        .setY(Screen.height, 0.5f)
                        .setMoveType(MoveType.active, 0, 0)
                        .setMoveStyle(500, new BounceInterpolator())
                        .setFilter(true, MainActivity.class)//设置MainActivity.class显示悬浮窗true
                        .setViewStateListener(mStateListener)
                        .setPermissionListener(mPermissionListener)
                        .setDesktopShow(true)
                        .build();
                FloatWindow.get().show();
            }
        } else {
            applyPermission();
        }
    }


    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onFail() {

        }
    };

    private ViewStateListener mStateListener = new ViewStateListener() {
        @Override
        public void onPositionUpdate(int x, int y) {

        }

        @Override
        public void onShow() {

        }

        @Override
        public void onHide() {

        }

        @Override
        public void onDismiss() {

        }

        @Override
        public void onMoveAnimStart() {

        }

        @Override
        public void onMoveAnimEnd() {

        }

        @Override
        public void onBackToDesktop() {

        }
    };


}
