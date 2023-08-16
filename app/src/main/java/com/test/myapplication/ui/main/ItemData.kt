package com.test.myapplication.ui.main

import com.test.myapplication.R
import java.util.Random

class ItemData {


    companion object {

        private val IMG_RES = arrayListOf(
            R.mipmap.img_ace_of_spades, R.mipmap.img_2_of_spades, R.mipmap.img_3_of_spades,
            R.mipmap.img_4_of_spades, R.mipmap.img_5_of_spades, R.mipmap.img_6_of_spades,
            R.mipmap.img_7_of_spades, R.mipmap.img_8_of_spades, R.mipmap.img_9_of_spades,
            R.mipmap.img_10_of_spades, R.mipmap.img_jack_of_spades, R.mipmap.img_queen_of_spades,
            R.mipmap.img_king_of_spades,

            R.mipmap.img_ace_of_hearts, R.mipmap.img_2_of_hearts, R.mipmap.img_3_of_hearts,
            R.mipmap.img_4_of_hearts, R.mipmap.img_5_of_hearts, R.mipmap.img_6_of_hearts,
            R.mipmap.img_7_of_hearts, R.mipmap.img_8_of_hearts, R.mipmap.img_9_of_hearts,
            R.mipmap.img_10_of_hearts, R.mipmap.img_jack_of_hearts, R.mipmap.img_queen_of_hearts,
            R.mipmap.img_king_of_hearts,

            R.mipmap.img_ace_of_diamonds, R.mipmap.img_2_of_diamonds, R.mipmap.img_3_of_diamonds,
            R.mipmap.img_4_of_diamonds, R.mipmap.img_5_of_diamonds, R.mipmap.img_6_of_diamonds,
            R.mipmap.img_7_of_diamonds, R.mipmap.img_8_of_diamonds, R.mipmap.img_9_of_diamonds,
            R.mipmap.img_10_of_diamonds, R.mipmap.img_jack_of_diamonds, R.mipmap.img_queen_of_diamonds,
            R.mipmap.img_king_of_diamonds,

            R.mipmap.img_ace_of_clubs, R.mipmap.img_2_of_clubs, R.mipmap.img_3_of_clubs,
            R.mipmap.img_4_of_clubs, R.mipmap.img_5_of_clubs, R.mipmap.img_6_of_clubs,
            R.mipmap.img_7_of_clubs, R.mipmap.img_8_of_clubs, R.mipmap.img_9_of_clubs,
            R.mipmap.img_10_of_clubs, R.mipmap.img_jack_of_clubs, R.mipmap.img_queen_of_clubs,
            R.mipmap.img_king_of_clubs
        )

        private val TEXT_RES = arrayListOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "中", "文", "来", "凑", "数", "了")

        init {
            refreshData()
        }

        fun refreshData(count: Int = 8) {
            imageResource = combine(IMG_RES, count)
            text = combine(TEXT_RES, count)
            numbers = (1 .. count).toList()
        }

        var imageResource = ArrayList<Int>(

        )

        var text = ArrayList<String>()

        var numbers = (1.. 8).toList()

        private fun <T: Any> combine(list: ArrayList<T>, count: Int): ArrayList<T> {
            if (count > list.size) {
                return list
            }

            val result = ArrayList<T>()
            val temp = ArrayList<T>()
            temp.addAll(list)

            for (i in 0 until count) {
                val index = Random().nextInt(temp.size)
                result.add(temp[index])
                temp.removeAt(index)
            }
            return result
        }
    }
}