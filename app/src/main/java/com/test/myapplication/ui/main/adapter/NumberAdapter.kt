package com.test.myapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.myapplication.R
import com.test.myapplication.ui.main.ItemData

const val IMAGE_TYPE = 1
const val TEXT_TYPE = 2
const val NUMBER_TYPE = 3

class NumberAdapter(val list: ArrayList<Int>): RecyclerView.Adapter<NumberAdapter.NumberHolder>() {

    var type: Int = NUMBER_TYPE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberHolder
            = NumberHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_number, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NumberHolder, position: Int) {
        if (type == IMAGE_TYPE) {
            holder.ivPic.visibility = View.VISIBLE
            holder.tvNumber.visibility = View.GONE

            holder.ivPic.setImageResource(ItemData.imageResource[list[position]])
        } else {
            holder.ivPic.visibility = View.GONE
            holder.tvNumber.visibility = View.VISIBLE

            if (type == TEXT_TYPE) {
                holder.tvNumber.text = ItemData.text[list[position]]
            } else {
                holder.tvNumber.text = ItemData.numbers[list[position]].toString()
            }
        }

    }

    inner class NumberHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvNumber: TextView
        var ivPic: ImageView
        init {
            tvNumber = itemView.findViewById(R.id.tv_number)
            ivPic = itemView.findViewById(R.id.iv_pic)
        }
    }
}