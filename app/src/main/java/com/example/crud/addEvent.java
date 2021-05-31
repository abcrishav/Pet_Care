package com.example.crud;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.crud.CalendarProviderDatabase.addEventToCalendarProvider;
import static com.example.crud.CalendarProviderDatabase.deletfromCalendarProvider;
import static com.example.crud.CalendarProviderDatabase.updateEventInCalendarProvider;

public class addEvent extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 1;
    public static final String FROM_ADD_TO_MAIN = "return from add";
    static final int RESULT_CODE_FROM_ADD_TO_MAIN = 4;
    //Spinner petNamesSpinner;
    Spinner vaccineNamesSpinner;
    TextView selectDate,puppy;
    CheckBox VaccineDoneCHKBOX;
    Button addBtn;
    Button deleteBtn;


    //String pet;
    String vaccine;
    String date;
    int eventId;

    DataBaseHelper dbHandler;
    Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        intent =getIntent();


        //initializing the views
        VaccineDoneCHKBOX=(CheckBox) findViewById(R.id.done);
        deleteBtn=findViewById(R.id.delete);
        addBtn=findViewById(R.id.add);
        //petNamesSpinner=(Spinner) findViewById(R.id.petname);
        puppy = findViewById(R.id.select);
        selectDate =(TextView) findViewById(R.id.editTextDate);
        vaccineNamesSpinner=(Spinner) findViewById(R.id.vaccination);
        dbHandler= new DataBaseHelper(addEvent.this);

        //Bundle bundle=getIntent().getExtras();
        //String Link1 =bundle.getString("Link1");
        String puppy_select =intent.getStringExtra(book_activity.jump);
        //String puppy_select = bundle.getString(book_activity.jump);
        puppy.setText(puppy_select);

        //setting data based on editing or adding
        eventId=intent.getIntExtra(CustomListAdapter.DISPLAY_DATA,-1);




        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventId==-1) {
                    VaccinationDataItem item=addEventToCalendarProvider(date, vaccine, puppy_select, VaccineDoneCHKBOX.isChecked(), addEvent.this);
                    dbHandler.addVaccinationData(item);
                }
                else updateEvent();
                Intent intent =new Intent(addEvent.this,book_activity.class);
                intent.putExtra(FROM_ADD_TO_MAIN,date);
                setResult(RESULT_CODE_FROM_ADD_TO_MAIN,intent);
                finish();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.deleteContact(eventId);
                deletfromCalendarProvider(eventId,addEvent.this);
                Intent intent =new Intent(addEvent.this,book_activity.class);
                intent.putExtra(FROM_ADD_TO_MAIN,date);
                setResult(RESULT_CODE_FROM_ADD_TO_MAIN,intent);
                finish();
            }

        });

        AdapterView.OnItemSelectedListener vacccinationClickListner = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vaccine = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };


 /*       AdapterView.OnItemSelectedListener petSpinnerClickListner = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //pet=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };*/
        //petNamesSpinner.setOnItemSelectedListener(petSpinnerClickListner);
        vaccineNamesSpinner.setOnItemSelectedListener(vacccinationClickListner);
        //loadPetData();
        loadVaccineData();
        setData();
        mRequestPermisions();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void mRequestPermisions() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_CALENDAR) ==
                PackageManager.PERMISSION_GRANTED) {
        } else {
            requestPermissions(
                    new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                    REQUEST_PERMISSION_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Please restart app and click allow", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }



    private void setData() {
        if(eventId==-1){
            deleteBtn.setVisibility(View.GONE);
            date=intent.getStringExtra(book_activity.ADD_DATE);
            selectDate.setText(date);
        }else
        {
            VaccinationDataItem item=dbHandler.getVaccineData(eventId);
            date=item.getVaccinationDate();
            selectDate.setText(item.getVaccinationDate());
            //petNamesSpinner.setSelection(DummyDataBaseLogics.getPetNames().indexOf(item.getPetName()));
            vaccineNamesSpinner.setSelection(DummyDatabaseLogics.getVaccineData().indexOf(item.getVaccineName()));
            VaccineDoneCHKBOX.setChecked(item.getVaccinationDone()==0);
            addBtn.setText("save");
        }
    }
    private void updateEvent() {
        dbHandler.updateEvent(new VaccinationDataItem(eventId,puppy.getText().toString(),date,vaccine,VaccineDoneCHKBOX.isChecked()?0:1));
        updateEventInCalendarProvider(new VaccinationDataItem(eventId,puppy.getText().toString(),date,vaccine,VaccineDoneCHKBOX.isChecked()?0:1),addEvent.this );
    }



    private void loadVaccineData() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.dropdown_menu_popup_item, DummyDatabaseLogics.getVaccineData());
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vaccineNamesSpinner.setAdapter(dataAdapter);
    }
    /*private void loadPetData() {
        List<String> lables = DummyDataBaseLogics.getPetNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.dropdown_menu_popup_item, lables);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petNamesSpinner.setAdapter(dataAdapter);
    }*/
}
