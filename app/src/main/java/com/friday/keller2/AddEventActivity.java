package com.friday.keller2;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.friday.keller2.adapters.NotificationsListRecyclerViewAdapter;
import com.friday.keller2.enums.TimeUnitEnum;
import com.friday.keller2.models.EventModel;
import com.friday.keller2.models.LocationModel;
import com.friday.keller2.models.NotificationModel;
import com.friday.keller2.models.WeatherModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddEventActivity extends AppCompatActivity {

    private static final String TAG = "AddEventActivity";

    EditText endDate;

    EditText endTime;

    EditText location;

    FloatingActionButton notification_fab;

    RecyclerView notification_list;

    EditText startDate;

    EditText startTime;

    EditText title;

    private NotificationsListRecyclerViewAdapter adapter;

    private List<NotificationModel> notification_list_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        final Calendar date = Calendar.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton saveFab = findViewById(R.id.fab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                logList();
                SaveAndClose();
            }
        });
        title = findViewById(R.id.add_event_event_title);
        startTime = findViewById(R.id.add_event_start_time);
        startTime.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, final boolean hasFocus) {
                if (hasFocus) {
                    final TimePickerDialog picker = new TimePickerDialog(startTime.getContext(),
                            new OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                    startTime.setText(sHour + ":" + sMinute);
                                }
                            }, date.get(Calendar.HOUR), date.get(Calendar.MINUTE), true);
                    picker.show();
                }
            }
        });
        startDate = findViewById(R.id.add_event_start_date);
        String today = (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.DAY_OF_MONTH) + "/" + date
                .get(Calendar.YEAR);
        startDate.setText(today);
        startDate.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, final boolean hasFocus) {
                if (hasFocus) {
                    final DatePickerDialog picker = new DatePickerDialog(endDate.getContext(),
                            new OnDateSetListener() {
                                @Override
                                public void onDateSet(final DatePicker view, final int year, final int month,
                                        final int dayOfMonth) {
                                    startDate.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                                }
                            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
                    picker.show();
                }
            }
        });
        endTime = findViewById(R.id.add_event_end_time);
        endTime.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, final boolean hasFocus) {
                if (hasFocus) {
                    final TimePickerDialog picker = new TimePickerDialog(startTime.getContext(),
                            new OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                    endTime.setText(sHour + ":" + sMinute);
                                }
                            }, date.get(Calendar.HOUR), date.get(Calendar.MINUTE), true);
                    picker.show();
                }
            }
        });
        endDate = findViewById(R.id.add_event_end_date);
        endDate.setText(today);
        endDate.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, final boolean hasFocus) {
                if (hasFocus) {
                    final DatePickerDialog picker = new DatePickerDialog(endDate.getContext(),
                            new OnDateSetListener() {
                                @Override
                                public void onDateSet(final DatePicker view, final int year, final int month,
                                        final int dayOfMonth) {
                                    endDate.setText(month + "/" + dayOfMonth + "/" + year);
                                }
                            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
                    picker.show();
                }
            }
        });
        location = findViewById(R.id.add_event_location);
        notification_list = findViewById(R.id.add_event_notifications_handler);
        notification_fab = findViewById(R.id.add_event_fab);
        notification_list_array = createBlankList();

        adapter = new NotificationsListRecyclerViewAdapter(
                notification_list_array);
        notification_list.setAdapter(adapter);
        notification_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        notification_fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                addNewNotificationVal();
            }
        });
    }

    List<NotificationModel> createBlankList() {
        final List<NotificationModel> list = new ArrayList<>();
//        list.add(new NotificationModel());
        return list;
    }

    private void SaveAndClose() {
        EventModel new_event = new EventModel();

        final Calendar start = new_event.parseDate(startTime.getText().toString(), startDate.getText().toString());
        final Calendar end = new_event.parseDate(endTime.getText().toString(), endDate.getText().toString());
        final LocationModel loc = new LocationModel(location.getText().toString());
        final WeatherModel weather = new WeatherModel();
        weather.getWeather(location, start, end);

        new_event.setTitle(title.getText().toString());
        new_event.setStart(start);
        new_event.setEnd(end);
        new_event.setLocation(loc);
        new_event.setWeather(weather);
        new_event.setNotificationList((List<NotificationModel>) adapter.getList());

        new_event.sendToServer();
        finish();
    }

    private void addNewNotificationVal() {
        Log.d(TAG, "addNewNotificationVal() called");
        adapter.getList().add(new NotificationModel(0, TimeUnitEnum.minutes));
        adapter.notifyDataSetChanged();
    }

    private void logList() {
        int i = 0;
        Log.d(TAG, "logList() called");
        final List<NotificationModel> myList = adapter.getList();
        for (NotificationModel model : myList) {
            Log.d(TAG, "logList : " + i + " : ");
            model.log();
            i++;
        }
    }

}