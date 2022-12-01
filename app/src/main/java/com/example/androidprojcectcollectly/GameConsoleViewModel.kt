package com.example.androidprojcectcollectly

import androidx.lifecycle.*
import com.example.androidprojcectcollectly.entities.GameConsole
import com.example.androidprojcectcollectly.repositories.GameConsoleRepository
import kotlinx.coroutines.launch

class GameConsoleViewModel (private val repository: GameConsoleRepository): ViewModel()  {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allGameConsoles: LiveData<List<GameConsole>> = repository.allGameConsoles.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(gameConsole: GameConsole) = viewModelScope.launch {
        repository.insert(gameConsole)
    }
}

class GameConsoleViewModelFactory(private val repository: GameConsoleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameConsoleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameConsoleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}