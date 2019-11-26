package com.friday.keller2;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.friday.keller2.models.ServerEventModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.dmfs.rfc5545.Weekday;
import org.dmfs.rfc5545.recur.Freq;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRule.WeekdayNum;

public class AddEventActivity extends AppCompatActivity {

    private static final String TAG = "AddEventActivity";

    EditText endDate;

    EditText endTime;

    EditText location;

    EditText startDate;

    EditText startTime;

    EditText title;

    private CheckBox cb_friday;

    private CheckBox cb_monday;

    private CheckBox cb_saturday;

    private CheckBox cb_sunday;

    private CheckBox cb_thursday;

    private CheckBox cb_tueday;

    private CheckBox cb_wednesday;

    private Freq f;

    private EditText recNumber;

    private Spinner rec_spinner;

    private Switch rec_tog;

    private LinearLayout rev_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        final Calendar date = Calendar.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rev_view = findViewById(R.id.recurrance_view);
        rev_view.setVisibility(View.GONE);

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
        View v = findViewById(R.id.add_event_weather_miniview);
        v.setVisibility(View.GONE);
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
        rec_tog = findViewById(R.id.recurrance_switch);
        recNumber = findViewById(R.id.recurrance_count);
        cb_monday = findViewById(R.id.cb_monday);
        cb_tueday = findViewById(R.id.cb_tuesday);
        cb_wednesday = findViewById(R.id.cb_wednesday);
        cb_thursday = findViewById(R.id.cb_thursday);
        cb_friday = findViewById(R.id.cb_friday);
        cb_saturday = findViewById(R.id.cb_saturday);
        cb_sunday = findViewById(R.id.cb_sunday);

        rec_spinner = findViewById(R.id.rec_spinner);

        rec_tog.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    rev_view.setVisibility(View.VISIBLE);
                } else {
                    rev_view.setVisibility(View.GONE);
                }
            }
        });
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return p1;
    }


    private void SaveAndClose() {
        if (isAnyBlank()) {
            Toast.makeText(this, "All Fields need to be filled", Toast.LENGTH_SHORT).show();
        } else {
            LatLng p = getLocationFromAddress(getApplicationContext(), getLocation());
            ServerEventModel model = new ServerEventModel(
                    this.title.getText().toString(),
                    getStartDate(),
                    getEndDate(),
                    "#ff8855",
                    getRrule(),
                    String.valueOf(p.latitude),
                    String.valueOf(p.longitude)

            );
            model.send();
            finish();
        }
    }

    private String formatDate(String date) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "DATE SEEN: " + date);
        }
        SimpleDateFormat before = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());
        SimpleDateFormat after = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
        Date time = new Date();
        try {
            time = before.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date = after.format(time);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "DATE SENT: " + date);
        }
        return date;
    }

    private List<WeekdayNum> getArrayByCB() {
        List<WeekdayNum> v = new ArrayList<>();
        if (cb_monday.isChecked()) {
            v.add(new WeekdayNum(1, Weekday.MO));
        }
        if (cb_tueday.isChecked()) {
            v.add(new WeekdayNum(2, Weekday.TU));
        }
        if (cb_wednesday.isChecked()) {
            v.add(new WeekdayNum(3, Weekday.WE));
        }
        if (cb_thursday.isChecked()) {
            v.add(new WeekdayNum(4, Weekday.TH));
        }
        if (cb_friday.isChecked()) {
            v.add(new WeekdayNum(5, Weekday.FR));
        }
        if (cb_saturday.isChecked()) {
            v.add(new WeekdayNum(6, Weekday.SA));
        }
        if (cb_sunday.isChecked()) {
            v.add(new WeekdayNum(7, Weekday.SU));
        }
        return v;
    }

    private String getEndDate() {
        String end = "";
        end = end + endDate.getText().toString();
        end = end + " " + endTime.getText().toString();

        end = formatDate(end);
        return end;

    }

    private String getLocation() {
        return location.getText().toString();
    }

    private String getRrule() {
//        ```
        /**
         * {
         *   "id": "5ddc88c60f9327b11f5b6ea5",
         *   "title": "Title",
         *   "rrule": "DTSTART:20191126T020655Z\nRRULE:INTERVAL=1;COUNT=1;UNTIL=20191127T020655Z",
         *   "startDate": "2019-11-25T20:06:55",
         *   "endDate": "2019-11-26T20:06:55",
         *   "color": "#7e4fa8",
         *   "location": {
         *     "latitude": 24.56,
         *     "longitude": 54.26
         *   }
         * }
         *
         * Recurring:
         * {
         *   "id": "5ddc89200f9327b11f5b6ea6",
         *   "title": "Recurring",
         *   "rrule": "DTSTART:20191126T020810Z\nRRULE:INTERVAL=2;UNTIL=20191127T020810Z;BYDAY=MO,TU,WE,TH,FR,SA,SU",
         *   "startDate": "2019-11-25T20:08:10",
         *   "endDate": "2019-11-26T20:08:10",
         *   "color": "#e4cc94",
         *   "location": {
         *     "latitude": 24.56,
         *     "longitude": 54.26
         *   }
         * }
         */

//        ```

        RecurrenceRule rule;
        f = Freq.DAILY;
        rec_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position,
                    final long id) {

                String freq = parent.getItemAtPosition(position).toString();
                if (freq.equals("Hour")) {
                    f = Freq.HOURLY;
                } else if (freq.equals("Day")) {
                    f = Freq.DAILY;
                } else if (freq.equals("Week")) {
                    f = Freq.WEEKLY;
                } else if (freq.equals("Month")) {
                    f = Freq.MONTHLY;
                } else if (freq.equals("Year")) {
                    f = Freq.YEARLY;
                }
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });
        rule = new RecurrenceRule(f);
        rule.setCount(Integer.parseInt(recNumber.getText().toString()));
        rule.setByDayPart(getArrayByCB());

        return rule.toString();
    }

    private String getStartDate() {
        String start = "";
        start = start + startDate.getText().toString();
        start = start + " " + startTime.getText().toString();
        start = formatDate(start);
        return start;
    }

    private boolean isAnyBlank() {
        if (!rec_tog.isChecked()) {
            return location.getText().toString().equals("") ||
                    title.getText().toString().equals("") ||
                    startTime.getText().toString().equals("") ||
                    endDate.getText().toString().equals("") ||
                    startDate.getText().toString().equals("") ||
                    endTime.getText().toString().equals("");
        } else {
            return location.getText().toString().equals("") ||
                    title.getText().toString().equals("") ||
                    startTime.getText().toString().equals("") ||
                    endDate.getText().toString().equals("") ||
                    startDate.getText().toString().equals("") ||
                    endTime.getText().toString().equals("") ||
                    recNumber.getText().toString().equals("");
        }
    }


}
