package com.example.desafiofirebase.ui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.example.desafiofirebase.R
import com.example.desafiofirebase.domain.Game
import com.example.desafiofirebase.service.RepositoryDatabase
import com.example.desafiofirebase.ui.home.HomeActivity
import com.example.desafiofirebase.ui.viewmodel.GameViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.storage.StorageReference
import dmax.dialog.SpotsDialog

class RegisterGameActivity : AppCompatActivity() {
    private val CODE_IMG = 1000
    private lateinit var alertDialog: AlertDialog
    private lateinit var storageReference: StorageReference
    private lateinit var urlImage: String
    private var imageIsLoaded: Boolean = false

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
        setContentView(R.layout.activity_register_game)

        findViewById<LinearLayout>(R.id.llCamera).setOnClickListener {
            configureIntentToGetImageOfGalery()
        }

        findViewById<Button>(R.id.btnSaveGame).setOnClickListener {
            val etNameGame = findViewById<TextInputEditText>(R.id.etNameGame)
            val etCreatedAtGame = findViewById<TextInputEditText>(R.id.etCreatedAtGame)
            val etDescriptionGame = findViewById<TextInputEditText>(R.id.etDescriptionGame)
            
            if (etNameGame.text.toString() == "" || etCreatedAtGame.text.toString() == "" || etDescriptionGame.text.toString() == "") {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else if (!imageIsLoaded) {
                Toast.makeText(this, "Carregue uma imagem do game!", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.connectDatabase()
                viewModel.insertGameDatabase(Game(
                        urlImage,
                        etNameGame.text.toString(),
                        etCreatedAtGame.text.toString(),
                        etDescriptionGame.text.toString()
                    )
                )

                Toast.makeText(this, "Game registrado com sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }

        alertDialog = SpotsDialog.Builder().setContext(this).build()
    }

    fun configureIntentToGetImageOfGalery() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "image"), CODE_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewModel.startReferenceStorage()
        viewModel.storageReference.observe(this) {
            storageReference = it

            if (requestCode == CODE_IMG) {

                alertDialog.show()

                val uploadTask = storageReference.putFile(data!!.data!!)
                uploadTask.continueWithTask { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Imagem carreagada com sucesso...", Toast.LENGTH_LONG).show()
                    }
                    storageReference.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        urlImage = downloadUri!!.toString().substring(0, downloadUri.toString().indexOf("&token"))
                        imageIsLoaded = true
                        Log.i("Link Direto", urlImage)
                        alertDialog.dismiss()
                    }
                }
            }
        }
    }
}