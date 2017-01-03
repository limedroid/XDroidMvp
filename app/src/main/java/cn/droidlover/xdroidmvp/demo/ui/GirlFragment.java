package cn.droidlover.xdroidmvp.demo.ui;

import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.demo.adapter.GirlAdapter;
import cn.droidlover.xdroidmvp.demo.model.GankResults;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class GirlFragment extends BasePagerFragment {

    GirlAdapter adapter;

    @Override
    public SimpleRecAdapter getAdapter() {
        if (adapter == null) {
            adapter = new GirlAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<GankResults.Item, GirlAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GankResults.Item model, int tag, GirlAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                }
            });
        }
        return adapter;
    }

    @Override
    public void setLayoutManager(XRecyclerView recyclerView) {
        recyclerView.gridLayoutManager(context, 2);
    }

    @Override
    public String getType() {
        return "福利";
    }

    public static GirlFragment newInstance() {
        return new GirlFragment();
    }

}
