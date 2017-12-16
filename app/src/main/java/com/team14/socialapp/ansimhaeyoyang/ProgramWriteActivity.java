package com.team14.socialapp.ansimhaeyoyang;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team14.socialapp.ansimhaeyoyang.model.Program;
import com.team14.socialapp.ansimhaeyoyang.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ProgramWriteActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText editdate;
    private EditText edittime;
    private EditText programname;
    private EditText content;
    private EditText subject;
    private EditText manager;
    private Button addprogram;
    FirebaseDatabase firebaseDatabase ;
    private DatePickerDialog DatePickerDialoge;
    private TimePickerDialog timePickerDialoge;
    private SimpleDateFormat timeformatter;
    private SimpleDateFormat dateFormatter;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_write);
        setupActionBar();
        GregorianCalendar calendar = new GregorianCalendar();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        timeformatter  = new SimpleDateFormat("HH시 mm분", Locale.KOREA);
        editdate = (EditText) findViewById(R.id.editTextDate);
        editdate.setInputType(InputType.TYPE_NULL);
        edittime = (EditText) findViewById(R.id.editTextTime);
        addprogram = (Button)findViewById(R.id.buttonAddProgram);
        programname = (EditText) findViewById(R.id.editTextName);
        content = (EditText) findViewById(R.id.editTextContent);
        subject = (EditText) findViewById(R.id.editTextSubject);
        manager = (EditText) findViewById(R.id.editTextManager);

        setDateTimeField();
        edittime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(ProgramWriteActivity.this, timeSetListener, hour, minute, false).show();
            }
        });


        firebaseDatabase = FirebaseDatabase.getInstance();
        addprogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Program p = new Program(editdate.getText().toString(),edittime.getText().toString(),programname.getText().toString(),0,content.getText().toString(),manager.getText().toString(),subject.getText().toString());
                firebaseDatabase.getReference("program").push().setValue(p);
                Toast.makeText(getBaseContext(),"요양 프로그램 추가 완료", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ProgramWriteActivity.this,ProgramActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }
    private void setDateTimeField() {
        editdate.setOnClickListener(this);
        edittime.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialoge = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editdate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            String msg = String.format("%d시 %d분", hourOfDay, minute,false);
            edittime.setText(msg);
        }

    };



    @Override
    public void onClick(View view) {
        if(view == editdate) {
            DatePickerDialoge.show();
        }
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
}