package com.charlie.sawl.timetable

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class TimetableDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val getAll: List<TimetableDB>
        get() {
            val subject = ArrayList<TimetableDB>()
            val selectQuery = "SELECT * FROM " + TimetableDB.TABLE_NAME + " ORDER BY " + TimetableDB.COLUMN_ID + " DESC"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val info = TimetableDB()
                    info.id = cursor.getInt(cursor.getColumnIndex(TimetableDB.COLUMN_ID))
                    info.subject = cursor.getString(cursor.getColumnIndex(TimetableDB.COLUMN_SUBJECT))
                    info.teacher = cursor.getString(cursor.getColumnIndex(TimetableDB.COLUMN_TEACHER))

                    subject.add(info)
                } while (cursor.moveToNext())
            }
            db.close()

            return subject
        }

    val timetableCount: Int
        get() {
            val countQuery = "SELECT  * FROM " + TimetableDB.TABLE_NAME
            val db = this.readableDatabase
            val cursor = db.rawQuery(countQuery, null)

            val count = cursor.count
            cursor.close()

            return count
        }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TimetableDB.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TimetableDB.TABLE_NAME)
        onCreate(db)
    }

    fun insert(subject: String, teacher: String): Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(TimetableDB.COLUMN_SUBJECT, subject)
        values.put(TimetableDB.COLUMN_TEACHER, teacher)
        val id = db.insert(TimetableDB.TABLE_NAME, null, values)
        db.close()

        return id
    }

    fun get(id: Long): TimetableDB {
        val db = this.readableDatabase

        val cursor = db.query(TimetableDB.TABLE_NAME,
                arrayOf(TimetableDB.COLUMN_ID, TimetableDB.COLUMN_SUBJECT, TimetableDB.COLUMN_TEACHER), TimetableDB.COLUMN_ID + "=?",
                arrayOf(id.toString()), null, null, null, null)

        cursor?.moveToFirst()

        val info = TimetableDB(cursor.getInt(cursor.getColumnIndex(TimetableDB.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(TimetableDB.COLUMN_SUBJECT)),
                cursor.getString(cursor.getColumnIndex(TimetableDB.COLUMN_TEACHER)))

        cursor.close();

        return info
    }

    // 과목과 선생님을 불러올 수 있는 함수
    /*
    fun getSubjectAndTeacher(id: Long): ContentValues {
        val db = this.readableDatabase
        val row = ContentValues()
        val cursor = db.rawQuery("SELECT "+ TimetableDB.COLUMN_SUBJECT + ", " + TimetableDB.COLUMN_TEACHER + " FROM "
        + TimetableDB.TABLE_NAME + " WHERE " + TimetableDB.COLUMN_ID + "=?", String.valueOf(id))
        return row
    }
    */

    fun update(timetable: TimetableDB) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(TimetableDB.COLUMN_SUBJECT, timetable.subject)
        values.put(TimetableDB.COLUMN_TEACHER, timetable.teacher)

        db.update(TimetableDB.TABLE_NAME, values, TimetableDB.COLUMN_ID + " = ?", arrayOf(timetable.id.toString()))
    }

    fun delete(timetable: TimetableDB) {
        val db = this.writableDatabase
        db.delete(TimetableDB.TABLE_NAME, TimetableDB.COLUMN_ID + " = ?", arrayOf(timetable.id.toString()))
        db.close()
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Timetable.db"
    }
}