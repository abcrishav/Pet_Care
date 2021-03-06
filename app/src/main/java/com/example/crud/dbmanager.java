package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dbmanager extends SQLiteOpenHelper
{
    private static final String dbname="dbcontact";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_VACCINATION_DATA="vaccination_table";
    public static final String EVENT_ID="event_id";
    public static final String PET_NAME ="petName";
    public static final String VACCINATION_DATE ="vaccinationdate";
    public static final String VACCINE_NAME ="vaccinename";
    public static final String VACCINATION_DONE ="vaccinenationDone";

    public dbmanager(@Nullable Context context)
    {
        super(context, dbname, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String qry="create table tbl_contact ( id integer primary key autoincrement, name text, type text, contact text, email text)";
        sqLiteDatabase.execSQL(qry);
        //new table
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_VACCINATION_DATA+ "("
                + EVENT_ID + " INTEGER PRIMARY KEY," + PET_NAME + " TEXT,"
                + VACCINATION_DATE+ " TEXT," + VACCINE_NAME+" TEXT,"+VACCINATION_DONE+" INTEGER"+");";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        String qry="DROP TABLE IF EXISTS tbl_contact";
        sqLiteDatabase.execSQL(qry);
        //onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINATION_DATA);
        onCreate(sqLiteDatabase);
    }

    public  String addrecord(String name, String type, String contact, String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("type", type);
        cv.put("contact",contact);
        cv.put("email",email);
        float res=db.insert("tbl_contact",null,cv);

        if(res==-1)
            return "Failed";
        else
            return  "Successfully inserted";

    }

    public Cursor readalldata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String qry="select * from tbl_contact order by id desc";
        Cursor cursor=db.rawQuery(qry,null);
        return  cursor;
    }


    public void deleteContact(Long eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VACCINATION_DATA, EVENT_ID + " = ?",
                new String[] { String.valueOf(eventId) });
        db.close();
    }
    public void updateEvent(VaccinationDataItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getcv(item);

        // updating row
        db.update(TABLE_VACCINATION_DATA, values, EVENT_ID + " = ?",
                new String[] { String.valueOf(item.getEventId()) });
    }
    public List<VaccinationDataItem> getAllContacts(String date) {
        List<VaccinationDataItem> vaccinationList = new ArrayList<VaccinationDataItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VACCINATION_DATA;

        SQLiteDatabase db = this.getWritableDatabase();
        String[] str={EVENT_ID,PET_NAME,VACCINATION_DATE,VACCINE_NAME,VACCINATION_DONE};
        Cursor cursor = db.query(TABLE_VACCINATION_DATA,str,VACCINATION_DATE+"= ?",new String[]{date},null,null,null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VaccinationDataItem vaccinationDataItem = new VaccinationDataItem( cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4) );

                // Adding contact to list
                vaccinationList.add(vaccinationDataItem);
            } while (cursor.moveToNext());
        }

        // return contact list
        return vaccinationList;
    }
    public void addVaccinationData(VaccinationDataItem vaccinationDataItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getcv(vaccinationDataItem);
        db.insert(TABLE_VACCINATION_DATA, null, values);
        db.close();
    }
    private ContentValues getcv(VaccinationDataItem vaccinationDataItem) {
        ContentValues values = new ContentValues();
        values.put(EVENT_ID, vaccinationDataItem.getEventId());
        values.put(PET_NAME, vaccinationDataItem.getPetName());
        values.put(VACCINATION_DATE, vaccinationDataItem.getVaccinationDate());
        values.put(VACCINE_NAME, vaccinationDataItem.getVaccineName());
        values.put(VACCINATION_DONE, vaccinationDataItem.getVaccinationDone());
        return values;
    }
    public void deleteContact(int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VACCINATION_DATA,EVENT_ID+" = ?",new String[]{String.valueOf(eventId)});
        db.close();
    }

    public VaccinationDataItem getVaccineData(int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr=db.query(TABLE_VACCINATION_DATA,new String[]{EVENT_ID,PET_NAME,VACCINATION_DATE,VACCINE_NAME,VACCINATION_DONE},EVENT_ID+" = ?",new String[]{String.valueOf(eventId)},null,null,null);
        if (cr != null)
            cr.moveToFirst();
        return new VaccinationDataItem(cr.getInt(0),cr.getString(1),cr.getString(2),cr.getString(3),cr.getInt(4));
    }
}