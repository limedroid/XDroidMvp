package com.lennon.cn.utill.adapter

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import com.lennon.cn.utill.bean.StringBean
import com.lennon.cn.utill.utill.TeminalDevice
import com.lennon.cn.utill.utill.Utill
import lennon.com.utill.R

/**
 * Created by lennon on 2017/9/20.
 */

open class StringItemAdapter<T : StringBean>(context: Context) :
    SimpleRecAdapter<T, StringItemAdapter<T>.ViewHolder>(context) {

    private var textSize: List<Int>? = null
    private var typefaces: List<Typeface>? = null
    private var colors: List<Int>? = null
    fun setTypefaces(typefaces: List<Typeface>) {
        this.typefaces = typefaces
    }

    fun setColors(colors: List<Int>) {
        this.colors = colors
    }

    fun setTextSize(textSize: List<Int>) {
        this.textSize = textSize
    }

    override fun newViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun getLayoutId(): Int {
        return R.layout.item_string_item
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.name.text = data[position].itemString
        holder.name.setBackgroundResource(R.color.color_ffffff)
        holder.name.setTextColor(Utill.getColor(context.resources, R.color.color_0F83F0))
        if (typefaces != null && typefaces!!.isNotEmpty()) {
            holder.name.typeface = typefaces!![position % typefaces!!.size]
        }
        if (colors != null && colors!!.isNotEmpty()) {
            holder.name.setBackgroundColor(Utill.getColor(context.resources, colors!![position % colors!!.size]))
        }
        if (textSize != null && textSize!!.isNotEmpty()) {
            holder.name.textSize = TeminalDevice.dip2px(
                context,
                textSize!![position % textSize!!.size].toFloat()
            ).toFloat()
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name = itemView.findViewById<View>(R.id.name) as TextView

        fun upBack(resource: Int, textColor: Int) {
            name.setBackgroundResource(resource)
            name.setTextColor(Utill.getColor(context.resources, textColor))
        }

        fun unselect() {
            upBack(R.drawable.conner_f2f2f2, R.color.color_333333)
        }

        fun select() {
            upBack(R.drawable.conner_fd0202, R.color.color_ffffff)
        }
    }
}
