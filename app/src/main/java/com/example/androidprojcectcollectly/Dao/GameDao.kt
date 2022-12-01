package com.example.androidprojcectcollectly.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidprojcectcollectly.entities.Game

import kotlinx.coroutines.flow.Flow
@Dao
interface GameDao {

    @Query("SELECT * FROM game where game.gameConsole = :gameconsole ORDER BY title ASC")
    fun getGamesWithGameConsole(gameconsole: String): Flow<List<Game>>

    @Query("SELECT * FROM game  ORDER BY title ASC")
    fun getGames(): Flow<List<Game>>

    @Query("DELETE  FROM game")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(game: Game)
}