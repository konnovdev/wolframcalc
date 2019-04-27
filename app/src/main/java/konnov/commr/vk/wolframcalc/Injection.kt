package konnov.commr.vk.wolframcalc

import android.content.Context
import konnov.commr.vk.wolframcalc.data.source.Repository
import konnov.commr.vk.wolframcalc.data.source.local.HistoryDatabase
import konnov.commr.vk.wolframcalc.data.source.local.LocalDataSource
import konnov.commr.vk.wolframcalc.data.source.remote.RemoteWolframDataSource
import konnov.commr.vk.wolframcalc.util.AppExecutors

object Injection {

    fun provideRepository(context: Context): Repository {
        val database = HistoryDatabase.getInstance(context)
        return Repository.getInstance(RemoteWolframDataSource,
            LocalDataSource.getInstance(AppExecutors(), database.historyDao()))
    }

}