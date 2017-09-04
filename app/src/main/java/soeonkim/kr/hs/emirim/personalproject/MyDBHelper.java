package soeonkim.kr.hs.emirim.personalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP on 2017-09-03.
 */

public class MyDBHelper extends SQLiteOpenHelper{
    String sql;
    public MyDBHelper(Context context){
        super(context ,"noteDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sql = "create table noteTable(_id integer primary key autoincrement," +
                " title text, contents text, create_date date, type integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        sql = "drop table if exists noteTable";
        db.execSQL(sql);
        onCreate(db);
    }
}
