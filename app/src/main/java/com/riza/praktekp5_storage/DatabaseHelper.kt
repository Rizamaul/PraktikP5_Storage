package com.riza.praktekp5_storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "universitas.db"
        const val DATABASE_VERSION = 1
        const val TABLE_MAHASISWA = "mahasiswa"
        const val COL_ID = "id"
        const val COL_NAMA = "nama"
        const val COL_NIM = "nim"
        const val COL_PRODI = "prodi"
        const val COL_IPK = "ipk"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_MAHASISWA (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAMA TEXT NOT NULL,
                $COL_NIM TEXT UNIQUE NOT NULL,
                $COL_PRODI TEXT NOT NULL,
                $COL_IPK REAL DEFAULT 0.0
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MAHASISWA")
        onCreate(db)
    }
}