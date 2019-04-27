package konnov.commr.vk.wolframcalc.util

import androidx.annotation.StringRes
import konnov.commr.vk.wolframcalc.data.ResultPod


sealed class ViewState

class ViewStateEmpty(@StringRes val message : Int) : ViewState()

class ViewStateSuccess(val result : ArrayList<ResultPod>?) : ViewState()