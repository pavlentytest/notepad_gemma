package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(private val repository: NoteRepository) {
    operator fun invoke(): Flow<List<com.example.myapplication.domain.model.Note>> = repository.getNotes()
}
