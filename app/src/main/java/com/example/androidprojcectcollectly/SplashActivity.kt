package com.example.androidprojcectcollectly

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.alpha
import kotlin.time.Duration.Companion.seconds

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var mediaplayer = MediaPlayer.create(this, R.raw.intro_music)
        mediaplayer.start()
        //Animations setting
        var topAnim = AnimationUtils.loadAnimation(this, R.transition.from_top)
        var bottomAnim = AnimationUtils.loadAnimation(this, R.anim.buttom_animation)
        var rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_animation)

        var logo = findViewById<ImageView>(R.id.gameController)
        var slogan = findViewById<TextView>(R.id.slogan)
        var createdBy = findViewById<TextView>(R.id.created)


        slogan.setAnimation(bottomAnim);
        createdBy.setAnimation(bottomAnim)
        logo.setAnimation(rotateAnim)


        Handler(Looper.getMainLooper()).postDelayed({
            mediaplayer.pause()
            mediaplayer.release()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()


        }, 6000)

    }
}