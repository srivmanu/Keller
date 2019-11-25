package com.friday.keller2.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.friday.keller2.R;
import com.friday.keller2.adapters.NotificationsListRecyclerViewAdapter.NotificationItemViewHolder;
import com.friday.keller2.enums.TimeUnitEnum;
import com.friday.keller2.models.NotificationModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By srivmanu on 11/4/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class NotificationsListRecyclerViewAdapter extends Adapter<NotificationItemViewHolder> {

    class NotificationItemViewHolder extends ViewHolder implements View.OnClickListener {

        FloatingActionButton fab;

        NotificationsListRecyclerViewAdapter mContext;

        NotificationModel model;

        EditText time;

        Spinner unit;

        public NotificationItemViewHolder(@NonNull final View itemView,
                NotificationsListRecyclerViewAdapter context) {
            super(itemView);
            model = new NotificationModel();
            time = itemView.findViewById(R.id.num_unit_time);
            unit = itemView.findViewById(R.id.time_unit_spin);

            fab = itemView.findViewById(R.id.remove_fab);
            fab.setOnClickListener(this);
            unit.setAdapter(new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_list_item_1,
                    getStringList(TimeUnitEnum.values())));
            this.mContext = context;

            time.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(final Editable s) {
                    mContext.list.get(getAdapterPosition()).setTime(convertTime(s.toString()));
                }

                @Override
                public void beforeTextChanged(final CharSequence s, final int start, final int count,
                        final int after) {

                }

                @Override
                public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                    mContext.list.get(getAdapterPosition()).setTime(convertTime(s.toString()));
                }
            });

            unit.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(final AdapterView<?> parent, final View view, final int position,
                        final long id) {
                    mContext.list.get(getAdapterPosition()).setUnits(TimeUnitEnum.values()[position]);
                }

                @Override
                public void onNothingSelected(final AdapterView<?> parent) {

                }
            });
        }

        @Override
        public void onClick(final View v) {
            if (v.equals(fab)) {
                removeAt(getAdapterPosition());
            }
        }

        private int convertTime(final String toString) {
            return Integer.parseInt(toString);
        }

        private String[] getStringList(final TimeUnitEnum[] values) {
            ArrayList<String> list = new ArrayList<String>();
            for (TimeUnitEnum e : values) {
                list.add(e.name() + " before");
            }
            return list.toArray(new String[0]);
        }
    }

    List<NotificationModel> list;

    public NotificationsListRecyclerViewAdapter(final List<NotificationModel> old) {
        this.list = new ArrayList<>(old);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List getList() {
        return list;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationItemViewHolder holder, final int position) {

    }

    @NonNull
    @Override
    public NotificationItemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent
            , final int viewType) {
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.my_text_view, parent, false);
//        ...
//        MyViewHolder vh = new MyViewHolder(v);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item, parent, false);
        NotificationItemViewHolder holder = new NotificationItemViewHolder(view, this);
        return holder;
    }

    public void removeAt(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }
}
