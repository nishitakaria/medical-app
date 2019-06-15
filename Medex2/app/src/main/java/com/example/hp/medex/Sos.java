package com.example.hp.medex;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sos extends AppCompatActivity {

    public static String num1 = null;
    public static String num2 = null;
    public static String num3 = null;
    Button b1;
    EditText ed1,ed2,ed3;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        b1 = findViewById(R.id.button2);
        ed1 = findViewById(R.id.phone1num);
        ed2 = findViewById(R.id.phone2num);
        ed3 = findViewById(R.id.phone3num);
        sharedpreferences = getSharedPreferences("numbers", Context.MODE_PRIVATE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num1 = ed1.getText().toString();
                num2 = ed2.getText().toString();
                num3 = ed3.getText().toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("n1",num1);
                editor.putString("n2",num2);
                editor.putString("n3",num3);
                editor.commit();
                Toast.makeText(Sos.this,"Numbers added",Toast.LENGTH_LONG).show();
            }

        });
    }

}
