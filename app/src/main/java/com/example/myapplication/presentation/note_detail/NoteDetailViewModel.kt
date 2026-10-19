package com.example.myapplication.presentation.note_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Note
import com.example.myapplication.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteDetailViewModel(
    private val getNoteUseCase: GetNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<Note?>(null)
    val uiState: StateFlow<Note?> = _uiState.asStateFlow()

    private var currentNoteId: Long? = null

    fun loadNote(id: Long) {
        viewModelScope.launch {
            _uiState.value = getNoteUseCase(id)
            currentNoteId = id
        }
    }

    fun setEmptyNote() {
        _uiState.value = Note(title = "", content = "")
        currentNoteId = null
    }

    fun saveNote(title: String, content: String) {
        viewModelScope.launch {
            val note = Note(
                id = currentNoteId ?: 0,
                title = title,
                content = content
            )
            if (currentNoteId == 0L || currentNoteId == null) {
                addNoteUseCase(note)
            } else {
                updateNoteUseCase(note)
            }
        }
    }
}
