package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Note
import com.example.myapplication.domain.repository.NoteRepository

class GetNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Long): Note? = repository.getNoteById(id)
}
