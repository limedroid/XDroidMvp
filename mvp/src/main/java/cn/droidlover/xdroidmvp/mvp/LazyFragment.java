package cn.droidlover.xdroidmvp.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.Field;

/**
 * Created by wanglei on 2017/1/28.
 */

public class LazyFragment extends RxFragment {
    protected LayoutInflater layoutInflater;
    protected Activity context;

    private View rootView;
    private ViewGroup container;

    private boolean isInitReady = false;
    private int isVisibleToUserState = STATE_NO_SET;
    private Bundle saveInstanceState;
    private boolean isLazyEnable = true;
    private boolean isStart = false;
    private FrameLayout layout;

    private static final int STATE_VISIBLE = 1; //用户可见
    private static final int STATE_NO_SET = -1; //未设置值
    private static final int STATE_NO_VISIBLE = 0;  //用户不可见

    private static final String TAG_ROOT_FRAMELAYOUT = "tag_root_framelayout";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.layoutInflater = inflater;
        this.container = container;
        onCreateView(savedInstanceState);
        if (rootView == null)
            return super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    private void onCreateView(Bundle savedInstanceState) {
        this.saveInstanceState = savedInstanceState;
        boolean isVisible;
        if (isVisibleToUserState == STATE_NO_SET) {
            isVisible = getUserVisibleHint();
        } else {
            isVisible = isVisibleToUserState == STATE_VISIBLE;
        }
        if (isLazyEnable) {
            if (isVisible && !isInitReady) {
                onCreateViewLazy(savedInstanceState);
                isInitReady = true;
            } else {
                LayoutInflater mInflater = layoutInflater;
                if (mInflater == null && context != null) {
                    mInflater = LayoutInflater.from(context);
                }
                layout = new FrameLayout(context);
                layout.setTag(TAG_ROOT_FRAMELAYOUT);

                View view = getPreviewLayout(mInflater, layout);
                if (view != null) {
                    layout.addView(view);
                }
                layout.setLayoutParams(
                        new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                setContentView(layout);
            }
        } else {
            onCreateViewLazy(savedInstanceState);
            isInitReady = true;
        }
    }

    protected View getRealRootView() {
        if (rootView != null) {
            if (rootView instanceof FrameLayout
                    && TAG_ROOT_FRAMELAYOUT.equals(rootView.getTag())) {
                return ((FrameLayout) rootView).getChildAt(0);
            }
        }

        return rootView;
    }

    protected View getRootView() {
        return rootView;
    }

    protected View $(int id) {
        if (rootView != null) {
            return rootView.findViewById(id);
        }
        return null;
    }

    protected void setContentView(int layoutResID) {
        if (isLazyEnable && getRootView() != null && getRootView().getParent() != null) {
            layout.removeAllViews();
            View view = layoutInflater.inflate(layoutResID, layout, false);
            layout.addView(view);
        } else {
            rootView = layoutInflater.inflate(layoutResID, container, false);
        }
    }

    protected void setContentView(View view) {
        if (isLazyEnable && getRootView() != null && getRootView().getParent() != null) {
            layout.removeAllViews();
            layout.addView(view);
        } else {
            rootView = view;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisibleToUserState = isVisibleToUser ? STATE_VISIBLE : STATE_NO_VISIBLE;
        if (isVisibleToUser
                && !isInitReady
                && getRootView() != null) {
            isInitReady = true;
            onCreateViewLazy(saveInstanceState);
            onResumeLazy();
        }
        if (isInitReady && getRootView() != null) {
            if (isVisibleToUser) {
                isStart = true;
                onStartLazy();
            } else {
                isStart = false;
                onStopLazy();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isInitReady) {
            onResumeLazy();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isInitReady) {
            onPauseLazy();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isInitReady
                && !isStart
                && getUserVisibleHint()) {
            isStart = true;
            onStartLazy();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isInitReady
                && isStart
                && getUserVisibleHint()) {
            isStart = false;
            onStopLazy();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.context = (Activity) context;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        context = null;

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView = null;
        container = null;
        layoutInflater = null;
        if (isInitReady) onDestoryLazy();
        isInitReady = false;
    }

    protected View getPreviewLayout(LayoutInflater mInflater, FrameLayout layout) {
        return null;
    }

    protected void onCreateViewLazy(Bundle savedInstanceState) {

    }

    protected void onStartLazy() {

    }

    protected void onStopLazy() {

    }

    protected void onResumeLazy() {

    }

    protected void onPauseLazy() {

    }

    protected void onDestoryLazy() {

    }


}
