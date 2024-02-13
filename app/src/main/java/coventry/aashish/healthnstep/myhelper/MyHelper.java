package coventry.aashish.healthnstep.myhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import coventry.aashish.healthnstep.model.Steps;

import java.util.ArrayList;
import java.util.List;

public class MyHelper extends SQLiteOpenHelper {

    private static final String dbName = "Dictionary";
    private static final int dbVersion = 1;

    private static final String TblDays = "tbldays";
    private static final String DayId = "DayId";
    private static final String Days = "days";
    private static final String Steps = "steps";


    public MyHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        db = getWritableDatabase();

        String createtable = "CREATE TABLE " + TblDays +
                "("
                + DayId + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                Steps + " TEXT,"
                + Days + " TEXT" +
                ")";
        db.execSQL(createtable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
//
//    public boolean InsertData(String word, String meaning, SQLiteDatabase db) {
//        try {
//            String query = "insert into Words(Word,Meaning) values('" + word + "','" + meaning + "')";
//            db.execSQL(query);
//            return true;
//        } catch (Exception e) {
//            Log.d("error: ", e.toString());
//            return false;
//        }
//    }
    public long InsertData(String word, String meaning, SQLiteDatabase db){
        long id;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Steps,word);
        contentValues.put(Days,meaning);
        id = db.insert(TblDays, null, contentValues);
        return id;

    }

    public List<coventry.aashish.healthnstep.model.Steps> GetAllWords(SQLiteDatabase db) {
        List<coventry.aashish.healthnstep.model.Steps> dictionaryList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from tblWord",null);
        if (cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                dictionaryList.add(new Steps(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            }
        }
        return dictionaryList;
    }
}
