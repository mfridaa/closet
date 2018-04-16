package com.example.frida.closet;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
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
        OnMapReadyCallback {

    private IdGenerator id;
    private GoogleMap mMap;

    private LinearLayout mapView;
    private TableLayout recentsView;
    private LinearLayout mostViewedView;
    private LinearLayout searchView;
    private LinearLayout moreView;
    private TableLayout infoView;

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

        mapView = (LinearLayout) findViewById(R.id.map_view);
        recentsView = (TableLayout) findViewById(R.id.recents_view);
        mostViewedView = (LinearLayout) findViewById(R.id.most_viewed_view);
        searchView = (LinearLayout) findViewById(R.id.search_view);
        moreView = (LinearLayout) findViewById(R.id.more_view);
        infoView = (TableLayout) findViewById(R.id.info_view);

        mapView.setVisibility(View.VISIBLE);
        mostViewedView.setVisibility(View.GONE);
        recentsView.setVisibility(View.GONE);
        searchView.setVisibility(View.GONE);
        moreView.setVisibility(View.GONE);
        infoView.setVisibility(View.GONE);

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
                                break;
                            case R.id.action_recents:
                                mapView.setVisibility(View.GONE);
                                mostViewedView.setVisibility(View.VISIBLE);
                                recentsView.setVisibility(View.GONE);
                                searchView.setVisibility(View.GONE);
                                moreView.setVisibility(View.GONE);
                                infoView.setVisibility(View.GONE);
                                break;
                            case R.id.action_most_viewed:
                                mapView.setVisibility(View.GONE);
                                mostViewedView.setVisibility(View.GONE);
                                recentsView.setVisibility(View.VISIBLE);
                                searchView.setVisibility(View.GONE);
                                moreView.setVisibility(View.GONE);
                                infoView.setVisibility(View.GONE);
                                break;
                            case R.id.action_search:
                                mapView.setVisibility(View.GONE);
                                mostViewedView.setVisibility(View.GONE);
                                recentsView.setVisibility(View.GONE);
                                searchView.setVisibility(View.VISIBLE);
                                moreView.setVisibility(View.GONE);
                                infoView.setVisibility(View.GONE);
                                break;
                            case R.id.action_more:
                                mapView.setVisibility(View.GONE);
                                mostViewedView.setVisibility(View.GONE);
                                recentsView.setVisibility(View.GONE);
                                searchView.setVisibility(View.GONE);
                                moreView.setVisibility(View.VISIBLE);
                                infoView.setVisibility(View.GONE);
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

        // Add a marker in Sydney and move the camera
        LatLng budapest = new LatLng(47.497912, 19.040235);
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

        mMap.setInfoWindowAdapter(new InfoWindow());

        mMap.moveCamera(CameraUpdateFactory.newLatLng(budapest));

        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

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
        /*String line = "[ { \"name\" : \"Elte Déli Tömb\", \"location\": { \"latitude\": 47.472348, \"longitude\": 19.062353 }, " +
                "\"rating\": 1.1, \"status\": \"OPEN\", \"ratings\": [] },  { \"name\" : \"Corvinus\", \"location\": { \"latitude\": 47.486157, \"longitude\": 19.057975 }, " +
                "\"rating\": 1.1, \"status\": \"CLOSED\", \"ratings\": [] } ]";*/
        String line = toiletDataManager.getResult();
        JSONArray json = null;
        try {
            json = new JSONArray(line);
            line = Integer.toString(json.length());
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
    }
}