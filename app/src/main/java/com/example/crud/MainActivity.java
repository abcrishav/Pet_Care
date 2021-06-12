package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextInputLayout name, contact,type, email;
    FloatingActionButton fb;
    Button sbmt;
    AutoCompleteTextView act;
    ArrayList<String> season;
    ArrayAdapter<String> adap_season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name = (TextInputLayout) findViewById(R.id.nametext);
        contact = (TextInputLayout) findViewById(R.id.contacttext);
        email = (TextInputLayout) findViewById(R.id.emailtext);
        type = (TextInputLayout) findViewById(R.id.menu);
        fb = (FloatingActionButton) findViewById(R.id.fbtn);
        sbmt = (Button) findViewById(R.id.sbmt_add);
        act = (AutoCompleteTextView) findViewById(R.id.dropdown);

        season = new ArrayList<>();
        season.add("Dog");
        season.add("Cat");

        adap_season = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_menu_popup_item,season);
        act.setAdapter(adap_season);

        act.setThreshold(1);




        sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                processinsert(name.getEditText().getText().toString(),act.getText().toString(),contact.getEditText().getText().toString(),email.getEditText().getText().toString());
                //act.clearListSelection();
            }
        });


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),fetchdata.class));
            }
        });


    }


    private void processinsert(String n, String t,String c, String e)
    {
        String res=new dbmanager(this).addrecord(n,t,c,e);
        name.getEditText().setText("");
        //type.getEditText().setText("");
        //act.getAdapter();
        contact.getEditText().setText("");
        email.getEditText().setText("");
        Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
    }
}