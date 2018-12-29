package com.example.a3droplets.beijingnewsdemo.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Mickey_Ma on 2018/12/28.
 * Description:缓存工具类
 */
public class CacheUtils {
    /**
     * 保存参数
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("amy",Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }
    /**
     * 得到保存的参数
     * @param context  上下文
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("amy",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }

    /**
     * 缓存文本信息
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context,String key, String value){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //SDCard可用
            //就用文本缓存到sdCard
            try{
                String filename = MD5Encoder.enconde(key);
                String dir = Environment.getExternalStorageDirectory() + "/beijingnewsdemo/txt/";
                File file = new File(dir, filename);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()){
                    parentFile.mkdirs();//创建多级目录
                }
                //创建新文件
                if (!file.exists()){
                    file.createNewFile();
                }
                //保存到sdcard上
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(value.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            SharedPreferences sharedPreferences = context.getSharedPreferences("amy",Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(key, value).commit();
        }
    }

    /**
     * 得到缓存的文本信息
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key){
        String result = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //SDCard可用
            //就用文本缓存到sdCard
            try{
                String filename = MD5Encoder.enconde(key);
                String dir = Environment.getExternalStorageDirectory() + "/beijingnewsdemo/txt/";
                File file = new File(dir, filename);
                //创建新文件
                if (!file.exists()){
                    int length;
                    byte[] buffer = new byte[1024];

                    //文件输入流
                    FileInputStream inputStream = new FileInputStream(file);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                    while ((length = inputStream.read(buffer))!= -1){
                        outputStream.write(buffer,0,length);
                    }

                    //转换成字符串
                    result = outputStream.toString();
                    inputStream.close();
                    outputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            SharedPreferences sharedPreferences = context.getSharedPreferences("amy",Context.MODE_PRIVATE);
            result = sharedPreferences.getString(key, "");
        }
        return result;
    }

}
