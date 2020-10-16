package com.example.chatbot_stiki.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatbot_stiki.Api.UserService;
import com.example.chatbot_stiki.R;
import com.example.chatbot_stiki.Server.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Setelan extends AppCompatActivity {

    CardView help, about;
    CircleImageView photo;
    TextView TvNama, TvEmail;
    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setelan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        photo = findViewById(R.id.profile_image);
        TvNama = findViewById(R.id.nama);
        TvEmail = findViewById(R.id.email);
        btnEdit = findViewById(R.id.edit);
        help = (CardView) findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setelan.this, Help.class);
                startActivity(intent);
            }
        });

        about = findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setelan.this, About.class));
            }
        });


        getData();
    }

    private void getData() {
        String iduser = getIntent().getStringExtra("id");
        System.out.println(iduser);
        UserService userService = Server.getUserService();
        userService.getMahasiswa(iduser).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
                    JSONObject object = new JSONObject(json);
                    JSONArray result = object.getJSONArray("result");
                    for (int i = 0; i < result.length(); i++) {
                        final JSONObject mhs = result.getJSONObject(i);
                        Picasso.get().load(mhs.getString("image_url")).into(photo);
                        TvNama.setText(mhs.getString("first_name") + mhs.getString("last_name"));
                        TvEmail.setText(mhs.getString("user_email"));
                        btnEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    String iduser = getIntent().getStringExtra("id");
                                    Intent intent = new Intent(Setelan.this, EditProfile.class);
                                    intent.putExtra("first_name", mhs.getString("first_name"));
                                    intent.putExtra("last_name", mhs.getString("last_name"));
                                    intent.putExtra("user_email", mhs.getString("user_email"));
                                    intent.putExtra("image_url", mhs.getString("image_url"));
                                    intent.putExtra("id", iduser);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}
