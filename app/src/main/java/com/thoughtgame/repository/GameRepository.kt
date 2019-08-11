package com.thoughtgame.repository


class GameRepository private constructor() {

    companion object {
        @Volatile
        private var instance: GameRepository? = null

        fun getInstance(): GameRepository = instance
            ?: synchronized(this) {
                instance
                    ?: GameRepository().also { instance = it }
            }

    }



    data class users(
        var users:Int
    )





}