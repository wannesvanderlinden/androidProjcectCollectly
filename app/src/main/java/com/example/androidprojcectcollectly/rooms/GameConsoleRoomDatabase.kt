package com.example.androidprojcectcollectly.rooms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.androidprojcectcollectly.dao.GameConsoleDao
import com.example.androidprojcectcollectly.dao.GameDao
import com.example.androidprojcectcollectly.entities.Game
import com.example.androidprojcectcollectly.entities.GameConsole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [GameConsole::class, Game::class], version = 7, exportSchema = false)
 abstract class GameConsoleRoomDatabase : RoomDatabase()  {
    abstract fun gameConsoleDao(): GameConsoleDao
    abstract fun gameDao(): GameDao
    private class GameConsoleDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    var gameConsoleDao = database.gameConsoleDao()
                    var gameDao=database.gameDao()
                    // Delete all content here.
                    gameConsoleDao.deleteAll()


                    var gameCube = GameConsole(null,"GameCube")
                    gameConsoleDao.insert(gameCube)
                    var mario_bros= Game(null,"Mario Bros",gameCube.name.toString())
                    gameDao.insert(mario_bros)


                    var wii = GameConsole(null,"Wii")
                    gameConsoleDao.insert(wii)


                }
            }
        }



    }
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: GameConsoleRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): GameConsoleRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameConsoleRoomDatabase::class.java,
                    "gameconsole_database"
                ).fallbackToDestructiveMigration().addCallback(GameConsoleDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }




}