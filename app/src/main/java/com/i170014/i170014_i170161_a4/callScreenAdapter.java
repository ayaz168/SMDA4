package com.i170014.i170014_i170161_a4;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class callScreenAdapter extends RecyclerView.Adapter<callScreenAdapter.MyViewHolder> implements Filterable {
    List<userData> lX;
    Context c;
    userData currentUser;
    List<userData> displayedlX;

    public callScreenAdapter(Context c,List<userData> lX,userData usX) {
        this.lX = lX;
        this.c = c;
        this.currentUser=usX;
        this.displayedlX=lX;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(c).inflate(R.layout.call_row,parent,false);
        //Initializes each row for data?
        return new MyViewHolder(item);
    }
    void start(String reX){
        Intent inGX=new Intent(c,chatMessage.class);
        inGX.putExtra("UserId",currentUser.getEmail());
        inGX.putExtra("Sender",currentUser.getId());
        inGX.putExtra("Reciever", reX);
        c.startActivity(inGX);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.contactNameRV7.setText(lX.get(position).getFirstName()+" "+lX.get(position).getLastName());
        holder.contactNumberRV7.setText(lX.get(position).getPhone());
        holder.contactPictureRV7.setImageBitmap(lX.get(position).getImage());
        holder.recyclerViewRowFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c,
                        "Call Placed to : "+ lX.get(holder.getAdapterPosition()).getFirstName(),
                        Toast.LENGTH_LONG).show();
                placeCall(lX.get(holder.getAdapterPosition()).getEmail(),lX.get(holder.getAdapterPosition()).getFirstName()+" "+lX.get(holder.getAdapterPosition()).getLastName(),lX.get(position).getImage());
            }
        });
    }

    private void placeCall(String email,String name,Bitmap image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(c.getContentResolver(), image, "Title", null);
        Intent inX=new Intent(c, CallingScreen.class);
        inX.putExtra("name",name);
        inX.putExtra("myemail",currentUser.getEmail());
        inX.putExtra("email",email);
        inX.putExtra("image",Uri.parse(path));
        c.startActivity(inX);
    }

    @Override
    public int getItemCount() {
        return this.lX.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<userData> FilteredArrList = new ArrayList<userData>();

                if (lX == null) {
                    lX = new ArrayList<userData>(displayedlX); // saves the original data in mOriginalValues
                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = lX.size();
                    results.values = lX;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < lX.size(); i++) {
                        String data = lX.get(i).getFirstName();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(new userData(lX.get(i).getId(), lX.get(i).getEmail(), lX.get(i).getPass(), lX.get(i).getFirstName(), lX.get(i).getLastName(), lX.get(i).getGender(), lX.get(i).getBio(), lX.get(i).getStatus(), lX.get(i).getPhone(), lX.get(i).getImage()));
                        }
                        data = lX.get(i).getLastName();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(new userData(lX.get(i).getId(), lX.get(i).getEmail(), lX.get(i).getPass(), lX.get(i).getFirstName(), lX.get(i).getLastName(), lX.get(i).getGender(), lX.get(i).getBio(), lX.get(i).getStatus(), lX.get(i).getPhone(), lX.get(i).getImage()));
                        }
                        data = lX.get(i).getPhone();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(new userData(lX.get(i).getId(), lX.get(i).getEmail(), lX.get(i).getPass(), lX.get(i).getFirstName(), lX.get(i).getLastName(), lX.get(i).getGender(), lX.get(i).getBio(), lX.get(i).getStatus(), lX.get(i).getPhone(), lX.get(i).getImage()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                displayedlX=(ArrayList<userData>)results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout recyclerViewRowFriends;
        TextView contactNumberRV7,contactNameRV7;
        CircleImageView contactPictureRV7;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewRowFriends=itemView.findViewById(R.id.recyclerViewRowFriends);
            contactNumberRV7=itemView.findViewById(R.id.contactNumberRV7);
            contactNameRV7=itemView.findViewById(R.id.contactNameRV7);
            contactPictureRV7=itemView.findViewById(R.id.contactPictureRV7);
        }
    }

}
