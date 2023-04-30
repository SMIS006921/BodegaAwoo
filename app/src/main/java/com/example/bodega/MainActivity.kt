package com.example.bodega

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnmenu = findViewById<Button>(R.id.btnmenu)
        btnmenu.setOnClickListener{
            val intent = Intent(this, menu::class.java)
            startActivity(intent)
        }
    }
}