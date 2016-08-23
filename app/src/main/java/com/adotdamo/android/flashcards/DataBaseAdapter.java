package com.adotdamo.android.flashcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by karloandrada on 3/15/16.
 */
public class DataBaseAdapter {

    private DataBaseHelper mDataBaseHelper;
    private static int update = 1;

    public DataBaseAdapter(String tablename, Context context, Boolean Update)
    {
        if(Update == true) {
            update++;
        }
        mDataBaseHelper = new DataBaseHelper(tablename,context,update);
        mDataBaseHelper.getWritableDatabase();
        mDataBaseHelper.close();

    }



    public void deletetable(String table)
    {
        String delete = "DROP TABLE IF EXISTS [" +table+"]";

        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();

        db.execSQL(delete);

        db.close();

    }

    public void update(String mTableName ,String mOldQuestion,String mOldAnswer,String mNewQuestion,String mNewAnswer)
    {
        String tablename = "["+mTableName+"]";
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
        String[] whereArgs ={mOldQuestion,mOldAnswer};
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.QUESTION, mNewQuestion);
        contentValues.put(DataBaseHelper.ANSWER,mNewAnswer);
        String where = mDataBaseHelper.QUESTION +" =? OR "+ mDataBaseHelper.ANSWER+ " =? ";
        db.update(tablename, contentValues,where, whereArgs);
        db.close();
    }

    public void deleteRow(Question question)
    {
        String tablename = "["+mDataBaseHelper.TABLENAME+"]";
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
        String[] whereArgs = {question.getQuestion()};
        db.delete(tablename, mDataBaseHelper.QUESTION + " =? ", whereArgs);
        db.close();


    }

    public long insertData(String TableName,String Question, String Answer)
    {
        TableName = "["+TableName+"]";
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.QUESTION, Question);
        contentValues.put(DataBaseHelper.ANSWER, Answer);
        long id = db.insert(TableName,null,contentValues);
        db.close();
        return id;
    }

    public void insertTable(String TableName)
    {
        TableName = "["+TableName+"]";
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DataBaseHelper.QUESTION, "");
        contentValues.put(DataBaseHelper.ANSWER, "");
        long id = db.insert(TableName, null, contentValues);
        db.close();
    }
/*
    public void getAllData(String tablename)
    {

        tablename = "["+tablename+"]";
        SQLiteDatabase db = mDataBaseHelper.getReadableDatabase();

        String[] column = {DataBaseHelper.UID,DataBaseHelper.QUESTION,DataBaseHelper.ANSWER};
        Cursor cursor = db.query(tablename, column, null, null, null, null, null);

        while(cursor.moveToNext())
        {
            int index0 = cursor.getColumnIndex(mDataBaseHelper.UID);
            int index1 = cursor.getColumnIndex(mDataBaseHelper.QUESTION);
            int index2 = cursor.getColumnIndex(mDataBaseHelper.ANSWER);
            int cid = cursor.getInt(index0);
            String Question = cursor.getString(index1);
            String Answer = cursor.getString(index2);
        }
        db.close();
    }
*/
    public ArrayList<String> getTableNames()
    {


        ArrayList<String> tablename = new ArrayList<String>();

        SQLiteDatabase db = mDataBaseHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
               String table = c.getString(c.getColumnIndex("name"));
               tablename.add(table);
                c.moveToNext();
            }
        }
        db.close();
        return  tablename;

    }

    public ArrayList<Question> QuestionArray(String tablename)
    {
        tablename = "["+tablename+"]";
        ArrayList<Question> flashcards = new ArrayList<Question>();
        SQLiteDatabase db = mDataBaseHelper.getReadableDatabase();

        String[] column = {DataBaseHelper.UID,DataBaseHelper.QUESTION,DataBaseHelper.ANSWER};
        Cursor cursor = db.query(tablename ,column,null,null,null,null,null);


        while(cursor.moveToNext())
        {
            int index0 = cursor.getColumnIndex(mDataBaseHelper.UID);
            int index1 = cursor.getColumnIndex(mDataBaseHelper.QUESTION);
            int index2 = cursor.getColumnIndex(mDataBaseHelper.ANSWER);
            int cid = cursor.getInt(index0);
            String Question = cursor.getString(index1);
            String Answer = cursor.getString(index2);
            flashcards.add(new Question(Question,Answer));
        }
        db.close();
        return flashcards;

    }

    static class DataBaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "FLASHCARD DATABASE";
        private static final String UID = "_id";
        private static final String QUESTION = "mQuestionEditText";
        private static final String ANSWER = "mAnswerEditText";
        private static String CREATE_TABLE;
        private static String TABLENAME;





        public DataBaseHelper(String tablename,Context context,int update) {
            super(context, DATABASE_NAME, null, update);

            TABLENAME = tablename;
            CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ["+TABLENAME+"](" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + QUESTION + " VARCHAR(255), " + ANSWER + " VARCHAR(255));";

        }




        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {

                db.setVersion(newVersion);
                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
