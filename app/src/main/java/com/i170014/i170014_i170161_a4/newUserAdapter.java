package com.i170014.i170014_i170161_a4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class newUserAdapter extends RecyclerView.Adapter<newUserAdapter.MyViewHolder>{
    List<userData> lX;
    Context c;
    String Email,Pass;
    userData currentUser;


    public newUserAdapter(Home_Five c, List<userData> allusers, String email, String pass) {
        this.lX = allusers;
        this.c = c;
        this.Email=email;
        this.Pass=pass;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(c).inflate(R.layout.message_row_screen_five_rv,parent,false);
        //Initializes each row for data?
        return new MyViewHolder(item);
    }
    void start(String reX, String FullName){
        Intent inGX=new Intent(c,chatMessage.class);
        inGX.putExtra("SenderEmail",Email);
        inGX.putExtra("SenderID",currentUser.getId());
        inGX.putExtra("RecieverName",FullName);
        inGX.putExtra("RecieverID", reX);
        c.startActivity(inGX);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(lX.get(position).getEmail().equals(Email)){
            this.currentUser=lX.get(position);
        }
        else{
            Log.d("Binder", String.valueOf(position));
            holder.contactNameRV5.setText(lX.get(position).getFirstName());
            holder.contactMessageRV5.setText(lX.get(position).getBio());
            Date currentTime = Calendar.getInstance().getTime();
            int hour=currentTime.getHours();
            int min = currentTime.getMinutes();
            holder.contactTimeRV5.setText(String.valueOf(hour)+":"+String.valueOf(min));
            if(lX.get(position).getStatus().equals("1")){
                holder.contactStatusRV5.setImageResource(R.drawable.circle_button);
            }
            holder.contactPictureRV5.setImageBitmap(lX.get(position).getImage());
            holder.clickMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String fullname=lX.get(position).getFirstName()+" "+lX.get(position).getLastName();
                    String recV=lX.get(holder.getAdapterPosition()).getId();
                    start(recV,fullname);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.lX.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout clickMe;
        TextView contactNameRV5,contactMessageRV5,contactTimeRV5;
        CircleImageView contactPictureRV5;
        ImageView contactStatusRV5;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            clickMe=itemView.findViewById(R.id.clickMe);
            contactNameRV5=itemView.findViewById(R.id.contactNameRV5);
            contactMessageRV5=itemView.findViewById(R.id.contactMessageRV5);
            contactTimeRV5=itemView.findViewById(R.id.contactTimeRV5);
            contactPictureRV5=itemView.findViewById(R.id.contactPictureRV5);
            contactStatusRV5=itemView.findViewById(R.id.contactStatusRV5);
        }
    }
}
