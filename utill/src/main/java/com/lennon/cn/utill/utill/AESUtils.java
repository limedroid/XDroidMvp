package com.lennon.cn.utill.utill;

import android.os.Build;

import androidx.annotation.RequiresApi;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import kotlin.text.Charsets;


public class AESUtils {

    public static final String PASSWARDKEY_STRING = "KrOrO67PfIfjOjqe";
    private static final String IV_STRING = "16-Bytes--String";

    public AESUtils() {
    }

    public static byte[] decryptAESByte(String content, String key) throws Exception {
        byte[] encryptedBytes = ByteUtil.hexToByteArray(content);
        byte[] enCodeFormat = key.getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, "AES");
        byte[] initParam = "16-Bytes--String".getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secretKey, ivParameterSpec);
        byte[] result = cipher.doFinal(encryptedBytes);
        return result;
    }

    public static String encryptAES(byte[] byteContent, String key) throws Exception {
        byte[] enCodeFormat = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
        byte[] initParam = "16-Bytes--String".getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(byteContent);
        return ByteUtil.byteToHexStr(encryptedBytes);
    }

    public static String encryptAES(String content, String key) throws Exception {
        byte[] byteContent = content.getBytes("UTF-8");
        byte[] enCodeFormat = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
        byte[] initParam = "16-Bytes--String".getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(byteContent);
        return toHex(encryptedBytes);
    }

    public static String toHex(byte[] buffer) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);

        for (int i = 0; i < buffer.length; ++i) {
            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 15, 16));
        }

        return sb.toString();
    }

    public static byte[] fromHex(String hex) {
        byte[] res = new byte[hex.length() / 2];
        char[] chs = hex.toCharArray();
        int i = 0;

        for (int c = 0; i < chs.length; ++c) {
            res[c] = (byte) Integer.parseInt(new String(chs, i, 2), 16);
            i += 2;
        }

        return res;
    }

    public static String decryptAES(String content, String key) throws Exception {
        byte[] encryptedBytes = fromHex(content);
        byte[] enCodeFormat = key.getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, "AES");
        byte[] initParam = "16-Bytes--String".getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secretKey, ivParameterSpec);
        byte[] result = cipher.doFinal(encryptedBytes);
        return new String(result, "UTF-8");
    }

}
