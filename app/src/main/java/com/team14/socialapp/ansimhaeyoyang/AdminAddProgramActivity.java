package com.team14.socialapp.ansimhaeyoyang;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.team14.socialapp.ansimhaeyoyang.model.Program;
import com.team14.socialapp.ansimhaeyoyang.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminAddProgramActivity extends AppCompatActivity {
    @BindView(R.id.program_date_tv)
    TextView dateTextView;
    @BindView(R.id.program_time_tv)
    TextView timeTextView;
    @BindView(R.id.program_title_et)
    EditText titleEditText;
    @BindView(R.id.program_detail_et)
    EditText detailEditText;
    @BindView(R.id.program_manager_et)
    EditText managerEditText;
    @BindView(R.id.program_place_et)
    EditText placeEditText;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private SimpleDateFormat timeFormatter;
    private SimpleDateFormat dateFormatter;
    private int hour, minute;

    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_program);
        ButterKnife.bind(this);
        setupActionBar();

        firebaseDatabase = FirebaseDatabase.getInstance();

        GregorianCalendar calendar = new GregorianCalendar();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        timeFormatter  = new SimpleDateFormat("HH시 mm분", Locale.KOREA);

    }


    @OnClick(R.id.program_time_tv)
    public void onclickTimeTextView(){
        new TimePickerDialog(AdminAddProgramActivity.this, timeSetListener, hour, minute, false).show();
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String msg = String.format("%d시 %d분", hourOfDay, minute,false);
            timeTextView.setText(msg);
        }

    };

    @OnClick(R.id.program_date_tv)
    public void onclickDateTextView(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateTextView.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("요양 프로그램 추가");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @OnClick(R.id.add_program_button)
    public void addProgramButton(){
        Program p = new Program();
        p.setDate(dateTextView.getText().toString());
        p.setTime(timeTextView.getText().toString());
        p.setTitle(titleEditText.getText().toString());
        p.setDetail(detailEditText.getText().toString());
        p.setPlace(placeEditText.getText().toString());
        p.setManagerName(managerEditText.getText().toString());
        p.setKey(null);
        ArrayList<User> users = new ArrayList<>();
        //p.setParticipants(users);
        firebaseDatabase.getReference("program").push().setValue(p);

        Toast.makeText(getBaseContext(),"요양 프로그램 추가 완료", Toast.LENGTH_LONG).show();
        startActivity(new Intent(AdminAddProgramActivity.this,AdminProgramActivity.class));

    }
}
