package konnov.commr.vk.wolframcalc.data.source

import konnov.commr.vk.wolframcalc.data.ResultPod

interface WolframDataSource {

    interface GetResultCallback {

        fun onPodsLoaded(pods: List<ResultPod>)

        fun onDataNotAvailable()
    }

    fun getHistory(callback: GetResultCallback)

    fun persistHistory(pods: List<ResultPod>)

    fun clearHistory()

    fun getQueryResult(query: String, callback: GetResultCallback)
}