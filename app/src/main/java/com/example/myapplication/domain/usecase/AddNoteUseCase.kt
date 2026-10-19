package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Note
import com.example.myapplication.domain.repository.NoteRepository

class AddNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.addNote(note)
}
