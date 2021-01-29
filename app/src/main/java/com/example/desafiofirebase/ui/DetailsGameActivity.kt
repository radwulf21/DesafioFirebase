package com.example.desafiofirebase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.desafiofirebase.R
import com.example.desafiofirebase.domain.Game

class DetailsGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_game)

        val game = intent.getParcelableExtra<Game>("game")

        Glide.with(this).load(game?.pathImg).into(findViewById(R.id.ivGame))
        findViewById<TextView>(R.id.tvTitleNameGame).text = game?.name
        findViewById<TextView>(R.id.tvNameGame).text = game?.name
        findViewById<TextView>(R.id.tvCreatedAtGame).text = game?.createdAt
        findViewById<TextView>(R.id.tvDescriptionGame).text = game?.description
    }
}