package com.isay.cywu.floatwindow;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Wucy on 2019/9/6 19:28
 * des：悬浮窗view
 */
public class FloatView extends LinearLayout implements View.OnClickListener {

    private View mFloatBtn;
    private View mFloatContent;

    public FloatView(Context context) {
        this(context, null);
    }

    public FloatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_float_window, this);
        initView();
    }

    private void initView() {
        mFloatContent = findViewById(R.id.view_float_content);
        //点击展开
        mFloatBtn = findViewById(R.id.view_float_open);
        setOnClickListener(this);
        //点击收起
        findViewById(R.id.view_float_fold).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_float_fold:
                mFloatContent.setVisibility(GONE);
                mFloatBtn.setVisibility(VISIBLE);
                break;
            default:
                mFloatContent.setVisibility(VISIBLE);
                mFloatBtn.setVisibility(GONE);
                break;
        }
    }
}
