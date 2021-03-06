package com.abhi.kotlinnotesmvvm.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room annotation
 * @Entity (tableName=) : create a table with name
 * @ColumnInfo(name=) : rename col. Default name is same as variable name.
 * @PrimaryKey(autoGenerated=) mark col as primary key.
 * */

@Entity(tableName = "note_table")
class Note(
    var title: String,
    var description: String,
    var priority: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "Note: id:$id,  title:$title, description:$description, priority:$priority"
    }

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) {
            return false
        }

        other as Note
        return title.equals(other.title)
            && description.equals(other.description)
            && priority == other.priority
    }

    override fun hashCode() =
        31 * 31 * 31 * id.hashCode() + 31 * 31 * title.hashCode() + 31 * description.hashCode() + priority.hashCode()

}
