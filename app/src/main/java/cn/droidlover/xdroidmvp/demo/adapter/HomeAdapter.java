package cn.droidlover.xdroidmvp.demo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.demo.model.GankResults;
import cn.droidlover.xdroidmvp.imageloader.ILFactory;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/10.
 */

public class HomeAdapter extends SimpleRecAdapter<GankResults.Item, HomeAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;

    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_home;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GankResults.Item item = data.get(position);

        String type = item.getType();
        switch (type) {
            case "休息视频":
                holder.rlMessage.setVisibility(View.VISIBLE);
                holder.ivPart.setVisibility(View.GONE);
                holder.ivVedio.setVisibility(View.VISIBLE);
                holder.tvItem.setText(item.getDesc());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getRecItemClick() != null) {

                        }
                    }
                });
                break;
            case "福利":
                holder.rlMessage.setVisibility(View.GONE);
                holder.ivPart.setVisibility(View.VISIBLE);
                holder.ivVedio.setVisibility(View.GONE);

                ILFactory.getLoader().loadNet(holder.ivPart, item.getUrl(), null);
                holder.tvItem.setText("瞧瞧妹纸，扩展扩展视野......");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getRecItemClick() != null) {

                        }
                    }
                });
                break;
            default:
                holder.rlMessage.setVisibility(View.VISIBLE);
                holder.ivPart.setVisibility(View.GONE);
                holder.ivVedio.setVisibility(View.GONE);
                holder.tvItem.setText(item.getDesc());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getRecItemClick() != null) {

                        }
                    }
                });
                break;
        }
        Uri uri = null;
        switch (item.getType()) {
            case "Android":
                holder.ivType.setImageResource(R.mipmap.android_icon);
                break;
            case "iOS":
                holder.ivType.setImageResource(R.mipmap.ios_icon);
                break;
            case "前端":
                holder.ivType.setImageResource(R.mipmap.js_icon);
                break;
            case "拓展资源":
                holder.ivType.setImageResource(R.mipmap.other_icon);
                break;
        }

        String author = item.getWho();
        if (author != null) {
            holder.tvAuthor.setText(author);
            holder.tvAuthor.setTextColor(Color.parseColor("#87000000"));
        } else {
            holder.tvAuthor.setText("");
        }

        holder.tvTime.setText(item.getCreatedAt());

        holder.tvType.setText(type);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, item, TAG_VIEW, holder);
                }
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_type)
        ImageView ivType;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.iv_author)
        ImageView ivAuthor;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.rl_message)
        RelativeLayout rlMessage;
        @BindView(R.id.iv_part)
        ImageView ivPart;
        @BindView(R.id.iv_vedio)
        ImageView ivVedio;
        @BindView(R.id.tv_item)
        TextView tvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
