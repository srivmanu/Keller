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
import com.friday.keller2.App;
import com.friday.keller2.BuildConfig;
import com.friday.keller2.R;
import com.friday.keller2.enums.TempUnitEnum;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    private int REQ_CODE = 1232;

    private ConstraintLayout importCalendar;

    private TextView serverUrl;

    private Switch temp_toggle;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        importCalendar = root.findViewById(R.id.import_calendar_text);
        temp_toggle = root.findViewById(R.id.switch2);
        serverUrl = root.findViewById(R.id.footer_server_url_text);
        initializeView();
        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_CODE) {
                Uri uri = data.getData();
                String FilePath = null;
                try {
                    FilePath = PathUtil.getPath(getContext(), uri);
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, ": " + FilePath);
                    }
                    FileInputStream fis = new FileInputStream(FilePath);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader bufferedReader = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    final String url = App.getInstance().getServerURL() + "/calendarimport";
                    JSONObject json = new JSONObject();
                    json.put("calendarFile", sb.toString());
                    App.getInstance().post(url, json.toString());
                } catch (URISyntaxException | IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //TODO ICS TO UPLOAD TO SERVER
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
                    App.getInstance().setUserTemperatureUnitChoice(TempUnitEnum.fahrenheit);
                } else {
                    //celcius
                    App.getInstance().setUserTemperatureUnitChoice(TempUnitEnum.celsius);
                }
            }
        });
        serverUrl.setText(((App) getActivity().getApplication()).getServerURL());
    }

    private void openImportIntent() {
        getPermissions();
    }
}