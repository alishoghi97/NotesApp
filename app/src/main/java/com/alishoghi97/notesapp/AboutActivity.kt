package com.alishoghi97.notesapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textview.MaterialTextView

class AboutActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Set up the toolbar
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "About"

        // Set up the content
        val aboutTextView: MaterialTextView = findViewById(R.id.aboutTextView)
        aboutTextView.text = """
    Welcome to MyApp!

    MyApp is a modern Android application built with the latest technologies to provide you with a seamless and efficient experience. Our app follows the MVVM architecture pattern, ensuring a clean and organized codebase.

    Features:
    - **Room Database:** MyApp utilizes Room, a powerful SQLite object mapping library, to provide a robust and efficient local data storage solution. This ensures your data is stored securely on your device, and the app can seamlessly retrieve and manage data.

    - **MVVM Architecture:** We follow the MVVM architecture pattern, separating concerns between the Model, View, and ViewModel layers. This not only enhances code readability but also promotes maintainability and scalability.

    - **Material Design:** The app's user interface is designed using Material Components for Android, offering a modern and consistent design language. Enjoy a visually appealing and user-friendly experience.

    - **Advanced Features:** MyApp comes with a range of advanced features, such as [mention some key features, e.g., real-time updates, offline mode, etc.].

    We are committed to providing a top-notch user experience and continually improving our app. If you have any feedback or suggestions, feel free to reach out to us. Thank you for choosing MyApp!
"""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}