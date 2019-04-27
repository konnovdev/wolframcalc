package konnov.commr.vk.wolframcalc

import konnov.commr.vk.wolframcalc.data.ResultPod
import konnov.commr.vk.wolframcalc.data.source.Repository
import konnov.commr.vk.wolframcalc.data.source.WolframDataSource
import konnov.commr.vk.wolframcalc.data.source.local.LocalDataSource
import konnov.commr.vk.wolframcalc.data.source.remote.RemoteWolframDataSource
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class WolframTest {

    @Mock
    private lateinit var localDataSource: LocalDataSource
    private lateinit var repository: Repository

    @Before
    fun setUpRepo() {
        MockitoAnnotations.initMocks(this)
        repository = Repository(RemoteWolframDataSource, localDataSource)
    }

    @Test
    fun checkCalculationIsCorrect() {
        val query = "2323-324234+352453*3483827482748274/34723487827437824788724-0.12424+8828-sin(0.32)+log(7)/3478932679423462938497824389"
        val expectedResult = "-313083.403445..."
        var answerIsCorrect = false

        repository.getQueryResult(query, object : WolframDataSource.GetResultCallback {
            override fun onPodsLoaded(pods: List<ResultPod>) {
                for (result in pods) {
                    if (result.description.contains(expectedResult)) {
                        println("result: " + result.description)
                        answerIsCorrect = true
                        break
                    }
                }
            }

            override fun onDataNotAvailable() {
                print("onDataNotAvailable")
            }
        })
        Thread.sleep(9000) //waiting until it calculates
        assertTrue(answerIsCorrect)
    }
}