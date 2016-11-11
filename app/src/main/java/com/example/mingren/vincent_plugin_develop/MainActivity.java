package com.example.mingren.vincent_plugin_develop;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileWriter;

public class MainActivity extends Activity implements View.OnClickListener {
    private ImageView ivCloud;
    private String pluginPackageName;//插件包名
    private String pluginApkName;//插件apk名
    private String runningPlugin = "";//当前播放的插件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivCloud = (ImageView) findViewById(R.id.iv_cloud);
        findViewById(R.id.bt_anim1).setOnClickListener(this);
        findViewById(R.id.bt_anim2).setOnClickListener(this);
        findViewById(R.id.bt_anim3).setOnClickListener(this);
        findViewById(R.id.bt_uninstall_1).setOnClickListener(this);
        findViewById(R.id.bt_uninstall_2).setOnClickListener(this);
        findViewById(R.id.bt_uninstall_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_anim1:
                pluginPackageName = "com.example.plugin_demo_1";
                pluginApkName = "plugin_demo_1.apk";
                DownLoadPluginHelper.INSTANCE.downLoadAndPlayPlugin(this, pluginApkName, pluginPackageName, getResListener);
                break;
            case R.id.bt_anim2:
                pluginPackageName = "com.example.plugin_demo_2";
                pluginApkName = "plugin_demo_2.apk";
                DownLoadPluginHelper.INSTANCE.downLoadAndPlayPlugin(this, pluginApkName, pluginPackageName, getResListener);
                break;
            case R.id.bt_anim3:
                pluginPackageName = "com.example.plugin_demo_3";
                pluginApkName = "plugin_demo_3.apk";
                DownLoadPluginHelper.INSTANCE.downLoadAndPlayPlugin(this, pluginApkName, pluginPackageName, getResListener);
                break;
            case R.id.bt_uninstall_1:
                if (runningPlugin == "plugin_demo_1.apk") {
                    ivCloud.setBackgroundDrawable(null);
                    runningPlugin = "";
                }
                pluginApkName = "plugin_demo_1.apk";
                DownLoadPluginHelper.INSTANCE.InstallPlugin(this, pluginApkName);
                break;
            case R.id.bt_uninstall_2:
                if (runningPlugin == "plugin_demo_2.apk") {
                    ivCloud.setBackgroundDrawable(null);
                    runningPlugin = "";
                }
                pluginApkName = "plugin_demo_2.apk";
                DownLoadPluginHelper.INSTANCE.InstallPlugin(this, pluginApkName);
                break;
            case R.id.bt_uninstall_3:
                if (runningPlugin == "plugin_demo_3.apk") {
                    ivCloud.setBackgroundDrawable(null);
                    runningPlugin = "";
                }
                pluginApkName = "plugin_demo_3.apk";
                DownLoadPluginHelper.INSTANCE.InstallPlugin(this, pluginApkName);
                break;

        }
    }

    private void startAnim(View view) {
        Drawable background = view.getBackground();
        if (background == null) return;
        if (background instanceof AnimationDrawable) {
            if (((AnimationDrawable) background).isRunning()) {
                ((AnimationDrawable) background).stop();
            } else {
                ((AnimationDrawable) background).stop();
                ((AnimationDrawable) background).start();
            }
        }
    }

    private DownLoadPluginHelper.getResListener getResListener = new DownLoadPluginHelper.getResListener() {
        @Override
        public void getDrawable(Drawable drawable, String pluginToRun) {
            if (drawable == null) return;
            runningPlugin = pluginToRun;
            ivCloud.setBackgroundDrawable(drawable);
            startAnim(ivCloud);
        }
    };
}
