package com.friday.keller2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.friday.keller2.App;
import com.friday.keller2.R;
import com.friday.keller2.adapters.CalendarListItemAdapter.CalendarListViewHolder;
import com.friday.keller2.models.EventModel;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created By srivmanu on 11/5/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class CalendarListItemAdapter extends Adapter<CalendarListViewHolder> {

    /**
     * Created By srivmanu on 11/5/2019 for Keller 2
     * This will always be a test run.
     * Unless you are compiling to submit on play store.
     * In which case, God help your soul.
     */
    class CalendarListViewHolder extends ViewHolder {

        EventModel model;

        private final CalendarListItemAdapter context;

        private final TextView endTime;

        private final TextView eventLocation;

        private final TextView eventTitle;

        private final ConstraintLayout layout;

        private final TextView startTime;

        private final ImageView weatherView;


        public CalendarListViewHolder(final View view, CalendarListItemAdapter context) {
            super(view);
            this.context = context;
            eventTitle = view.findViewById(R.id.add_event_event_title);
            eventLocation = view.findViewById(R.id.add_event_event_location);
            startTime = view.findViewById(R.id.add_event_start_time);
            endTime = view.findViewById(R.id.add_event_end_time);
            weatherView = view.findViewById(R.id.add_event_weather_view);
            layout = view.findViewById((R.id.add_event_layout_back));
        }

        void setView(EventModel event) {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            this.model = event;
            eventTitle.setText(event.getTitle());
            if (event.getLocation() != null) {
                Geocoder geocoder = new Geocoder(context.context, Locale.getDefault());
                List<Address> addresses = null;
                String cityName = "";
                try {
                    addresses = geocoder.getFromLocation(
                            Double.parseDouble(event.getLocation().getLat()),
                            Double.parseDouble(event.getLocation().getLon()),
                            1);
                    if (addresses != null && addresses.size() > 0) {
                        cityName = addresses.get(0).getAddressLine(0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                event.getLocation().setName(cityName);
                eventLocation.setText(event.getLocation().getName());
            }
            startTime.setText((sdf.format(event.getStart().getTime())));
            endTime.setText((sdf.format(event.getEnd().getTime())));
            if (event.getModel() != null) {
                weatherView.setImageDrawable((App.getInstance().getImageBasedOnWeather(
                        event.getModel().getWeather()
                        , context.context)));
            } else {
                weatherView.setImageDrawable(context.context.getDrawable(R.drawable.weather_unknown));
            }
            layout.getBackground().setColorFilter(Color.parseColor(event.getColor()), PorterDuff.Mode.SRC_ATOP);
        }

    }

    ArrayList<EventModel> list;

    private final Context context;


    public CalendarListItemAdapter(final ArrayList<EventModel> eventList, Context context) {
        this.list = eventList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final CalendarListViewHolder holder, final int position) {
        holder.setView(list.get(position));
    }

    @NonNull
    @Override
    public CalendarListViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false);
        CalendarListViewHolder holder = new CalendarListViewHolder(view, this);
        return holder;
    }
}
