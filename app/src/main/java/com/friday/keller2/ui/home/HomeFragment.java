package com.friday.keller2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.friday.keller2.AddEventActivity;
import com.friday.keller2.App;
import com.friday.keller2.R;
import com.friday.keller2.models.SummaryModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    private TextView curr_unit;

    private TextView curr_weather;

    private TextView next_unit;

    private TextView next_weather;

    private TextView summaryView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
//        final HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final FloatingActionButton fab = root.findViewById(R.id.fab_home);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(getContext(), AddEventActivity.class);
                startActivity(intent);
            }
        });
        summaryView = (TextView) root.findViewById(R.id.text_home);
        curr_weather = (TextView) root.findViewById(R.id.now_weather_text);
        curr_unit = (TextView) root.findViewById(R.id.now_weather_unit);
        next_unit = (TextView) root.findViewById(R.id.next_weather_unit);
        next_weather = (TextView) root.findViewById(R.id.next_weather_text);

//        initializePage(); //todo uncomment once SummaryModel is done
        return root;
    }

    private void initializePage() {
        SummaryModel model = App.getSummaryModel();
        model.getSummaryFromServer();
        summaryView.setText(model.getSummaryText());
        curr_weather.setText(String.valueOf(model.getWeather_now().getTemp()));
        curr_unit.setText(model.getWeather_now().getUnitString());
        next_weather.setText(String.valueOf(model.getEvent_next().getWeather().getTemp()));
        next_unit.setText(model.getEvent_next().getWeather().getUnitString());

    }
}