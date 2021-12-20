package com.i170014.i170014_i170161_a4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class newFriendAdapter extends RecyclerView.Adapter<newFriendAdapter.MyViewHolder>{
    List<Group> lX;
    Context c;
    userData currentUser;

    public newFriendAdapter(Context c,List<Group> lX,userData cX) {
        this.lX = lX;
        this.c = c;
        this.currentUser=cX;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(c).inflate(R.layout.show_friends_7_rv,parent,false);
        //Initializes each row for data?
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.groupName.setText(lX.get(position).getGroupName());
        holder.contactPictureRV7.setImageResource(R.drawable.stock_image);
        holder.recyclerViewRowFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c,
                        "Goto group",
                        Toast.LENGTH_LONG).show();
                gotoGroupMessage(lX.get(position).getGroupId(),lX.get(position).GroupName);
            }
        });
        holder.recyclerViewRowFriends.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(c,
                        "Get In Group",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private void gotoGroupMessage(String groupId, String groupName) {
        Intent inX=new Intent(c,groupChatMessage.class);
        inX.putExtra("SenderID",currentUser.getId());
        inX.putExtra("SenderEmail",currentUser.getEmail());
        inX.putExtra("GroupID",groupId);
        inX.putExtra("GroupName",groupName);
        c.startActivity(inX);

    }

    @Override
    public int getItemCount() {
        return this.lX.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView groupName;
        CircleImageView contactPictureRV7;
        LinearLayout recyclerViewRowFriends;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            groupName=itemView.findViewById(R.id.groupName);
            contactPictureRV7=itemView.findViewById(R.id.contactPictureRV7);
            recyclerViewRowFriends=itemView.findViewById(R.id.recyclerViewRowFriends);
        }
    }
}
