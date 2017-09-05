package soeonkim.kr.hs.emirim.personalproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;
    ListView listview;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWrite.class);
                startActivity(intent);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NewWrite.class);
                intent.putExtra("mode", "modify");
                intent.putExtra("type", "1");
                String _id = ((TextView) view.findViewById(R.id._id)).getText().toString();
                intent.putExtra("_id", _id);
                String title = ((TextView) view.findViewById(R.id.title)).getText().toString();
                intent.putExtra("title", title);
                String contents = ((TextView) view.findViewById(R.id.contents)).getText().toString();
                intent.putExtra("contents", contents);

                startActivity(intent);
            }
        });

        listview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //삭제 작업 업데이트하기
                return false;
            }
        });


    }

    public void selectTable() {
        sqlDB = myHelper.getReadableDatabase();
        String sql = "select * from noteTable where type = 1 order by _id desc";

        cursor = sqlDB.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            startManagingCursor(cursor);
            DBAdapter dbAdapter = new DBAdapter(this, cursor);
            listview.setAdapter(dbAdapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.waste_basket) {
            Intent intent = new Intent(MainActivity.this, WasteBasket.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}







