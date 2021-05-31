package com.example.crud;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>{
    ArrayList<model> dataholder;
    private ClickInterface recyclerViewClickInterface;

    public myadapter(ArrayList<model> dataholder, ClickInterface recyclerViewClickInterface) {
        this.dataholder = dataholder;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.dimage.setImageResource(R.drawable.dp_dog);

        holder.dname.setText(dataholder.get(position).getName());
        holder.dtype.setText(dataholder.get(position).getType());
        holder.dcontact.setText(dataholder.get(position).getContact());
        holder.demail.setText(dataholder.get(position).getEmail());


    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView dname, dtype, dcontact, demail;
        ImageView dimage;
        //OnNoteListener onNoteListener;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            dname = (TextView) itemView.findViewById(R.id.first);
            dtype = (TextView) itemView.findViewById(R.id.second);
            dcontact = (TextView) itemView.findViewById(R.id.third);
            demail =  (TextView) itemView.findViewById(R.id.fourth);
            dimage = (ImageView) itemView.findViewById(R.id.avtar);

            //this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });


            /*itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerViewClickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });*/


        }
    }
}