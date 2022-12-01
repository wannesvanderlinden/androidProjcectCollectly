package com.example.androidprojcectcollectly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
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