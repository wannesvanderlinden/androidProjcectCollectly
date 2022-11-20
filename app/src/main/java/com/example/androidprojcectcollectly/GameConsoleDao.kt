package com.example.androidprojcectcollectly

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface GameConsoleDao {

    @Query("SELECT * FROM gameconsole ORDER BY name ASC")
    fun getAlphabetizedWords(): List<GameConsole>



    @Delete()
    fun deleteAll(gameConsole: GameConsole)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(gameconsole: GameConsole)
}