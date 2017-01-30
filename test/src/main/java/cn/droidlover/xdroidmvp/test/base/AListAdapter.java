package cn.droidlover.xdroidmvp.test.base;

import android.content.Context;
import android.view.View;

import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;
import cn.droidlover.xdroidmvp.test.R;

/**
 * Created by wanglei on 2017/1/30.
 */

public class AListAdapter extends SimpleListAdapter<String, AListAdapter.ViewHolder> {


    public AListAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_single;
    }

    @Override
    protected void convert(ViewHolder holder, String item, int position) {

    }

    public static class ViewHolder {

        public ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
    }
}
