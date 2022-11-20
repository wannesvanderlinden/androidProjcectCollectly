package com.example.androidprojcectcollectly

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [GameConsole::class], version = 1)
 abstract class GameConsoleRoomDatabase : RoomDatabase()  {
    abstract fun gameConsoleDao(): GameConsoleDao





}