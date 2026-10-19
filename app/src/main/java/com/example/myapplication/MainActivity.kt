package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.presentation.di.AppContainer
import com.example.myapplication.presentation.note_detail.NoteDetailScreen
import com.example.myapplication.presentation.note_detail.NoteDetailViewModel
import com.example.myapplication.presentation.note_list.NoteListScreen
import com.example.myapplication.presentation.note_list.NoteListViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as NotepadApplication
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NoteApp(app.container)
                }
            }
        }
    }
}

@Composable
fun NoteApp(container: AppContainer) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "note_list") {
        composable("note_list") {
            val viewModel: NoteListViewModel = viewModel(
                factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return NoteListViewModel(
                            container.getNotesUseCase,
                            container.deleteNoteUseCase
                        ) as T
                    }
                }
            )
            NoteListScreen(
                viewModel = viewModel,
                onNoteClick = { note ->
                    navController.navigate("note_detail/${note.id}")
                },
                onAddNoteClick = {
                    navController.navigate("note_detail/0")
                }
            )
        }
        composable("note_detail/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toLong() ?: 0L
            val viewModel: NoteDetailViewModel = viewModel(
                factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return NoteDetailViewModel(
                            container.getNoteUseCase,
                            container.addNoteUseCase,
                            container.updateNoteUseCase,
                            container.deleteNoteUseCase
                        ) as T
                    }
                }
            )
            
            // LaunchedEffect используется для выполнения побочных эффектов (side-effects) 
            // при входе в этот Composable или при изменении ключа (в данном случае noteId).
            // Мы используем его здесь, чтобы обновить состояние ViewModel в зависимости от переданного ID.
            LaunchedEffect(noteId) {
                if (noteId == 0L) {
                    // Если noteId равен 0, это означает, что пользователь нажал "Добавить новую заметку".
                    // Мы вызываем метод для подготовки ViewModel к созданию новой, пустой заметки.
                    viewModel.setEmptyNote()
                } else {
                    // Если noteId не 0, значит пользователь выбрал уже существующую заметку.
                    // Мы загружаем данные этой заметки из базы данных через ViewModel.
                    viewModel.loadNote(noteId)
                }
            }

            NoteDetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onSaveClick = { navController.popBackStack() }
            )
        }
    }
}
