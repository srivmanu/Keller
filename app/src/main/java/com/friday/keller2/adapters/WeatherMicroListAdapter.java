package com.friday.keller2.adapters;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.friday.keller2.R;
import com.friday.keller2.adapters.WeatherMicroListAdapter.WeatherMicroViewHolder;
import com.friday.keller2.enums.WeatherEnum;
import com.friday.keller2.models.WeatherModel;
import java.util.ArrayList;

/**
 * Created By srivmanu on 11/5/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class WeatherMicroListAdapter extends Adapter<WeatherMicroViewHolder> {


    private Context context;

    private final ArrayList<WeatherModel> list;

    public WeatherMicroListAdapter(final ArrayList<WeatherModel> weatherList, Context context) {
        this.context = context;
        this.list = weatherList;
    }

    /**
     * Created By srivmanu on 11/5/2019 for Keller 2
     * This will always be a test run.
     * Unless you are compiling to submit on play store.
     * In which case, God help your soul.
     */
    class WeatherMicroViewHolder extends ViewHolder {

        private final WeatherMicroListAdapter context;

        private final TextView date;

        private final ImageView image;

        private final TextView temp;

        public WeatherMicroViewHolder(@NonNull final View itemView, WeatherMicroListAdapter context) {
            super(itemView);
            this.context = context;
            this.image = (ImageView) itemView.findViewById(R.id.weather_image);
            this.temp = (TextView) itemView.findViewById(R.id.weather_temp);
            this.date = (TextView) itemView.findViewById(R.id.weather_date);
        }

        public void setView(final WeatherModel weatherModel) {
            this.image.setImageDrawable(getImageBasedOnWeather(weatherModel.getWeather()));
            this.temp.setText(String.valueOf(weatherModel.getTemp()));
            this.date.setText(weatherModel.getDate());
        }

        private Drawable getImageBasedOnWeather(final WeatherEnum weather) {
            switch (weather) {

                case clear:
                    return context.context.getDrawable(R.drawable.weather_clear);

                case rainy:
                    return context.context.getDrawable(R.drawable.weather_rainy);

                case windy:
                    return context.context.getDrawable(R.drawable.weather_windy);

                case cloudy:
                    return context.context.getDrawable(R.drawable.weather_cloudy);

                case snowy:
                    return context.context.getDrawable(R.drawable.weather_snowy);
                case unknown:
                    return context.context.getDrawable(R.drawable.weather_unknown);
                default:
                    return context.context.getDrawable(R.drawable.weather_unknown);
            }
        }
    }

    @NonNull
    @Override
    public WeatherMicroViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comp_weather_micro, parent, false);
        final WeatherMicroViewHolder holder = new WeatherMicroViewHolder(view, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final WeatherMicroViewHolder holder, final int position) {
        holder.setView(list.get(position));
    }

    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();
    }
}
