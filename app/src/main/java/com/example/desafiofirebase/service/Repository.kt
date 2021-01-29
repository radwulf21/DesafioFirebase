package com.example.desafiofirebase.service

import com.example.desafiofirebase.domain.Game
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

interface Repository {
    var database: FirebaseDatabase
    var reference: DatabaseReference
    var storageReference: StorageReference

    suspend fun connectDatabaseTask()

    suspend fun startReferenceStorageTask(): StorageReference

    suspend fun insertGameDatabaseTask(game: Game)

    suspend fun getAllGamesDatabaseTask(): Task<DataSnapshot>
}

class RepositoryDatabase() : Repository {
    override lateinit var database: FirebaseDatabase
    override lateinit var reference: DatabaseReference
    override lateinit var storageReference: StorageReference

    override suspend fun connectDatabaseTask() {
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("games")
    }

    override suspend fun startReferenceStorageTask(): StorageReference {
        storageReference = FirebaseStorage.getInstance().getReference("game_img/" + UUID.randomUUID())
        return storageReference
    }

    override suspend fun insertGameDatabaseTask(game: Game) {
        reference.push().setValue(game)
    }

    override suspend fun getAllGamesDatabaseTask(): Task<DataSnapshot> {
        return reference.get()
    }
}