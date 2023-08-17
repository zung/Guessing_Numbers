package com.test.myapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.test.myapplication.R

class Number2Adapter(val list: ArrayList<Int>): RecyclerView.Adapter<Number2Adapter.Number2Holder>() {

    var isSelected = false

    inner class Number2Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvNumber: TextView = itemView.findViewById(R.id.tv_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = Number2Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_number_2, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: Number2Holder, position: Int) {
        holder.tvNumber.text = "${list[position]}"
        if (isSelected) {
            holder.tvNumber.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.purple_200))
            holder.tvNumber.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        } else {
            holder.tvNumber.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.tvNumber.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        }
    }
}