package com.lennon.cn.utill.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lennon.cn.utill.widget.interf.OnHeadBarListener;
import lennon.com.utill.R;

/**
 * Created by dingyi on 2016/11/15.
 */

public class HeadBar extends LinearLayout {

    TextView mLeftTv;
    TextView mMidTv;
    TextView mRightTv;
    ImageView mRightIgv;


    private Context mContext;
    private OnHeadBarListener mOnHeadBarListener;

    public HeadBar(Context context) {
        super(context);
    }

    public HeadBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadBar);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.headbar, this);
        inintView(view);
        setDefault(typedArray);
    }

    public HeadBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setStatusBar(int bar) {
        ViewGroup.LayoutParams params =  getLayoutParams();
        params.height += bar;
        setLayoutParams(params);
        setPadding(0, bar, 0, 0);
    }

    /**
     * 设置监听器
     */
    public void setOnHeadBarListener(OnHeadBarListener listener) {
        this.mOnHeadBarListener = listener;
    }

    public void setTextColor(int color) {
        mLeftTv.setTextColor(color);
        mRightTv.setTextColor(color);
        mMidTv.setTextColor(color);
    }

    /**
     * 设置最左边显示栏位提示信息
     */
    public void setLeftMsg(String msg) {
        setLeftMsg(R.mipmap.back, msg);
    }

    public void setLeftMsg(int id, String msg) {
        mLeftTv.setText(msg);
        if (id == -1) {
            return;
        }
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mLeftTv.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 设置HeadBar标题
     */
    public void setMidMsg(String msg) {
        mMidTv.setVisibility(View.VISIBLE);
        mMidTv.setText(msg);
    }

    /**
     * 设置最右边文字提示信息
     */
    public void setRightMsg(String msg) {
        mRightTv.setVisibility(View.VISIBLE);
        mRightTv.setText(msg);
        mRightTv.setDrawingCacheEnabled(false);
        mRightIgv.setVisibility(View.GONE);
    }

    public void setRightMsg(int drawableId, String msg) {
        mRightTv.setVisibility(View.VISIBLE);
        mRightTv.setText(msg);
        mRightTv.setDrawingCacheEnabled(true);
        mRightIgv.setVisibility(VISIBLE);
        mRightIgv.setImageDrawable(getResources().getDrawable(drawableId));
    }

    public void setRightIgv(int drawableId) {
        mRightIgv.setVisibility(VISIBLE);
        mRightIgv.setImageDrawable(getResources().getDrawable(drawableId));
    }

    private void setDefault(TypedArray typedArray) {
        mLeftTv.setText("");
        mLeftTv.setDrawingCacheEnabled(false);
        mMidTv.setText("");
        setRightMsg("");
        if (typedArray != null) {
            mLeftTv.setTextColor(typedArray.getColor(R.styleable.HeadBar_text_color, Color.WHITE));
            mRightTv.setTextColor(typedArray.getColor(R.styleable.HeadBar_text_color, Color.WHITE));
            mMidTv.setTextColor(typedArray.getColor(R.styleable.HeadBar_text_color, Color.WHITE));
        }
        mRightTv.setVisibility(GONE);
        mRightIgv.setVisibility(GONE);
    }

    private void inintView(View v) {
        mLeftTv = (TextView) v.findViewById(R.id.headbar_left_tv);
        mLeftTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnHeadBarListener) {
                    mOnHeadBarListener.onLeft();
                }
            }
        });
        mMidTv = (TextView) v.findViewById(R.id.headbar_mid_tv);
        mMidTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnHeadBarListener) {
                    mOnHeadBarListener.onMid();
                }
            }
        });
        mRightTv = (TextView) v.findViewById(R.id.headbar_right_tv);
        mRightTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnHeadBarListener) {
                    mOnHeadBarListener.onRight();
                }
            }
        });
        mRightIgv = (ImageView) v.findViewById(R.id.headbar_right_igv);
        mRightIgv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnHeadBarListener) {
                    mOnHeadBarListener.onRight();
                }
            }
        });
    }

    public void onRight() {
        if (null != mOnHeadBarListener) {
            mOnHeadBarListener.onRight();
        }
    }
}
