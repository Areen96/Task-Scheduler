package com.asa.taskscheduler;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReminderEditActivity extends Activity {
    private Date date1,date2;
    DatePickerDialog datepk;
    Button date;
    Button time1;
    Button time2;
    Button btnSave;

    String name,startTime;
    long duration;
    EditText txtName;
   DatabaseReference mRef;
    int code;
    int mode = 1;
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // do nothing, just override
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_edit);
        Intent intent =  getIntent();
        date = findViewById(R.id.btnDate);
        btnSave = (Button) findViewById(R.id.btnSave);
        txtName = findViewById(R.id.txtName);

        mRef = FirebaseDatabase.getInstance().getReference();

        String key  = getIntent().getStringExtra("key");
        if(key != null) {
            mode = 2;

            mRef = FirebaseDatabase.getInstance().getReference().child(key);
            code = getIntent().getIntExtra("code",1);

        }



        date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final Calendar c= Calendar.getInstance();
                int mYear=c.get(Calendar.YEAR);
                int mMonth=c.get(Calendar.MONTH);
                int mDay=c.get(Calendar.DAY_OF_MONTH);
                datepk= new DatePickerDialog(ReminderEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        //date.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String dateForButton = dateFormat.format(c.getTime());
                        date.setText(dateForButton);

                    }
                },mYear,mMonth,mDay);
                datepk.show();



            }
        });




        time1 = findViewById(R.id.btnTime);
        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ReminderEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time1.setText(selectedHour + ":" + selectedMinute);
                        date1 = new Date();
                        date1.setHours(selectedHour);
                        date1.setMinutes(selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        time2 = findViewById(R.id.btnTime2);
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ReminderEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time2.setText(selectedHour + ":" + selectedMinute);
                        date2 = new Date();
                        date2.setHours(selectedHour);
                        date2.setMinutes(selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

                              /*  findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        long diff = DateUtil.getDiff(date1,date2);

                                        long diffSeconds = diff / 1000 % 60;
                                        long diffMinutes = diff / (60 * 1000) % 60;
                                        long diffHours = diff / (60 * 60 * 1000) % 24;
                                        long diffDays = diff / (24 * 60 * 60 * 1000);

                                        Log.d("tag","diffMinutes" + diffMinutes +" diffHours"+ diffHours +" diffDays" +diffDays);

                                        Toast.makeText(ReminderEditActivity.this, "diffMinutes: " + diffMinutes, Toast.LENGTH_SHORT).show();
                                    }
                                });*/




    }



    public void save(View view) {


       /* SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here

            name = txtName.getText().toString();
            startTime = time1.getText().toString();
            duration = DateUtil.getDiff(date1,date2);
            tasks.add(new Task(name,startTime,duration));

            mRef.setValue(tasks);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }else*/
       try{
           name = txtName.getText().toString();
            startTime = time1.getText().toString();
            duration = DateUtil.getDiff(date1, date2);

            String key = mRef.push().getKey();
            Task t = new Task(name,startTime,duration, (int) (Math.random()*100000));

            if(mode == 1){
                Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
            mRef.child(key).setValue(t);

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
                t.setI(code);
                mRef.setValue(t);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

            AlarmController.addAlarm(this,t.getI(),date1.getTime(),name,duration);
            Log.d("tag",t.getI()+ " added");


            //copy.add(new Task(name, startTime, duration));
            //mRef.setValue(copy);


        }catch (Exception e){e.printStackTrace();
           Toast.makeText(this, "Insert Data", Toast.LENGTH_SHORT).show();
       }
    }



    }

