package com.example.androidprojcectcollectly.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidprojcectcollectly.entities.GameConsole
import kotlinx.coroutines.flow.Flow


@Dao
interface GameConsoleDao {
    @Query("SELECT * FROM gameConsole where gameConsole.name = :name")
    fun getGameConsolesByName(name: String): Flow<List<GameConsole>>

    @Query("SELECT * FROM gameconsole ORDER BY name ASC")
    fun getAlphabetizedGameConsoles(): Flow<List<GameConsole>>
    @Query("SELECT * FROM gameconsole")
    fun getGameConsolesList(): List<GameConsole>
    @Query("SELECT * FROM gameConsole where gameConsole.name = :name")
    fun getGameConsolesByNameList(name: String): List<GameConsole>


    @Query("DELETE  FROM gameconsole")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gameconsole: GameConsole)
}