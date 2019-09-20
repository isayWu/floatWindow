package com.isay.cywu.floatwindow.service;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.isay.cywu.floatwindow.R;


/**
 * Created by Wucy on 2019/9/5 19:45
 * des：开启悬浮窗的时候，开启前台服务，提高应用优先级
 */
public class FloatWindowService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Intent notificationIntent = new Intent(this, MainActivity.class);
        // PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentTitle("悬浮窗");
        builder.setContentText("悬浮窗正在运行");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // builder.setContentIntent(pendingIntent);
        // 设置通知出现时不震动
        builder.setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE);
        builder.setVibrate(new long[]{0});
        builder.setSound(null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("com.isay.cywu.floatwindow", "floatService",
                    NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId("com.isay.cywu.floatwindow");
            // 设置通知出现时震动
            channel.enableVibration(true);
            channel.enableLights(false);
            channel.setVibrationPattern(new long[]{0});
            channel.setSound(null, null);
            //设置channel
            NotificationManager notificationChannel = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (notificationChannel != null) {
                notificationChannel.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        //如果 id 为 0 ，那么状态栏的 notification 将不会显示。
        startForeground(111, notification);//启动前台服务

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
    }


    public static void start(Application application) {
        try {
            Context context = application;
            Intent intent = new Intent(context, FloatWindowService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop(Application application) {
        try {
            Context context = application;
            Intent intent = new Intent(context, FloatWindowService.class);
            context.stopService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
