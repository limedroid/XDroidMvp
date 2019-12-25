package com.lennon.cn.utill.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import lennon.com.utill.R;


/**
 * Created by fulianchen on 24/7/13.
 */
public class MyRatingBar extends View {
    //星星水平排列
    public static final int HORIZONTAL = 0;
    //星星垂直排列
    public static final int VERTICAL = 1;
    //实心图片
    private Bitmap mSolidBitmap;
    //空心图片
    private Bitmap mHollowBitmap;
    private Context context;
    //最大的数量
    private int starMaxNumber;
    private  float starRating;
    private Paint paint;
    private int mSpaceWidth;//星星间隔
    private int mStarWidth;//星星宽度
    private int mStarHeight;//星星高度
    private boolean isIndicator;//是否是一个指示器（用户无法进行更改）
    private int mOrientation;

    public MyRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.MyRatingBar);
        mSpaceWidth = a.getDimensionPixelSize(R.styleable.MyRatingBar_space_width, 0);
        mStarWidth = a.getDimensionPixelSize(R.styleable.MyRatingBar_star_width, 0);
        mStarHeight = a.getDimensionPixelSize(R.styleable.MyRatingBar_star_height, 0);
        starMaxNumber = a.getInt(R.styleable.MyRatingBar_star_max, 0);
        starRating = a.getFloat(R.styleable.MyRatingBar_star_rating, 0);
        isIndicator = a.getBoolean(R.styleable.MyRatingBar_star_isIndicator, true);
        mSolidBitmap = getZoomBitmap(BitmapFactory.decodeResource(context.getResources(), a.getResourceId(R.styleable.MyRatingBar_star_solid, 0)));
        mHollowBitmap = getZoomBitmap(BitmapFactory.decodeResource(context.getResources(),a.getResourceId(R.styleable.MyRatingBar_star_hollow, 0)));
        mOrientation = a.getInt(R.styleable.MyRatingBar_star_orientation, HORIZONTAL);
        a.recycle();
    }

    public MyRatingBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyRatingBar, defStyle, 0);
        mSpaceWidth = a.getDimensionPixelSize(R.styleable.MyRatingBar_space_width, 0);
        mStarWidth = a.getDimensionPixelSize(R.styleable.MyRatingBar_star_width, 0);
        mStarHeight = a.getDimensionPixelSize(R.styleable.MyRatingBar_star_height, 0);
        starMaxNumber = a.getInt(R.styleable.MyRatingBar_star_max, 0);
        starRating = a.getFloat(R.styleable.MyRatingBar_star_rating, 0);
        mSolidBitmap = getZoomBitmap(BitmapFactory.decodeResource(context.getResources(), a.getResourceId(R.styleable.MyRatingBar_star_solid, 0)));
        mHollowBitmap = getZoomBitmap(BitmapFactory.decodeResource(context.getResources(),a.getResourceId(R.styleable.MyRatingBar_star_hollow, 0)));
        mOrientation = a.getInt(R.styleable.MyRatingBar_star_orientation, HORIZONTAL);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mHollowBitmap ==null|| mSolidBitmap ==null){
            return;
        }
        //绘制实心进度
        int solidStarNum = (int)starRating;
        //绘制实心的起点位置
        int solidStartPoint = 0;
        for(int i=1;i<=solidStarNum;i++){
            canvas.drawBitmap(mSolidBitmap, solidStartPoint, 0, paint);
            solidStartPoint = solidStartPoint + mSpaceWidth +  mHollowBitmap.getWidth();
        }
        //虚心开始位置
        int hollowStartPoint = solidStartPoint;
        //多出的实心部分起点
        int extraSolidStarPoint = hollowStartPoint;
        //虚心数量
        int hollowStarNum = starMaxNumber - solidStarNum;
        for(int j = 1;j<=hollowStarNum;j++){
            canvas.drawBitmap(mHollowBitmap, hollowStartPoint, 0, paint);
            hollowStartPoint = hollowStartPoint + mSpaceWidth + mSolidBitmap.getWidth();
        }
        //多出的实心长度
        int extraSolidLength = (int)((starRating - solidStarNum)* mHollowBitmap.getWidth());
        Rect rectSrc = new Rect(0,0,extraSolidLength, mHollowBitmap.getHeight());
        Rect dstF = new Rect(extraSolidStarPoint,0,extraSolidStarPoint+extraSolidLength, mHollowBitmap.getHeight());
        canvas.drawBitmap(mSolidBitmap, rectSrc, dstF, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isIndicator){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    float starTotalWidth = starMaxNumber *(mStarWidth+mSpaceWidth)-mSpaceWidth;
                    if(event.getX()<=starTotalWidth){
                        float newStarRating = (int)event.getX()/(mStarWidth+mSpaceWidth)+1;
                        setStarRating(newStarRating);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:

                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置星星的进度
     * @param starRating
     */
    public void setStarRating(float starRating){
        this.starRating = starRating;
        invalidate();
    }

    public float getStarRating() {
        return starRating;
    }


    /**
     * 获取缩放图片
     * @param bitmap
     * @return
     */
    public Bitmap getZoomBitmap(Bitmap bitmap){
        if(mStarWidth==0||mStarHeight==0){
            return bitmap;
        }
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // 设置想要的大小
        int newWidth = mStarWidth;
        int newHeight = mStarHeight;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                true);
        return newbm;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mOrientation==HORIZONTAL) {
            setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
        } else {
            setMeasuredDimension(measureShort(widthMeasureSpec), measureLong(heightMeasureSpec));
        }
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec
     *            A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureLong(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY)) {
            //We were told how big to be
            result = specSize;
        } else {
            //Calculate the width according the views count
            result = (int)(getPaddingLeft() + getPaddingRight()
                    + (mSpaceWidth+mStarWidth)*(starMaxNumber));
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec
     *            A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureShort(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            //We were told how big to be
            result = specSize;
        } else {
            //Measure the height
            result = (int)(mStarHeight + getPaddingTop() + getPaddingBottom());
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    public int getStarMaxNumber() {
        return starMaxNumber;
    }

    public void setStarMaxNumber(int starMaxNumber) {
        this.starMaxNumber = starMaxNumber;
        invalidate();
    }

    public boolean isIndicator() {
        return isIndicator;
    }

    public void setIsIndicator(boolean isIndicator) {
        this.isIndicator = isIndicator;
    }
}
