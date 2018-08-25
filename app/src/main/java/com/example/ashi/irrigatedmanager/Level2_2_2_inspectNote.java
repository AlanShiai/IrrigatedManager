package com.example.ashi.irrigatedmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


public class Level2_2_2_inspectNote extends AppCompatActivity {

    private LinearLayout query_layout;

    private String[] items = new String[] {
            "全部",
            "渠首",
            "闸门",
            "桥梁",
            "渡槽",
            "涵洞",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_2_2_inspect_note);

        query_layout = (LinearLayout) findViewById(R.id.query_layout);

        Spinner spinner = (Spinner) findViewById(R.id.level_2_2_2_spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,items);
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array,
//                R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Level2_2_2_inspectNote.this, items[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button leve1_2_2_back = (Button) findViewById(R.id.leve1_2_2_2_back);
        leve1_2_2_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_2_inspectNote.this, Level2_2_projectInspection2.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnDatePickerDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker=new DatePickerDialog(Level2_2_2_inspectNote.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        Toast.makeText(Level2_2_2_inspectNote.this, year+"year "+(monthOfYear+1)+"month "+dayOfMonth+"day", Toast.LENGTH_SHORT).show();
                    }
                }, 2018, 7, 15);
                datePicker.show();
            }
        });

        findViewById(R.id.query_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != query_layout) {
                    query_layout.setVisibility(View.GONE);
                }
            }
        });
    }
}
