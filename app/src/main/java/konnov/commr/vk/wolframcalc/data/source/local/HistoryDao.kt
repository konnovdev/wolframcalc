package konnov.commr.vk.wolframcalc.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import konnov.commr.vk.wolframcalc.data.ResultPod

@Dao interface HistoryDao {

    @Query ("SELECT * FROM history")fun getHistory() : List<ResultPod>

    @Query ("DELETE FROM history") fun deleteHistory()

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertHistory(resultPod: ResultPod)

}