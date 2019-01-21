package com.example.sammrabatool.solutions5d.Reminder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.sammrabatool.solutions5d.R;

import java.util.ArrayList;

public class Recyclerview extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static ArrayList<Model> modelArrayList;
    private CustomAdapter customAdapter;
    private Button btnnext;
    private String[] reminder = new String[]{"FYR", "FYR", "FYR", "FYR", "FYI", "FYI", "FYR", "FYI", "FYI"};
    private ProgressDialog progressDialog;
    boolean user_valid = false;
    String message;
    String instanceStr, userID, token, userName;
    int lg, bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        userID = getIntent().getStringExtra("userID");
        instanceStr = getIntent().getStringExtra("instance");
        token = getIntent().getStringExtra("token");
        lg = getIntent().getIntExtra("lg", 0);
        bg = getIntent().getIntExtra("bg", 0);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        btnnext = (Button) findViewById(R.id.next);

//        modelArrayList = getModel();
        customAdapter = new CustomAdapter(this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

//        btnnext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Recyclerview.this,NextActivity.class);
//                startActivity(intent);
//            }
//        });
    }

//    private ArrayList<Model> getModel() {
//        ArrayList<Model> list = new ArrayList<>();
//        for(int i = 0; i < 9; i++){
//
//            Model model = new Model();
//            model.setNotifcation_id();
//            model.setName(reminder[i]);
//            model.setFyr(reminder[i]);
//            model.setDate(reminder[i]);
//            model.setStatus(reminder[i]);
//            model.setMessage(reminder[i]);
////            model.setImage();
//            list.add(model);
//        }
//        return list
    }


