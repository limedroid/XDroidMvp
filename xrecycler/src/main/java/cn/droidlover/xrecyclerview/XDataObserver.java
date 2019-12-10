package cn.droidlover.xrecyclerview;


import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wanglei on 2016/10/30.
 */

public class XDataObserver extends RecyclerView.AdapterDataObserver {

    private XRecyclerAdapter adapter;

    public XDataObserver(XRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onChanged() {
        super.onChanged();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        adapter.notifyItemRangeChanged(positionStart + adapter.getHeaderSize(), itemCount);
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        adapter.notifyItemRangeInserted(positionStart + adapter.getHeaderSize(), itemCount);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        adapter.notifyItemRangeChanged(fromPosition + adapter.getHeaderSize(), itemCount + adapter.getHeaderSize() + toPosition);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        adapter.notifyItemRangeRemoved(positionStart + adapter.getHeaderSize(), itemCount);
    }
}
