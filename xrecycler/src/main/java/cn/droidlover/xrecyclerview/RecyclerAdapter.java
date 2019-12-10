package cn.droidlover.xrecyclerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wanglei on 2016/10/30.
 */

public abstract class RecyclerAdapter<T, F extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<F> {

    protected Context context;
    protected List<T> data = new ArrayList<T>();
    private RecyclerItemCallback<T, F> recItemClick;

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    /**
     * 设置数据源
     *
     * @param data
     */
    public void setData(List<T> data) {
        this.data.clear();
        if (data != null) {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置数据源
     *
     * @param data
     */
    public void setData(T[] data) {
        if (data != null && data.length > 0) {
            setData(Arrays.asList(data));
        }
    }

    /**
     * 添加数据
     *
     * @param data
     */
    public void addData(List<T> data) {
        int preSize = this.data.size();
        if (data != null && data.size() > 0) {
            if (this.data == null) {
                this.data = new ArrayList<T>();
            }
            this.data.addAll(data);
            notifyItemRangeInserted(preSize, this.data.size());
        }
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param data
     */
    public void addData(T[] data) {
        addData(Arrays.asList(data));
    }


    /**
     * 删除元素
     *
     * @param element
     */
    public void removeElement(T element) {
        if (data.contains(element)) {
            int position = data.indexOf(element);
            data.remove(element);
            notifyItemRemoved(position);
            notifyItemChanged(position);
        }
    }

    /**
     * 删除元素
     *
     * @param position
     */
    public void removeElement(int position) {
        if (data != null && data.size() > position) {
            data.remove(position);
            notifyItemRemoved(position);
            notifyItemChanged(position);
        }
    }

    /**
     * 删除元素
     *
     * @param elements
     */
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

    /**
     * 删除元素
     *
     * @param elements
     */
    public void removeElements(T[] elements) {
        if (elements != null && elements.length > 0) {
            removeElements(Arrays.asList(elements));
        }
    }

    /**
     * 更新元素
     *
     * @param element
     * @param position
     */
    public void updateElement(T element, int position) {
        if (position >= 0 && data.size() > position) {
            data.remove(position);
            data.add(position, element);
            notifyItemChanged(position);
        }
    }

    /**
     * 添加元素
     *
     * @param element
     */
    public void addElement(T element) {
        if (element != null) {
            if (this.data == null) {
                this.data = new ArrayList<T>();
            }
            data.add(element);
            notifyItemInserted(this.data.size());
        }
    }

    public void addElement(int position, T element) {
        if (element != null) {
            if (this.data == null) {
                this.data = new ArrayList<T>();
            }
            data.add(position, element);
            notifyItemInserted(position);
        }
    }

    /**
     * 清除数据源
     */
    public void clearData() {
        if (this.data != null) {
            this.data.clear();
            notifyDataSetChanged();
        }
    }


    /**
     * 设置控件可见
     *
     * @param view
     */
    protected void setVisible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 设置控件不可见
     *
     * @param view
     */
    protected void setGone(View view) {
        view.setVisibility(View.GONE);
    }

    /**
     * 设置控件不可见
     *
     * @param view
     */
    protected void setInvisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置控件可用
     *
     * @param view
     */
    protected void setEnable(View view) {
        view.setEnabled(true);
    }

    /**
     * 设置控件不可用
     *
     * @param view
     */
    protected void setDisable(View view) {
        view.setEnabled(false);
    }

    /**
     * 获取图片资源
     *
     * @param resId
     * @return
     */
    protected Drawable getDrawable(int resId) {
        return context.getResources().getDrawable(resId);
    }

    /**
     * 获取字符串资源
     *
     * @param resId
     * @return
     */
    protected String getString(int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * 获取颜色资源
     *
     * @param resId
     * @return
     */
    protected int getColor(int resId) {
        return context.getResources().getColor(resId);
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public List<T> getDataSource() {
        return data;
    }


    public void setRecItemClick(RecyclerItemCallback<T, F> recItemClick) {
        this.recItemClick = recItemClick;
    }

    public RecyclerItemCallback<T, F> getRecItemClick() {
        return recItemClick;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public abstract F onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(F holder, int position);

    @Override
    public int getItemCount() {
        return data == null || data.isEmpty() ? 0 : data.size();
    }


}


