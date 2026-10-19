package com.example.myapplication.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.local.NoteDatabase
import com.example.myapplication.data.repository.NoteRepositoryImpl
import com.example.myapplication.domain.usecase.*

class AppContainer(context: Context) {
    private val database: NoteDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note_database"
        ).build()
    }

    private val noteRepository by lazy {
        NoteRepositoryImpl(database.noteDao())
    }

    val getNotesUseCase by lazy { GetNotesUseCase(noteRepository) }

    val getNoteUseCase by lazy { GetNoteUseCase(noteRepository) }
    val addNoteUseCase by lazy { AddNoteUseCase(noteRepository) }
    val updateNoteUseCase by lazy { UpdateNoteUseCase(noteRepository) }
    val deleteNoteUseCase by lazy { DeleteNoteUseCase(noteRepository) }
}
