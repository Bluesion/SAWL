package com.charlie.sawl.timetable

class TimetableDB {
    var id: Int = 0
    var subject: String? = null
    var teacher: String? = null

    internal constructor()

    internal constructor(id: Int, subject: String, teacher: String) {
        this.id = id
        this.subject = subject
        this.teacher = teacher
    }

    companion object {
        const val TABLE_NAME = "timetable"

        const val COLUMN_ID = "_id"
        const val COLUMN_SUBJECT = "subject"
        const val COLUMN_TEACHER = "teacher"

        const val CREATE_TABLE = (
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_SUBJECT + " TEXT, "
                        + COLUMN_TEACHER + " TEXT)")
    }
}
