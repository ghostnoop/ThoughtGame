package com.thoughtgame.storage

import android.annotation.SuppressLint
import android.content.Context


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SharedPrefManager private constructor(private val mCtx: Context) {
    private val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()


    val getPlayers: MutableList<String>
        get() {
            val plnam: MutableList<String> = mutableListOf()
            plnam.add("/cod881")
            val pl = sharedPreferences.getStringSet("player", plnam.toSet())

            return pl!!.toMutableList()

        }
    val getTime: Int
        get() {
            return sharedPreferences.getInt("time", 60)
        }

    val getCardsSize: Int
        get() {
            return sharedPreferences.getInt("cardsize", -1)
        }


    val getLang: String
        get() {
            return sharedPreferences.getString("lang","Rus").toString()
        }
    val getVers:Float
        get() {
            return sharedPreferences.getFloat("vers",1.1F)
        }

    fun setVersion(vers:Float){
        editor.putFloat("version",vers)
        editor.apply()
    }
    fun setCardsSize(cards:Int){
        editor.putInt("cardsize",cards)
        editor.apply()
    }


    fun setPlayers(plname: MutableList<String>) {
        editor.putStringSet("player", plname.toSet())
        editor.apply()
    }

    fun setTime(time: Int) {
        editor.putInt("time", time)
        editor.apply()
    }

    fun setLang(lang: String) {
        editor.putString("lang", lang)
        editor.apply()
    }


    fun clear() {
        editor.clear()
        editor.apply()
    }

    companion object {
        val SHARED_PREF_NAME = "my_shared_preff"
        private val SHARED_PREF_ADVER = "my_shared_adver"

        @SuppressLint("StaticFieldLeak")
        private var mInstance: SharedPrefManager? = null

        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}