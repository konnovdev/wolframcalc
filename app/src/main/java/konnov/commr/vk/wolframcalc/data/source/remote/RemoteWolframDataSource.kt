package konnov.commr.vk.wolframcalc.data.source.remote

import konnov.commr.vk.wolframcalc.data.ResultPod
import konnov.commr.vk.wolframcalc.data.source.WolframDataSource
import okhttp3.*
import java.io.IOException
import java.net.URLEncoder

object RemoteWolframDataSource : WolframDataSource {

    override fun getQueryResult(query: String, callback: WolframDataSource.GetResultCallback) {
        makeRestCall(getFormattedUrl(query), callback)
    }

    override fun getHistory(callback: WolframDataSource.GetResultCallback) {
        //Only for local database
    }

    override fun persistHistory(pods: List<ResultPod>) {
        //Only for local database
    }

    override fun clearHistory() {
        //Only for local database
    }


    private fun makeRestCall(url: String?, callback: WolframDataSource.GetResultCallback): String? {

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url!!)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onDataNotAvailable()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val result = ResultPodXmlParser.parseResultXml(response.body()!!.string())
                if(result == null) {
                    callback.onDataNotAvailable()
                    return
                }
                callback.onPodsLoaded(result)
            }
        })
        return null
    }

    private fun getFormattedUrl(query: String): String? {
        return WOLFRAM_BASE_URL + "input=" + URLEncoder.encode(
            query,
            "utf-8"
        ) + "&appid=" + WOLFRAM_APP_ID
    }
}