package com.example.alarm_sb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE1 = 1000;
    public static final int REQUEST_CODE2 = 1001;
    private AdapterActivity arrayAdapter;
    private Button tpBtn, removeBtn;
    private ListView listView;
    private TextView textView;
    private int hour, minute;
    private String  am_pm;
    private Handler handler;
    private SimpleDateFormat mFormat;
    private int adapterPosition;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayAdapter = new AdapterActivity();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterPosition = position;
                arrayAdapter.removeItem(position);
                Intent intent = new Intent(MainActivity.this, TimePickerActivity.class);
                startActivityForResult(intent, REQUEST_CODE2);
            }
        });

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Calendar cal = Calendar.getInstance();

                mFormat = new SimpleDateFormat("HH:mm:ss");
                String strTime = mFormat.format(cal.getTime());
                textView = (TextView) findViewById(R.id.current);
                textView.setTextSize(30);
                textView.setText(strTime);
            }
        };

        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                }
            }
        }
        NewRunnable runnable = new NewRunnable();
        Thread thread = new Thread(runnable);
        thread.start();

        tpBtn = (Button) findViewById(R.id.addBtn);
        tpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tpIntent = new Intent(MainActivity.this, TimePickerActivity.class);
                startActivityForResult(tpIntent, REQUEST_CODE1);
            }
        });

        removeBtn = (Button) findViewById(R.id.removeBtn);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayAdapter.removeItem();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE1 && resultCode == RESULT_OK && data !=null) {
            hour = data.getIntExtra("hour", 1);
            minute = data.getIntExtra("minute", 2);
            am_pm = data.getStringExtra("am_pm");


            arrayAdapter.addItem(hour, minute, am_pm);
            arrayAdapter.notifyDataSetChanged();
        }
        if(requestCode == REQUEST_CODE2 && resultCode == RESULT_OK && data !=null) {
            hour = data.getIntExtra("hour", 1);
            minute = data.getIntExtra("minute", 2);
            am_pm = data.getStringExtra("am_pm");

            arrayAdapter.addItem(hour, minute, am_pm);
            arrayAdapter.notifyDataSetChanged();
        }
    }
}