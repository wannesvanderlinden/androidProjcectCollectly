package com.example.androidprojcectcollectly

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewMainActivity : AppCompatActivity() {
    private lateinit var editGameConsoleView: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_main)
        editGameConsoleView = findViewById(R.id.edit_gameConsole)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editGameConsoleView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {

                val gameConsole = editGameConsoleView.text.toString()

                replyIntent.putExtra("gameConsole", gameConsole)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

}