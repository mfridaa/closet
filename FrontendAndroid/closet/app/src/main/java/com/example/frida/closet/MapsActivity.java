package com.example.frida.closet;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMarkerClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    //private ToiletDataManager toiletDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //toiletDataManager = new ToiletDataManager();
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
        // Add a marker in Sydney and move the camera
        LatLng budapest = new LatLng(47.497912, 19.040235);
        Marker bp = mMap.addMarker(new MarkerOptions()
                .position(budapest)
                .title("Budapest")
                .snippet("")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        for(int i = 0; i < this.JsonParse().length(); ++i){
            try {
                JSONObject obj = this.JsonParse().getJSONObject(i);
                JSONObject latln = obj.getJSONObject("latitudeAndLongitude");
                this.addMarker(latln.getDouble("latitude"), latln.getDouble("longitude"),
                        obj.getString("name"), obj.getString("status"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(budapest));

        mMap.setOnMarkerClickListener(this);

    }

    private void addMarker(double lat, double lon, String title, String open){
        LatLng latlng = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title(title)
                .snippet(open)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast toast = Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
            toast.show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
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
        String line = "[ { \"name\" : \"Elte Déli Tömb\", \"latitudeAndLongitude\": { \"latitude\": 47.472348, \"longitude\": 19.062353 }, " +
                "\"rating\": 1.1, \"status\": \"OPEN\", \"ratings\": [] },  { \"name\" : \"Corvinus\", \"latitudeAndLongitude\": { \"latitude\": 47.486157, \"longitude\": 19.057975 }, " +
                "\"rating\": 1.1, \"status\": \"OPEN\", \"ratings\": [] } ]";
        JSONArray json = null;
        try {
            json = new JSONArray(line);
            line = Integer.toString(json.length());
            //line = json.get(1).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
