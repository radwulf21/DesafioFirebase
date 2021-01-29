package com.example.desafiofirebase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.desafiofirebase.R
import com.example.desafiofirebase.domain.Game
import com.example.desafiofirebase.service.RepositoryDatabase
import com.example.desafiofirebase.ui.viewmodel.GameViewModel

class DetailsGameActivity : AppCompatActivity() {
    private val repository = RepositoryDatabase()
    private val viewModel by viewModels<GameViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return GameViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_game)

        val game = intent.getParcelableExtra<Game>("game")

        Glide.with(this).load(game?.pathImg.toString()).into(findViewById(R.id.ivGame))
        findViewById<TextView>(R.id.tvTitleNameGame).text = game?.name.toString()
        findViewById<TextView>(R.id.tvNameGame).text = game?.name.toString()
        findViewById<TextView>(R.id.tvCreatedAtGame).text = game?.createdAt.toString()
        findViewById<TextView>(R.id.tvDescriptionGame).text = game?.description.toString()
    }
}