package com.example.mingren.vincent_plugin_develop;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

/**
 * Created by vincent on 2016/11/11.
 * email-address:674928145@qq.com
 * description:
 */

public enum DownLoadPluginHelper {

    INSTANCE;


    public void downLoadAndPlayPlugin(Context context, String pluginApkName, String pluginPackageName, getResListener listener) {
        String pluginPath = context.getCacheDir() + "/" + pluginApkName;
        File file = new File(pluginPath);
        if (file.exists()) {
            PluginResources pluginResources = PluginResources.getPluginResources(context.getResources(), file);
            //反射R文件
            DexClassLoader classLoader = new DexClassLoader(file.getAbsolutePath(), context.getDir(pluginApkName, Context.MODE_PRIVATE).getAbsolutePath(), null, context.getClassLoader());
            try {
                Class<?> aClass = classLoader.loadClass(pluginPackageName + ".R$anim");//内部类表示用$
                Field[] declaredFields = aClass.getDeclaredFields();
                for (Field field : declaredFields) {
                    System.out.println(field.getName() + "呵呵");
                    if (field.getName().equals("anim")) {//找到变量名
                        int anInt = field.getInt(R.anim.class);//找到变量名再相应的类里面对应的id
                        Drawable drawable = pluginResources.getDrawable(anInt);//得到相应的资源
                        if (listener != null) listener.getDrawable(drawable,pluginApkName);
                    }
                }

            } catch (ClassNotFoundException e) {
                System.out.println("异常" + e.toString());
            } catch (IllegalAccessException e) {
                System.out.println("异常" + e.toString());
            }
        } else {
            //下载插件
            File pluginFile = new File(pluginPath);
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = context.getAssets().open(pluginApkName);
                byte[] buffer = new byte[1024];
                int len;
                fos = new FileOutputStream(pluginFile);
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                Toast.makeText(context, "下载插件" + pluginApkName + "完成", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void InstallPlugin(Context context, String pluginApkName) {
        String pluginPath = context.getCacheDir() + "/" + pluginApkName;
        File file = new File(pluginPath);
        if (file.exists()) {
            file.delete();
            Toast.makeText(context, "卸载插件" + pluginApkName + "完成", Toast.LENGTH_SHORT).show();
        }
    }

    public interface getResListener {
        void getDrawable(Drawable drawable,String pluginToRun);
    }
}
