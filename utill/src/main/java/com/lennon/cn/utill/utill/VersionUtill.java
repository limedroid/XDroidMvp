package com.lennon.cn.utill.utill;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import androidx.core.app.ActivityCompat;

import com.lennon.cn.utill.base.BaseApplication;

/**
 * 作者：11361 on 2019/1/24 10:07
 * <p>
 * 邮箱：1136160757@qq.com
 */
public class VersionUtill {
    @SuppressLint({"WifiManagerLeak", "MissingPermission", "HardwareIds"})
    public static String getAndroidId(Context context) {
        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.BLUETOOTH,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context,
                Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            if (context instanceof Activity) {
                ActivityCompat.requestPermissions(
                        (Activity) context,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }else if (BaseApplication.Companion.getCuttureActivity()!=null){
                ActivityCompat.requestPermissions(
                        BaseApplication.Companion.getCuttureActivity(),
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }
        }
        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String m_szBTMAC = "";
        if (m_BluetoothAdapter != null) {
            m_szBTMAC = m_BluetoothAdapter.getAddress();//蓝牙MAC地址
        }
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();//wifi MAC地址
        String m_szDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 +//主板编号
                Build.BRAND.length() % 10 +//系统定制商
                Build.CPU_ABI.length() % 10 +//cpu指令集
                Build.DEVICE.length() % 10 +//设备参数
                Build.DISPLAY.length() % 10 +//显示屏参数
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +//修订版本列表
                Build.MANUFACTURER.length() % 10 +//硬件制造商
                Build.MODEL.length() % 10 +//版本即最终用户可见的名称
                Build.PRODUCT.length() % 10 +//整个产品的名称
                Build.TAGS.length() % 10 +//描述build的标签,如未签名，debug等等
                Build.TYPE.length() % 10 +//build的类型
                Build.USER.length() % 10; //13 digits
        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        String m_szImei = TelephonyMgr.getDeviceId();
        String m_szLongID = m_szImei + m_szDevIDShort
                + m_szAndroidID + m_szWLANMAC + m_szBTMAC;
// compute md5
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
// get md5 bytes
        byte p_md5Data[] = m.digest();
// create a hex string
        StringBuilder m_szUniqueID = new StringBuilder();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
// if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF) {
                m_szUniqueID.append("0");
            }
// add number to string
            m_szUniqueID.append(Integer.toHexString(b));
        }   // hex string to uppercase
        m_szUniqueID = new StringBuilder(m_szUniqueID.toString().toUpperCase());
        return m_szUniqueID.toString();
    }

    @SuppressLint("MissingPermission")
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }
}
