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
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import lucas.robs.entrega2.InfoUser;
import lucas.robs.entrega2.MapHelper;
import lucas.robs.entrega2.MarkerInfoAdapter;
import lucas.robs.entrega2.Message;
import lucas.robs.entrega2.OpenBooking;
import lucas.robs.entrega2.R;
import lucas.robs.entrega2.Walker;

public class MainActivityPetFriend extends AppCompatActivity implements OnMapReadyCallback {
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
        LatLng sydney = new LatLng(-34, 151);
        LatLng sydney2 = new LatLng(-35, 152);

        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));

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
}