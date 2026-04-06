package com.riza.praktekp5_storage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "perpustakaan.db", null, 1) {

    companion object {
        const val TABLE_BUKU = "buku"
        const val TABLE_ANGGOTA = "anggota"
        const val TABLE_PEMINJAMAN = "peminjaman"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Tabel Buku sesuai modul
        db.execSQL("""
            CREATE TABLE $TABLE_BUKU (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                judul TEXT NOT NULL,
                pengarang TEXT NOT NULL,
                stok INTEGER DEFAULT 1
            )
        """.trimIndent())

        // Tabel Anggota sesuai modul
        db.execSQL("""
            CREATE TABLE $TABLE_ANGGOTA (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nama TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL
            )
        """.trimIndent())

        // Tabel Peminjaman sesuai modul
        db.execSQL("""
            CREATE TABLE $TABLE_PEMINJAMAN (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                buku_id INTEGER NOT NULL,
                anggota_id INTEGER NOT NULL,
                status TEXT DEFAULT 'dipinjam',
                FOREIGN KEY (buku_id) REFERENCES $TABLE_BUKU (id),
                FOREIGN KEY (anggota_id) REFERENCES $TABLE_ANGGOTA (id)
            )
        """.trimIndent())
    }

    // Implementasi Transaksi Atomik sesuai modul
    fun prosesPeminjaman(bukuId: Int, anggotaId: Int): Boolean {
        val db = writableDatabase
        db.beginTransaction()
        return try {
            val values = ContentValues().apply {
                put("buku_id", bukuId)
                put("anggota_id", anggotaId)
            }
            val res = db.insert(TABLE_PEMINJAMAN, null, values)
            if (res == -1L) throw Exception("Gagal insert peminjaman")

            // Update stok buku
            db.execSQL("UPDATE $TABLE_BUKU SET stok = stok - 1 WHERE id = ? AND stok > 0", arrayOf(bukuId))

            db.setTransactionSuccessful()
            true
        } catch (e: Exception) {
            false
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PEMINJAMAN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ANGGOTA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BUKU")
        onCreate(db)
    }
}