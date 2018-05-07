package com.asa.taskscheduler;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    DatabaseReference mRef;
    ArrayList<Task> tasks = new ArrayList<Task>();
    ListView listview;
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // do nothing, just override
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReminderEditActivity.class);
                startActivity(intent);

            }
        });


        mRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskscheduler-64108.firebaseio.com/");
//        final ArrayAdapter<Task> arrayAdapter = new ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1,tasks);
        listview = (ListView)findViewById(R.id.listview);
//        listview.setAdapter(arrayAdapter);
//        mRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Task task = dataSnapshot.getValue(Task.class);
//                    task.setKey(dataSnapshot.getKey());
//                tasks.add(task);
//                arrayAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        final FirebaseListAdapter<Task> adapter = new FirebaseListAdapter<Task>(this,Task.class,android.R.layout.simple_list_item_1
        ,mRef) {
            @Override
            protected void populateView(View v, Task model, int position) {
                Log.d("tag",getRef(position).getKey());
                model.setKey(getRef(position).getKey());
                TextView text = (TextView)v.findViewById(android.R.id.text1);
                text.setText(model.getName() + "\n" + model.getStartTime());

            }
        };
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String key = adapter.getRef(i).getKey();
                Task t = adapter.getItem(i);
                Intent intent = new Intent(MainActivity.this,ReminderEditActivity.class);

                intent.putExtra("key",key);
                intent.putExtra("code",t.getI());
                startActivity(intent);




            }
        });



        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                final int pos2 = pos ;
               /* adapter.getRef(pos).removeValue();
                Task t = adapter.getItem(pos);
                Log.d("tag",t.getI()+ "");
                AlarmController.deleteAlarm(MainActivity.this,t.getI());
                Toast.makeText(MainActivity.this, "Task Removed", Toast.LENGTH_LONG).show();*/

                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Are you sure you want to delete this Task?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adapter.getRef(pos2).removeValue();
                                Task t = adapter.getItem(pos2);
                                Log.d("tag",t.getI()+ "");
                                AlarmController.deleteAlarm(MainActivity.this,t.getI());
                                Toast.makeText(MainActivity.this, "Task Removed", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });






    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Log.d("tag","settings");
            //startActivity(new Intent(this,Settings.class));

            /*DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.removeValue();*/

            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to clear all Tasks?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference.removeValue();
                            Toast.makeText(MainActivity.this, "Tasks Cleared", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

                return true;
 }

        return super.onOptionsItemSelected(item);
    }


    public void sett(MenuItem item) {

      //  Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
    }

    public void EditTask(MenuItem item) {

        Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();

    }


}
