package com.lennon.cn.utill.utill.photo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;


import androidx.core.content.FileProvider;

import com.lennon.cn.utill.conf.Lennon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SelectPicUtil {

    private static final String temp = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp.jpg";

    public static final int GET_BY_ALBUM = 801;// 打开相册
    public static final int GET_BY_CAMERA = 802;// 打开相机
    public static final int CROP = 803;// 裁剪图片

    public static void getByAlbum(Activity act) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            act.startActivityForResult(intent, GET_BY_ALBUM);
        } else {
            Intent intentFromGallery = new Intent();
            intentFromGallery.setType("image/*");
            intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
            act.startActivityForResult(intentFromGallery, GET_BY_ALBUM);
        }
    }

    public static void getByCamera(Activity act) {
        getByCamera(act, temp, GET_BY_CAMERA);
    }

    // 给其他地方用
    public static void getByCamera(Activity act, String path, int requestCode) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri = getUri(act, new File(path));
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            takePictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            act.startActivityForResult(takePictureIntent, requestCode);
        } else {
            Toast.makeText(act, "请安装sd卡", Toast.LENGTH_SHORT).show();
        }
    }

    public static Uri onActivityResult(Activity act, int requestCode,
                                       int resultCode, Intent data) {
        return onActivityResult(act, requestCode, resultCode, data, 0, 0, 0, 0, false);
    }

    public static Uri onActivityResult(Activity act, int requestCode,
                                       int resultCode, Intent data, int w, int h, int aspectX, int aspectY) {
        return onActivityResult(act, requestCode, resultCode, data, w, h, aspectX, aspectY, true);
    }

    public static Uri onActivityResult(Activity act, int requestCode,
                                       int resultCode, Intent data, int outputX, int outputY, int aspectX, int aspectY, boolean isCut) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            switch (requestCode) {
                case GET_BY_ALBUM:
                    if (isCut) {
                        if (null == data) return null;
                        String path = getImageAbsolutePath(act, data.getData());
                        if (TextUtils.isEmpty(path)) return null;
                        uri = getUri(act, new File(path));
                    } else {
                        return data.getData();
                    }
                    break;
                case GET_BY_CAMERA:
                    if (isCut) {
                        uri = getUri(act, new File(temp));
                    } else {
                        return Uri.parse(temp);
                    }
                    break;
                case CROP:
//                    return getUri(act, new File(temp));
                    return Uri.fromFile(new File(temp));
            }
            if (isCut && null != uri) {
                crop(act, uri, outputX, outputY, aspectX, aspectY);
                return null;
            }
        }
        return null;
    }

    public static void crop(Activity act, Uri uri, int outputX, int outputY, int aspectX, int aspectY) {
        if (outputX == 0 && outputY == 0) {
            outputX = outputY = 480;
        }
        if (aspectX == 0 && aspectY == 0) {
            aspectX = aspectY = 1;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);

//        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri(act, new File(temp)));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(temp)));

        intent.putExtra("outputFormat", "JPEG");

        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);
        act.startActivityForResult(intent, CROP);
    }

    public static String getImageAbsolutePath(Activity context, Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null,
                    null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    public static Uri getUri(Context context, File file) {
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // BuildConfig 是工程包路径下的：如：com.yingyou.demo.BuildConfig
            fileUri = FileProvider.getUriForFile(context, Lennon.Companion.getFileProvide(), file);
        } else {
            fileUri = Uri.fromFile(file);
        }

        return fileUri;
    }

    public static Bitmap getImageThumbnail(String imagePath, int width,
                                           int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        // Options中有个属性inJustDecodeBounds。我们可以充分利用它，来避免大图片的溢出问题
        options.inJustDecodeBounds = true;// 设置为true可以不加载到内存，直接获取Bitmap宽高
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        if (bitmap == null) {
            // 计算缩放比
            int h = options.outHeight;// 获取Bitmap的实际高度
            int w = options.outWidth;// 获取Bitmap的实际宽度

            int beWidth = w / width;
            int beHeight = h / height;
            int rate = 1;
            if (beWidth < beHeight) {
                rate = beWidth;
            } else {
                rate = beHeight;
            }
            if (rate <= 0) {// 图片实际大小小于缩略图,不缩放
                rate = 1;
            }
            options.inSampleSize = rate;// rate就是压缩的比例
            options.inJustDecodeBounds = false;
            // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
            bitmap = BitmapFactory.decodeFile(imagePath, options);// 获取压缩后的图片
        }
        return bitmap;
    }

    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) {
        InputStream input;
        try {
            input = ac.getContentResolver().openInputStream(uri);
            BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
            onlyBoundsOptions.inJustDecodeBounds = true;
            onlyBoundsOptions.inDither = true;// optional
            onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
            BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
            input.close();
            int originalWidth = onlyBoundsOptions.outWidth;
            int originalHeight = onlyBoundsOptions.outHeight;
            if ((originalWidth == -1) || (originalHeight == -1))
                return null;
            // 图片分辨率以480x800为标准
            float hh = 800f;// 这里设置高度为800f
            float ww = 480f;// 这里设置宽度为480f
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;// be=1表示不缩放
            if (originalWidth > originalHeight && originalWidth > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (originalWidth / ww);
            } else if (originalWidth < originalHeight && originalHeight > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (originalHeight / hh);
            }
            if (be <= 0)
                be = 1;
            // 比例压缩
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = be;// 设置缩放比例
            bitmapOptions.inDither = true;// optional
            bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
            input = ac.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null,
                    bitmapOptions);
            input.close();
            return bitmap;// 再进行质量压缩
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}