package cn.droidlover.xdroidmvp.base;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.droidlover.xdroidmvp.XDroidMvpUtill;
import cn.droidlover.xrecyclerview.RecyclerAdapter;

/**
 * Created by wanglei on 2016/11/29.
 */

public abstract class SimpleRecAdapter<T, F extends RecyclerView.ViewHolder> extends RecyclerAdapter<T, F> {
    public static final int TAG_VIEW = 0;

    public SimpleRecAdapter(Context context) {
        super(context);
    }

    @Override
    public F onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return newViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final F holder, final int position) {
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

    public abstract F newViewHolder(View itemView);

    public abstract int getLayoutId();

}
