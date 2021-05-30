package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class second extends AppCompatActivity {
    TextView title,booked;
    Button schedule;
    FloatingActionButton fbtn2;

    public static String Msg = "move to calender";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        title = findViewById(R.id.copied);
        Intent intent = getIntent();
        String message = intent.getStringExtra(fetchdata.msg);
        title.setText(message);

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(second.this, book_activity.class);
                intent.putExtra(Msg,message);
                startActivity(intent);
            }
        });

        fbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),pic.class));
            }
        });

    }
}