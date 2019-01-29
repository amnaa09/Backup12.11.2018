package com.example.sammrabatool.solutions5d.leave;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.Tools;
import com.example.sammrabatool.solutions5d.ViewAnimation;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;


public class ExpansionPanelInvoice extends AppCompatActivity {
    String[] spaceProbeHeaders = {"Leave Type", "Duration", "Status"};
    String[][] spaceProbes;

    private ImageButton bt_toggle_items, bt_toggle_address, bt_toggle_description;
    private View lyt_expand_items, lyt_expand_address, lyt_expand_description;
    private NestedScrollView nested_scroll_view;


    private static final String TAG = "ExpansionPanelnvoice";
    int lg, bg;
    TextView count0, count00;
    String instanceStr, userID, token;
    TextView mDisplayDate;
     DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expansion_panel_invoice);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        count0 = (TextView) findViewById(R.id.count0);
        count00 = (TextView) findViewById(R.id.count00);
        userID = getIntent().getStringExtra("userID");
        instanceStr = getIntent().getStringExtra("instance");
        token = getIntent().getStringExtra("token");
        lg = getIntent().getIntExtra("lg", 0);
        bg = getIntent().getIntExtra("bg", 0);

        final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.tableView);
//        mDisplayDate = (TextView) findViewById(R.id.tvDate);
//        mDisplayDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog dialog = new DatePickerDialog(
//                        ExpansionPanelInvoice.this,
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        mDateSetListener,
//                        year,month,day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//
//            }
//        });
//        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                month = month + 1;
//                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//
//                String date = month + "/" + day + "/" + year;
//                mDisplayDate.setText(date);
//                //showCustomDialog();
//            }
//        };

       // Button btn=(Button)findViewById(R.id.APPLY);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//           @SuppressLint("WrongViewCast")
//            private void showCustomDialog() {
//                final Calendar myCalendar = Calendar.getInstance();
//
//                final TextView edittext= (TextView) findViewById(R.id.tvDate);
//                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                          int dayOfMonth) {
//                        // TODO Auto-generated method stub
//                        myCalendar.set(Calendar.YEAR, year);
//                        myCalendar.set(Calendar.MONTH, monthOfYear);
//                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                        updateLabel();
//                    }
//                    private void updateLabel() {
//
//                        String myFormat = "MM/dd/yy"; //In which you need put here
//                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//                        edittext.setText(sdf.format(myCalendar.getTime()));
//                    }
//
//                };
//

//                    final Dialog dialog = new Dialog(ExpansionPanelInvoice.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
//                dialog.setContentView(R.layout.leavepopup);
//                dialog.setCancelable(true);
//
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                lp.copyFrom(dialog.getWindow().getAttributes());
//                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                //  final EditText et_post = (EditText) dialog.findViewById(R.id.et_post);

//                ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });



//                dialog.show();
//                dialog.getWindow().setAttributes(lp);
//            }

        tb.setColumnCount(3);
        tb.setHeaderBackgroundColor(Color.parseColor("#1E88E5"));



        initComponent();
        populateData();

        //ADAPTERS
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this, spaceProbeHeaders));
        tb.setDataAdapter(new SimpleTableDataAdapter(this, spaceProbes));

        tb.addDataClickListener(new TableDataClickListener<String[]>() {
            @Override
            public void onDataClicked(int rowIndex, String[] clickedData) {
                Toast.makeText(ExpansionPanelInvoice.this, ((String[]) clickedData)[1], Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void populateData() {
        Spaceprobe spaceprobe = new Spaceprobe();
        ArrayList<Spaceprobe> spaceprobeList = new ArrayList<>();

//        spaceprobe.setId("1");
//        spaceprobe.setName("Pioneer");
//        spaceprobe.setPropellant("Solar");
//        spaceprobe.setDestination("Venus");
//        spaceprobeList.add(spaceprobe);

        spaceprobe = new Spaceprobe();
//        spaceprobe.setId("2");
        spaceprobe.setName("Casini");
        spaceprobe.setPropellant("Nuclear");
        spaceprobe.setDestination("Jupiter");
        spaceprobeList.add(spaceprobe);

        spaceprobe = new Spaceprobe();
//        spaceprobe.setId("3");
        spaceprobe.setName("Apollo");
        spaceprobe.setPropellant("Chemical");
        spaceprobe.setDestination("Moon");
        spaceprobeList.add(spaceprobe);

        spaceprobe = new Spaceprobe();
//        spaceprobe.setId("4");
        spaceprobe.setName("Enterpise");
        spaceprobe.setPropellant("Anti-Matter");
        spaceprobe.setDestination("Andromeda");
        spaceprobeList.add(spaceprobe);

        spaceProbes = new String[spaceprobeList.size()][4];
        // spaceProbes= new String[][]{{}};

        for (int i = 0; i < spaceprobeList.size(); i++) {

            Spaceprobe s = spaceprobeList.get(i);

//            spaceProbes[i][0] = s.getId();
            spaceProbes[i][0] = s.getName();
            spaceProbes[i][1] = s.getPropellant();
            spaceProbes[i][2] = s.getDestination();

        }

    }




    private void initComponent() {

        // nested scrollview
        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);

        // section items
        bt_toggle_items = (ImageButton) findViewById(R.id.bt_toggle_items);
        lyt_expand_items = (View) findViewById(R.id.lyt_expand_items);
        bt_toggle_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(view, lyt_expand_items);
            }
        });
        }


    private void toggleSection(View bt, final View lyt) {
        boolean show = toggleArrow(bt);
        if (show) {
            ViewAnimation.expand(lyt, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt);
                }
            });
        } else {
            ViewAnimation.collapse(lyt);
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
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
