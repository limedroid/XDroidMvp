package cn.droidlover.xdroidmvp.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wanglei on 2016/12/1.
 */

public abstract class XListAdapter<T> extends BaseAdapter {
    protected List<T> data = new ArrayList<T>();
    private ListItemCallback<T> callback;
    protected Context context;

    public XListAdapter(Context context) {
        this.context = context;
    }

    public XListAdapter(Context context, ListItemCallback<T> callback) {
        this(context);
        this.callback = callback;
    }

    public XListAdapter(Context context, List<T> data) {
        this.context = context;
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setData(List<T> data) {
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
        } else {
            this.data.clear();
        }
        notifyDataSetChanged();
    }


    public void setData(T[] data) {
        if (data != null && data.length > 0) {
            setData(Arrays.asList(data));
        }
    }


    public void addData(List<T> data) {
        if (data != null && data.size() > 0) {
            if (this.data == null) {
                this.data = new ArrayList<T>();
            }
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }


    public void addData(T[] data) {
        addData(Arrays.asList(data));
    }

    public void removeElement(T element) {
        if (data.contains(element)) {
            data.remove(element);
            notifyDataSetChanged();
        }
    }

    public void removeElement(int position) {
        if (data != null && data.size() > position) {
            data.remove(position);
            notifyDataSetChanged();
        }
    }

    public void removeElements(List<T> elements) {
        if (data != null && elements != null && elements.size() > 0
                && data.size() >= elements.size()) {

            for (T element : elements) {
                if (data.contains(element)) {
                    data.remove(element);
                }
            }

            notifyDataSetChanged();
        }
    }

    public void removeElements(T[] elements) {
        if (elements != null && elements.length > 0) {
            removeElements(Arrays.asList(elements));
        }
    }

    public void updateElement(T element, int position) {
        if (position >= 0 && data.size() > position) {
            data.remove(position);
            data.add(position, element);
            notifyDataSetChanged();
        }
    }

    public void addElement(T element) {
        if (element != null) {
            if (this.data == null) {
                this.data = new ArrayList<T>();
            }
            data.add(element);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        if (this.data != null) {
            this.data.clear();
            notifyDataSetChanged();
        }
    }

    protected void visible(boolean flag, View view) {
        if (flag)
            view.setVisibility(View.VISIBLE);
    }


    protected void gone(boolean flag, View view) {
        if (flag)
            view.setVisibility(View.GONE);
    }

    protected void inVisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }


    protected Drawable getDrawable(int resId) {
        return context.getResources().getDrawable(resId);
    }


    protected String getString(int resId) {
        return context.getResources().getString(resId);
    }


    protected int getColor(int resId) {
        return context.getResources().getColor(resId);
    }


    public List<T> getDataSource() {
        return data;
    }

    public void setCallback(ListItemCallback<T> callback) {
        this.callback = callback;
    }

    public ListItemCallback<T> getCallback() {
        return callback;
    }


    public int getSize() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getCount() {
        return data == null || data.isEmpty() ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data != null ? data.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView,
                                 ViewGroup parent);


}
