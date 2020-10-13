package com.example.chatbot_stiki.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.chatbot_stiki.Api.UserService;
import com.example.chatbot_stiki.R;
import com.example.chatbot_stiki.Server.Server;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setelan extends AppCompatActivity {

    CardView help, about;
    CircleImageView photo;
    TextView TvNama, TvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setelan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        photo = findViewById(R.id.profile_image);
        TvNama = findViewById(R.id.nama);
        TvEmail = findViewById(R.id.email);

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

    private void getData(){
        String iduser = getIntent().getStringExtra("id");
        System.out.println(iduser);
        UserService userService = Server.getUserService();
    }





}
