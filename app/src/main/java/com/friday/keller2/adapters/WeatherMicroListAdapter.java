package com.friday.keller2.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.friday.keller2.App;
import com.friday.keller2.R;
import com.friday.keller2.adapters.WeatherMicroListAdapter.WeatherMicroViewHolder;
import com.friday.keller2.models.WeatherModel;
import java.util.List;

/**
 * Created By srivmanu on 11/5/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class WeatherMicroListAdapter extends Adapter<WeatherMicroViewHolder> {


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
            this.image = itemView.findViewById(R.id.weather_image);
            this.temp = itemView.findViewById(R.id.weather_temp);
            this.date = itemView.findViewById(R.id.weather_date);
        }

        public void setView(final WeatherModel weatherModel) {
            this.image.setImageDrawable(
                    App.getInstance().getImageBasedOnWeather(weatherModel.getWeather(), context.context));
            this.temp.setText(String.valueOf(weatherModel.getTemp()));
            this.date.setText(weatherModel.getDate());
        }

    }

    private Context context;

    private final List<WeatherModel> list;

    public WeatherMicroListAdapter(final List<WeatherModel> weatherList, Context context) {
        this.context = context;
        this.list = weatherList;
    }

    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final WeatherMicroViewHolder holder, final int position) {
        holder.setView(list.get(position));
    }

    @NonNull
    @Override
    public WeatherMicroViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comp_weather_micro, parent, false);
        final WeatherMicroViewHolder holder = new WeatherMicroViewHolder(view, this);
        return holder;
    }
}
