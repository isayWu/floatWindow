package com.isay.cywu.floatwindow.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
//
//import com.framwork.utils.statusbar.StatusBarBottomUtil;
//import com.framwork.utils.statusbar.StatusBarUtil;
//import com.hysoso.www.utillibrary.ScreenUtil;
//import com.youth.banner.util.DensityUtil;

/**
 * Created by yhao on 17-11-14.
 * https://github.com/yhaolpz
 */

public class FloatPhone extends FloatView {

    private final Context mContext;

    private final WindowManager mWindowManager;
    private final WindowManager.LayoutParams mLayoutParams;
    private View mView;
    private int mX, mY;
    private boolean isRemove = false;
    private PermissionListener mPermissionListener;

    private static int mDefaultBtnSize = 0;
    private static int mDefaultTopSize = 0;
    private static int mDefaultBottomSize = 0;
    private static Point mPointScreenSize = null;

    FloatPhone(Context applicationContext, PermissionListener permissionListener) {
        mContext = applicationContext;
        mPermissionListener = permissionListener;
        mWindowManager = (WindowManager) applicationContext.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mLayoutParams.windowAnimations = 0;

    }

    /**
     * 屏幕大小改变：横竖屏
     *
     * @param context
     */
    public static void onScreenSizeChange(Context context, Point screenSize) {
        mPointScreenSize = screenSize;
//        mDefaultBtnSize = DensityUtil.dp2px(context, 58);
//        mDefaultTopSize = StatusBarUtil.getStatusBarHeight(context);
//        int bottom = StatusBarBottomUtil.getBottomStatusHeight(context);
//        if (bottom > 0) {
//            mDefaultBottomSize = bottom;
//        }
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
    public void setGravity(int gravity, int xOffset, int yOffset) {
        mLayoutParams.gravity = gravity;
        mLayoutParams.x = mX = xOffset;
        mLayoutParams.y = mY = yOffset;
    }


    @Override
    public void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            req();
        } else if (Miui.rom()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                req();
            } else {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                Miui.req(mContext, new PermissionListener() {
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
        } else {
            try {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
                mWindowManager.addView(mView, mLayoutParams);
            } catch (Exception e) {
                mWindowManager.removeView(mView);
                LogUtil.e("TYPE_TOAST 失败");
                req();
            }
        }
    }

    private void req() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        FPermission.request(mContext, new PermissionListener() {
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
        mWindowManager.removeView(mView);
    }


    @Override
    public void updateXY(int x, int y) {
        if (isRemove) return;

        Point point = mPointScreenSize;
        if (point == null) {
            point = ScreenUtil.getCurScreenSize(mContext);
        }
        if (point != null && point.x > point.y) {
            //横屏
            if (x < 0) {
                x = 0;
            }
            if (x > point.x - mDefaultBtnSize + mDefaultBottomSize) {
                x = point.x - mDefaultBtnSize + mDefaultBottomSize;
            }
            if (y < -mDefaultTopSize) {
                y = -mDefaultTopSize;
            }
            if (y > point.y - mDefaultBtnSize - mDefaultTopSize) {
                y = point.y - mDefaultBtnSize - mDefaultTopSize;
            }
        } else {
            //竖屏
            if (x < 0) {
                x = 0;
            }
            if (x > point.x - mDefaultBtnSize) {
                x = point.x - mDefaultBtnSize;
            }
            if (y < -mDefaultTopSize) {
                y = -mDefaultTopSize;
            }
            if (y > point.y - mDefaultBtnSize) {
                y = point.y - mDefaultBtnSize;
            }
        }


        mLayoutParams.x = mX = x;
        mLayoutParams.y = mY = y;
        mWindowManager.updateViewLayout(mView, mLayoutParams);
    }

    @Override
    void updateX(int x) {
        if (isRemove) return;
        mLayoutParams.x = mX = x;
        mWindowManager.updateViewLayout(mView, mLayoutParams);
    }

    @Override
    void updateY(int y) {
        if (isRemove) return;
        mLayoutParams.y = mY = y;
        mWindowManager.updateViewLayout(mView, mLayoutParams);
    }

    @Override
    int getX() {
        return mX;
    }

    @Override
    int getY() {
        return mY;
    }


}
