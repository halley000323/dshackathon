package com.example.walkdonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DSLoginActivity extends AppCompatActivity {

    private Spinner spinner;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dslogin);

        TextView textView = (TextView) findViewById(R.id. text1);


        arrayList = new ArrayList<>();
        arrayList.add("학과");
        arrayList.add("IT미디어공학전공");
        arrayList.add("사이버보안전공");
        arrayList.add("소프트웨어전공");
        arrayList.add("컴퓨터보안전공");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList);

        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ///Toast.makeText(getApplicationContext(),arrayList.get(i)+"가 선택되었습니다.",
                //        Toast.LENGTH_SHORT).show();
                textView.setText(arrayList.get(i));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Button start_btn = (Button) findViewById(R.id.start_login);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Duksungs님! 어서오세요!\n오늘도 힘차게 걸어봐요!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}