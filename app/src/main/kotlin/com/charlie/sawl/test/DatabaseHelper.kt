package com.charlie.sawl.test

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val allSubject: List<Subject>
        get() {
            val subject = ArrayList<Subject>()
            val selectQuery = "SELECT * FROM " + Subject.TABLE_NAME + " ORDER BY " + Subject.COLUMN_ID + " DESC"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val info = Subject()
                    info.id = cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_ID))
                    info.name = cursor.getString(cursor.getColumnIndex(Subject.COLUMN_NAME))
                    info.model = cursor.getString(cursor.getColumnIndex(Subject.COLUMN_MODEL))
                    info.csc = cursor.getString(cursor.getColumnIndex(Subject.COLUMN_CSC))

                    subject.add(info)
                } while (cursor.moveToNext())
            }
            db.close()

            return subject
        }

    val bookMarkCount: Int
        get() {
            val countQuery = "SELECT  * FROM " + Subject.TABLE_NAME
            val db = this.readableDatabase
            val cursor = db.rawQuery(countQuery, null)

            val count = cursor.count
            cursor.close()

            return count
        }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Subject.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + Subject.TABLE_NAME)
        onCreate(db)
    }

    fun insertBookMark(name: String, model: String, csc: String): Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(Subject.COLUMN_NAME, name)
        values.put(Subject.COLUMN_MODEL, model)
        values.put(Subject.COLUMN_CSC, csc)
        val id = db.insert(Subject.TABLE_NAME, null, values)
        db.close()

        return id
    }

    fun getBookMark(id: Long): Subject {
        val db = this.readableDatabase

        val cursor = db.query(Subject.TABLE_NAME,
                arrayOf(Subject.COLUMN_ID, Subject.COLUMN_NAME, Subject.COLUMN_MODEL, Subject.COLUMN_CSC),
                Subject.COLUMN_ID + "=?",
                arrayOf(id.toString()), null, null, null, null)

        cursor?.moveToFirst()

        val bookmark = Subject(cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Subject.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Subject.COLUMN_MODEL)),
                cursor.getString(cursor.getColumnIndex(Subject.COLUMN_CSC)))

        cursor.close();

        return bookmark
    }

    fun updateBookMark(subject: Subject) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(Subject.COLUMN_NAME, subject.name)
        values.put(Subject.COLUMN_MODEL, subject.model)
        values.put(Subject.COLUMN_CSC, subject.csc)

        db.update(Subject.TABLE_NAME, values, Subject.COLUMN_ID + " = ?", arrayOf(subject.id.toString()))
    }

    fun deleteBookMark(subject: Subject) {
        val db = this.writableDatabase
        db.delete(Subject.TABLE_NAME, Subject.COLUMN_ID + " = ?", arrayOf(subject.id.toString()))
        db.close()
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Subject.db"
    }
}