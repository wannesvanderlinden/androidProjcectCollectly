package com.example.androidprojcectcollectly

import android.app.Application
import com.example.androidprojcectcollectly.repositories.GameConsoleRepository
import com.example.androidprojcectcollectly.repositories.GameRepository
import com.example.androidprojcectcollectly.rooms.GameConsoleRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GameConsolesApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    //Create the database when only needed
    val database by lazy { GameConsoleRoomDatabase.getDatabase(this, applicationScope) }

    //Create repository of the game and gameconsole when needed
    val repository by lazy { GameConsoleRepository(database.gameConsoleDao()) }
    val game_repository by lazy { GameRepository(database.gameDao()) }


}