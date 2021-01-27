package com.example.desafiofirebase.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiofirebase.R
import com.example.desafiofirebase.ui.RegisterGameActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<FloatingActionButton>(R.id.fbRegisterGame).setOnClickListener {
            startActivity(Intent(this, RegisterGameActivity::class.java))
        }
    }
}