package konnov.commr.vk.wolframcalc

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import konnov.commr.vk.wolframcalc.data.source.Repository
import konnov.commr.vk.wolframcalc.historyscreen.HistoryViewModel
import konnov.commr.vk.wolframcalc.mainscreen.MainViewModel

class ViewModelFactory private constructor(
        private val repository : Repository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)
                isAssignableFrom(HistoryViewModel::class.java) ->
                    HistoryViewModel(repository)
                else ->
                    throw IllegalArgumentException("Unknown class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideRepository(application.applicationContext))
                    .also { INSTANCE = it }
            }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}