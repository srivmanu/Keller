package com.friday.keller2.models;

import com.friday.keller2.App;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created By srivmanu on 11/26/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class ServerEventModel {

    String color;

    String endDate;

    String id;

    String latitude;

    String longitude;

    String rrule;

    String startDate;

    String title;

    public ServerEventModel(final String title,
            final String startDate,
            final String endDate,
            final String color,
            final String rrule,
            final String latitude,
            final String longitude) {
        this.id = App.getInstance().getNewId();
        this.title = title;
        this.rrule = rrule;
        this.startDate = startDate;
        this.endDate = endDate;
        this.color = color;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void send() {
        JSONObject se_obj = new JSONObject();
        try {
            se_obj.put("id", this.id);
            se_obj.put("title", this.title);
            se_obj.put("rrule", this.rrule);

            se_obj.put("startDat", this.startDate);

            se_obj.put("endDate", this.endDate);

            se_obj.put("color", this.color);
            se_obj.put("location",
                    new JSONObject()
                            .put("latitude", this.latitude)
                            .put("longitude", this.longitude));

            App.getInstance().sendNewEventToServer(se_obj);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Non Recurring :
     * {
     *   "id": "5ddc88c60f9327b11f5b6ea5",
     *   "title": "Title",
     *   "rrule": "DTSTART:20191126T020655Z\nRRULE:INTERVAL=1;COUNT=1;UNTIL=20191127T020655Z",
     *   "startDate": "2019-11-25T20:06:55",
     *   "endDate": "2019-11-26T20:06:55",
     *   "color": "#7e4fa8",
     *   "location": {
     *     "latitude": 24.56,
     *     "longitude": 54.26
     *   }
     * }
     */

    /**
     * Recurring:
     * {
     *   "id": "5ddc89200f9327b11f5b6ea6",
     *   "title": "Recurring",
     *   "rrule": "DTSTART:20191126T020810Z\nRRULE:INTERVAL=2;UNTIL=20191127T020810Z;BYDAY=MO,TU,WE,TH,FR,SA,SU",
     *   "startDate": "2019-11-25T20:08:10",
     *   "endDate": "2019-11-26T20:08:10",
     *   "color": "#e4cc94",
     *   "location": {
     *     "latitude": 24.56,
     *     "longitude": 54.26
     *   }
     * }
     */

}
