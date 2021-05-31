package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class fetchdata extends AppCompatActivity implements ClickInterface{

    public static String msg = "move from here";;
    RecyclerView recyclerView;
    ArrayList<model> dataholder;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchdata);

        recyclerView=(RecyclerView)findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor=new dbmanager(this).readalldata();
        dataholder=new ArrayList<>();

        while(cursor.moveToNext())
        {
            model obj=new model(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            dataholder.add(obj);
        }

        myadapter adapter=new myadapter(dataholder,this);
        recyclerView.setAdapter(adapter);

    }



    @Override
    public void onItemClick(int position) {
        String t = dataholder.get(position).getName();
        Toast.makeText(getApplicationContext(),t, Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(fetchdata.this, second.class);
        Intent intent = new Intent(fetchdata.this, second.class);
        intent.putExtra("pict",t);
        startActivity(intent);

    }

    /*@Override
    public void onLongItemClick(int position) {
        dataholder.remove(position);
        //.notifyItemRemoved(position);

    }*/
}