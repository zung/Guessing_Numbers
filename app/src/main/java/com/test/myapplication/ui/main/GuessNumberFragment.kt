package com.test.myapplication.ui.main

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.test.myapplication.R
import com.test.myapplication.databinding.FragmentGuessNumberBinding
import com.test.myapplication.ui.main.adapter.WrapAdapter
import kotlin.math.pow

/**
 * A simple [Fragment] subclass.
 * Use the [GuessNumberFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuessNumberFragment : Fragment() {

    lateinit var mBinding: FragmentGuessNumberBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentGuessNumberBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (mBinding.rvWrap.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false

        mBinding.rvWrap.layoutManager = GridLayoutManager(context, 6)

        mBinding.rvWrap.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val p = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
                if (p % 6 != 0) {
                    outRect.left = 10
                }
            }
        })
        val list = createData()
        mBinding.rvWrap.adapter = WrapAdapter(list)
    }

    private fun createData(): Array<ArrayList<Int>> {
        val data = Array<ArrayList<Int>>(6){ arrayListOf() }

        for (i in 1 until 64) {
            val placeHolder = intArrayOf(0, 0, 0, 0, 0, 0)
            var sum = 0.0

            for (j in placeHolder.size - 1 downTo 0) {
                val adder = 2.0.pow(j.toDouble())

                if (i < adder) {
                    continue
                } else if (i == adder.toInt()) {
                    placeHolder[j] = 1
                    break
                } else {
                    sum += adder
                    placeHolder[j] = 1
                }

                if (i == sum.toInt()) {
                    break
                } else if (i < sum.toInt()) {
                    sum -= adder
                    placeHolder[j] = 0
                    continue
                } else {
                    continue
                }
            }

            placeHolder.forEachIndexed {ind, it ->
                if (it == 1) {
                    data[ind].add(i)
                }
            }
        }
        return data
    }

    companion object {
        @JvmStatic
        fun newInstance() = GuessNumberFragment()
    }
}