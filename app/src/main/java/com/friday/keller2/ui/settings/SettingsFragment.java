package com.friday.keller2.ui.settings;

import static android.app.Activity.RESULT_OK;

import android.Manifest.permission;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.friday.keller2.App;
import com.friday.keller2.BuildConfig;
import com.friday.keller2.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.net.URISyntaxException;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    private int REQ_CODE = 1232;

    private ConstraintLayout importCalendar;

    private TextView serverUrl;

    private Switch temp_toggle;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.fragment_settings, container, false);
        importCalendar = (ConstraintLayout) root.findViewById(R.id.import_calendar_text);
        temp_toggle = (Switch) root.findViewById(R.id.switch2);
        serverUrl = (TextView) root.findViewById(R.id.footer_server_url_text);
        initializeView();
        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_CODE) {
                Uri uri = data.getData();
                try {
                    String FilePath = PathUtil.getPath(getContext(), uri);
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, ": " + FilePath);
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                //TODO UPLOAD TO SERVER
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    private void getPermissions() {
        Dexter.withActivity(getActivity()).withPermission(permission.READ_EXTERNAL_STORAGE)
                .withListener(
                        new PermissionListener() {
                            @Override
                            public void onPermissionDenied(final PermissionDeniedResponse response) {
                                Toast.makeText(getContext(), "Storage Permission is needed", Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onPermissionGranted(final PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("*/*");
                                startActivityForResult(intent, REQ_CODE);
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(final PermissionRequest permission,
                                    final PermissionToken token) {

                            }
                        })
                .check();

    }

    private void initializeView() {
        importCalendar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                openImportIntent();
            }
        });

        temp_toggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    //Fahrenheit
                } else {
                    //celcius
                }
            }
        });
        serverUrl.setText(((App) getActivity().getApplication()).getServerURL());
    }

    private void openImportIntent() {
        getPermissions();
    }
}