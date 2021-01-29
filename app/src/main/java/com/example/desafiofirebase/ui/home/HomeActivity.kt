package com.example.desafiofirebase.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofirebase.R
import com.example.desafiofirebase.service.RepositoryDatabase
import com.example.desafiofirebase.ui.DetailsGameActivity
import com.example.desafiofirebase.ui.RegisterGameActivity
import com.example.desafiofirebase.ui.viewmodel.GameViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity(), GameAdapter.OnClickGameListener {
    lateinit var adapterGame: GameAdapter
    lateinit var rvGames: RecyclerView

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
        setContentView(R.layout.activity_home)

        findViewById<FloatingActionButton>(R.id.fbRegisterGame).setOnClickListener {
            startActivity(Intent(this, RegisterGameActivity::class.java))
        }

        setSupportActionBar(findViewById(R.id.tbDetailsGame))
        instantiateAdapterAndRecyler()

        adapterGame.listGames

        messageListGamesEmpty()
    }

    private fun instantiateAdapterAndRecyler() {
        adapterGame = GameAdapter(this, this)
        rvGames = findViewById(R.id.rvGames)

        rvGames.adapter = adapterGame
        rvGames.layoutManager = GridLayoutManager(this, 2)
        rvGames.hasFixedSize()
    }

    private fun messageListGamesEmpty() {
        if (adapterGame.listGames.size == 0) {
            findViewById<TextView>(R.id.tvMsgListEmpty).visibility = View.VISIBLE
        } else {
            findViewById<TextView>(R.id.tvMsgListEmpty).visibility = View.INVISIBLE
        }
    }

    override fun onClickGame(position: Int) {
        val game = adapterGame.listGames[position]
        startActivity(
            Intent(this, DetailsGameActivity::class.java).apply {
                putExtra("game", game)
            }
        )
    }
}