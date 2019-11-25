package com.friday.keller2.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.friday.keller2.R;
import com.friday.keller2.adapters.WeatherMicroListAdapter;
import com.friday.keller2.enums.TempUnitEnum;
import com.friday.keller2.enums.WeatherEnum;
import com.friday.keller2.models.WeatherModel;
import java.util.ArrayList;

public class WeatherFragment extends Fragment {


    private RecyclerView list;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weather, container, false);
        list = (RecyclerView) root.findViewById(R.id.weather_recycler);
        list.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        ArrayList<WeatherModel> weatherList = getDemoWeatherList(6);
        list.setAdapter(new WeatherMicroListAdapter(weatherList, this.getContext()));
        return root;
    }

    private ArrayList<WeatherModel> getDemoWeatherList(final int size) {
        final ArrayList<WeatherModel> arr = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            WeatherModel model = new WeatherModel();
            model.setTemp(30 + i);
            model.setUnit(TempUnitEnum.celsius);
            model.setWeather(WeatherEnum.values()[i]);
            model.setDate("11/" + i+1 + "/2019");
            arr.add(model);
        }
        return arr;
    }
}