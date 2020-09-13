package com.example.colormyviews

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        val viewList=
            listOf(box_one_text, box_two_text, box_three_text, box_four_text, box_five_text,
                constraint_layout, red_button, green_button, blue_button)
        for (view in viewList) {
            view.setOnClickListener { makeColored(it) }
        }
    }

    private fun makeColored(view: View) {
        when (view.id) {
            // Boxes using Color class colors for background
            R.id.box_one_text -> view.setBackgroundColor(Color.MAGENTA)
            R.id.box_two_text -> view.setBackgroundColor(Color.CYAN)
            R.id.box_three_text -> view.setBackgroundColor(Color.RED)
            R.id.box_four_text -> view.setBackgroundColor(Color.GREEN)
            R.id.box_five_text -> view.setBackgroundColor(Color.BLUE)
            // Buttons using color values from the resources
            R.id.red_button -> box_three_text.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.my_red))
            R.id.green_button -> box_four_text.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.my_green))
            R.id.blue_button -> box_five_text.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.my_blue))
            else -> view.setBackgroundColor(Color.LTGRAY)
        }
    }
}