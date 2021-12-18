package com.i170014.i170014_i170161_a4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class showChatAdapter extends RecyclerView.Adapter<showChatAdapter.MyViewHolder> {
    Context c;
    List<messageData> lX;
    userData currentUser;//For screenshot
    Bitmap recImage;
    Bitmap imageP;
    public static final int TYPELEFTTEXT=0;
    public static final int TYPERIGHTTEXT=1;
    public static final int TYPERIGHTIMAGE=2;
    public static final int TYPELEFTIMAGE=3;
    public static boolean showImage=false;
    public static boolean showText=false;
    public static boolean showCall=false;
    public showChatAdapter(Context c, List<messageData> lX, userData currentUser, Bitmap Path) {
        this.c = c;
        this.lX = lX;
        this.currentUser = currentUser;
        this.recImage=Path;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item;
        if(viewType==TYPERIGHTTEXT){
            item= LayoutInflater.from(c).inflate(R.layout.show_chat_row_right,parent,false);
        }
        else if(viewType==TYPERIGHTIMAGE){
            item= LayoutInflater.from(c).inflate(R.layout.show_image_row_right,parent,false);
        }
        else if(viewType==TYPELEFTTEXT) {
            item= LayoutInflater.from(c).inflate(R.layout.show_chat_row,parent,false);
        }
        else{
            item= LayoutInflater.from(c).inflate(R.layout.show_image_row,parent,false);
        }
        return new MyViewHolder(item);
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(!lX.get(holder.getAdapterPosition()).getSender().equals(currentUser.getId())){
            holder.userPictureChat.setImageBitmap(recImage);
        }
        holder.usersTime.setText(lX.get(holder.getAdapterPosition()).getHour()+":"+lX.get(holder.getAdapterPosition()).getMinute());
        if(lX.get(position).getMessageType().equals("ImageMessage")){
            holder.usersPicture.setImageBitmap(lX.get(holder.getAdapterPosition()).getMessageImage());
        }
        else if(lX.get(position).getMessageType().equals("TextMessage")){
            holder.usersMessage.setText(lX.get(holder.getAdapterPosition()).getText());
        }
        holder.userScrenshot.setText("SAFE");
        holder.messageTile1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                checkDelete(holder.getAdapterPosition());
                return false;
            }
        });
        holder.messageTile2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                checkDelete(holder.getAdapterPosition());
                return false;
            }
        });
    }

    public void checkDelete(int adapterPosition) {
        messageData msG=lX.get(adapterPosition);
        int hourM=Integer.parseInt(msG.getHour());
        int minuteM=Integer.parseInt(msG.getMinute());
        Date currentTime = Calendar.getInstance().getTime();
        int hour=currentTime.getHours();
        int min = currentTime.getMinutes();
        Log.d("time",String.valueOf(hourM));
        Log.d("time",String.valueOf(hour));
        Log.d("time",String.valueOf(minuteM));
        Log.d("time",String.valueOf(min));
        if(hourM==hour&&minuteM+5>min){
            Toast.makeText(c,
                    "Message Deleted",
                    Toast.LENGTH_LONG).show();
            this.lX.remove(adapterPosition);
            notifyDataSetChanged();

        }else{
            Toast.makeText(c,
                    "Message can only be deleted in 5 minutes",
                    Toast.LENGTH_LONG).show();
        }
    }

    void updateChat(List<messageData> ld){
        this.lX=ld;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return this.lX.size();
    }
    @Override
    public int getItemViewType(int position) {
        if(lX.get(position).getMessageType().equals("ImageMessage")){//!
            if (! lX.get(position).getSender().equals(currentUser.getId())){
                //imageP=lX.get(position).getImage();
                //Log.d("MSGX",lX.get(position).getImage());
                return TYPELEFTIMAGE;
            }
            else{
                //imageP=lX.get(position).getImage();
                //Log.d("MSGX",lX.get(position).getImage());
                return TYPERIGHTIMAGE;
            }

        }else if(lX.get(position).getMessageType().equals("TextMessage")) {
            if (! lX.get(position).getSender().equals(currentUser.getId())){
                return TYPELEFTTEXT;
            }
            else{
                return TYPERIGHTTEXT;
            }
    }
        return -1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView usersMessage,usersTime,userScrenshot;
        CircleImageView userPictureChat;
        ImageView usersPicture;
        LinearLayout messageTile1,messageTile2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            usersPicture=itemView.findViewById(R.id.usersPicture);
            usersMessage=itemView.findViewById(R.id.usersMessage);
            usersTime=itemView.findViewById(R.id.usersTime);
            userScrenshot=itemView.findViewById(R.id.userScrenshot);
            userPictureChat=itemView.findViewById(R.id.userPictureChat);
            messageTile1=itemView.findViewById(R.id.messageTile1);
            messageTile2=itemView.findViewById(R.id.messageTile2);
        }
    }
}
