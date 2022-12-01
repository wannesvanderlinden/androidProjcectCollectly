package com.example.androidprojcectcollectly

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.androidprojcectcollectly.adapters.GameConsoleListAdapter
import com.example.androidprojcectcollectly.databinding.ActivityNavBinding
import com.example.androidprojcectcollectly.entities.GameConsole

class MainActivity : AppCompatActivity() {


    private val gameConsoleViewModel: GameConsoleViewModel by viewModels {
        GameConsoleViewModelFactory((application as GameConsolesApplication).repository)
    }

    //gets the result of the other activity when it is okay and not empty it will then insert the game console into the database
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {

                it.data?.getStringExtra("gameConsole")?.let { reply ->

                    val gameConsole = GameConsole(null, reply)

                    gameConsoleViewModel.insert(gameConsole)

                }
            } else {
                Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            }


        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Define the recyclerview
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        //create the adapter and let some listeners with there attribute
        val adapter = GameConsoleListAdapter() {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("gameConsole", it)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        gameConsoleViewModel.allGameConsoles.observe(this) { gameConsoles ->
            // Update the cached copy of the words in the adapter.
            gameConsoles.let { adapter.submitList(it) }
        }
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewMainActivity::class.java)
            //registerForActivityResult(intent,)
            getResult.launch(intent)
            //startActivityForResult(intent,newWordActivityRequestCode)


        }


        var buttomNav = findViewById<BottomNavigationView>(R.id.buttom_nav)

        buttomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_collections -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)


                }
                R.id.ic_price -> {
                    startActivity(Intent(this, PricesActivity::class.java))
                    overridePendingTransition(0, 0)
                }

            }
            true
        }
    }


}