package com.example.androidprojcectcollectly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class PricesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prices)
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
}