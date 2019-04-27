package konnov.commr.vk.wolframcalc.mainscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import konnov.commr.vk.wolframcalc.R
import konnov.commr.vk.wolframcalc.data.source.Repository
import konnov.commr.vk.wolframcalc.data.ResultPod
import konnov.commr.vk.wolframcalc.data.source.WolframDataSource
import konnov.commr.vk.wolframcalc.util.ViewState
import konnov.commr.vk.wolframcalc.util.ViewStateEmpty
import konnov.commr.vk.wolframcalc.util.ViewStateSuccess

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    val mLiveData = MutableLiveData<ViewState>()

    fun loadData(query : String?) {
        if(query!!.isEmpty()) {
            mLiveData.value = ViewStateEmpty(R.string.input_text)
        } else {
            repository.getQueryResult(query, object : WolframDataSource.GetResultCallback{
                override fun onPodsLoaded(pods: List<ResultPod>) {
                    mLiveData.postValue(ViewStateSuccess(pods as ArrayList<ResultPod>))
                }

                override fun onDataNotAvailable() {
                    mLiveData.postValue(ViewStateEmpty(R.string.error))
                }
            })
        }
    }

}