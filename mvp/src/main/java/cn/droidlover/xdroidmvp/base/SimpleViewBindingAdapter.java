package cn.droidlover.xdroidmvp.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import cn.droidlover.xdroidmvp.XDroidMvpUtill;
import cn.droidlover.xrecyclerview.RecyclerAdapter;

public abstract class SimpleViewBindingAdapter<T, E extends ViewBinding> extends RecyclerAdapter<T, SimpleViewBindingAdapter.ViewHolder<E>> {
    public static final int TAG_VIEW = 0;

    public SimpleViewBindingAdapter(Context context) {
        super(context);
    }

    public static class ViewHolder<F extends ViewBinding> extends RecyclerView.ViewHolder {
        F v;

        public ViewHolder(F viewBinding) {
            super(viewBinding.getRoot());
            v = viewBinding;
        }

        public F getV() {
            return v;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder<E> holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    T d = null;
                    if (position < data.size() && position >= 0) {
                        d = data.get(position);
                    }
                    getRecItemClick().onItemClick(position, d, TAG_VIEW, holder);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                XDroidMvpUtill.vibrator(context);
                if (getRecItemClick() != null) {
                    T d = null;
                    if (position < data.size() && position >= 0) {
                        d = data.get(position);
                    }
                    getRecItemClick().onItemLongClick(position, d, TAG_VIEW, holder);
                }
                return true;
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getViewBinding(viewType, LayoutInflater.from(context), parent));
    }


    /*
     *       获取   ViewBinding   实现类
     * */
    protected abstract E getViewBinding(int viewType, LayoutInflater from, ViewGroup parent);
}
