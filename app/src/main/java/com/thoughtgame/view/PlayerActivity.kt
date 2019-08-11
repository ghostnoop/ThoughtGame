package com.thoughtgame.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.thoughtgame.R
import com.thoughtgame.storage.SharedPrefManager
import android.content.Intent
import kotlinx.android.synthetic.main.activity_player.*


class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        var plnames = mutableListOf<String>()

        var j = 0




        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                textView2.text = "Players : $i"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val bbs = textView2.text.toString().substring(10)
                Toast.makeText(applicationContext, bbs, Toast.LENGTH_SHORT).show()
            }
        })


        fab_clear.setOnClickListener {
            SharedPrefManager.getInstance(this).clear()
            back()

        }

        add_player.setOnClickListener { view ->
            val bbs = textView2.text.toString().substring(10)
            if (j != bbs.toInt() && name_text.text.toString() != "") {
                plnames.add(name_text.text.toString())

//                Snackbar.make(view, name_text.text.toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show()
                name_text.setText("")
                j++
            } else
                name_text.error
        }



        fab_add.setOnClickListener { view ->
            val bbs = textView2.text.toString().substring(10)
            if(plnames.size==bbs.toInt()){
                SharedPrefManager.getInstance(this).setPlayers(plnames)
                back()
            }
            else
                Snackbar.make(view, "ERROR", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onBackPressed() {
       back()
    }

    fun back(){
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION

        startActivity(intent)
        anim()
        finish()
    }


    fun anim() {
        overridePendingTransition(R.anim.fade_out, R.anim.fade_out);
    }

}
