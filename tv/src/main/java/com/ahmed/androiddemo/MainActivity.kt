package com.ahmed.androiddemo

import android.os.Bundle
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ListRowPresenter
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Create a simple demo showing Android TV interface
        val rowsFragment = BrowseSupportFragment()
        val adapter = ArrayObjectAdapter(ListRowPresenter())
        
        // Add demo items for presentation
        val items = listOf(
            "Android TV Demo",
            "Leanback Library",
            "Big Screen Experience",
            "D-Pad Navigation",
            "Recommendations Row"
        )
        
        rowsFragment.adapter = adapter
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, rowsFragment)
            .commit()
            
        setTitle("Android TV Demo")
    }
}
