package konnov.commr.vk.wolframcalc.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "history")
data class ResultPod @JvmOverloads constructor(
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString()) {


    override fun toString(): String {
        return "title: $title, \ndescription: $description"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val resultPod = other as ResultPod
        if(title.equals(resultPod.title) && description.equals(resultPod.description)) {
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        return Objects.hash(title, description)
    }
}