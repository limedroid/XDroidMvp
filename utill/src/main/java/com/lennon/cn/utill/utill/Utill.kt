package com.lennon.cn.utill.utill

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager
import com.lennon.cn.utill.dialog.CommonAlertDialog
import com.lennon.cn.utill.dialog.OnAlertDialogListener
import java.io.File
import java.io.UnsupportedEncodingException
import java.util.ArrayList
import com.lennon.cn.utill.conf.Lennon
import android.provider.MediaStore
import android.content.ContentValues
import android.R.layout
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.view.View


object Utill {
    /**
     * 产生图片的路径，带文件夹和文件名，文件名为当前毫秒数
     */
    fun generateImgePath(): String {
        val f = File(Lennon.getFilePathName() + File.separator + "images")
        if (!f.exists()) {
            f.mkdirs()
        }
        return f.absolutePath + File.separator + System.currentTimeMillis().toString() + ".jpg"
    }
    /**
     * view转bitmap
     */
    fun viewConversionBitmap(v: View): Bitmap {
        val w = v.getWidth()
        val h = v.getHeight()

        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)

//        c.drawColor(Color.WHITE)
        /** 如果不设置canvas画布为白色，则生成透明  */

        v.layout(0, 0, w, h)
        v.draw(c)

        return bmp
    }
    fun getImageContentUri(context: Context, imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID), MediaStore.Images.Media.DATA + "=? ",
            arrayOf(filePath), null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            val baseUri = Uri.parse("content://media/external/images/media")
            return Uri.withAppendedPath(baseUri, "" + id)
        } else {
            if (imageFile.exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, filePath)
                return context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                )
            } else {
                return null
            }
        }
    }

    /**
     * 获取当前应用程序的包名
     * @param context 上下文对象
     * @return 返回包名
     */
    fun getAppProcessName(context: Context): String {
        //当前应用pid
        val pid = android.os.Process.myPid()
        //任务管理类
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        //遍历所有应用
        val infos = manager.getRunningAppProcesses()
        for (info in infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName//返回包名
        }
        return ""
    }


    /**
     * 获取程序 图标
     * @param context
     * @param packname 应用包名
     * @return
     */
    fun getAppIcon(context: Context, packname: String): Drawable? {
        try {
            //包管理操作管理类
            val pm = context.getPackageManager()
            //获取到应用信息
            val info = pm.getApplicationInfo(packname, 0)
            return info.loadIcon(pm)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取程序的版本号
     * @param context
     * @param packname
     * @return
     */
    fun getAppVersion(context: Context, packname: String): String {
        //包管理操作管理类
        val pm = context.getPackageManager()
        try {
            val packinfo = pm.getPackageInfo(packname, 0)
            return packinfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()

        }
        return packname
    }


    /**
     * 获取程序的名字
     * @param context
     * @param packname
     * @return
     */
    fun getAppName(context: Context, packname: String): String {
        //包管理操作管理类
        val pm = context.getPackageManager()
        try {
            val info = pm.getApplicationInfo(packname, 0)
            return info.loadLabel(pm).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()

        }
        return packname
    }

    /*
     * 获取程序的权限
     */
    fun getAllPermissions(context: Context, packname: String): Array<String>? {
        try {
            //包管理操作管理类
            val pm = context.getPackageManager()
            val packinfo = pm.getPackageInfo(packname, PackageManager.GET_PERMISSIONS)
            //获取到所有的权限
            return packinfo.requestedPermissions
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()

        }
        return null
    }


    /**
     * 获取程序的签名
     * @param context
     * @param packname
     * @return
     */
    fun getAppSignature(context: Context, packname: String): String {
        try {
            //包管理操作管理类
            val pm = context.getPackageManager()
            val packinfo = pm.getPackageInfo(packname, PackageManager.GET_SIGNATURES)
            //获取当前应用签名
            return packinfo.signatures[0].toCharsString()

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()

        }
        return packname
    }

    /**
     * 获取当前展示 的Activity名称
     * @return
     */
//    fun getCurrentActivityName(context: Context): String? {
//        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val runningActivity = activityManager.getRunningTasks(1).get(0).topActivity?.getClassName()
//        return runningActivity
//    }

    fun makeDir(dirPath: String) {
        val file = File(dirPath)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    /**
     * 判断是否为平板
     *
     * @return
     */
    fun isPad(context: Context): Boolean {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        // 屏幕宽度
        val screenWidth = display.width.toFloat()
        // 屏幕高度
        val screenHeight = display.height.toFloat()
        val dm = DisplayMetrics()
        display.getMetrics(dm)
        val x = Math.pow((dm.widthPixels / dm.xdpi).toDouble(), 2.0)
        val y = Math.pow((dm.heightPixels / dm.ydpi).toDouble(), 2.0)
        // 屏幕尺寸
        val screenInches = Math.sqrt(x + y)
        // 大于6尺寸则为Pad
        return screenInches >= 8.0
    }

    fun toUtf8(str: String): String? {
        var result: String? = null
        try {
            result = String(str.toByteArray(charset("UTF-8")), charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return result
    }

    fun getFileName(url: String): String {
        val s = url.split("/".toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()
        return s[s.size - 1]
    }

    fun StringToStringArray(random: String): List<String> {
        val l = ArrayList<String>()
        val chars = random.toCharArray()
        for (i in chars.indices) {
            l.add(chars[i] + "")
        }
        return l
    }

    @Suppress("DEPRECATION")
    fun getColor(res: Resources, color: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return res.getColor(color, res.newTheme())
        }
        return res.getColor(color)
    }

    fun showTel(context: Context, t: String) {
        showTel(context, t, "4001006501")
    }

    fun showTel(context: Context, title: String, cell: String) {
        val mAlertDialog = CommonAlertDialog(context)
        mAlertDialog.setDialogListener(object : OnAlertDialogListener() {
            override fun onSure() {
                super.onSure()
                mAlertDialog.dismiss()
                val intent = Intent()
                intent.action = Intent.ACTION_CALL
                intent.data = Uri.parse("tel:$cell")
                context.startActivity(intent)
            }

            override fun onCancle() {
                super.onCancle()
                mAlertDialog.dismiss()
            }
        })
        mAlertDialog.setTitle(
            if (TextUtils.isEmpty(title)) {
                "联系客服"
            } else {
                title
            }
        )
        mAlertDialog.setSureMsg("立即拨号")
        mAlertDialog.setMsg("电话：$cell")
        mAlertDialog.show()
    }


    fun isMobileNO(mobiles: String): Boolean {
        //        mobiles = mobiles.replace(" ", "")
        val telRegex = "[1]\\d{10}" //"[1]"代表第1位为数字1，"\\d{9}"代表后面是可以是0～9的数字，有10位。
        return if (TextUtils.isEmpty(mobiles)) false
        else mobiles.matches(telRegex.toRegex())
    }

}
