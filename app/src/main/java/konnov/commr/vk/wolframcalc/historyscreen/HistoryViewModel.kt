package konnov.commr.vk.wolframcalc.historyscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import konnov.commr.vk.wolframcalc.R
import konnov.commr.vk.wolframcalc.data.ResultPod
import konnov.commr.vk.wolframcalc.data.source.Repository
import konnov.commr.vk.wolframcalc.data.source.WolframDataSource
import konnov.commr.vk.wolframcalc.util.ViewState
import konnov.commr.vk.wolframcalc.util.ViewStateEmpty
import konnov.commr.vk.wolframcalc.util.ViewStateSuccess

class HistoryViewModel (private val repository: Repository) : ViewModel(){

    val mLiveData = MutableLiveData<ViewState>()

    fun getHistory() {
        repository.getHistory(object : WolframDataSource.GetResultCallback{
            override fun onPodsLoaded(pods: List<ResultPod>) {
                mLiveData.value = ViewStateSuccess(pods as ArrayList<ResultPod>)
            }

            override fun onDataNotAvailable() {
                mLiveData.value  = ViewStateEmpty(R.string.error)
            }
        })
    }

    fun clearHistory(){
        repository.clearHistory()
        mLiveData.value  = ViewStateEmpty(R.string.empty)
    }
}