package lucas.robs.entrega2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

import lucas.robs.entrega2.MapHelper;
import lucas.robs.entrega2.MarkerInfoAdapter;
import lucas.robs.entrega2.OpenBooking;
import lucas.robs.entrega2.Pet;
import lucas.robs.entrega2.PetAdapter;
import lucas.robs.entrega2.R;
import lucas.robs.entrega2.Walker;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String PETS = "INTENT_PETS";
    public static final String PETS_SELECT = "INTENT_PETS_SELECT";
    public static final String CHAT_SELECT = "INTENT_CHAT_SELECT";


    ArrayList<Pet> pets = new ArrayList<>();
    private PetAdapter petAdapter;
    private GoogleMap mMap;
    private Boolean petsWereLoaded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setList();
        setMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setList();
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
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void setList() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.child(user.getUid()).child("pets").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful() ) {
                    Gson gson = new Gson();
                    if (task.getResult().getValue() != null && !petsWereLoaded) {
                        petsWereLoaded = true;
                        DataSnapshot snapshot = task.getResult();
                        for (DataSnapshot entity : snapshot.getChildren()) {
                            Pet pet = gson.fromJson(entity.getValue().toString(), Pet.class);
                            pet.setId(entity.getKey());
                            pets.add(pet);
                        }
                        petAdapter = new PetAdapter(pets);
                        recyclerView.setAdapter(petAdapter);
                        //Log.i("firebase", pets.toString());
                    }
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                }
            }
        });

    }

    public void pushAddPet(View view) {
        Intent intent = new Intent(this, AddPet.class);
        intent.putExtra(MainActivity.PETS, pets);
        startActivity(intent);
    }

    public void pushTour(View view) {
        Intent intent = new Intent(this, Tour.class);
        startActivity(intent);
    }

    public void findBoking(View view) {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mDatabase.getReference("/openForBooking").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Gson gson = new Gson();
                    if (task.getResult().getValue() != null) {
                        DataSnapshot snapshot = task.getResult();
                        for (DataSnapshot entity : snapshot.getChildren()) {

                            OpenBooking openBooking = gson.fromJson(entity.getValue().toString(), OpenBooking.class);
                            Log.i("fire", openBooking.toString());

                            if(!openBooking.hasRequestBooking){
                                updateStatusBookin(true,openBooking );
                                break;
                            }
                        }
                    }
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                }
            }
        });
    }
    public void updateStatusBookin(boolean statusBooking, OpenBooking openBooking){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Gson gson = new Gson();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/openForBooking").child(openBooking.idFriendPet);
        openBooking.setIdPetOwner(user.getUid());
        openBooking.setHasRequestBooking(statusBooking);
        mDatabase.setValue(gson.toJson(openBooking));
    }
}