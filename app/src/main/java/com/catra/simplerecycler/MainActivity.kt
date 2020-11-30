package com.catra.simplerecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.holder_example.view.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var list = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        simpleRecycler.listBuilder(
                layoutRes = R.layout.holder_example,
                itemCount = list.size
        ) { view, position ->

            view.name.text = list[position].toString()
            view.setOnClickListener {
                Toast.makeText(
                        this,
                        "click element ${list[position]}",
                        Toast.LENGTH_SHORT
                ).show()
            }

        }

        addElements.setOnClickListener {
            list.add(getRandomNumber())
            simpleRecycler.submitItems(list.size)
        }
    }

    private fun getRandomNumber(): Int {
        return Random.nextInt()
    }
}
