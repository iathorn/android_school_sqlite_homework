package com.sqliteexampleone.iathorn.sqliteexampleone;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InsertActivity extends AppCompatActivity {

    Button insertButton;
    EditText et_country;
    EditText et_capital;
    MainActivity mainActivity = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        insertButton = (Button) findViewById(R.id.button);
        et_capital = (EditText) findViewById(R.id.et_capital);
        et_country = (EditText) findViewById(R.id.et_country);
        mainActivity.dbHelper = new MyDBHelper(this);

        Intent intent = getIntent();

        if(intent != null && intent.getStringExtra("isUpdate").equals("true")){

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == mainActivity.RQCODE_INSERT) {
            insertButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.db = mainActivity.dbHelper.getWritableDatabase();
                    mainActivity.values = new ContentValues();
                    mainActivity.values.put("country", et_country.getText().toString());
                    Log.d("TEST2", "TESTING2: " + et_country.getText());
                    mainActivity.values.put("capital", et_capital.getText().toString());
                    mainActivity.db.insert("country", null, mainActivity.values);
                    mainActivity.dbHelper.close();
                    Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        } else if (resultCode == mainActivity.RQCODE_UPDATE) {
            insertButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (et_country.getText().length() > 0 && et_capital.getText().length() > 0) {
                        mainActivity.db = mainActivity.dbHelper.getWritableDatabase();
                        String country = et_country.getText().toString();
                        String capital = et_capital.getText().toString();
                        mainActivity.values = new ContentValues();
                        mainActivity.values.put("country", country);
                        mainActivity.values.put("capital", capital);
                        mainActivity.db.update("country", mainActivity.values, "country=?, capital=?", new String[]{country, capital});
                        mainActivity.dbHelper.close();
                        Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
