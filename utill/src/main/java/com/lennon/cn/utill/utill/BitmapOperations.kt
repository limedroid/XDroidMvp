package com.lennon.cn.utill.utill

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint

object BitmapOperations {

    private val paint: Paint
        get() {
            val paint = Paint()
            paint.isAntiAlias = true
            return paint
        }

    /**
     * 按比例缩放图片
     * baseBitmap: 需要缩放的原图
     * float x:横向缩放比例
     * float y:纵向缩放比例
     */
    fun bitmapScale(baseBitmap: Bitmap, x: Float, y: Float): Bitmap {
        // 因为要将图片放大，所以要根据放大的尺寸重新创建Bitmap
        val afterBitmap = Bitmap.createBitmap((baseBitmap.width * x).toInt(), (baseBitmap.height * y).toInt(), baseBitmap.config)
        val canvas = Canvas(afterBitmap)
        // 初始化Matrix对象
        val matrix = Matrix()
        // 根据传入的参数设置缩放比例
        matrix.setScale(x, y)
        // 根据缩放比例，把图片draw到Canvas上
        canvas.drawBitmap(baseBitmap, matrix, paint)
        return afterBitmap
    }

    /**
     * 按给定尺寸缩放图片
     * baseBitmap: 需要缩放的原图
     * new_width:缩放后的宽度
     * new_height:缩放后的高度
     */
    fun bitmapScale(baseBitmap: Bitmap, new_width: Int, new_height: Int): Bitmap {
        // 因为要将图片放大，所以要根据放大的尺寸重新创建Bitmap
        val afterBitmap = Bitmap.createBitmap(new_width, new_height, baseBitmap.config)
        val canvas = Canvas(afterBitmap)
        // 初始化Matrix对象
        val matrix = Matrix()
        // 根据传入的参数设置缩放比例
        val sx = new_width.toFloat() / baseBitmap.width.toFloat()
        val sy = new_height.toFloat() / baseBitmap.height.toFloat()
        matrix.setScale(sx, sy)
        // 根据缩放比例，把图片draw到Canvas上
        canvas.drawBitmap(baseBitmap, matrix, paint)
        return afterBitmap
    }

    /**
     * 倾斜图片
     */
    fun bitmapSkew(baseBitmap: Bitmap, dx: Float, dy: Float): Bitmap {
        // 根据图片的倾斜比例，计算变换后图片的大小，
        val afterBitmap = Bitmap.createBitmap(baseBitmap.width + (baseBitmap.width * dx).toInt(), baseBitmap.height + (baseBitmap.height * dy).toInt(), baseBitmap.config)
        val canvas = Canvas(afterBitmap)
        val matrix = Matrix()
        // 设置图片倾斜的比例
        matrix.setSkew(dx, dy)
        canvas.drawBitmap(baseBitmap, matrix, paint)
        return afterBitmap
    }

    /**
     * 图片移动
     */
    fun bitmapTranslate(baseBitmap: Bitmap, dx: Float, dy: Float): Bitmap {
        // 需要根据移动的距离来创建图片的拷贝图大小
        val afterBitmap = Bitmap.createBitmap((baseBitmap.width + dx).toInt(), (baseBitmap.height + dy).toInt(), baseBitmap.config)
        val canvas = Canvas(afterBitmap)
        val matrix = Matrix()
        // 设置移动的距离
        matrix.setTranslate(dx, dy)
        canvas.drawBitmap(baseBitmap, matrix, paint)
        return afterBitmap
    }

    /**
     * 图片绕中心旋转
     */
    fun bitmapRotate(baseBitmap: Bitmap, degrees: Float): Bitmap {
        // 创建一个和原图一样大小的图片
        val afterBitmap = Bitmap.createBitmap(baseBitmap.width, baseBitmap.height, baseBitmap.config)
        val canvas = Canvas(afterBitmap)
        val matrix = Matrix()
        // 根据原图的中心位置旋转
        matrix.setRotate(degrees, baseBitmap.width.toFloat() / 2.0f, baseBitmap.height.toFloat() / 2.0f)
        canvas.drawBitmap(baseBitmap, matrix, paint)
        return afterBitmap
    }

    /**
     * 图片绕中心旋转
     */
    fun rotaingImage(bitmap: Bitmap, angle: Int): Bitmap {
        //旋转图片 动作
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
