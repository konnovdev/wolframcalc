package konnov.commr.vk.wolframcalc.historyscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import konnov.commr.vk.wolframcalc.R
import konnov.commr.vk.wolframcalc.data.ResultPod
import konnov.commr.vk.wolframcalc.mainscreen.ResultPodAdapter
import konnov.commr.vk.wolframcalc.util.ViewState
import konnov.commr.vk.wolframcalc.util.ViewStateEmpty
import konnov.commr.vk.wolframcalc.util.ViewStateSuccess
import konnov.commr.vk.wolframcalc.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_history.*
import java.util.ArrayList

class HistoryActivity : AppCompatActivity() {

    private lateinit var mHistoryViewModel: HistoryViewModel

    private lateinit var resultPodAdapter: ResultPodAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        history_toolbar.title = resources.getString(R.string.history)
        setSupportActionBar(history_toolbar)
        if(supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        history_rv.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        history_rv.layoutManager = linearLayoutManager

        mHistoryViewModel = obtainViewModel()
        mHistoryViewModel.mLiveData.observe(this, Observer <ViewState> { response -> updateViewState(response) })
    }

    override fun onResume() {
        super.onResume()
        mHistoryViewModel.getHistory()
    }


    private fun updateViewState(state: ViewState) {
        when (state) {
            is ViewStateSuccess -> showHistory(state.result!!)
            is ViewStateEmpty -> showHistory(ArrayList<ResultPod>())
        }
    }

    private fun showHistory(resultPods: ArrayList<ResultPod>) {
        resultPodAdapter = ResultPodAdapter(resultPods)
        history_rv.adapter = resultPodAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
                android.R.id.home -> onBackPressed()
                R.id.clear_history_item -> mHistoryViewModel.clearHistory()
            }
        return super.onOptionsItemSelected(item)
    }

    private fun obtainViewModel(): HistoryViewModel = obtainViewModel(HistoryViewModel::class.java)

}
