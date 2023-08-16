package com.test.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.myapplication.ui.main.DetailFragment
import com.test.myapplication.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    fun jump() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailFragment.newInstance())
            .commitNow()
    }

    fun back() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
    }

}