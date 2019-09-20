# floatWindow
悬浮窗：可以设置是否在桌面展示，是否在某个Activity才展示

最近公司有个需求，开发一个悬浮窗。要求悬浮窗能在桌面进行展示，时间给的很紧张，好在github上找到两个比较好用的库（如下）。在实际开发中发现结合这两个库一起用比较好，另外还稍微修改了我在其中遇到的一些缺陷，以及加入了前台通知。现在抽空把代码剥离出来了，仅仅供各位开发者参考使用。

1、悬浮窗代码参考 ： https://github.com/yhaolpz/FloatWindow 

2、悬浮窗权限参考 ： https://github.com/zhaozepeng/FloatWindowPermission 


演示图：
 
![image](https://github.com/isayWu/floatWindow/blob/master/images/demo_2.gif)


以下是悬浮的使用方式，完成参考@yhaolpz的github项目：  https://github.com/yhaolpz/FloatWindow 

这里也贴出一份摘抄的悬浮窗的使用方式：

## 特性：

1.支持拖动，提供自动贴边等动画

2.内部自动进行权限申请操作

3.可自由指定要显示悬浮窗的界面

4.应用退到后台时，悬浮窗会自动隐藏

5.除小米外，4.4~7.0 无需权限申请

6.位置及宽高可设置百分比值，轻松适配各分辨率

7.支持权限申请结果、位置等状态监听

8.链式调用，简洁清爽


## 权限声明：

 <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 
 
## 基础使用

      FloatWindow
           .with(getApplicationContext())
           .setView(view)
           .setWidth(100)                               //设置控件宽高
           .setHeight(Screen.width,0.2f)
           .setX(100)                                   //设置控件初始位置
           .setY(Screen.height,0.3f)
           .setDesktopShow(true)                        //桌面显示
           .setViewStateListener(mViewStateListener)    //监听悬浮控件状态改变
           .setPermissionListener(mPermissionListener)  //监听权限申请结果
           .build();
              
## 指定界面显示

             .setFilter(true, A_Activity.class, C_Activity.class)
此方法表示 A_Activity、C_Activity 显示悬浮窗，其他界面隐藏。

             .setFilter(false, B_Activity.class)
此方法表示 B_Activity 隐藏悬浮窗，其他界面显示。

             .setFilter(true, BaseActivity.class)
注意：setFilter 方法参数可以识别该 Activity 的子类。也就是说，如果 A_Activity、C_Activity 继承自 BaseActivity，你可以这样设置.
            
              
## 可拖动悬浮窗及回弹动画

              .setMoveType(MoveType
