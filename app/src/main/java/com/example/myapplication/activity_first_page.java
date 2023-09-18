package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class activity_first_page extends AppCompatActivity {
    private RadioButton msw,plastic,electronic,chemical;
    private RadioGroup RG;
    private RadioButton RB;
    private Button nxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        msw=findViewById(R.id.radioButton1);
        plastic=findViewById(R.id.radioButton2);
        electronic=findViewById(R.id.radioButton3);
        chemical=findViewById(R.id.radioButton4);
        RG.findViewById(R.id.radio_group);
       //nxt=findViewById(R.id.nextButton);

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int yes=RG.getCheckedRadioButtonId();
                RB.findViewById(yes);
                String val=RB.getText().toString();
                Intent intent=new Intent(getApplicationContext(), activity_second_page.class);
                intent.putExtra("type",val);
                startActivity(intent);


            }
        });
    }
}