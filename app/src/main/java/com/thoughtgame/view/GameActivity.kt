package com.thoughtgame.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_game.*
import org.json.JSONArray
import java.lang.Error
import android.R.id
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.interfaces.ParsedRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thoughtgame.R
import com.thoughtgame.model.Cards
import com.thoughtgame.model.Player
import com.thoughtgame.repository.GameRepository
import com.thoughtgame.storage.SharedPrefManager
import com.thoughtgame.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jetbrains.anko.custom.style
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import kotlin.random.Random
import java.util.Collections.swap


class GameActivity : AppCompatActivity() {

    private val rus_alf = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
    private val alf = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private var player = mutableListOf<Player>()
    private var index = 0
    private var isend: Boolean = true
    private var size = SharedPrefManager.getInstance(this).getCardsSize

    private lateinit var gvModel: GameViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        val factory: GameViewModel.Factory = GameViewModel.Factory(GameRepository.getInstance())
        gvModel = ViewModelProviders.of(this, factory).get(GameViewModel::class.java)
        val plnames = SharedPrefManager.getInstance(this).getPlayers



        doAsync {
            fetchAdvers()

            for (k in 0..plnames.size - 1)
                player.add(Player(name = plnames[k], stars = 0, cards = 0))

        }


    }


    private fun fetchAdvers() {

        AndroidNetworking.get("https://api.jsonbin.io/b/5d48717889ed890b24cc404b/3")
            .setTag(this)
            .setPriority(Priority.LOW)
            .build()
            .getAsObjectList(Cards::class.java, object : ParsedRequestListener<List<Cards>> {
                override fun onResponse(cards: List<Cards>) {
                    doAsync {
                        if (size < 0)
                            SharedPrefManager.getInstance(applicationContext).setCardsSize(cards.size)
                    }
                    msg(cards[0].title)
                    onclick(cards.shuffled(Random))


                }


                override fun onError(anError: ANError) {
                    msg(anError.toString())
                    Log.e("@24", anError.toString())
                }
            })

    }


    fun onclick(cards: List<Cards>) {
        var i = 0;
        var j = 0
        val alfi: MutableList<Int> = gvModel.alfain(alf)

        alfi.shuffle(Random)






        starter.setOnClickListener {
            if (size < 0 || size >= cards.size) size = cards.size


            if (i < size) {

                changer(index)

                gamebackground(1)

                rating_bar.setRating(cards[i].stars.toFloat())

                if (cards[i].type == 1) tV1.setTextColor(Color.parseColor("#d43129"))
                if (cards[i].type == 0) tV1.setTextColor(Color.parseColor("#fdf48d"))

                tV1.text = cards[i].title

                visor("show")

                showLet.setOnClickListener {

                    gamebackground(3)

                    tV2.text = alf[alfi[j]].toString()

                    visor("stop")

                    timer(i, size)

                    stop_btn.setOnClickListener {

                        tV1.text = ""
                        tV2.text = ""
                        time_text.text = ""
                        rating_bar.setRating(0F)

                        gamebackground(0)
                        gamebackground(2)
                        isend = false
                        visor("start")

                    }

                    que(index)
                    plus_btn.setOnClickListener { stars(index, 1) }
                    plus2_btn.setOnClickListener { stars(index, 2) }


                    if (index == player.size - 1) index = 0
                    else index++
                    i++
                    j++


                }


            }
            if (i == size) {
                player = gvModel.qsort(player)

                tV1.text = "Кончилось"
                tV2.text = "NULL"
                GameLayout.visibility = View.GONE
                ResultsLayout.visibility = View.VISIBLE
                showResults()
            }


        }

    }

    fun timer(show: Int, cardsSize: Int) {
        val t = SharedPrefManager.getInstance(this).getTime
        var i = 0
        time_text.text = ""



        doAsync {
            while (i <= t) {
                Log.e("N!!22", "$i  $t")
                if (isend == true) {
                    val k = t - i
                    if (k > 9)
                        runOnUiThread { time_text.text = "0:$k" }
                    else
                        runOnUiThread { time_text.text = "0:0$k" }
                    Thread.sleep(1000)

                    i++
                } else {
                    i = t + 1
                    isend = false
                    continue
                }
                if (isend == false) continue


            }
            isend = true
            i = 0






            gamebackground(0)
            rating_bar.setRating(0F)
            gamebackground(2)
            runOnUiThread {
                tV1.text = ""
                tV2.text = ""

                time_text.text = ""
                visor("start")
            }


        }


    }


    fun msg(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }


    fun que(inx: Int) {
        player[inx].cards++
    }

    fun stars(inx: Int, count: Int) {
        player[inx].stars = player[inx].stars + count
    }

    fun changer(inx: Int) {
        name_view.setText(player[inx].name)
    }

    fun showResults() {
        Log.e("N212", "ok")
        mainRecyclerView.layoutManager = LinearLayoutManager(this)


        val adapter = ResultsListAdapter(player) { adapterPosition ->
            var adapterposition: Int = adapterPosition

//            if (adapterPosition < 0) {
//                toast("longclick")
//                adapterposition = adapterPosition * -1 - 1
//
//
//            }

        }
        mainRecyclerView.adapter = adapter


    }

    fun visor(st: String) {
        if (st == "start") {
            starter.show()
            stop_btn.hide()
            showLet.hide()
        }
        if (st == "stop") {
            starter.hide()
            stop_btn.show()
            showLet.hide()
        }
        if (st == "show") {
            starter.hide()
            stop_btn.hide()
            showLet.show()
        }
    }

    fun gamebackground(st: Int) {

        when (st) {
            0 -> {
                imageView1.setImageResource(R.drawable.ic_arter)
            }
            1 -> {
                imageView1.setImageResource(R.drawable.ic_thinker)
            }
            2 -> {
                imageView2.setImageResource(R.drawable.ic_arifut)
            }
            3 -> {
                imageView2.setImageResource(R.drawable.ic_thinker2)
            }
        }


    }


}
