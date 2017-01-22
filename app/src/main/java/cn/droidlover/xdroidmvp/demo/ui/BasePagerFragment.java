package cn.droidlover.xdroidmvp.demo.ui;

import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.demo.model.GankResults;
import cn.droidlover.xdroidmvp.demo.present.PBasePager;
import cn.droidlover.xdroidmvp.demo.widget.StateView;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by wanglei on 2016/12/31.
 */

public abstract class BasePagerFragment extends XFragment<PBasePager> {

    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;

    StateView errorView;

    protected static final int MAX_PAGE = 10;


    @Override
    public void initData(Bundle savedInstanceState) {
        initAdapter();
        getP().loadData(getType(), 1);
    }

    private void initAdapter() {
        setLayoutManager(contentLayout.getRecyclerView());
        contentLayout.getRecyclerView()
                .setAdapter(getAdapter());
        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        getP().loadData(getType(), 1);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().loadData(getType(), page);
                    }
                });


        if (errorView == null) {
            errorView = new StateView(context);
        }
        contentLayout.errorView(errorView);
        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null));

        contentLayout.getRecyclerView().useDefLoadMoreView();
    }

    public abstract SimpleRecAdapter getAdapter();

    public abstract void setLayoutManager(XRecyclerView recyclerView);

    public abstract String getType();


    public void showError(NetError error) {
        if (error != null) {
            switch (error.getType()) {
                case NetError.ParseError:
                    errorView.setMsg("数据解析异常");
                    break;

                case NetError.AuthError:
                    errorView.setMsg("身份验证异常");
                    break;

                case NetError.BusinessError:
                    errorView.setMsg("业务异常");
                    break;

                case NetError.NoConnectError:
                    errorView.setMsg("网络无连接");
                    break;

                case NetError.NoDataError:
                    errorView.setMsg("数据为空");
                    break;

                case NetError.OtherError:
                    errorView.setMsg("其他异常");
                    break;
            }
            contentLayout.showError();
        }
    }

    public void showData(int page, GankResults model) {
        if (page > 1) {
            getAdapter().addData(model.getResults());
        } else {
            getAdapter().setData(model.getResults());
        }

        contentLayout.getRecyclerView().setPage(page, MAX_PAGE);

        if (getAdapter().getItemCount() < 1) {
            contentLayout.showEmpty();
            return;
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_pager;
    }

    @Override
    public PBasePager newP() {
        return new PBasePager();
    }
}
