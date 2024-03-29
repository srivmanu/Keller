package com.friday.keller2.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.friday.keller2.App;
import com.friday.keller2.R;
import com.friday.keller2.adapters.WeatherMicroListAdapter;
import com.friday.keller2.models.WeatherModel;
import java.util.List;

public class WeatherFragment extends Fragment {


    TextView nextWeather;

    TextView nextWeatherUnit;

    TextView nowWeather;

    TextView nowWeatherUnit;

    private TextView currTime;

    private RecyclerView list;

    private TextView nextTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weather, container, false);
        list = root.findViewById(R.id.weather_recycler);
        list.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        List<WeatherModel> weatherList = App.getInstance().gethourlyweather();
        if (weatherList != null) {
            list.setAdapter(new WeatherMicroListAdapter(weatherList, this.getContext()));
        }
        nowWeather = root.findViewById(R.id.now_weather_text);
        nowWeatherUnit = root.findViewById(R.id.now_weather_unit);
        nextWeather = root.findViewById(R.id.next_weather_text);
        nextWeatherUnit = root.findViewById(R.id.next_weather_unit);
        nextTime = root.findViewById(R.id.next_event_time);
        currTime = root.findViewById(R.id.time_updated_at);
        fillDataForWeather();
        return root;
    }

    private void fillDataForWeather() {
        WeatherModel now = App.getInstance().getCurrentWeather();
        WeatherModel next = App.getInstance().getLocalSummaryModel().getEvent_next().getModel();
        if (now != null) {
            nowWeather.setText(String.valueOf(now.getTemp()));
            nowWeatherUnit.setText((now.getUnitString()));
            currTime.setText(App.getInstance().getCurrentTime("K:mm a"));
        } else {
            nowWeather.setText("ERR");
            nowWeatherUnit.setText("");
            currTime.setText("");
        }

        if (next != null) {
            nextWeather.setText(String.valueOf(next.getTemp()));
            nextWeatherUnit.setText(App.getInstance().convertTimeStampToDateString(
                    App.getInstance().getLocalSummaryModel().getEvent_next().getStart().getTime().getTime()));
            nextTime.setText(next.getDate());
        } else {
            nextWeather.setText("ERR");
            nextWeatherUnit.setText("");
            nextTime.setText("");
        }
    }
}