package com.example.androidprojcectcollectly

import androidx.lifecycle.*
import com.example.androidprojcectcollectly.entities.Game
import com.example.androidprojcectcollectly.entities.GameConsole
import com.example.androidprojcectcollectly.repositories.GameConsoleRepository
import com.example.androidprojcectcollectly.repositories.GameRepository
import kotlinx.coroutines.launch

class GameViewModel (private val repository: GameRepository): ViewModel()  {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allGames: LiveData<List<Game>> = repository.allGames.asLiveData()


    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(game: Game) = viewModelScope.launch {
        repository.insert(game)
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun deleteGamesOfGameconsole(name:String) = viewModelScope.launch {
        repository.deleteGamesOfGameconsole(name)
    }
}

class GameViewModelFactory(private val repository: GameRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}