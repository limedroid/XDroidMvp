package com.lennon.cn.utill.utill;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import cn.droidlover.xdroidmvp.log.XLog;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GetDeviceId {

    /**
     * 获取设备唯一标识符
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Activity context) {
        //读取保存的在sd卡中的唯一标识符
        List<String> deviceIds = readDeviceID(context);
        if (deviceIds != null) {
            XLog.e("readDeviceID-------------------------" + deviceIds.toString());
        }
        if (deviceIds != null && deviceIds.size() > 0) {
            if (deviceIds.size() == 1) {
                String deviceId = deviceIds.get(0);
                //判断是否已经生成过,
                if (deviceId != null && !"".equals(deviceId)) {
                    if (deviceId.contains("-") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        return VersionUtill.getUUID();
                    }
                    return deviceId.replace("deviceId:", "").trim();
                }
            } else {
                if (!TextUtils.isEmpty(deviceIds.get(0))) {
                    String deviceId = deviceIds.get(0).replace("deviceId:", "").trim();
                    //判断是否已经生成过,
                    if (!"".equals(deviceId)) {
                        return deviceId;
                    }
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return VersionUtill.getUUID();
        } else {
            return VersionUtill.getAndroidId(context);
        }
    }

    /**
     * 读取固定的文件中的内容,这里就是读取sd卡中保存的设备唯一标识符
     *
     * @param context
     * @return
     */
    public static List<String> readDeviceID(Context context) {
        File newFile = getNewDevicesDir(context);
        List<String> strings = getFileContent(new File(newFile.getAbsolutePath() + "/", "devices.txt"));
        if (strings.size() > 0) {
            return strings;
        }
        return new ArrayList<>();
    }

    /**
     * 保存 内容到 SD卡中,  这里保存的就是 设备唯一标识符
     *
     * @param str
     * @param context
     */
    public static void saveDeviceID(String str, Context context) {
        File newFile = getNewDevicesDir(context);
        File f = new File(newFile.getPath(), newFile.getName() + ".txt");
        try {
            if (f.exists()) {
                f.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            XLog.e(e.getMessage());
        }

        writeTxtToFile("deviceId:" + str, newFile.getAbsolutePath() + "/", "devices.txt");

        writeTxtToFile("deviceId-version:2", newFile.getAbsolutePath() + "/", "devices.txt");


    }


    // 将字符串写入到文本文件中
    private static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                XLog.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            XLog.e("TestFile", "Error on write File:" + e);
        }
    }

//生成文件

    private static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

//生成文件夹

    private static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            XLog.e("error:", e + "");
        }
    }

    //读取指定目录下的所有TXT文件的文件内容
    private static List<String> getFileContent(File file) {
        List<String> content = new ArrayList<>();
        if (!file.isDirectory()) {  //检查此路径名的文件是否是一个目录(文件夹)
//            if (file.getName().endsWith("txt")) {//文件格式为""文件
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null) {
                    InputStreamReader inputreader
                            = new InputStreamReader(instream, "UTF-8");
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line = "";
                    //分行读取
                    while ((line = buffreader.readLine()) != null) {
                        String s = "" + line;
                        content.add(s);
                    }
                    instream.close();//关闭输入流
                }
            } catch (java.io.FileNotFoundException e) {
                XLog.e("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                XLog.e("TestFile", e.getMessage());
            }
//            }
        }
        return content;
    }

    private static File getNewDevicesDir(Context context) {
        File mCropFile = null;
        mCropFile = new File(context.getFilesDir(), "devices");
        if (!mCropFile.getParentFile().exists()) {
            mCropFile.getParentFile().mkdirs();
        }
        XLog.e("getDevicesDir-----------------" + context.getFilesDir() + "-------------" + mCropFile.getParent() + "-----------------" + mCropFile.getName() + "-------------" + mCropFile.getPath());
        return mCropFile;
    }

}

