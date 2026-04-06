package com.riza.praktekp5_storage

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class JournalManager(private val context: Context) {

    fun saveEntry(title: String, content: String) {
        val timestamp = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())
        val filename = "journal_${timestamp}.txt"
        val fullContent = "$title\n${Date()}\n\n$content"

        context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(fullContent.toByteArray())
        }
    }

    fun getAllEntries(): List<String> {
        val files = context.fileList()
        return files?.filter { it.startsWith("journal_") }?.sortedDescending() ?: listOf()
    }
}