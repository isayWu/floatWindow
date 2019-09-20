package com.isay.cywu.floatwindow.floatwindow;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yhao on 2017/12/22.
 * https://github.com/yhaolpz
 */

public class MoveType {

    //MoveType.slide : 可拖动，释放后自动贴边 （默认）
    //MoveType.back : 可拖动，释放后自动回到原位置
    //MoveType.active : 可拖动
    //MoveType.inactive : 不可拖动

    static final int fixed = 0;
    public static final int inactive = 1;
    public static final int active = 2;
    public static final int slide = 3;
    public static final int back = 4;

    @IntDef({fixed, inactive, active, slide, back})
    @Retention(RetentionPolicy.SOURCE)
    @interface MOVE_TYPE {
    }
}
