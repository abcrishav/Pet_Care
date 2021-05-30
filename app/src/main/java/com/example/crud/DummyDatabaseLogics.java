package com.example.crud;

import java.util.ArrayList;
import java.util.List;

public class DummyDatabaseLogics {
    public static String selectedVaccine;
    public static String selectedPetName;
    /*public static List<String> getPetNames(){
        List<String> list=new ArrayList<>();
        list.add("puppu");
        list.add("kittu");
        return list;
    }
    */


    public static List<String> getVaccineData() {
        List<String> arrayList=new ArrayList<>();
        for(int i=1;i<10;i++ ){
            arrayList.add("Vaccine "+i);
        }
        return arrayList;
    }
}
