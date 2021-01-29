package com.example.desafiofirebase.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafiofirebase.R
import com.example.desafiofirebase.domain.Game

class GameAdapter(
        var context: HomeActivity,
        var listener: OnClickGameListener
): RecyclerView.Adapter<GameAdapter.GameAdapterViewHolder>() {
    var listGames = arrayListOf<Game>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameAdapterViewHolder, position: Int) {
        val game = listGames[position]
        Glide.with(holder.itemView).load(game.pathImg).into(holder.ivGame)
        holder.tvNameGame.text = game.name
        holder.tvCreatedAtGame.text = game.createdAt
    }

    override fun getItemCount() = listGames.size

    inner class GameAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val ivGame: ImageView = itemView.findViewById(R.id.ivGame)
        val tvNameGame: TextView = itemView.findViewById(R.id.tvNameGame)
        val tvCreatedAtGame: TextView = itemView.findViewById(R.id.tvCreatedAtGame)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onClickGame(position)
            }
        }
    }

    interface OnClickGameListener {
        fun onClickGame(position: Int)
    }
}