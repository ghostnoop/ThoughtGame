
package com.thoughtgame

import android.app.Application
import com.androidnetworking.AndroidNetworking

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }
}
