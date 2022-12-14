package com.example.androidprojcectcollectly.repositories

import androidx.annotation.WorkerThread
import com.example.androidprojcectcollectly.dao.GameDao
import com.example.androidprojcectcollectly.entities.Game

import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allGames: Flow<List<Game>> = gameDao.getGames()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(game: Game) {
        gameDao.insert(game)
    }
    suspend fun deleteAll() {
        gameDao.deleteAll()
    }
    suspend fun deleteGamesOfGameconsole(name: String) {
        gameDao.deleteGamesOfGameconsole(name)
    }
}