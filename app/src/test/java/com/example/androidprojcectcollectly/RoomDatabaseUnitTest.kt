package com.example.androidprojcectcollectly

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.androidprojcectcollectly.dao.GameConsoleDao
import com.example.androidprojcectcollectly.dao.GameDao
import com.example.androidprojcectcollectly.entities.GameConsole
import com.example.androidprojcectcollectly.rooms.GameConsoleRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.util.concurrent.CountDownLatch

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//@RunWith(AndroidJUnit4::class)
class RoomDatabaseUnitTest {


    private lateinit var database: GameConsoleRoomDatabase
    private lateinit var gamesDao: GameDao
    private lateinit var gameconsolesDao: GameConsoleDao

    /**
     * Setting up the database for tests afterwards
     *
     */
    @Before
    fun setupDatabase() {
        //create the room database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            database::class.java
        ).allowMainThreadQueries().build()
        // get the Dao's and link them to the variables
        gamesDao = database.gameDao()
        gameconsolesDao = database.gameConsoleDao()
    }
    @Test
    fun insertGameconsoleTest() = runBlocking {
        val gameConsole = GameConsole(null,"3ds")
        gameconsolesDao.insert(gameConsole)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            gameconsolesDao.getAlphabetizedGameConsoles().collect() {
                assertTrue(it.contains(gameConsole))
                latch.countDown()

            }
        }
        latch.await()
        job.cancelAndJoin()
    }


    /**
     * Close the database on the end
     *
     */
    @After
    fun closeDatabase() {
        database.close()
    }
}