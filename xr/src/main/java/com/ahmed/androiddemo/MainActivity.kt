package com.ahmed.androiddemo

import android.os.Bundle
import android.widget.TextView
import android.view.Gravity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Create a simple demo showing Android XR/AR capabilities
        val textView = TextView(this).apply {
            text = "Android XR/AR Demo\n\n" +
                   "• Augmented Reality\n" +
                   "• ARCore Support\n" +
                   "• 3D Object Rendering\n" +
                   "• Motion Tracking\n" +
                   "• Environmental Understanding\n" +
                   "• Light Estimation\n\n" +
                   "Note: Run on device with AR support\n" +
                   "or use emulator with ARCore enabled"
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            textSize = 16f
            setPadding(20, 40, 20, 40)
            setBackgroundColor(Color.BLACK)
        }
        
        setContentView(textView)
        setTitle("XR/AR Demo")
    }
}
