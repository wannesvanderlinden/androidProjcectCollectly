package com.example.androidprojcectcollectly.repositories

import androidx.annotation.WorkerThread
import com.example.androidprojcectcollectly.dao.GameConsoleDao
import com.example.androidprojcectcollectly.entities.GameConsole
import kotlinx.coroutines.flow.Flow

class GameConsoleRepository(private val gameConsoleDao: GameConsoleDao) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allGameConsoles: Flow<List<GameConsole>> = gameConsoleDao.getAlphabetizedGameConsoles()


    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(gameConsole: GameConsole) {
        gameConsoleDao.insert(gameConsole)
    }
    suspend fun deleteAll(){
        gameConsoleDao.deleteAll()
    }

}