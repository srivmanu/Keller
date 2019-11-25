package com.friday.keller2.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.friday.keller2.R;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel(@NonNull final Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue(application.getResources().getString(R.string.dummy_home_text));

    }

    public LiveData<String> getText() {
        return mText;
    }
}