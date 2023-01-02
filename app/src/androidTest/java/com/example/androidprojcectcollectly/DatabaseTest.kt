package com.example.androidprojcectcollectly

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidprojcectcollectly.dao.GameConsoleDao
import com.example.androidprojcectcollectly.dao.GameDao
import com.example.androidprojcectcollectly.entities.Game
import com.example.androidprojcectcollectly.entities.GameConsole
import com.example.androidprojcectcollectly.rooms.GameConsoleRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
@RunWith(AndroidJUnit4::class)
class DatabaseTest {
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
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, GameConsoleRoomDatabase::class.java).build()

        // get the Dao's and link them to the variables
        gamesDao = database.gameDao()
        gameconsolesDao = database.gameConsoleDao()
    }

    /**
     * Testing of the gameconsoleInsert function is working
     *
     */
    @Test
    fun insertGameconsoleTest() = runBlocking {
        val gameConsole = GameConsole(50000,"3ds")
        gameconsolesDao.insert(gameConsole)
        val byName = gameconsolesDao.getGameConsolesByNameList("3ds")
        assertThat(byName.get(0),equalTo(gameConsole))

    }
    /**
     * Test for that the delete all function is working
     *
     */

    @Test
    fun deleteGameconsoleTest() = runBlocking {
        val gameConsole = GameConsole(50000,"3ds")
        gameconsolesDao.insert(gameConsole)
        gameconsolesDao.deleteAll()
        val all = gameconsolesDao.getGameConsolesList()
        val emptyList = emptyList<GameConsole>()

        Assert.assertEquals(emptyList,all)

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