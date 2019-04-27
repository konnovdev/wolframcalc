package konnov.commr.vk.wolframcalc.mainscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import konnov.commr.vk.wolframcalc.R
import konnov.commr.vk.wolframcalc.historyscreen.HistoryActivity
import konnov.commr.vk.wolframcalc.data.ResultPod
import konnov.commr.vk.wolframcalc.util.ViewState
import konnov.commr.vk.wolframcalc.util.ViewStateEmpty
import konnov.commr.vk.wolframcalc.util.ViewStateSuccess
import konnov.commr.vk.wolframcalc.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var mMainViewModel : MainViewModel

    private lateinit var resultPodAdapter: ResultPodAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)

        main_screen_rv.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        main_screen_rv.setLayoutManager(linearLayoutManager)

        mMainViewModel = obtainViewModel()
        mMainViewModel.mLiveData.observe(this, Observer <ViewState> { response -> updateViewState(response) })

        calculate_btn.setOnClickListener {
            indeterminateBar.visibility = ProgressBar.VISIBLE
            calculate_btn.isEnabled = false
            calculate_btn.alpha = 0.5f

            mMainViewModel.loadData(input_et.text.toString())
        }
    }


    private fun updateViewState(state: ViewState) {
        when (state) {
            is ViewStateSuccess -> showResult(state.result!!)
            is ViewStateEmpty -> showError(resources.getString(state.message))
        }
    }

    private fun showError(message : String?){
        indeterminateBar.visibility = ProgressBar.INVISIBLE
        calculate_btn.isEnabled = true
        calculate_btn.alpha = 1f
        Snackbar.make(findViewById(android.R.id.content), message!!, Snackbar.LENGTH_LONG).show()
    }

    private fun showResult(resultPods: ArrayList<ResultPod>) {
        indeterminateBar.visibility = ProgressBar.INVISIBLE
        calculate_btn.isEnabled = true
        calculate_btn.alpha = 1f

        resultPodAdapter = ResultPodAdapter(resultPods)
        main_screen_rv.adapter = resultPodAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        startActivity(Intent(this, HistoryActivity::class.java))
        return true
    }

    private fun obtainViewModel(): MainViewModel = obtainViewModel(MainViewModel::class.java)
}
