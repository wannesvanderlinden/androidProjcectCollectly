package com.example.androidprojcectcollectly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidprojcectcollectly.adapters.GameConsoleListAdapter
import com.example.androidprojcectcollectly.adapters.GameListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class GameActivity : AppCompatActivity() {
    private val gameViewModel: GameViewModel by viewModels{
        GameViewModelFactory((application as GameConsolesApplication).game_repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        //create the adapter and let some listeners with there attribute
        val adapter = GameListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        gameViewModel.allGames.observe(this) { game ->
            // Update the cached copy of the words in the adapter.
            game.let { adapter.submitList(it) }
        }
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@GameActivity, NewMainActivity::class.java)

            startActivity(intent)
        }
        findViewById<TextView>(R.id.gameConsoleText).text = intent.getStringExtra("gameConsole")
        var addButtonScanner = findViewById<Button>(R.id.AddItemScanner)
        addButtonScanner.setOnClickListener {
            var scanoption = ScanOptions()
            scanoption.setDesiredBarcodeFormats(ScanOptions.EAN_8, ScanOptions.EAN_13, ScanOptions.UPC_E)


            scanoption.setOrientationLocked(false)
            barcodeLauncher.launch(scanoption)


        }
    }

    //zie documentatie https://github.com/journeyapps/zxing-android-embedded
    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            // this.findViewById<TextView>(R.id.resultText).text = result.contents
        } else {
            Toast.makeText(
                this,
                "Scanned Upc: " + result.contents,
                Toast.LENGTH_LONG
            ).show()
            //this.findViewById<TextView>(R.id.resultText).text = result.contents
        }
    }
}