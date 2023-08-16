package com.test.myapplication.ui.main

import android.graphics.Rect
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.test.myapplication.MainActivity
import com.test.myapplication.R
import com.test.myapplication.databinding.DetailFragmentBinding
import com.test.myapplication.ui.main.adapter.IMAGE_TYPE
import com.test.myapplication.ui.main.adapter.NUMBER_TYPE
import com.test.myapplication.ui.main.adapter.NumberAdapter
import com.test.myapplication.ui.main.adapter.TEXT_TYPE
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class MindReadingFragment : Fragment() {

    companion object {
        fun newInstance() : MindReadingFragment {
            val fr = MindReadingFragment()

            return fr
        }
    }

    private var gameOver: Boolean = false
    lateinit var mBinding: DetailFragmentBinding

    private lateinit var mAdapter: NumberAdapter
    private lateinit var viewModel: MainViewModel
    lateinit var gridLayoutManager: GridLayoutManager

    var list = ArrayList<Int>()

    val firstList = ArrayList<Int>()

    var count = 0
    var logarithm = 0f

    var item = "数字"
    var itemCount = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DetailFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.run {
            setOnClickListener {
                viewModel.setData(Calendar.getInstance().time.toLocaleString())
            }
        }
        view?.findViewById<Button>(R.id.button)?.setOnClickListener {
            (requireActivity() as MainActivity).back()
        }

        viewModel.logSb.observe(requireActivity()) {
            mBinding.tvLog.text = it.toString()
        }

        viewModel.isRunning.observe(requireActivity()) {
            mBinding.btnTop.isEnabled = !it
            mBinding.btnBottom.isEnabled = !it
            mBinding.btnImage.isEnabled = !it
            mBinding.btnText.isEnabled = !it
            mBinding.btnNumber.isEnabled = !it
            mBinding.btnItem2.isEnabled = !it
            mBinding.btnItem4.isEnabled = !it
            mBinding.btnItem8.isEnabled = !it
            mBinding.btnItem16.isEnabled = !it
            mBinding.btnShuffle.isEnabled = !it
            mBinding.btnItem32.isEnabled = !it
        }

        mBinding.btnShuffle.setOnClickListener {
            restart()
        }

        mBinding.btnTop.setOnClickListener {
            clickTop()
        }

        mBinding.btnBottom.setOnClickListener {
            clickBottom()
        }

        mBinding.btnItem2.setOnClickListener {
            itemCount = 2
            selectItemCount()
            restart()
        }

        mBinding.btnItem4.setOnClickListener {
            itemCount = 4
            selectItemCount()
            restart()
        }

        mBinding.btnItem8.setOnClickListener {
            itemCount = 8
            selectItemCount()
            restart()
        }

        mBinding.btnItem16.setOnClickListener {
            itemCount = 16
            selectItemCount()
            restart()
        }

        mBinding.btnItem32.setOnClickListener {
            itemCount = 32
            selectItemCount()
            restart()
        }

        (mBinding.rvNumber.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false

        selectItemCount()

        mBinding.rvNumber.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val p = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
                if (itemCount in listOf(2, 4, 8)) {
                    if (p >= itemCount / 2) {
                        outRect.top = 50
                    }
                } else {
                    if (p >= itemCount / 2 && p < itemCount / 2 + 4) {
                        outRect.top = 50
                    } else {
                        outRect.top = 10
                    }
                }
            }
        })

        mAdapter = NumberAdapter(list)
        mBinding.rvNumber.adapter = mAdapter

        mBinding.btnImage.setOnClickListener {
            mAdapter.type = IMAGE_TYPE
            restart()
        }
        mBinding.btnText.setOnClickListener {
            mAdapter.type = TEXT_TYPE
            restart()
        }
        mBinding.btnNumber.setOnClickListener {
            mAdapter.type = NUMBER_TYPE
            restart()
        }

        shuffle()

        start()
    }

    /**
     * 选择纸牌数量
     */
    private fun selectItemCount() {
        list.clear()
        ItemData.refreshData(itemCount)
        val spanCount = if (itemCount in listOf(2, 4)) itemCount / 2 else 4
        gridLayoutManager = GridLayoutManager(context, spanCount)

        mBinding.rvNumber.layoutManager = gridLayoutManager
        logarithm = log(itemCount.toFloat(), 2f)
    }

    private fun restart() {
        lifecycleScope.async {
            firstList.clear()
            viewModel.clearLog()
            count = 0
            item = when(mAdapter.type) {
                IMAGE_TYPE -> getString(R.string.image_type)
                TEXT_TYPE -> getString(R.string.text_type)
                else -> getString(R.string.number_type)
            }
            mBinding.btnShuffle.isEnabled = false
            mBinding.tvResult.text = "?"
            mBinding.ivResult.setImageResource(0)
            mBinding.tvResult.visibility = View.VISIBLE
            mBinding.ivResult.visibility = View.GONE

            viewModel.isRunning.postValue(false)
            shuffle(true)
            viewModel.updateLog(getString(R.string.shuffle_over))
            start()
        }
    }

    private fun start() {

        lifecycleScope.async {
            gameOver = false
            viewModel.updateLog(getString(R.string.please_select_an_item, item))
            viewModel.updateLog(getString(R.string.tell_me_use_click_button))
        }

    }

    private fun clickBottom() {
        if (gameOver) {
            Toast.makeText(context, getString(R.string.play_agin), Toast.LENGTH_SHORT).show()
            return
        } else {
            viewModel.isRunning.postValue(true)

            lifecycleScope.async {
                viewModel.updateLog(getString(R.string.you_select_bottom))
                thinking(0)
            }
        }
    }

    private fun clickTop() {
        if (gameOver) {
            Toast.makeText(context, getString(R.string.play_agin), Toast.LENGTH_SHORT).show()
            return
        } else {
            viewModel.isRunning.postValue(true)

            lifecycleScope.async {
                viewModel.updateLog(getString(R.string.you_select_top))
                thinking(1)
            }
        }
    }

    /**
     * @param area 所选item的区域 1:上层，0:下层
     */
    private suspend fun thinking(area: Int) {

        if (count < logarithm - 1) {

            if (count == 0) {

                if (area == 1) {
                    firstList.addAll(list.subList(0, list.size / 2))
                } else {
                    firstList.addAll(list.subList(list.size / 2, list.size))
                }

            } else {

                val tempList = ArrayList<Int>()

                if (area == 1) {
                    tempList.addAll(firstList.subList(0, firstList.size / 2))
                } else {
                    tempList.addAll(firstList.subList(firstList.size / 2, firstList.size))
                }

                firstList.clear()
                firstList.addAll(tempList)

            }
            //refresh
            shuffle(true)

            //replace
            replace(firstList)

            mAdapter.notifyItemRangeChanged(0, list.size)
            viewModel.updateLog(getString(R.string.shuffle_over))
            viewModel.updateLog(getString(R.string.now_tell_me_which_layer, item))
            count++

        } else {
            if (firstList.isEmpty()) {
                firstList.addAll(list)
            }
            val result = if (area == 1) firstList[0] else firstList[1]

            val txt = when (mAdapter.type) {
                TEXT_TYPE -> ItemData.text[result]
                NUMBER_TYPE -> ItemData.numbers[result]
                else -> {}
            }

            viewModel.updateLog(getString(R.string.think_result, item))

            if (mAdapter.type == IMAGE_TYPE) {
                mBinding.ivResult.visibility = View.VISIBLE
                mBinding.tvResult.visibility = View.GONE
                mBinding.ivResult.setImageResource(ItemData.imageResource[result])
            } else {
                mBinding.ivResult.visibility = View.GONE
                mBinding.tvResult.visibility = View.VISIBLE
                mBinding.tvResult.text = "${txt}"
            }
            gameOver = true
        }
        viewModel.isRunning.postValue(false)
    }

    private fun replace(resList: List<Int>) {
        val len = resList.size / 2
        val top = resList.subList(0, len)
        val bottom = resList.subList(len, resList.size)

        //保证洗牌后，用户所选牌所在的那一层牌一半在上，一半在下
        top.forEach {
            if (it !in list.subList(0, list.size / 2)) {
                val bIndex = list.indexOf(it)//先找出它所在位置，其实就是在下层的某一个位置

                val index = Random().nextInt(list.size / 2)
                val temp = list[index]
                list[index] = it
                list[bIndex] = temp
            }
        }

        bottom.forEach {
            if (it !in list.subList(list.size / 2, list.size)) {
                val tIndex = list.indexOf(it)//同top，在上层的某一个位置

                val index = list.size / 2 + Random().nextInt(list.size / 2)
                val temp = list[index]
                list[index] = it

                list[tIndex] = temp
            }
        }
    }

    private fun shuffle() {
        val temp = (0 until itemCount).toList()
        Collections.shuffle(temp)
        list.clear()
        list.addAll(temp)
        mAdapter.notifyItemRangeChanged(0, list.size)
    }

    private suspend fun shuffle(smooth: Boolean) {
        viewModel.updateLog(getString(R.string.re_shuffle))

        if (!smooth) {
            shuffle()
        } else {

            repeat(10) {
                delay(300)
                shuffle()
            }

        }
    }

}