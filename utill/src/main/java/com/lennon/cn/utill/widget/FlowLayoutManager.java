package com.lennon.cn.utill.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import cn.droidlover.xdroidmvp.log.XLog;

public class FlowLayoutManager extends RecyclerView.LayoutManager {
    private final String TAG = this.getClass().getName();
    private SparseArray<View> cachedViews = new SparseArray();
    private SparseArray<Rect> layoutPoints = new SparseArray<>();
    private int totalWidth;
    private int totalHeight;
    private int mContentHeight;
    private int mOffset;
    private boolean mIsFullyLayout;

    public FlowLayoutManager(Context context, boolean isFullyLayout) {
        mIsFullyLayout = isFullyLayout;
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return true;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (int i = 0; i < getItemCount(); ++i) {
            View v = cachedViews.get(i);
            Rect rect = layoutPoints.get(i);
            layoutDecorated(v, rect.left, rect.top, rect.right, rect.bottom);
        }

    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return dx;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int shouldOffset = 0;
        if (mContentHeight - totalHeight > 0) {
            int targetOffset = mOffset + dy;
            if (targetOffset < 0) {
                targetOffset = 0;
            } else if (targetOffset > (mContentHeight - totalHeight)) {
                targetOffset = (mContentHeight - totalHeight);
            }
            shouldOffset = targetOffset - mOffset;
            offsetChildrenVertical(-shouldOffset);
            mOffset = targetOffset;
        }

        if (mIsFullyLayout) {
            shouldOffset = dy;
        }
        return shouldOffset;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        removeAllViews();
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
        final int widthMode = View.MeasureSpec.getMode(widthSpec);
        final int heightMode = View.MeasureSpec.getMode(heightSpec);
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);
        int height;
        switch (widthMode) {
            case View.MeasureSpec.UNSPECIFIED:
                XLog.d(TAG, "WidthMode is unspecified.");
                break;
            case View.MeasureSpec.AT_MOST:
                break;
            case View.MeasureSpec.EXACTLY:
                break;
            default:
                break;
        }
        removeAndRecycleAllViews(recycler);
        recycler.clear();
        cachedViews.clear();
        mContentHeight = 0;
        totalWidth = widthSize - getPaddingRight() - getPaddingLeft();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int maxTop = top;
        for (int i = 0; i < getItemCount(); ++i) {
            View v = recycler.getViewForPosition(i);
            addView(v);
            measureChildWithMargins(v, 0, 0);
            cachedViews.put(i, v);
        }
        for (int i = 0; i < getItemCount(); ++i) {
            View v = cachedViews.get(i);
            int w = getDecoratedMeasuredWidth(v);
            int h = getDecoratedMeasuredHeight(v);
            if (w > totalWidth - left) {
                left = getPaddingLeft();
                top = maxTop;
            }
            Rect rect = new Rect(left, top, left + w, top + h);
            layoutPoints.put(i, rect);
            left = left + w;
            if (top + h >= maxTop) {
                maxTop = top + h;
            }
        }

        mContentHeight = maxTop - getPaddingTop();

        height = mContentHeight + getPaddingTop() + getPaddingBottom();

        switch (heightMode) {
            case View.MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case View.MeasureSpec.AT_MOST:
                if (height > heightSize) {
                    height = heightSize;
                }
                break;
            case View.MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }

        totalHeight = height - getPaddingTop() - getPaddingBottom();

        setMeasuredDimension(widthSize, height);
    }
}