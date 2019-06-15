package com.example.hp.medex;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;

public class dboutput extends AppCompatActivity {

    ListView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dboutput);

        SQLiteDatabase mydatabase = openOrCreateDatabase("Ambbooking",MODE_PRIVATE,null);
        Cursor res = mydatabase.rawQuery("select * from Details",null);
        if(res.getCount() == 0) {
            // show message
            //showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        String det[] = new String[1];
        while (res.moveToNext()) {
            buffer.append("Emergency Type :"+ res.getString(0)+"\n");
            buffer.append("Ambulance Type :"+ res.getString(1)+"\n");
            buffer.append("Patient Location :"+ res.getString(2)+"\n");
            buffer.append("Destination Location :"+ res.getString(3)+"\n");
            buffer.append("Patient Name :"+ res.getString(4)+"\n");
            buffer.append("Mobile No :"+ res.getString(5)+"\n\n");
        }
        det[0] = buffer.toString();
        // Show all data
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, det);
        ListView listView = (ListView) findViewById(R.id.listview1);
        listView.setAdapter(itemsAdapter);
    }

}
