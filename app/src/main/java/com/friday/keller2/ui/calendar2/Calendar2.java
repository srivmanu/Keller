package com.friday.keller2.ui.calendar2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.friday.keller2.R;
import com.friday.keller2.adapters.CalendarListItemAdapter;
import com.friday.keller2.enums.TempUnitEnum;
import com.friday.keller2.enums.WeatherEnum;
import com.friday.keller2.models.EventModel;
import com.friday.keller2.models.LocationModel;
import com.friday.keller2.models.WeatherModel;
import java.util.ArrayList;
import java.util.Calendar;

public class Calendar2 extends Fragment {

    private CalendarView cal;


    public static Calendar2 newInstance() {
        return new Calendar2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.calendar2_fragment, container, false);
        cal = root.findViewById(R.id.calendar_view2);
        final RecyclerView list = root.findViewById(R.id.calendar2_recycler);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        final ArrayList<EventModel> eventList = getEventList(5);
        list.setAdapter(new CalendarListItemAdapter(eventList, getContext()));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private ArrayList<EventModel> getEventList(int size) {
        //todo getList from server and populate
        ArrayList<EventModel> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String color;
            WeatherEnum weather;
            switch (i) {
                case 0:
                    weather = WeatherEnum.clear;
                    color = "#" + Integer
                            .toHexString(ContextCompat.getColor(getActivity(), R.color.light_green));
                    break;
                case 1:
                    weather = WeatherEnum.cloudy;
                    color = "#" + Integer
                            .toHexString(ContextCompat.getColor(getActivity(), R.color.light_blue));
                    break;
                case 2:
                    weather = WeatherEnum.rainy;
                    color = "#" + Integer
                            .toHexString(ContextCompat.getColor(getActivity(), R.color.light_yellow));
                    break;
                case 3:
                    weather = WeatherEnum.snowy;
                    color = "#" + Integer
                            .toHexString(ContextCompat.getColor(getActivity(), R.color.light_orange));
                    break;
                case 4:
                    weather = WeatherEnum.windy;
                    color = "#" + Integer
                            .toHexString(ContextCompat.getColor(getActivity(), R.color.light_purple));
                    break;
                default:
                    weather = WeatherEnum.unknown;
                    color = "#" + Integer
                            .toHexString(ContextCompat.getColor(getActivity(), R.color.light_gray));
                    break;
            }
            EventModel model = new EventModel();
            model.setColor(color);
            model.setTitle("Demo");
            model.setLocation(new LocationModel("0.0", "0.0", "Arlington, TX"));
            model.setWeather(new WeatherModel("11/1/2019", i, TempUnitEnum.celsius,weather));
            Calendar current = Calendar.getInstance();
            current.add(Calendar.HOUR, 1);
            Calendar one_hour = Calendar.getInstance();
            one_hour.add(Calendar.HOUR, 2);
            model.setStart(current);
            model.setEnd(one_hour);

            list.add(model);

        }
        return list;//TODO
    }

}
