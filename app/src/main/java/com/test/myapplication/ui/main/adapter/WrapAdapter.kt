package com.test.myapplication.ui.main.adapter

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.test.myapplication.R
import com.test.myapplication.ui.main.data.NumberWrap

class WrapAdapter(val list: Array<NumberWrap>): RecyclerView.Adapter<WrapAdapter.WrapHolder> () {


    inner class WrapHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var rvNumber: RecyclerView = itemView.findViewById(R.id.rv_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = WrapHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_wrap, parent, false))

    override fun getItemCount() = list.size

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: WrapHolder, position: Int) {
        val numberWrap = list[position]
        
        holder.rvNumber.layoutManager = LinearLayoutManager(holder.itemView.context)

        holder.rvNumber.adapter = Number2Adapter(numberWrap.list!!)

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

        holder.rvNumber.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val number2Adapter: Number2Adapter = holder.rvNumber.adapter as Number2Adapter
                number2Adapter.isSelected = !number2Adapter.isSelected
                list[position].isSelected = number2Adapter.isSelected
                number2Adapter.notifyItemRangeChanged(0, numberWrap.list?.size!!)
            }
            v.onTouchEvent(event)
        }

    }
}