package com.charlie.sawl.memo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "memo.db";
    private static final String TABLE_REMINDERS = "Memo";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_REPEAT = "repeat";
    private static final String KEY_REPEAT_NO = "repeat_no";
    private static final String KEY_REPEAT_TYPE = "repeat_type";
    private static final String KEY_ACTIVE = "active";

    public MemoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_REMINDERS +
                "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_TIME + " INTEGER,"
                + KEY_REPEAT + " BOOLEAN,"
                + KEY_REPEAT_NO + " INTEGER,"
                + KEY_REPEAT_TYPE + " TEXT,"
                + KEY_ACTIVE + " BOOLEAN" + ")";
        db.execSQL(CREATE_REMINDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);

        onCreate(db);
    }

    public int addMemo(MemoClass memoClass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE , memoClass.getTitle());
        values.put(KEY_DATE , memoClass.getDate());
        values.put(KEY_TIME , memoClass.getTime());
        values.put(KEY_REPEAT , memoClass.getRepeat());
        values.put(KEY_REPEAT_NO , memoClass.getRepeatNo());
        values.put(KEY_REPEAT_TYPE, memoClass.getRepeatType());
        values.put(KEY_ACTIVE, memoClass.getActive());

        long ID = db.insert(TABLE_REMINDERS, null, values);
        db.close();
        return (int) ID;
    }

    public MemoClass getMemo(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REMINDERS, new String[]
                        {
                                KEY_ID,
                                KEY_TITLE,
                                KEY_DATE,
                                KEY_TIME,
                                KEY_REPEAT,
                                KEY_REPEAT_NO,
                                KEY_REPEAT_TYPE,
                                KEY_ACTIVE
                        }, KEY_ID + "=?",

                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return new MemoClass(Integer.parseInt(Objects.requireNonNull(cursor).getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7));
    }

    public List<MemoClass> getAllMemo(){
        List<MemoClass> memoClassList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_REMINDERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                MemoClass memoClass = new MemoClass();
                memoClass.setID(Integer.parseInt(cursor.getString(0)));
                memoClass.setTitle(cursor.getString(1));
                memoClass.setDate(cursor.getString(2));
                memoClass.setTime(cursor.getString(3));
                memoClass.setRepeat(cursor.getString(4));
                memoClass.setRepeatNo(cursor.getString(5));
                memoClass.setRepeatType(cursor.getString(6));
                memoClass.setActive(cursor.getString(7));

                memoClassList.add(memoClass);
            } while (cursor.moveToNext());
        }
        return memoClassList;
    }

    public void updateMemo(MemoClass memoClass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE , memoClass.getTitle());
        values.put(KEY_DATE , memoClass.getDate());
        values.put(KEY_TIME , memoClass.getTime());
        values.put(KEY_REPEAT , memoClass.getRepeat());
        values.put(KEY_REPEAT_NO , memoClass.getRepeatNo());
        values.put(KEY_REPEAT_TYPE, memoClass.getRepeatType());
        values.put(KEY_ACTIVE, memoClass.getActive());

        db.update(TABLE_REMINDERS, values, KEY_ID + "=?", new String[]{String.valueOf(memoClass.getID())});
    }

    public void deleteMemo(MemoClass memoClass){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, KEY_ID + "=?", new String[]{String.valueOf(memoClass.getID())});
        db.close();
    }
}
