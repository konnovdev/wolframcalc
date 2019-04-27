package konnov.commr.vk.wolframcalc.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import konnov.commr.vk.wolframcalc.R
import konnov.commr.vk.wolframcalc.data.ResultPod
import konnov.commr.vk.wolframcalc.util.getScreenHeight
import kotlinx.android.synthetic.main.result_pod_card_layout.view.*

class ResultPodAdapter(private val resultPods: List<ResultPod>) :
    RecyclerView.Adapter<ResultPodAdapter.ResultPodViewHolder>() {

    private var lastAnimatedPosition = -1

    override fun getItemCount(): Int {
       return resultPods.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ResultPodViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_pod_card_layout, parent, false)

        return ResultPodViewHolder(view)
    }

    override fun onBindViewHolder(resultPodViewHolder: ResultPodViewHolder, position: Int) {
        resultPodViewHolder.title.text = resultPods[position].title
        resultPodViewHolder.description.text = resultPods[position].description
        setAnimation(resultPodViewHolder.cardView, position)
    }



    private fun setAnimation(viewToAnimate: View, position: Int) {
        viewToAnimate.clearAnimation()

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position
            viewToAnimate.translationY = getScreenHeight(viewToAnimate.context).toFloat()
            viewToAnimate.animate()
                .translationY(0f)
                .setInterpolator(DecelerateInterpolator(3f))
                .setDuration(700)
                .start()
        } else
            return
    }

    class ResultPodViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView = itemView.result_pod_card_view!!

        val title = itemView.text_result_pod_title!!

        val description = itemView.text_result_pod_description!!
    }
}
