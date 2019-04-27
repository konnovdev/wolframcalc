package konnov.commr.vk.wolframcalc.util

import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import konnov.commr.vk.wolframcalc.mainscreen.ResultPodAdapter


private var screenHeight = 0

fun RecyclerView.Adapter<ResultPodAdapter.ResultPodViewHolder>.getScreenHeight(c: Context?): Int {
    if (screenHeight == 0) {
        val wm = c?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenHeight = size.y
    }

    return screenHeight
}
