package com.test.myapplication.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.test.myapplication.MainActivity
import com.test.myapplication.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    lateinit var btn: Button
    lateinit var btnGuessNumber: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity?.application!!))
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
//        viewModel?.run {
//            setData("返回了")
//        }
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn = view.findViewById(R.id.button)
        btnGuessNumber = view.findViewById(R.id.btn_guess_number)
        view.setOnTouchListener { _, _ ->
            viewModel.setData(System.currentTimeMillis().toString())
            false
        }
        btn.setOnClickListener {
            (requireActivity() as MainActivity).jump()
        }

        btnGuessNumber.setOnClickListener {
            (requireActivity() as MainActivity).guessNumber()
        }
    }

}