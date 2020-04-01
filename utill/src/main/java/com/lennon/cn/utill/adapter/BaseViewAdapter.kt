package com.lennon.cn.utill.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import com.lennon.cn.utill.bean.BaseViewBean
import lennon.com.utill.R

class BaseViewAdapter(context: Context) : SimpleRecAdapter<BaseViewBean, BaseViewAdapter.ViewHolder>(context) {
    override fun newViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun getLayoutId(): Int {
        return R.layout.item_base_view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val d = data[position]
        d.loadIcon(holder.icon)
        d.loadName(holder.name)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val icon = v.findViewById<ImageView>(R.id.icon)
        val name = v.findViewById<TextView>(R.id.name)
    }
}