package com.riza.praktekp5_storage

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etJournalTitle = findViewById<EditText>(R.id.etJournalTitle)
        val etJournalContent = findViewById<EditText>(R.id.etJournalContent)
        val btnSaveAll = findViewById<Button>(R.id.btnSaveAll)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)

        val journalManager = JournalManager(this)

        btnSaveAll.setOnClickListener {
            val user = etUsername.text.toString()
            val title = etJournalTitle.text.toString()
            val content = etJournalContent.text.toString()

            if (user.isNotEmpty() && title.isNotEmpty()) {
                val prefs = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
                prefs.edit().putString("username", user).apply()

                journalManager.saveEntry(title, content)

                tvStatus.text = "Status: Data Berhasil Disimpan!"
                Toast.makeText(this, "Berhasil!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Isi kolom yang kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        val savedUser = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            .getString("username", "")
        etUsername.setText(savedUser)
    }
}