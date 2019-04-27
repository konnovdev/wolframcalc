package konnov.commr.vk.wolframcalc.data.source.local

import androidx.annotation.VisibleForTesting
import konnov.commr.vk.wolframcalc.data.ResultPod
import konnov.commr.vk.wolframcalc.data.source.WolframDataSource
import konnov.commr.vk.wolframcalc.util.AppExecutors

class LocalDataSource private constructor(
    val appExecutors: AppExecutors,
    val historyDao : HistoryDao) : WolframDataSource{

    companion object {
        private var INSTANCE: LocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, historyDao: HistoryDao) : LocalDataSource {
            if (INSTANCE == null) {
                synchronized(LocalDataSource::javaClass) {
                    INSTANCE = LocalDataSource(appExecutors, historyDao)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

    override fun getQueryResult(query: String, callback: WolframDataSource.GetResultCallback) {
        //only for remote source
    }

    override fun persistHistory(pods: List<ResultPod>) {
        appExecutors.diskIO.execute{
            for(pod in pods) {
                historyDao.insertHistory(pod)
            }
        }
    }

    override fun clearHistory() {
        appExecutors.diskIO.execute{ historyDao.deleteHistory() }
    }


    override fun getHistory(callback: WolframDataSource.GetResultCallback) {
        appExecutors.diskIO.execute {
            val history = historyDao.getHistory()
            appExecutors.mainThread.execute {
                if(history.isEmpty()){
                    callback.onDataNotAvailable()
                } else {
                    callback.onPodsLoaded(pods = history)
                }
            }
        }
    }


}