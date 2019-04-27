package konnov.commr.vk.wolframcalc.data.source

import konnov.commr.vk.wolframcalc.data.ResultPod

class Repository(
     private val resultRemoteDataSource: WolframDataSource,
     private val historyLocalDataSource: WolframDataSource
) : WolframDataSource {

    override fun getHistory(callback: WolframDataSource.GetResultCallback) {
        historyLocalDataSource.getHistory(object : WolframDataSource.GetResultCallback{
            override fun onPodsLoaded(pods: List<ResultPod>) {
                callback.onPodsLoaded(pods)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

        })
    }

    override fun persistHistory(pods: List<ResultPod>) {
        historyLocalDataSource.persistHistory(pods)
    }

    override fun clearHistory() {
        historyLocalDataSource.clearHistory()
    }

    override fun getQueryResult(query: String, callback: WolframDataSource.GetResultCallback) {
        resultRemoteDataSource.getQueryResult(query, object : WolframDataSource.GetResultCallback{
            override fun onPodsLoaded(pods: List<ResultPod>) {
                persistHistory(pods)
                callback.onPodsLoaded(pods)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

        })
    }

    companion object {

        private var INSTANCE: Repository?= null

        @JvmStatic fun getInstance(resultRemoteDataSource: WolframDataSource,
                                   historyLocalDataSource: WolframDataSource) =
                INSTANCE ?: synchronized(Repository::class.java) {
                    INSTANCE ?: Repository(resultRemoteDataSource, historyLocalDataSource)
                        .also { INSTANCE = it }
                }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }

    }
}