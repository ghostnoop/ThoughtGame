package com.thoughtgame.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thoughtgame.model.Player
import com.thoughtgame.repository.GameRepository


class GameViewModel internal constructor(private val gameRepository: GameRepository) : ViewModel() {


//    fun getUser(user: User, context: Context, ContentLayout: View) = profileRepository.getUser(user,context,ContentLayout)



    fun qsort(pl: MutableList<Player>): MutableList<Player> {


        for (i in 0 until pl.size - 1)
            for (j in pl.size - 2 downTo i)
                if (pl[j].stars > pl[j + 1].stars) {
                    val sp = pl[j]
                    pl[j] = pl[j + 1]
                    pl[j + 1] = sp

                }
        pl.reverse()
        return pl
    }

    fun alfain(alf:String): MutableList<Int> {
        val numbers = mutableListOf(0)
        for (i in 1..alf.length - 1) {
            numbers.add(i)
        }
        return numbers
    }



    class Factory(private val gameRepository: GameRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) = GameViewModel(gameRepository) as T
    }
}