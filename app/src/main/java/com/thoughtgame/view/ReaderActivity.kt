package com.thoughtgame.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.thoughtgame.R
import kotlinx.android.synthetic.main.activity_reader.*
import org.jetbrains.anko.doAsync

class ReaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reader)
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        doAsync {

            Thread.sleep(500)
            runOnUiThread {



                webview.loadUrl("file:///android_asset/website.html")


            }
        }
//        file:///android_asset/website.html
    }
}
