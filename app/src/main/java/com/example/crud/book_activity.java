package com.example.crud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class book_activity extends AppCompatActivity {
    public static final String ADD_DATE="add_intent";
    private static final int ADD_INTENT =1;
    private static final String DATE_ON_ROTATION ="date_on_rotation" ;
    private static final int RETURN_FROM_ADD =  3;
    private static final int RESULT_CODE_FROM_ADD_TO_MAIN = 4;
    private static final String FROM_ADD_TO_MAIN = "return from add";

    RecyclerView recyclerView;
    FloatingActionButton addEventFAB;
    CustomListAdapter adapter;
    DataBaseHelper db;
    SimpleDateFormat formatter;
    String date;
    Calendar calendar;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Intent intent2 = getIntent();
        String puppy = intent2.getStringExtra(second.Mesg);

        formatter=new SimpleDateFormat("dd/MM/yyyy");
        db=new DataBaseHelper(book_activity.this);


        // initializations
        recyclerView =findViewById(R.id.recycler_view);
        addEventFAB=(FloatingActionButton) findViewById(R.id.add_event_fab);
        calendarView=(CalendarView) findViewById(R.id.calendarView);
        Intent intent =getIntent();
        calendar = Calendar.getInstance();
        Date date_obj = calendar.getTime();
        date = formatter.format(date_obj);
        calendarView.setDate(date_obj.getTime());
        if(savedInstanceState!=null){
            date=savedInstanceState.getString(DATE_ON_ROTATION);
            try {
                calendar.setTime(formatter.parse(date));
                calendarView.setDate(calendar.getTime().getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        //recyclerview setup
        adapter = new CustomListAdapter(getList(formatter.format(calendar.getTime())),book_activity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(book_activity.this));
        recyclerView.setAdapter(adapter);



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                date=formatter.format(calendar.getTime());
                Toast.makeText(book_activity.this,dayOfMonth+"-"+(month+1)+"-"+year,Toast.LENGTH_SHORT).show();
                adapter.setVaccinationData(db.getAllContacts(date));
                adapter.notifyDataSetChanged();
            }
        });


        addEventFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(book_activity.this, addEvent.class);
                intent.putExtra(ADD_DATE,date);
                //Intent requestLink = new Intent(.this, Results.class);
                //requestLink.putExtra("Link1", sendLink1);
                //intent.putExtra("Link2", puppy);
                startActivity(intent);
                //startActivityForResult(intent,3);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(DATE_ON_ROTATION,date);
        super.onSaveInstanceState(outState);
    }

    private List<VaccinationDataItem> getList(String date) {
        return db.getAllContacts(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RETURN_FROM_ADD && resultCode==RESULT_CODE_FROM_ADD_TO_MAIN) {
            date = data.getStringExtra(FROM_ADD_TO_MAIN);
            try {
                Date date_obj = formatter.parse(date);
                calendar.setTime(date_obj);
                calendarView.setDate(date_obj.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            adapter.setVaccinationData(db.getAllContacts(date));
            adapter.notifyDataSetChanged();

        }
    }


}