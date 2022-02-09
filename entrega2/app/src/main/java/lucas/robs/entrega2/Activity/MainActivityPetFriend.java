package lucas.robs.entrega2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import lucas.robs.entrega2.InfoUser;
import lucas.robs.entrega2.MapHelper;
import lucas.robs.entrega2.MarkerInfoAdapter;
import lucas.robs.entrega2.OpenBooking;
import lucas.robs.entrega2.R;

public class MainActivityPetFriend extends AppCompatActivity implements OnMapReadyCallback {
    public static final String ID_PET_OWNER = "INTENT_ID_PET_OWNER";
    public static final String ID_PET_FRIEND = "INTENT_ID_PET_FRIEND";
    private FusedLocationProviderClient fusedLocationClient;

    private Intent intent;
    private GoogleMap mMap;
    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pet_friend);
        setMap();
        setListMenssages();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MapHelper mapHelper = new MapHelper();
        googleMap.setInfoWindowAdapter(new MarkerInfoAdapter(this));
        // Add a marker in Sydney and move the camera
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        Log.i("fire", "aaaaaaaaa");

                        if (location != null) {
                            LatLng youLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            //mMap.moveCamera(CameraUpdateFactory.newLatLng(youLocation));

                            LatLng sydney = new LatLng(location.getLatitude(),location.getLongitude());
                            LatLng sydney2 = new LatLng(location.getLatitude()+1, +1);
                            mMap.addMarker(new MarkerOptions()
                                    .position(sydney)
                                    .title("Marker in Sydney"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(youLocation, 17F));

                            Log.i("fire", location.toString());
                        }
                    }
                });
    }

    public void setMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
    }

    public void pushTour(View view) {
        startActivity(intent);
    }

    public void handleService(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/openForBooking").child(user.getUid());
        Gson gson = new Gson();
        mDatabase.setValue(gson.toJson(new OpenBooking(user.getUid())));
    }

    public void setListMenssages() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        adapter = new GroupAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/openForBooking").child(user.getUid());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Gson gson = new Gson();
                if (snapshot.getValue() != null) {
                    OpenBooking bookingss = gson.fromJson(snapshot.getValue().toString(), OpenBooking.class);
                    if(bookingss.isHasRequestBooking()) {
                        adapter.add(new MainActivityPetFriend.BookingItem(bookingss));
                    }
                    for (DataSnapshot entity : snapshot.getChildren()) {
                        OpenBooking bookings = gson.fromJson(snapshot.getValue().toString(), OpenBooking.class);
                        if(bookings.isHasRequestBooking()){
                            adapter.add(new MainActivityPetFriend.BookingItem(bookings));
                        }
                    }
                    Log.i("fire","snapshot..toString()");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "Error getting data" + error);
            }
        };
        mDatabase.addValueEventListener(postListener);
    }

    private class BookingItem extends Item<ViewHolder> {
    // continuar ViewHolder
        private final OpenBooking openBooking;

        private BookingItem(OpenBooking openBooking) {
            this.openBooking = openBooking;
        }


        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            openBooking.getIdPetOwner();
            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

            mDatabase.getReference("/users").child(openBooking.getIdPetOwner()).child("infoUser").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    if (task.isSuccessful()) {

                        Gson gson = new Gson();
                        if (task.getResult().getValue() != null) {
                            DataSnapshot snapshot = task.getResult();
                            InfoUser infoUser = gson.fromJson(snapshot.getValue().toString(), InfoUser.class);
                            TextView txt = viewHolder.itemView.findViewById(R.id.textView7);
                            txt.setText(infoUser.getName());
                            intent = new Intent(viewHolder.itemView.getContext(), Tour.class);
                            intent.putExtra(ID_PET_OWNER, openBooking.getIdPetOwner());
                            intent.putExtra(ID_PET_FRIEND, openBooking.getIdFriendPet());
                        }
                    } else {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                }
            });

        }

        @Override
        public int getLayout() {
            return R.layout.item_booking_request;
        }
    }
    public String getIntentUserSelect(){
        return getIntent().getStringExtra(MainActivityPetFriend.ID_PET_OWNER);
    }
}