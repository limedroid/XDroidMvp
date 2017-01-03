package cn.droidlover.xdroidmvp.demo.ui;

import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.demo.adapter.HomeAdapter;
import cn.droidlover.xdroidmvp.demo.model.GankResults;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class HomeFragment extends BasePagerFragment {

    HomeAdapter adapter;

    @Override
    public SimpleRecAdapter getAdapter() {
        if (adapter == null) {
            adapter = new HomeAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<GankResults.Item, HomeAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GankResults.Item model, int tag, HomeAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (tag) {
                        case HomeAdapter.TAG_VIEW:
                            WebActivity.launch(context, model.getUrl(), model.getDesc());
                            break;
                    }
                }
            });
        }
        return adapter;
    }

    @Override
    public void setLayoutManager(XRecyclerView recyclerView) {
        recyclerView.verticalLayoutManager(context);
    }

    @Override
    public String getType() {
        return "all";
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
}
