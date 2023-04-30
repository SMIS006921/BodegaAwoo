package com.example.bodega

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val btnprod = findViewById<Button>(R.id.btnproductos)
        val btnprov = findViewById<Button>(R.id.btnproveedores)

        btnprod.setOnClickListener{
            val intent = Intent(this, productos::class.java)
            startActivity(intent)
        }

        btnprov.setOnClickListener{
            val intent = Intent(this, proveedores::class.java)
            startActivity(intent)
        }
    }
}