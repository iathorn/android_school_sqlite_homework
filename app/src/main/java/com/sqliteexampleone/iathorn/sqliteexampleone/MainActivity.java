package com.sqliteexampleone.iathorn.sqliteexampleone;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private  ListView list;

     MyDBHelper dbHelper;
     SQLiteDatabase db;
     Cursor cursor;
     SimpleCursorAdapter cursorAdapter;
     ContentValues values;
     String[] projection = {"_id", "country", "capital"};
    private Button button;

    public static final int RQCODE_INSERT = 1;
    public static final int RQCODE_UPDATE = 2;
    public static final int RQCODE_DELETE = 3;

    private static final int MENU_INSERT = Menu.FIRST;
    private static final int MENU_UPDATE = Menu.FIRST + 1;
    private static final int MENU_DELETE = Menu.FIRST + 2;
    private static final int MENU_READ = Menu.FIRST + 3;
    private static final int MENU_DELETE_ALL = Menu.FIRST + 4;


    private EditText et_country;
    private EditText et_capital;

    private ArrayList<ListItem> countryArrayList;
    private CountryListAdapter adapter;

    private View insertView;

    private LinearLayout wholeLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wholeLinearLayout = (LinearLayout) findViewById(R.id.wholeLinearLayout);

        dbHelper = new MyDBHelper(this);



        list = (ListView)findViewById(R.id.list);
        countryArrayList = new ArrayList<>();

        button = (Button) findViewById(R.id.button);


//        registerForContextMenu(list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                intent.putExtra("isUpdate", "true");
                intent.putExtra("country", countryArrayList.get(i).getCountry());
                intent.putExtra("capital", countryArrayList.get(i).getCapital());
                startActivity(intent);

            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, MENU_UPDATE, 0, "데이터 수정하기").setIcon(R.drawable.ic_launcher_foreground);
        menu.add(0, MENU_DELETE, 1, "데이터 삭제하기").setIcon(R.drawable.ic_launcher_foreground);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_INSERT, 0, "데이터 저장하기.").setIcon(R.drawable.ic_launcher_background);
        menu.add(0, MENU_READ, 1, "데이터 리스트 보기.").setIcon(R.drawable.ic_launcher_background);
        menu.add(0, MENU_DELETE_ALL, 2, "데이터 전체 삭제.").setIcon(R.drawable.ic_launcher_background);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case MENU_UPDATE:
                Intent intent = new Intent(this, InsertActivity.class);
                startActivityForResult(intent, RQCODE_UPDATE);
                return true;

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_INSERT:
                startActivityForResult(new Intent(this, InsertActivity.class), RQCODE_INSERT);
                return true;
            case MENU_READ:
                readData();
                return true;
            case MENU_DELETE_ALL:
                deleteAllData();
                readData();
                list.invalidateViews();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void readData() {
        db = dbHelper.getReadableDatabase();
        cursor = db.query("country", projection, null, null, null, null, null);
        if(cursor != null) {
            int idColumn = cursor.getColumnIndex("_id");
            int countryColumn = cursor.getColumnIndex("country");
            int capitalColumn = cursor.getColumnIndex("capital");
            countryArrayList = new ArrayList<>();
            while(cursor.moveToNext())
            {
                int _id = cursor.getInt(idColumn);
                String country = cursor.getString(countryColumn);
                String capital = cursor.getString(capitalColumn);
                countryArrayList.add(new ListItem(_id, country, capital));
            }
            adapter = new CountryListAdapter(MainActivity.this, countryArrayList);
            list.setAdapter(adapter);

        }
    }

    public void deleteAllData(){
        db = dbHelper.getWritableDatabase();
        db.delete("country", null, null);
        dbHelper.close();
    }

//    public void updateData() {
//        if(et_country.getText().length() > 0 && et_capital.getText().length() > 0) {
//            db = dbHelper.getWritableDatabase();
//            String name =
//        }
//    }
}

class MyDBHelper extends SQLiteOpenHelper {

    public MyDBHelper(Context context) {
        super(context, "mytest.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE country(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +  "country TEXT, capital TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS country;");
        onCreate(sqLiteDatabase);
    }
}
