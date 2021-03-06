package com.example.circleview.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.aqumon.commonlib.base.BaseApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * ResourceUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-5-26
 */
public class ResourceUtils {

    private ResourceUtils() {
        throw new AssertionError();
    }

    public static String getString(int resId){
        return BaseApplication.getInstance().getResources().getString(resId);
    }

    public static String[] getStringArray(int resId) {
        return BaseApplication.getInstance().getResources().getStringArray(resId);
    }

    public static int getColor(int resId){
        return ContextCompat.getColor(BaseApplication.getInstance(), resId);
    }

    public static Drawable getDrawable(int drawableRes) {
        return ContextCompat.getDrawable(BaseApplication.getInstance(), drawableRes);
    }

    public static int getDimensionPixelSize(int dimenRes) {
        return BaseApplication.getInstance().getResources().getDimensionPixelSize(dimenRes);
    }

    /**
     * get an asset using ACCESS_STREAMING mode. This provides access to files that have been bundled with an
     * application as assets -- that is, files placed in to the "assets" directory.
     * 
     * @param context  context
     * @param fileName The name of the asset to open. This name can be hierarchical.
     * @return  geFileFromAssets
     */
    public static String geFileFromAssets(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        StringBuilder s = new StringBuilder();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get content from a raw resource. This can only be used with resources whose value is the name of an asset files
     * -- that is, it can be used to open drawable, sound, and raw resources; it will fail on string and color
     * resources.
     * 
     * @param context  context
     * @param resId The resource identifier to open, as generated by the appt tool.
     * @return  geFileFromRaw
     */
    public static String geFileFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        StringBuilder s = new StringBuilder();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param context 上下文
     * @param fileName  文件的名字
     * @return     返回资源下的文件
     */
    public static List<String> geFileToListFromAssets(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            br.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取asset
     */
    public static String readAssetsAsString(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = null;
        BufferedReader bf = null;
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            inputStreamReader = new InputStreamReader(assetManager.open(fileName));
            //通过管理器打开文件并读取
            bf = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIOQuietly(bf, inputStreamReader);
        }
        return stringBuilder.toString();
    }

    /**
     *
     * @param context  上下文
     * @param resId id
     * @return   raw下的文件
     */
    public static List<String> geFileToListFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            reader = new BufferedReader(in);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
