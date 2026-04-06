package com.riza.praktekp5_storage

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSimulasiPinjam = findViewById<Button>(R.id.btnSimulasiPinjam)
        val dbHelper = DatabaseHelper(this)

        btnSimulasiPinjam.setOnClickListener {
            // Simulasi meminjam Buku ID 1 oleh Anggota ID 1 [cite: 597]
            val berhasil = dbHelper.prosesPeminjaman(1, 1)

            if (berhasil) {
                Toast.makeText(this, "Peminjaman Berhasil & Stok Berkurang!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal! Periksa stok atau data database.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}