package com.test.myapplication.ui.main.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.test.myapplication.R

class WrapAdapter(val list: Array<ArrayList<Int>>): RecyclerView.Adapter<WrapAdapter.WrapHolder> () {


    inner class WrapHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var rvNumber: RecyclerView = itemView.findViewById(R.id.rv_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = WrapHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_wrap, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: WrapHolder, position: Int) {
        holder.rvNumber.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.rvNumber.adapter = Number2Adapter(list[position])
        holder.rvNumber.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val p = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
                if (p > 0) {
                    outRect.top = 5
                }
            }
        })
    }
}