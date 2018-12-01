package com.example.sammrabatool.solutions5d.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.Tools;
import com.example.sammrabatool.solutions5d.ViewAnimation;


public class HrProfile extends AppCompatActivity {

    private View parent_view;
    private NestedScrollView nested_scroll_view;
    private TextView tv_booking_code;
    private ImageButton bt_toggle_employ_info, bt_toggle_extra, bt_toggle_text;
    private Button bt_hide_info;
    private View lyt_expand_employ_info, lyt_expand_extra,lyt_expand_text;
    TextView quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_hr);

        parent_view = findViewById(android.R.id.content);
        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_content);

        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HR Profile");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void initComponent() {
        // info item_section
        bt_toggle_text=(ImageButton) findViewById(R.id.bt_toggle_text);
        lyt_expand_text = (View) findViewById(R.id.lyt_expand_text);
        lyt_expand_text.setVisibility(View.GONE);

        bt_toggle_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSectionText(bt_toggle_text);
            }
        });

        bt_toggle_employ_info = (ImageButton) findViewById(R.id.bt_toggle_employ_info);
      //  bt_hide_info = (Button) findViewById(R.id.bt_hide_info);
        lyt_expand_employ_info = (View) findViewById(R.id.lyt_expand_employ_info);
        lyt_expand_employ_info.setVisibility(LinearLayout.GONE);
        bt_toggle_employ_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSectionEmploymentInfo(bt_toggle_employ_info);
            }
        });


        bt_toggle_extra = (ImageButton) findViewById(R.id.bt_toggle_extra);
        lyt_expand_extra = (View) findViewById(R.id.lyt_expand_extra);
        lyt_expand_extra.setVisibility(LinearLayout.GONE);

        bt_toggle_extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSectionExtraInfo(bt_toggle_extra);
            }
        });

        // copy to clipboard

     /*   bt_copy_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.copyToClipboard(getApplicationContext(), tv_booking_code.getText().toString());
            }
        }); */


    }

    private void toggleSectionText(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lyt_expand_text, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt_expand_text);
                }
            });
        } else {
            ViewAnimation.collapse(lyt_expand_text);
        }
    }

    private void toggleSectionEmploymentInfo(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lyt_expand_employ_info, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt_expand_employ_info);
                }
            });
        } else {
            ViewAnimation.collapse(lyt_expand_employ_info);
        }
    }

    private void toggleSectionExtraInfo(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lyt_expand_extra, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt_expand_extra);
                }
            });
        } else {
            ViewAnimation.collapse(lyt_expand_extra);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.logout){
            Toast.makeText(this,"You have successfully logged out",Toast.LENGTH_LONG).show();
            SharedPreferences preferences =getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent=new Intent(HrProfile.this,LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
