package com.example.myapplication.data.repository

import com.example.myapplication.data.local.NoteDao
import com.example.myapplication.data.local.NoteEntity
import com.example.myapplication.domain.model.Note
import com.example.myapplication.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> = noteDao.getAllNotes().map { entities ->
        entities.map { it.toDomain() }
    }

    override suspend fun getNoteById(id: Long): Note? {
        return noteDao.getNoteById(id)?.toDomain()
    }

    override suspend fun addNote(note: Note) {
        noteDao.insertNote(note.toEntity())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toEntity())
    }

    private fun NoteEntity.toDomain(): Note = Note(
        id = id,
        title = title,
        content = content
    )

    private fun Note.toEntity(): NoteEntity = NoteEntity(
        id = id,
        title = title,
        content = content
    )
}
