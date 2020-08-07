package com.lennon.cn.utill.dialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.IBinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lennon.cn.utill.adapter.StringItemAdapter;
import com.lennon.cn.utill.bean.StringBean;
import com.lennon.cn.utill.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;
import lennon.com.utill.R;

public class BottomSearchSelectDialog<T extends StringBean> extends PopupWindow {
    protected Context context;
    private WindowManager wm;
    private View maskView;
    private SearchView dialog_search;
    private XRecyclerView xRecyclerView;
    private TextView cancle;
    private Listener<T> listener;
    private List<T> list;
    private List<Typeface> typefaces;
    private List<Integer> colors;
    private List<Integer> textSize;

    public void setTypefaces(List<Typeface> typefaces) {
        this.typefaces = typefaces;
    }

    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }

    public void setTextSize(List<Integer> textSize) {
        this.textSize = textSize;
    }


    public void setListener(Listener<T> listener) {
        this.listener = listener;
    }

    public interface Listener<T> {
        void onItemClick(int position, T model);

        boolean isResult(@NonNull String text, @NonNull T t);

        void onCancel();
    }

    public BottomSearchSelectDialog(Context context, final T... a) {
        List<T> t = new ArrayList<>();
        if (a != null) {
            Collections.addAll(t, a);
        }
        this.context = context;
        this.list = t;
        initType();
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        setContentView(generateCustomView());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        setAnimationStyle(R.style.Animations_BottomPush);
    }

    public BottomSearchSelectDialog(Context context, List<Typeface> typefaces,
                                    List<Integer> colors,
                                    List<Integer> textSize, final T... a) {
        this.typefaces = typefaces;
        this.colors = colors;
        this.textSize = textSize;
        List<T> t = new ArrayList<>();
        if (a != null) {
            Collections.addAll(t, a);
        }
        this.context = context;
        this.list = t;
        initType();
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        setContentView(generateCustomView());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        setAnimationStyle(R.style.Animations_BottomPush);
    }

    public BottomSearchSelectDialog(Context context, List<T> list) {
        this.context = context;
        this.list = list;
        initType();
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        setContentView(generateCustomView());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        setAnimationStyle(R.style.Animations_BottomPush);
    }

    private View generateCustomView() {
        View root = View.inflate(context, R.layout.dialog_bottom_search_select, null);
        dialog_search = root.findViewById(R.id.dialog_bottom_search_select_search);
        cancle = root.findViewById(R.id.dialog_bottom_search_select_cancle);
        xRecyclerView = root.findViewById(R.id.dialog_bottom_search_select_list);
        xRecyclerView.verticalLayoutManager(context);
        final StringItemAdapter adapter = new StringItemAdapter<T>(context);
        xRecyclerView.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<T, StringItemAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, T model, int tag, StringItemAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                if (listener != null) {
                    listener.onItemClick(position, model);
                }
            }
        });
        dialog_search.setSearchViewListener(new SearchView.SearchViewListener() {
            @Override
            public void onClear() {
                adapter.setData(BottomSearchSelectDialog.this.list);
            }

            @Override
            public void onSearch(String text) {
                List<T> l = new ArrayList<>();
                if (listener != null) {
                    for (T t : list) {
                        if (listener.isResult(text, t)) {
                            l.add(t);
                        }
                    }
                } else {
                    l.addAll(list);
                }
                adapter.setData(l);
            }

            @Override
            public void onResult(String text) {

            }

            @Override
            public void onSearchByButton(String text) {

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onCancel();
                }
            }
        });
        if (list != null) {
            adapter.setData(list);
        }
        if (typefaces != null) {
            adapter.setTypefaces(typefaces);
        }
        if (textSize != null) {
            adapter.setTextSize(textSize);
        }
        if (colors != null) {
            adapter.setColors(colors);
        }
        return root;
    }

    @TargetApi(23)
    private void initType() {
        // 解决华为手机在home建进入后台后，在进入应用，蒙层出现在popupWindow上层的bug。
        // android4.0及以上版本都有这个hide方法，根据jvm原理，可以直接调用，选择android6.0版本进行编译即可。
        setWindowLayoutType(WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        addMaskView(parent.getWindowToken());
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        addMaskView(anchor.getWindowToken());
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void dismiss() {
        removeMaskView();
        super.dismiss();
    }

    /**
     * 显示在界面的底部
     */
    public void show(Activity activity) {
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void addMaskView(IBinder token) {
        WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.MATCH_PARENT;
        p.format = PixelFormat.TRANSLUCENT;
        p.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        p.token = token;
        p.windowAnimations = android.R.style.Animation_Toast;
        maskView = new View(context);
        maskView.setBackgroundColor(0x7f000000);
        maskView.setFitsSystemWindows(false);
        // 华为手机在home建进入后台后，在进入应用，蒙层出现在popupWindow上层，导致界面卡死，
        // 这里新增加按bug返回。
        // initType方法已经解决该问题，但是还是留着这个按back返回功能，防止其他手机出现华为手机类似问题。
        maskView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    removeMaskView();
                    return true;
                }
                return false;
            }
        });
        wm.addView(maskView, p);
    }

    private void removeMaskView() {
        if (maskView != null) {
            wm.removeViewImmediate(maskView);
            maskView = null;
        }
    }
}
