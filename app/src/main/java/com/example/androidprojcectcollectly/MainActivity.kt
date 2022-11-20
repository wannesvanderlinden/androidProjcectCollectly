package com.example.androidprojcectcollectly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* val db = Room.databaseBuilder(
          applicationContext,
          GameConsoleRoomDatabase::class.java, "GameConsole"
      ).allowMainThreadQueries().build()*/
        var addButtonScanner = findViewById<Button>(R.id.AddItemScanner)
        addButtonScanner.setOnClickListener {
            var scanoption = ScanOptions()
            scanoption.setDesiredBarcodeFormats(ScanOptions.UPC_A)
            scanoption.setDesiredBarcodeFormats(ScanOptions.UPC_E)

            scanoption.setOrientationLocked(false)
            barcodeLauncher.launch(scanoption)


        }
        var buttomNav = findViewById<BottomNavigationView>(R.id.buttom_nav)

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