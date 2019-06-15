package com.example.hp.medex;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

class ambbooking extends Activity implements OnItemSelectedListener{

    Button b;
    EditText pl,dl,pn,mo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambbooking);

        // Spinner element
        final Spinner spinner1 = (Spinner) findViewById(R.id.emergencytype);
        final Spinner spinner2 = (Spinner) findViewById(R.id.ambulancetype);


        // Spinner click listener
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        final List<String> categories1 = new ArrayList<String>();
        categories1.add("Emergency Type");
        categories1.add("Advanced Life Support");
        categories1.add("Basic Life Support");
        categories1.add("Patient Transport");
        categories1.add("Mortuary");

        List<String> categories2 = new ArrayList<String>();
        categories2.add("Ambulance Type");
        categories2.add("Advanced Life Support");
        categories2.add("Basic Life Support");
        categories2.add("Patient Transport");
        categories2.add("Mortuary");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1){
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
        };
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2){
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
        };

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter1);
        spinner2.setAdapter(dataAdapter2);

        pl = findViewById(R.id.patientlocation);
        dl = findViewById(R.id.destination);
        pn = findViewById(R.id.patientname);
        mo = findViewById(R.id.mobile);
        b=findViewById(R.id.ambbookingsubmit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String et=spinner1.getSelectedItem().toString();
                String at=spinner2.getSelectedItem().toString();
                String plocation = pl.getText().toString();
                String dlocation = dl.getText().toString();
                String pname = pn.getText().toString();
                String mob = mo.getText().toString();
                SQLiteDatabase mydatabase = openOrCreateDatabase("Ambbooking",MODE_PRIVATE,null);
                mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Details(etype VARCHAR,atype VARCHAR,ploc VARCHAR,dloc VARCHAR,pn VARCHAR,mo VARCHAR);");
                ContentValues contentValues = new ContentValues();
                contentValues.put("etype",et);
                contentValues.put("atype",at);
                contentValues.put("ploc",plocation);
                contentValues.put("dloc",dlocation);
                contentValues.put("pn",pname);
                contentValues.put("mo",mob);
                long result = mydatabase.insert("Details",null ,contentValues);
                Intent i = new Intent(ambbooking.this,dboutput.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
