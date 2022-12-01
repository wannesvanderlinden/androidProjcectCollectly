package com.example.androidprojcectcollectly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class PricesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prices)
        var addButtonScanner = findViewById<Button>(R.id.searchPriceButton)
        addButtonScanner.setOnClickListener {
            var scanoption = ScanOptions()
            scanoption.setDesiredBarcodeFormats(ScanOptions.EAN_8,ScanOptions.EAN_13,ScanOptions.UPC_E)

            scanoption.setOrientationLocked(false)
            barcodeLauncher.launch(scanoption)


        }
        var buttomNav = findViewById<BottomNavigationView>(R.id.buttom_nav)

        buttomNav.setOnItemSelectedListener {
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