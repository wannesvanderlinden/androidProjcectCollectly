package com.example.androidprojcectcollectly

import android.app.Application
import com.example.androidprojcectcollectly.repositories.GameConsoleRepository
import com.example.androidprojcectcollectly.rooms.GameConsoleRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GameConsolesApplication : Application(){
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
// rather than when the application starts
val database by lazy { GameConsoleRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { GameConsoleRepository(database.gameConsoleDao()) }
}