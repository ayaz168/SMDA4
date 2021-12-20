package com.i170014.i170014_i170161_a4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CallingScreen extends AppCompatActivity {
    String imagePath;
    CircleImageView personPic;
    ImageView goBack;
    String image,name;
    TextView contactName;
    String recieverEmail,myEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling_screen);
        goBack=findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Bundle extras = getIntent().getExtras();
        image = extras.getString("image");
        name = extras.getString("name");
        recieverEmail = extras.getString("email");
        myEmail = extras.getString("myemail");

        contactName=findViewById(R.id.contactName);
        Log.d("name",name);
        contactName.setText(name);
        Bitmap bitmap = BitmapFactory.decodeFile(image);
        personPic=findViewById(R.id.contactPictureRV8);
        personPic.setImageBitmap(bitmap);

        makeCall(recieverEmail,myEmail);


    }

    private void makeCall(String recieverEmail,String callerEmail) {
        //Write Calling code here
    }
    private void makeVideoCall(String recieverEmail,String callerEmail) {
        //Write Video Calling code here
    }
    private void makeGroupCall(String recieverEmail,String callerEmail) {
        //Write Calling code here
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"method\": \"conferenceCallout\",\n  \"conferenceCallout\": {\n    \"cli\": \"PRIVATE\",\n    \"destination\": {\n      \"type\": \"number\",\n      \"endpoint\": \"+923329143592\"\n    },\n    \"locale\": \"en-US\",\n    \"greeting\": \"\",\n    \"conferenceId\": \"3mmrwbfw4sf\"\n  }\n}");

        Request request = new Request.Builder()
                .url("https://calling.api.sinch.com/calling/v1/callouts")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Basic ZDFkMDdjODUtMjU3OS00NjhhLWFmNzQtNzc2MmMyNTQ0MmQ5OlZZQ1dFbnZxaVVlemt2ZlgxWEVxRWc9PQ==")
                .method("POST", body)
                .build();

        //Response response = client.newCall(request).execute();
    }
    private void makeGroupVideoCall(String recieverEmail,String callerEmail) {
        //Write Video Calling code here
    }
}