package com.thoughtgame.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.thoughtgame.R
import com.thoughtgame.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val plnames = SharedPrefManager.getInstance(this).getPlayers
        if (plnames[0] != "/cod881")
            btn_start.visibility = View.VISIBLE


        btn_start.setOnClickListener {

            val intent = Intent(applicationContext, GameActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            anim()
        }
        btn_player.setOnClickListener {
            val intent = Intent(applicationContext, PlayerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            anim()
            finish()
        }
        btn_credits.setOnClickListener {
            val intent = Intent(applicationContext, ReaderActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            anim()


        }
        btn_settings.setOnClickListener{
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            anim()
        }


    }





    override fun onResume() {
        anim()
        super.onResume()
    }

    fun msg(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()
    }

    fun anim() {

        overridePendingTransition(R.anim.fade_out, R.anim.fade_out);
    }


}

