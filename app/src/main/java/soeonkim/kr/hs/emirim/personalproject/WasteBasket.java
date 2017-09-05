package soeonkim.kr.hs.emirim.personalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by HP on 2017-09-05.
 */

public class WasteBasket extends AppCompatActivity {
    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;
    ListView listview;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waste_basket);

        myHelper = new MyDBHelper(this);
        sqlDB = myHelper.getWritableDatabase();
        listview = (ListView) findViewById(R.id.listview);

        selectTable();


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WasteBasket.this, NewWrite.class);
                intent.putExtra("mode", "modify");
                intent.putExtra("type", "2");
                String _id = ((TextView) view.findViewById(R.id._id)).getText().toString();
                intent.putExtra("_id", _id);
                String title = ((TextView) view.findViewById(R.id.title)).getText().toString();
                intent.putExtra("title", title);
                String contents = ((TextView) view.findViewById(R.id.contents)).getText().toString();
                intent.putExtra("contents", contents);

                startActivity(intent);
            }
        });


    }
    public void selectTable() {
        sqlDB = myHelper.getReadableDatabase();
        String sql = "select * from noteTable where type = 2 order by _id desc";

        cursor = sqlDB.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            startManagingCursor(cursor);
            DBAdapter dbAdapter = new DBAdapter(this, cursor);
            listview.setAdapter(dbAdapter);
        }
    }
}

