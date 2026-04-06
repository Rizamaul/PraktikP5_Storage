package com.riza.praktekp5_storage

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class MahasiswaProvider : ContentProvider() {
    private lateinit var dbHelper: DatabaseHelper
    private val AUTHORITY = "com.riza.praktekp5_storage"
    private val MAHASISWA_DIR = 1

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, "mahasiswa", MAHASISWA_DIR)
    }

    override fun onCreate(): Boolean {
        dbHelper = DatabaseHelper(context!!)
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       args: Array<String>?, sort: String?): Cursor? {
        val db = dbHelper.readableDatabase
        return if (uriMatcher.match(uri) == MAHASISWA_DIR) {
            db.query(DatabaseHelper.TABLE_MAHASISWA, projection, selection, args, null, null, sort)
        } else null
    }

    override fun insert(uri: Uri, v: ContentValues?): Uri? {
        val id = dbHelper.writableDatabase.insert(DatabaseHelper.TABLE_MAHASISWA, null, v)
        context?.contentResolver?.notifyChange(uri, null)
        return Uri.withAppendedPath(uri, id.toString())
    }

    override fun update(u: Uri, v: ContentValues?, s: String?, a: Array<String>?) = 0
    override fun delete(u: Uri, s: String?, a: Array<String>?) = 0
    override fun getType(u: Uri) = "vnd.android.cursor.dir/vnd.$AUTHORITY.mahasiswa"
}