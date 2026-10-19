package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Note
import com.example.myapplication.domain.repository.NoteRepository

class UpdateNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.updateNote(note)
}
