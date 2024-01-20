package com.alishoghi97.notesapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddNoteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_CONTENT = "EXTRA_DESC"
    }

    private lateinit var txtTitle: EditText
    private lateinit var txtDescription: EditText
    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        bindViews()

        val inputIntent = intent
        if (inputIntent.hasExtra(EXTRA_ID)) {
            title = "Edit ToDo"
            txtTitle.setText(inputIntent.getStringExtra(EXTRA_TITLE))
            txtDescription.setText(inputIntent.getStringExtra(EXTRA_CONTENT))
            btnAdd.text = "Update Item"
        } else {
            title = "Add ToDo"
            btnAdd.text = "Add New Item"
        }

        btnAdd.setOnClickListener {
            val title = txtTitle.text.toString()
            val description = txtDescription.text.toString()
            if (title == "" || description == "") {
                Toast.makeText(this, "Please fill title and description", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val data = Intent()
            data.putExtra(EXTRA_TITLE, title)
            data.putExtra(EXTRA_CONTENT, description)

            val id = intent.extras?.getInt(EXTRA_ID, -1)
            if (id != -1) {
                data.putExtra(EXTRA_ID, id)
            }

            setResult(RESULT_OK, data)
            finish()
        }
    }

    private fun bindViews() {
        txtTitle = findViewById(R.id.txtTitle)
        txtDescription = findViewById(R.id.txtDescription)
        btnAdd = findViewById(R.id.btnAdd)
    }
}