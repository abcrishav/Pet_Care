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

    public static String Mesg = "move to calender";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        title = (TextView)findViewById(R.id.copied);
        schedule = (Button)findViewById(R.id.button3);
        fbtn2 = (FloatingActionButton)findViewById(R.id.fbtn2);
        Intent intent = getIntent();
        String message = intent.getStringExtra("pict");
        title.setText(message);

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(second.this, book_activity.class);
                intent.putExtra(Mesg,message);
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
