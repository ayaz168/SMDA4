package com.i170014.i170014_i170161_a4;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class callScreen extends AppCompatActivity {
    userData currentUser;
    List<userData> friends;
    CircleImageView currentUserImage7;
    TextView currentUserName7;
    RecyclerView recyclerViewScreen7;
    ImageView chatScreen5;
    RecyclerView recyclerViewScreenCall;
    callScreenAdapter myAdapter;
    ImageView cameraScreen5,contactsScreen5;
    int CAMERA_REQUEST = 1888;
    int MY_CAMERA_PERMISSION_CODE = 100;
    String Email,Pass;
    EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_screen);

        currentUserName7=findViewById(R.id.currentUserName7);
        currentUserImage7=findViewById(R.id.currentUserImage7);
        Bundle extras = getIntent().getExtras();
        Email = extras.getString("Email");
        Pass = extras.getString("Pass");
        getAllUsers();


        searchText=findViewById(R.id.search_text);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Pass changed search text to adapter for filtering search results
                myAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            Uri tempUri = getImageUri(getApplicationContext(), photo);
//            String imagePath = getFilePath2(tempUri);
//            Intent inX=new Intent(callScreen.this,SendImage_Four.class);
//            inX.putExtra("Image",imagePath);
//            inX.putExtra("email",this.value);
//            startActivity(inX);
//        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
        return null;
    }
    private String getFilePath2(Uri selectedImage) {
//        String imagePath;
//        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//        cursor.moveToFirst();
//
//        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//        imagePath = cursor.getString(columnIndex);
//        cursor.close();
//
//        return imagePath;
        return null;

    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
//    {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_CAMERA_PERMISSION_CODE)
//        {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            {
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//            }
//            else
//            {
//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
    public void getAllUsers(){
        Log.d("listData","Here0");
        String URL="http://192.168.18.12/A4/getUsers.php";
        List<userData> lX=new ArrayList<>();
        StringRequest request=new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj= null;
                        try {
                            Log.d("listData","Here01");
                            obj = new JSONObject(response);
                            Log.d("listData","Here02");
                            if(obj.getInt("success")==1){
                                Log.d("listData","Here1");
                                //checks if success flag in response json is true/1
                                JSONArray allUsers=obj.getJSONArray("users");
                                List<userData> lX=new ArrayList<>();
                                for(int i=0;i<allUsers.length();i++){
                                    Log.d("listData",allUsers.getJSONObject(i).getString("Email"));
                                    lX.add(new userData(
                                            allUsers.getJSONObject(i).getString("ID"),
                                            allUsers.getJSONObject(i).getString("Email"),
                                            allUsers.getJSONObject(i).getString("Pass"),
                                            allUsers.getJSONObject(i).getString("FirstName"),
                                            allUsers.getJSONObject(i).getString("LastName"),
                                            allUsers.getJSONObject(i).getString("Gender"),
                                            allUsers.getJSONObject(i).getString("Bio"),
                                            allUsers.getJSONObject(i).getString("Status"),
                                            allUsers.getJSONObject(i).getString("Phone"),
                                            allUsers.getJSONObject(i).getString("dp")

                                    ));
                                }
                                currentUser=getCurrentUser(Email,lX);
                                Log.d("CurrentUserX",currentUser.getFirstName());
                                Log.d("CurrentUserX", String.valueOf(lX.size()));

                                currentUserImage7.setImageBitmap(currentUser.getImage());
                                currentUserName7=findViewById(R.id.currentUserName7);
                                currentUserName7.setText(currentUser.getFirstName()+" "+currentUser.getLastName());
                                recyclerViewScreen7=findViewById(R.id.recyclerViewScreenCall);
                                RecyclerView.LayoutManager myManager=new LinearLayoutManager(callScreen.this);
                                recyclerViewScreen7.setLayoutManager(myManager);
                                myAdapter=new callScreenAdapter(callScreen.this,lX,currentUser);
                                recyclerViewScreen7.setAdapter(myAdapter);
                                chatScreen5=findViewById(R.id.chatScreen5);
                                chatScreen5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent inX=new Intent(callScreen.this, Home_Five.class);
                                        inX.putExtra("UserId",currentUser.getEmail());
                                        startActivity(inX);
                                    }
                                });
                                cameraScreen5=findViewById(R.id.cameraScreen5);
                                cameraScreen5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                                        } else {
                                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                        }
                                    }
                                });
                                contactsScreen5=findViewById(R.id.contactsScreen5);
                                contactsScreen5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent inX=new Intent(callScreen.this, ContactScreen_Seven.class);
                                        inX.putExtra("Email",currentUser.getEmail());
                                        inX.putExtra("Pass",currentUser.getPass());
                                        startActivity(inX);
                                    }
                                });
                            }else{
                                Log.d("Lg","User Load Error");
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Lg",error.toString());
                    }
                }
        );
        Volley.newRequestQueue(callScreen.this).add(request);
    }
    userData getCurrentUser(String Email,List<userData> usX){
        for(userData uS: usX){
            if(uS.getEmail().equals(Email)){
                return uS;
            }
        }
        return null;


    }
}