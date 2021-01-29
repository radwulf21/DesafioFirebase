package com.example.desafiofirebase.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiofirebase.domain.Game
import com.example.desafiofirebase.service.Repository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch

class GameViewModel(val repository: Repository) : ViewModel() {
    val listGames = MutableLiveData<ArrayList<Game>>()
    val storageReference = MutableLiveData<StorageReference>()

    fun connectDatabase() {
        viewModelScope.launch {
            repository.connectDatabaseTask()
        }
    }

    fun startReferenceStorage() {
        viewModelScope.launch {
             storageReference.value = repository.startReferenceStorageTask()
        }
    }

    fun insertGameDatabase(game: Game) {
        viewModelScope.launch {
            repository.insertGameDatabaseTask(game)
        }
    }

    fun getAllGamesDatabase() {

        val listGamesDatabase = arrayListOf<Game>()

        viewModelScope.launch {
            repository.getAllGamesDatabaseTask()
            repository.reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val game = it.getValue(Game::class.java)
                        listGamesDatabase.add(game!!)
                    }
                    listGames.value = listGamesDatabase
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i("Error Read Data", "Failed to read value.", error.toException())
                }
            })
        }
    }
}