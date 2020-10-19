package com.example.chatbot_stiki.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatbot_stiki.Api.UserService;
import com.example.chatbot_stiki.R;
import com.example.chatbot_stiki.Server.Server;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
    CircleImageView photo;
    EditText namaDepan, namaBelakang, email;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap;
    Button simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        photo = findViewById(R.id.profile_image);
        namaDepan = findViewById(R.id.nama_depan);
        namaBelakang = findViewById(R.id.nama_belakang);
        email = findViewById(R.id.email);
        simpan = findViewById(R.id.btn_submit);
        String photo_profile = getIntent().getStringExtra("image_url");
        String nama_depan = getIntent().getStringExtra("first_name");
        String nama_belakang = getIntent().getStringExtra("last_name");
        String user_email = getIntent().getStringExtra("user_email");
        String id = getIntent().getStringExtra("id");
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bmp, Picasso.LoadedFrom from) {
                photo.setImageBitmap(bmp);
                bitmap = bmp;
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }


            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        if (photo_profile.equalsIgnoreCase("")) {

        } else {
            Picasso.get().load(photo_profile).into(target);
        }
        System.out.println(target);

        namaDepan.setText(nama_depan);
        namaBelakang.setText(nama_belakang);
        email.setText(user_email);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(EditProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, IMAGE_REQUEST_CODE);
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

    }

    private void update() {
        final String id = getIntent().getStringExtra("id");
        String nama_depan = namaDepan.getText().toString();
        String nama_belakang = namaBelakang.getText().toString();
        String user_email = email.getText().toString();
        String imgdata = imgToString(bitmap);
        UserService userService = Server.getUserService();
        userService.updateProfile(id, nama_depan, nama_belakang, user_email, imgdata).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Intent intent = new Intent(EditProfile.this, MainActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    //permission untuk membuka file manager di hp dan mengambil image
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == IMAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(new Intent(Intent.ACTION_PICK));
                intent.setType("image/*");

                startActivityForResult(Intent.createChooser(intent, "select image"), IMAGE_REQUEST_CODE);

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                photo.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //mengubah image menjadi string
    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
    }
}