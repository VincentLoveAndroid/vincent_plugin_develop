package com.example.mingren.vincent_plugin_develop;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

/**
 * Created by vincent on 2016/11/11.
 * email-address:674928145@qq.com
 * description:
 */

public class PluginResources extends Resources {
    /**
     * @param assets
     * @param metrics
     * @param config
     * @deprecated
     */
    public PluginResources(AssetManager assets, DisplayMetrics metrics, Configuration config) {
        super(assets, metrics, config);
    }

    /**
     * 得到插件的Resources资源
     *
     * @param res       宿主的res
     * @param pluginApk 插件apk
     * @return
     */
    public static PluginResources getPluginResources(Resources res, File pluginApk) {
        if (getPluginAssetManager(pluginApk) == null) return null;
        PluginResources pluginResources = new PluginResources(getPluginAssetManager(pluginApk), res.getDisplayMetrics(), res.getConfiguration());
        return pluginResources;
    }

    /**
     * 自定义加载插件apk的AssetManager
     */
    private static AssetManager getPluginAssetManager(File pluginApk) {
        //AssetManager assets = new AssetManager();不能new，隐藏的方法
        try {
            Class<?> forName = Class.forName("android.content.res.AssetManager");
            AssetManager assetManager = AssetManager.class.newInstance();//创建AssetManager实例
            Method addAssetPath = forName.getDeclaredMethod("addAssetPath", String.class);//得到addAssetPath方法
            addAssetPath.invoke(assetManager, pluginApk.getAbsolutePath());//调用assetManager里面的addAssetPath方法
            return assetManager;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
