package konnov.commr.vk.wolframcalc.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import konnov.commr.vk.wolframcalc.data.ResultPod

@Database(entities = arrayOf(ResultPod::class), version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {

        private var INSTANCE: HistoryDatabase ?= null

        fun getInstance(context: Context) : HistoryDatabase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    HistoryDatabase::class.java, "history.db")
                    .build()
            }
            return INSTANCE!!
        }
    }

}