package com.example.frida.closet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Line;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import static com.google.android.gms.maps.GoogleMap.*;

public class MapsActivity extends FragmentActivity implements OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMarkerClickListener,
        OnInfoWindowClickListener,
        OnMapReadyCallback,
        OnMapLongClickListener {

    private IdGenerator id;
    private GoogleMap mMap;

    private LinearLayout mapView;
    private TableLayout recentsView;
    private LinearLayout mostViewedView;
    private LinearLayout searchView;
    private LinearLayout moreView;
    private TableLayout infoView;
    private TableLayout newView;

    private TimePicker tp;
    private TimePicker tp2;
    private TimePicker tp3;
    private TimePicker tp4;
    private TimePicker tp5;
    private TimePicker tp6;
    private TimePicker tp7;
    private TimePicker tp8;
    private TimePicker tp9;
    private TimePicker tp10;
    private TimePicker tp11;
    private TimePicker tp12;
    private TimePicker tp13;
    private TimePicker tp14;

    private GridLayout times;
    private GridLayout times2;
    private GridLayout times3;
    private GridLayout times4;
    private GridLayout times5;
    private GridLayout times6;
    private GridLayout times7;

    private Switch sw;
    private Switch sw2;
    private Switch sw3;
    private Switch sw4;
    private Switch sw5;
    private Switch sw6;
    private Switch sw7;

    private String nameText = "";
    private Button saveButton;
    private TableRow saveRow;

    private ToiletDataManager toiletDataManager = new ToiletDataManager();

    class InfoWindow implements InfoWindowAdapter {

        // These are both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".
        private final View mWindow;

        private final View mContents;

        InfoWindow() {
            mWindow = getLayoutInflater().inflate(R.layout.infowindow, null);
            mContents = getLayoutInflater().inflate(R.layout.infowindow_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            int badge = R.drawable.info;
            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);

            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null) {
                snippet = snippet.split(",")[0];
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, snippetText.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        id = new IdGenerator(this);
        id.getId();
        saveButton = (Button) findViewById(R.id.save_button);
        saveRow = (TableRow) findViewById(R.id.save_row);
        saveRow.setVisibility(View.GONE);

        tp = (TimePicker) findViewById(R.id.timePicker00);
        tp.setIs24HourView(true);
        tp2 = (TimePicker) findViewById(R.id.timePicker01);
        tp2.setIs24HourView(true);
        tp3 = (TimePicker) findViewById(R.id.timePicker10);
        tp3.setIs24HourView(true);
        tp4 = (TimePicker) findViewById(R.id.timePicker11);
        tp4.setIs24HourView(true);
        tp5 = (TimePicker) findViewById(R.id.timePicker20);
        tp5.setIs24HourView(true);
        tp6 = (TimePicker) findViewById(R.id.timePicker21);
        tp6.setIs24HourView(true);
        tp7 = (TimePicker) findViewById(R.id.timePicker30);
        tp7.setIs24HourView(true);
        tp8 = (TimePicker) findViewById(R.id.timePicker31);
        tp8.setIs24HourView(true);
        tp9 = (TimePicker) findViewById(R.id.timePicker40);
        tp9.setIs24HourView(true);
        tp10 = (TimePicker) findViewById(R.id.timePicker41);
        tp10.setIs24HourView(true);
        tp11 = (TimePicker) findViewById(R.id.timePicker50);
        tp11.setIs24HourView(true);
        tp12 = (TimePicker) findViewById(R.id.timePicker51);
        tp12.setIs24HourView(true);
        tp13 = (TimePicker) findViewById(R.id.timePicker60);
        tp13.setIs24HourView(true);
        tp14 = (TimePicker) findViewById(R.id.timePicker61);
        tp14.setIs24HourView(true);

        times = (GridLayout) findViewById(R.id.time1);
        times.setVisibility(View.GONE);
        times2 = (GridLayout) findViewById(R.id.time2);
        times.setVisibility(View.GONE);
        times3 = (GridLayout) findViewById(R.id.time3);
        times3.setVisibility(View.GONE);
        times4 = (GridLayout) findViewById(R.id.time4);
        times4.setVisibility(View.GONE);
        times5 = (GridLayout) findViewById(R.id.time5);
        times5.setVisibility(View.GONE);
        times6 = (GridLayout) findViewById(R.id.time6);
        times6.setVisibility(View.GONE);
        times7 = (GridLayout) findViewById(R.id.time7);
        times7.setVisibility(View.GONE);

        sw = (Switch) findViewById(R.id.switch0);
        sw2 = (Switch) findViewById(R.id.switch1);
        sw3 = (Switch) findViewById(R.id.switch2);
        sw4 = (Switch) findViewById(R.id.switch3);
        sw5 = (Switch) findViewById(R.id.switch4);
        sw6 = (Switch) findViewById(R.id.switch5);
        sw7 = (Switch) findViewById(R.id.switch6);

        setSwitches();

        mapView = (LinearLayout) findViewById(R.id.map_view);
        recentsView = (TableLayout) findViewById(R.id.recents_view);
        mostViewedView = (LinearLayout) findViewById(R.id.most_viewed_view);
        searchView = (LinearLayout) findViewById(R.id.search_view);
        moreView = (LinearLayout) findViewById(R.id.more_view);
        infoView = (TableLayout) findViewById(R.id.info_view);
        newView = (TableLayout) findViewById(R.id.new_view);

        mapView.setVisibility(View.VISIBLE);
        mostViewedView.setVisibility(View.GONE);
        recentsView.setVisibility(View.GONE);
        searchView.setVisibility(View.GONE);
        moreView.setVisibility(View.GONE);
        infoView.setVisibility(View.GONE);
        newView.setVisibility(View.GONE);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_toilets:
                                mapView.setVisibility(View.VISIBLE);
                                mostViewedView.setVisibility(View.GONE);
                                recentsView.setVisibility(View.GONE);
                                searchView.setVisibility(View.GONE);
                                moreView.setVisibility(View.GONE);
                                infoView.setVisibility(View.GONE);
                                newView.setVisibility(View.GONE);
                                break;
                            case R.id.action_recents:
                                mapView.setVisibility(View.GONE);
                                mostViewedView.setVisibility(View.VISIBLE);
                                recentsView.setVisibility(View.GONE);
                                searchView.setVisibility(View.GONE);
                                moreView.setVisibility(View.GONE);
                                infoView.setVisibility(View.GONE);
                                newView.setVisibility(View.GONE);
                                break;
                            case R.id.action_most_viewed:
                                mapView.setVisibility(View.GONE);
                                mostViewedView.setVisibility(View.GONE);
                                recentsView.setVisibility(View.VISIBLE);
                                searchView.setVisibility(View.GONE);
                                moreView.setVisibility(View.GONE);
                                infoView.setVisibility(View.GONE);
                                newView.setVisibility(View.GONE);
                                break;
                            case R.id.action_search:
                                mapView.setVisibility(View.GONE);
                                mostViewedView.setVisibility(View.GONE);
                                recentsView.setVisibility(View.GONE);
                                searchView.setVisibility(View.VISIBLE);
                                moreView.setVisibility(View.GONE);
                                infoView.setVisibility(View.GONE);
                                newView.setVisibility(View.GONE);
                                break;
                            case R.id.action_more:
                                mapView.setVisibility(View.GONE);
                                mostViewedView.setVisibility(View.GONE);
                                recentsView.setVisibility(View.GONE);
                                searchView.setVisibility(View.GONE);
                                moreView.setVisibility(View.VISIBLE);
                                infoView.setVisibility(View.GONE);
                                newView.setVisibility(View.GONE);
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //  mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        mMap.setMyLocationEnabled(true);*/
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        LatLng budapest = new LatLng(47.497912, 19.040235);
        update();

        mMap.setInfoWindowAdapter(new InfoWindow());

        mMap.moveCamera(CameraUpdateFactory.newLatLng(budapest));

        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMapLongClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

    }

    private void setSwitches(){
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    times.setVisibility(View.VISIBLE);
                } else {
                    times.setVisibility(View.GONE);
                }
            }
        });
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    times2.setVisibility(View.VISIBLE);
                } else {
                    times2.setVisibility(View.GONE);
                }
            }
        });
        sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    times3.setVisibility(View.VISIBLE);
                } else {
                    times3.setVisibility(View.GONE);
                }
            }
        });
        sw4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    times4.setVisibility(View.VISIBLE);
                } else {
                    times4.setVisibility(View.GONE);
                }
            }
        });
        sw5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    times5.setVisibility(View.VISIBLE);
                } else {
                    times5.setVisibility(View.GONE);
                }
            }
        });
        sw6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    times6.setVisibility(View.VISIBLE);
                } else {
                    times6.setVisibility(View.GONE);
                }
            }
        });
        sw7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    times7.setVisibility(View.VISIBLE);
                } else {
                    times7.setVisibility(View.GONE);
                }
            }
        });
    }

    private void addMarker(double lat, double lon, String title, String open, double rating, int id){
        LatLng latlng = new LatLng(lat, lon);
        float color = open.equals("OPEN") ? BitmapDescriptorFactory.HUE_AZURE : BitmapDescriptorFactory.HUE_RED;
        mMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title(title)
                .snippet(open + "," + Double.toString(rating) + "," + Integer.toString(id))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cleanshinytoilet))
                .infoWindowAnchor(0.5f, 0.5f));
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    public JSONArray JsonParse(){
        /*String line = "[ { \"id\": 1,\"name\" : \"Elte Déli Tömb\", \"location\": { \"latitude\": 47.472348, \"longitude\": 19.062353 }, " +
                "\"rating\": 1.1, \"status\": \"OPEN\", \"ratings\": [] },  { \"id\": 2, \"name\" : \"Corvinus\", \"location\": { \"latitude\": 47.486157, \"longitude\": 19.057975 }, " +
                "\"rating\": 1.1, \"status\": \"CLOSED\", \"ratings\": [] } ]";*/
        String line = toiletDataManager.getResult();
        JSONArray json = null;
        try {
            json = new JSONArray(line);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String title = marker.getTitle();
        TextView titleUi = ((TextView) infoView.findViewById(R.id.toilet_name_id));
        if (title != null) {
            titleUi.setText(title);
        } else {
            titleUi.setText("");
        }

        String snippet = marker.getSnippet();
        TextView status = ((TextView) infoView.findViewById(R.id.toilet_status_id));
        TextView rating = ((TextView) infoView.findViewById(R.id.toilet_rating_id));
        if (snippet != null) {
            status.setText(snippet.split(",")[0]);
            rating.setText(snippet.split(",")[1]);
        } else {
            status.setText("");
            rating.setText("");
        }

        mapView.setVisibility(View.GONE);
        mostViewedView.setVisibility(View.GONE);
        recentsView.setVisibility(View.GONE);
        searchView.setVisibility(View.GONE);
        moreView.setVisibility(View.GONE);
        infoView.setVisibility(View.VISIBLE);
        newView.setVisibility(View.GONE);
    }

    private void update(){
        for (int i = 0; i < this.JsonParse().length(); ++i) {
            try {
                JSONObject obj = this.JsonParse().getJSONObject(i);
                JSONObject latln = obj.getJSONObject("location");
                this.addMarker(latln.getDouble("latitude"), latln.getDouble("longitude"),
                        obj.getString("name"), obj.getString("status"), obj.getDouble("rating"), obj.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        Log.d("longclick %s", latLng.toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New toilet's name");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            final LatLng latln = latLng;
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameText = input.getText().toString();
                TextView name = ((TextView) infoView.findViewById(R.id.toilet_name_id));
                name.setText(nameText);
                TextView status = ((TextView) infoView.findViewById(R.id.toilet_status_id));
                TextView rating = ((TextView) infoView.findViewById(R.id.toilet_rating_id));
                status.setText("Unknown");
                rating.setText("0.0");

                saveRow.setVisibility(View.VISIBLE);
                mapView.setVisibility(View.GONE);
                mostViewedView.setVisibility(View.GONE);
                recentsView.setVisibility(View.GONE);
                searchView.setVisibility(View.GONE);
                moreView.setVisibility(View.GONE);
                infoView.setVisibility(View.VISIBLE);
                newView.setVisibility(View.GONE);
                saveButton.setOnClickListener(new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        String result = toiletDataManager.newPostAsync(nameText, Double.toString(latln.latitude), Double.toString(latln.longitude), getOpenClose().toString());
                        Log.i("result", result);
                        toiletDataManager = new ToiletDataManager();
                        update();
                        saveRow.setVisibility(View.GONE);
                        mapView.setVisibility(View.VISIBLE);
                        infoView.setVisibility(View.GONE);
                        //addMarker(latln.latitude, latln.longitude, nameText, "Unknown", 0.0, 0);
                    }
                } );
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private StringBuffer getOpenClose(){
        StringBuffer days = new StringBuffer();
        if(sw.isChecked()){
            days.append("Monday,").append(tp.getHour()).append(",").append(tp.getMinute()).append(",");
            days.append(tp2.getHour()).append(",").append(tp2.getMinute()).append(";");
        }
        if(sw2.isChecked()){
            days.append("Tuesday,").append(tp3.getHour()).append(",").append(tp3.getMinute()).append(",");
            days.append(tp4.getHour()).append(",").append(tp4.getMinute()).append(";");
        }
        if(sw3.isChecked()){
            days.append("Wednesday,").append(tp5.getHour()).append(",").append(tp5.getMinute()).append(",");
            days.append(tp6.getHour()).append(",").append(tp6.getMinute()).append(";");
        }
        if(sw4.isChecked()){
            days.append("Thursday,").append(tp7.getHour()).append(",").append(tp7.getMinute()).append(",");
            days.append(tp8.getHour()).append(",").append(tp8.getMinute()).append(";");
        }
        if(sw5.isChecked()){
            days.append("Friday,").append(tp9.getHour()).append(",").append(tp9.getMinute()).append(",");
            days.append(tp10.getHour()).append(",").append(tp10.getMinute()).append(";");
        }
        if(sw6.isChecked()){
            days.append("Saturday,").append(tp11.getHour()).append(",").append(tp11.getMinute()).append(",");
            days.append(tp12.getHour()).append(",").append(tp12.getMinute()).append(";");
        }
        if(sw7.isChecked()){
            days.append("Sunday,").append(tp13.getHour()).append(":").append(tp13.getMinute()).append(",");
            days.append(tp14.getHour()).append(":").append(tp14.getMinute());
        }
        return days;
    }
}