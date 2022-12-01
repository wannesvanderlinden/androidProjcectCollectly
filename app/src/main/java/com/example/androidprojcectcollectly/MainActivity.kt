package com.example.androidprojcectcollectly

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.androidprojcectcollectly.adapters.GameConsoleListAdapter
import com.example.androidprojcectcollectly.entities.GameConsole

class MainActivity : AppCompatActivity() {
    private val newWordActivityRequestCode = 1
    private var id= 3
    private val gameConsoleViewModel: GameConsoleViewModel by viewModels{
        GameConsoleViewModelFactory((application as GameConsolesApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        //create the adapter and let some listeners with there attribute
        val adapter = GameConsoleListAdapter {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("gameConsole",it)
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
            //anders in kotlin
            startActivityForResult(intent, newWordActivityRequestCode)
        }


        var buttomNav = findViewById<BottomNavigationView>(R.id.buttom_nav)

         //

        buttomNav.setOnNavigationItemSelectedListener {

            when(it.itemId) {
                R.id.ic_collections -> {
                    startActivity(Intent(this,MainActivity::class.java))
                    overridePendingTransition(0,0)


                }
                R.id.ic_price ->{
                    startActivity(Intent(this,PricesActivity::class.java))
                    overridePendingTransition(0,0)
                }

            }
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(NewMainActivity.EXTRA_REPLY)?.let { reply ->
                val gameConsole = GameConsole(id,reply)
                gameConsoleViewModel.insert(gameConsole)
                id++
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    }