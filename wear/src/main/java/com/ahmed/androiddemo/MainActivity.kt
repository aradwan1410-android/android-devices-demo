package com.ahmed.androiddemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.view.Gravity
import android.graphics.Color

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Create a simple demo showing Android Wear interface
        val textView = TextView(this).apply {
            text = "Android Wear Demo\n\n" +
                   "• Round Display Support\n" +
                   "• Complications\n" +
                   "• Health Tracking\n" +
                   "• Notifications\n" +
                   "• Voice Actions"
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            textSize = 16f
            setPadding(20, 40, 20, 40)
        }
        
        setContentView(textView)
        setTitle("Wear OS Demo")
    }
}
