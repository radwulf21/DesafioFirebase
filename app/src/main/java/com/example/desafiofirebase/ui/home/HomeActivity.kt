package com.example.desafiofirebase.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofirebase.R
import com.example.desafiofirebase.domain.Game
import com.example.desafiofirebase.ui.DetailsGameActivity
import com.example.desafiofirebase.ui.RegisterGameActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity(), GameAdapter.OnClickGameListener {
    lateinit var adapterGame: GameAdapter
    lateinit var rvGames: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<FloatingActionButton>(R.id.fbRegisterGame).setOnClickListener {
            startActivity(Intent(this, RegisterGameActivity::class.java))
        }

        setSupportActionBar(findViewById(R.id.tbDetailsGame))
        instantiateAdapterAndRecyler()
        messageListGamesEmpty()

        adapterGame.listGames
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