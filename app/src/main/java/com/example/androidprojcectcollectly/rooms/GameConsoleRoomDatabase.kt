package com.example.androidprojcectcollectly.rooms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.androidprojcectcollectly.Dao.GameConsoleDao
import com.example.androidprojcectcollectly.entities.GameConsole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [GameConsole::class], version = 2, exportSchema = false)
 abstract class GameConsoleRoomDatabase : RoomDatabase()  {
    abstract fun gameConsoleDao(): GameConsoleDao
    private class GameConsoleDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var gameConsoleDao = database.gameConsoleDao()
                    // Delete all content here.
                    gameConsoleDao.deleteAll()

                    // Add sample words.
                    var gameCube = GameConsole(1,"GameCube")
                    gameConsoleDao.insert(gameCube)

                    var Wii = GameConsole(2,"Wii")
                    gameConsoleDao.insert(Wii)

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