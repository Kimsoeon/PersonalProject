package soeonkim.kr.hs.emirim.personalproject;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by HP on 2017-09-03.
 */

public class NewWrite extends AppCompatActivity{
    MyDBHelper myhelper;
    SQLiteDatabase sqlDB;
    Button but_save, but_delete, but_recovery;
    EditText edit_text_title, edit_text_contents;
    String sql;
    String mode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_write);

        myhelper = new MyDBHelper(this);

        but_recovery = (Button)findViewById(R.id.button_save);
        but_save = (Button)findViewById(R.id.button_save);
        but_delete = (Button)findViewById(R.id.button_delete);
        edit_text_title = (EditText) findViewById(R.id.edit_text_title);
        edit_text_contents = (EditText) findViewById(R.id.edit_text_contents);

        final String _id = getIntent().getStringExtra("_id");
        final String type = getIntent().getStringExtra("type");
        String title = getIntent().getStringExtra("title");
        edit_text_title.setText(title);
        String contents = getIntent().getStringExtra("contents");
        edit_text_contents.setText(contents);

        mode =  getIntent().getStringExtra("mode");

        if(mode != null) {
            but_delete.setVisibility(View.VISIBLE);
            but_save.setText("수정");
        }

        but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myhelper.getWritableDatabase();
                if(mode != null && type != null) {
                    if(type.equals("1")) {
                        sql = "update noteTable set type = 2 where _id = " + _id;
                    }
                    else {
                        sql = "delete from noteTable where _id = " + _id + " and type = 2";
                    }
                    sqlDB.execSQL(sql);
                    sqlDB.close();
                    Toast.makeText(NewWrite.this, "삭제됨", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myhelper.getWritableDatabase();

                if(mode != null && type != null )
                {

                    sql = "update noteTable set title = '" + edit_text_title.getText() + "', contents = '" +  edit_text_contents.getText() +  "' where _id = " + _id ;
                }
                else {
                    sql = "insert into noteTable values(NULL, '" + edit_text_title.getText() + "', '" + edit_text_contents.getText() + "', datetime('now','localtime'), 1 )";
                }

                sqlDB.execSQL(sql);
                sqlDB.close();
                if(mode != null) {
                    Toast.makeText(NewWrite.this, "수정됨", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(NewWrite.this, "저장됨", Toast.LENGTH_LONG).show();
                }

                finish();
            }
        });

    }
}
