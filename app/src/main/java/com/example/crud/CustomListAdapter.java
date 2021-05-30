package com.example.crud;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.MyViewHolder> {
    public static final String DISPLAY_DATA="display data";
    private List<VaccinationDataItem> localData;
    public static final int RETURN_FROM_ADD=3;
    private Context mcontext;
    @NonNull
    @Override
    public CustomListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new CustomListAdapter.MyViewHolder(view,mcontext) ;
    }

    public CustomListAdapter(List<VaccinationDataItem> vaccinationDataItemList,Context context) {
        localData=vaccinationDataItemList;
        mcontext=context;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomListAdapter.MyViewHolder holder, int position) {
        holder.setDate(localData.get(position).getVaccinationDate());
        holder.setPetName(localData.get(position).getPetName());
        holder.setVaccinationDone(localData.get(position).getVaccinationDone());
        holder.setVaccineName(localData.get(position).getVaccineName());
        holder.setEventId(localData.get(position).getEventId());
        holder.setClickListner();
    }

    @Override
    public int getItemCount() {
        return localData.size();
    }

    public void setVaccinationData(List<VaccinationDataItem> allContacts) {
        localData=allContacts;
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{
        private TextView date;
        private int eventId;
        private TextView vaccineName;
        private TextView petName;
        private CheckBox vaccinationDone;
        public void setEventId(int eventId) {
            this.eventId = eventId;
        }
        public void setDate(String date){
            this.date.setText(date);
        }
        public void setVaccineName(String vaccineName) {
            this.vaccineName.setText( vaccineName);
        }

        public void setPetName(String petName) {
            this.petName.setText(petName);
        }

        public void setVaccinationDone(int vaccinationDone) {
            this.vaccinationDone.setChecked(vaccinationDone == 0);

        }

        public MyViewHolder(@NonNull View itemView, Context mcontext) {
            super(itemView);
            date=(TextView) itemView.findViewById(R.id.date);
            vaccinationDone=(CheckBox) itemView.findViewById(R.id.checkBox);
            vaccineName=(TextView) itemView.findViewById(R.id.vaccine_name);
            petName=(TextView)itemView.findViewById(R.id.pet_name);

        }
        public void setClickListner(){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(itemView.getContext(),addEvent.class);
                    intent.putExtra(DISPLAY_DATA,eventId);

                    ((AppCompatActivity)date.getContext()).startActivityForResult(intent,RETURN_FROM_ADD);
                }
            });
        }
    }
}
