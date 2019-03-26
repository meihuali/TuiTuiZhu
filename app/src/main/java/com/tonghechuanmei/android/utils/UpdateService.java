/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTPig
 * Author：马靖乘
 * Date：18-11-30 下午5:45
 */

package com.tonghechuanmei.android.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

/**
 * Created by shandirong on 2018/3/13.
 */

public class UpdateService extends Service {
    private static String APK_URL = "";
    private static final String APK_NAME = "推推猪.apk";
    private NotificationManager nm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        RecursionDeleteFile(new File(Environment.getExternalStorageDirectory() + "/download/推推猪/" + APK_NAME));
        if (intent != null) {
            APK_URL = intent.getStringExtra("url");
        }
        startDownload();
        return Service.START_STICKY;
    }

    private void startDownload() {
        FileDownloader.setup(this);
        FileDownloader.getImpl().create(APK_URL)
                .setPath(Environment.getExternalStorageDirectory() + "/download/推推猪/" + APK_NAME)
                .setListener(new FileDownloadLargeFileListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        showNotification(UpdateService.this, (int) (soFarBytes * 100.0 / totalBytes), nm);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        installApkFile(UpdateService.this, Environment.getExternalStorageDirectory() + "/download/推推猪/" + APK_NAME);
                        AlertUtils.dismiss();
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    @SuppressLint("WrongConstant")
    public static void installApkFile(Context context, String fileUri) {
        Log.e("-------", "已完成");
        Intent intent = new Intent("android.intent.action.VIEW");
        File apkFile = new File(fileUri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.e("-------", "installApkFile: 进来了===============");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(
                    context, context.getPackageName() + ".fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            Log.e("-------", "installApkFile: 进来了1===============");
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void showNotification(Context context, int progress, NotificationManager nm) {

        String notificationName = "notification";

        // 适配8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 通知渠道
            NotificationChannel channel = new NotificationChannel("1001", notificationName, NotificationManager.IMPORTANCE_HIGH);

            channel.enableLights(false); // 是否在桌面icon右上角展示小红点
            channel.setShowBadge(false); // 是否在久按桌面图标时显示此渠道的通知
            channel.enableVibration(false);

            // 最后在notificationmanager中创建该通知渠道
            nm.createNotificationChannel(channel);
        }

        Notification.Builder builder = new Notification.Builder(context);

        //NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("1001");
        }

        builder.setContentTitle("正在下载 " + UpdateService.APK_NAME);
        builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
        builder.setProgress(100, progress, false);

        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }

        nm.notify(1, notification);
    }
}
