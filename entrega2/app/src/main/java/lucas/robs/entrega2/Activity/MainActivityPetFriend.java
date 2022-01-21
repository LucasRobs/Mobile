package lucas.robs.entrega2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import lucas.robs.entrega2.MapHelper;
import lucas.robs.entrega2.MarkerInfoAdapter;
import lucas.robs.entrega2.R;
import lucas.robs.entrega2.Walker;

public class MainActivityPetFriend extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pet_friend);
        setMap();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        MapHelper mapHelper = new MapHelper();
        googleMap.setInfoWindowAdapter(new MarkerInfoAdapter(this));
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng sydney2 = new LatLng(-35, 152);

        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(sydney2)
                .title("Marker in Sydney")
                .icon(
                        mapHelper.vectorToBitmap(this, R.drawable.ic_walker, ContextCompat.getColor(this, R.color.black)))
        );
        Walker walker = new Walker(1234567890, "Dirlandia");
        marker.setTag(walker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void setMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
    }

    public void pushTour(View view) {
        Intent intent = new Intent(this, Tour.class);
        startActivity(intent);
    }

}